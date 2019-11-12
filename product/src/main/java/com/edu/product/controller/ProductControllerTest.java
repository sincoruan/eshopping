package com.edu.product.controller;


import com.edu.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductControllerTest {
    @Autowired
    ProductService productService;
    @RequestMapping("/test")
    public String getController(){
        return productService.getAccountById(1);
    }

}
