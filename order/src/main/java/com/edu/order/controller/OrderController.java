package com.edu.order.controller;

import com.edu.order.domain.Order;
import com.edu.order.domain.Product;
import com.edu.order.domain.User;
import com.edu.order.domain.UserOrderDetail;
import com.edu.order.repository.UserOrderDetailRepository;
import com.edu.order.repository.UserOrderRepository;
import com.edu.order.vo.ResultVO;
import com.google.gson.Gson;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RestController
public class OrderController {
    @Autowired
    UserOrderRepository userOrderRepository;
    @Autowired
    UserOrderDetailRepository userOrderDetailRepository;

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    @Value("${router.auth.url}")
    private String authUrl;

    @Value("${api.key}")
    private String apiKey;

    private Gson gson = new Gson();

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/")
    public String test(){
        return "this is order module!";
    }

    @PostMapping("/addProductToOrderDraft")
    public Order addOrder(@RequestHeader("userId") long userid, @RequestBody Product product){
        Order order=null;
        User user=null;
        UserOrderDetail userOrderDetail=null;
        List<Order> orderList =  userOrderRepository.findByUserId(userid);
        if(orderList.size() == 0)
        {
            order = new Order();
            user = new User();
            user.setId(userid);
            order.setBuyer(user);
            order.setUserOrderDetailList(new ArrayList<UserOrderDetail>());
        }
        else{
            order=orderList.get(0);
            userOrderDetail = getUserOrderDetail(order,product);
            if(userOrderDetail==null) {
                userOrderDetail =  new UserOrderDetail();
                userOrderDetail.setProduct(product);
                userOrderDetail.setNumberOfProduct(1l);
            }
            else{
                userOrderDetail.setNumberOfProduct(userOrderDetail.getNumberOfProduct()+1);
            }
        }
        userOrderRepository.save(order);
        return order;
    }

    @GetMapping("/getUserOrderDetail/{orderid}")
    public List<UserOrderDetail> getUserOrderDetailList(@RequestHeader("userId") long userid,@PathVariable long orderid){
        return userOrderDetailRepository.getUserOrderDetailList(orderid);
    }

    @PostMapping("/placeOrder")
    public ResultVO<Order> placeOrder(@RequestHeader("userId") long userid){
        List<Order> orderList =  userOrderRepository.findByUserId(userid);
        ResultVO<Order> resultVO = new ResultVO<Order>();
        if(orderList.size()>0){
            Order order = orderList.get(0);
            userOrderRepository.updateUserOrderStatusById(userid);
            resultVO.setCode(0);
            resultVO.setMsg("place order success!");
        }
        else{
            resultVO.setCode(1);
            resultVO.setMsg("there is no product in the draft order!");
        }
        return resultVO;
    }
    public ResultVO<List<Order>> getOrderList(@RequestHeader("userId") long userid){
        ResultVO<List<Order>> resultVO =  new  ResultVO<>();
        List<Order> orderList = userOrderRepository.findByUserId(userid);
        if(orderList.size()>0){
            resultVO.setCode(0);
            resultVO.setData(orderList);
            resultVO.setMsg("success!");
        }else{
            resultVO.setCode(1);
            resultVO.setMsg("fail!");
        }
        return resultVO;
    }

    public UserOrderDetail getUserOrderDetail(Order order,Product product){
        for(UserOrderDetail userOrderDetail:order.getUserOrderDetailList()){
            if(userOrderDetail.getProduct().equals(product))
                return userOrderDetail;
        }
        return null;
    }

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
