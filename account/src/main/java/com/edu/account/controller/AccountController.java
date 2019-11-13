package com.edu.account.controller;

import com.edu.account.domain.Address;
import com.edu.account.domain.Payment;
import com.edu.account.repository.AddressRepository;
import com.edu.account.repository.PaymentRepository;
import com.edu.account.vo.ResultVO;
import com.edu.account.domain.User;
import com.edu.account.repository.UserRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class AccountController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    PaymentRepository paymentRepository;

    @Value("${router.auth.url}")
    private String authUrl;

    @Value("${api.key}")
    private String apiKey;

    @Value("${router.paypalpay.url}")
    private String paypalUrl;

    @Value("${router.bankpay.url}")
    private String bankUrl;

    private Gson gson = new Gson();

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/")
    public String test(){
        return "test";
    }
    @GetMapping("/getAccount")
    public User getAccount(@RequestHeader("userId") long userid){
        User user = userRepository.findUserById(userid);
        return user;
    }
    @PostMapping("/register")
    public User addUser( @RequestBody User user){
        user = userRepository.save(user);
        return user;
    }
    @PostMapping("/login")
    public ResultVO<String> validateUser(@RequestBody User user){
        User result = userRepository.findUserByEmailAndPassword(user.getEmail(),user.getPassword());
        ResultVO<String> resultVO =  new ResultVO<String>();

        if(result==null){
            resultVO.setMsg("email or password error!");
            resultVO.setCode(-1);
        }else{
            resultVO.setCode(0);

            ResponseEntity<String> responseEntity = request(authUrl+"/create_token",gson.toJson(result));
            resultVO.setData(responseEntity.getBody());
        }
        return resultVO;
    }

    @GetMapping("/getUserById")
    public User getUserById(@RequestHeader("userId") long userid){
        User user = userRepository.findById(Long.valueOf(userid)).get();
        return user;
    }

    @GetMapping("/getPaymentListByUserid/{id}")
    public List<Payment> getPaymentListByUserid(@RequestHeader("userId") long userid){
        List<Payment> paymentList = paymentRepository.getPaymentsByUserId(userid);
        return paymentList;
    }
    @PostMapping("/addAddressByUser/{id}")
    public Address addAddress( @RequestHeader("userId") long userid,@RequestBody Address address){
        address = addressRepository.save(address);
        User user = userRepository.findUserById(userid);
        user.setShippingAddress(address);
        userRepository.save(user);
        return address;
    }
    @PostMapping("/addPaymentByUser/{id}")
    public Payment addPayment( @RequestHeader("userId") long userid,@RequestBody Payment payment){
        User user = userRepository.findUserById(userid);
        payment.setPayUser(user);
        paymentRepository.save(payment);
        //userRepository.save(user);
        return payment;
    }

    @PostMapping("/pay/{type}/{amount}")
    public String payment(@PathVariable("type") String type,@PathVariable double amount) {
        ResultVO<String> resultVO = new ResultVO<String>();
        System.out.println(type);
        String paymentUrl = "";
        if (type.equals("paypalpay")) {
            paymentUrl = paypalUrl;
        } else {
            paymentUrl = bankUrl;
        }

        ResponseEntity<String> responseEntity =request(paymentUrl ,"{}");
        return responseEntity.getBody();
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
