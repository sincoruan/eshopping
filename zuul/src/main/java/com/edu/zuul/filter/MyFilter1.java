package com.edu.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

//@Component
public class MyFilter1 extends ZuulFilter {

    /**
     * 类型包含 pre post route error
     * pre 代表在路由代理之前执行
     * route 代表代理的时候执行
     * error 代表出现错的时候执行
     * post 代表在route 或者是 error 执行完成后执行
     */
    @Override
    public String filterType() {
        // 路由之前(前置过滤器)
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        // 优先级，数字越大，优先级越低
        return 11;
    }

    @Override
    public boolean shouldFilter() {
        // 是否执行该过滤器，true代表需要过滤
        return true;
    }

    @Override
    public Object run() {
        System.out.println("MyFilter1 run");
        return null;
    }
}
