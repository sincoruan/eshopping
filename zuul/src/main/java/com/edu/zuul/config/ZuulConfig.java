package com.edu.zuul.config;

import com.edu.zuul.filter.MyFilter1;
import com.edu.zuul.filter.MyFilter2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZuulConfig {

    @Bean
    public MyFilter2 preFilter() {
        return new MyFilter2();
    }
}
