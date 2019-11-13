package com.edu.paypalpay.contoller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaypalController {

    @RequestMapping("/")
    public String pay() {
        return "paypalpay success";
    }
}
