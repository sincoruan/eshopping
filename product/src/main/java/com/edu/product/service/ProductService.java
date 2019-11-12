package com.edu.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductService {
    @Autowired
    RestTemplate restTemplate;

    @Value("${router.account.url}")
    String accountUrl;

    public String getAccountById(int id){
        String url = accountUrl+"/getAccount/{id}";
        String info = restTemplate.getForObject(url, String.class, id);
        return info;
    }
}
