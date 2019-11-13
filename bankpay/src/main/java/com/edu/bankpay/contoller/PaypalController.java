package com.edu.bankpay.contoller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaypalController {

    @RequestMapping("/")
    public String pay() {
        return "bankpay success";
    }
}
