package com.edu.zuul.filter;

import com.edu.zuul.vo.ResultVO;
import com.google.gson.Gson;
import com.netflix.ribbon.RequestTemplate;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

//@Component
public class MyFilter2 extends ZuulFilter {

    /**
     * 类型包含 pre post route error
     * pre 代表在路由代理之前执行
     * route 代表代理的时候执行
     * error 代表出现错的时候执行
     * post 代表在route 或者是 error 执行完成后执行
     */
    @Autowired
    RestTemplate restTemplate;
    private Gson gson = new Gson();

    @Value("${router.auth.url}")
    private String authUrl;

    @Value("${api.key}")
    private String apiKey;

    @Value("#{'${exclude-routes.urls}'.split(',')}")
    private List<String> excludeUrls;

    @Override
    public String filterType() {
        // 路由之前(前置过滤器)
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        // 优先级，数字越大，优先级越低
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        // 是否执行该过滤器，true代表需要过滤
        return true;
    }

    @Override
    public Object run() {
        System.out.println("MyFilter2 run");
        //
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest reque = context.getRequest();

        String url = reque.getRequestURL().toString();
        // add zuul api-key header for  authorization
        context.addZuulRequestHeader("api-key", apiKey);
        System.out.println(apiKey);
        for(String exurl:excludeUrls) {
            if(url.indexOf(exurl)>=0)
                return null;
        }

        String token = reque.getParameter("token");
        if (StringUtils.isEmpty(token)) {
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(401);
            try {
                context.getResponse().getWriter().print("token error");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("token", token);
        String jsonStr = gson.toJson(map);
        //校验令牌是否有效
        ResponseEntity<String> request1 = request(authUrl +"/valid_token", jsonStr);
        ResultVO<String> stringResultVO = gson.fromJson(request1.getBody(), ResultVO.class);
        //context.addZuulRequestHeader("email", );
        String resJsonString = this.gson.toJson(stringResultVO);
        if (stringResultVO.getCode() == 1) {
            output(context, resJsonString);
        }
        if(!stringResultVO.getData().equals("null") && stringResultVO.getData()!=null)
            context.addZuulRequestHeader("userId", stringResultVO.getData());
        return null;
    }
    //
    private void output(RequestContext ctx , String resJsonString) {
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        PrintWriter out = null;
        try {
            out = ctx.getResponse().getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ctx.getResponse().setContentType("application/json");
        ctx.getResponse().setCharacterEncoding("UTF-8");
        out.write(resJsonString);
    }
    //封装发送请求
    private ResponseEntity<String> request(String authUrl, String data) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("api-key", apiKey);
        //HttpEntity
        HttpEntity<String> formEntity = new HttpEntity<>(data, headers);
        return restTemplate.postForEntity(authUrl, formEntity, String.class);
    }
}
