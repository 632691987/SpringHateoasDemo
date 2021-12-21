package com.example.springhateoasdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@SpringBootApplication
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class SpringHateoasDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringHateoasDemoApplication.class, args);
    }

}
