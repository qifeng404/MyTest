package com.test.mytest.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.test.mytest.model.Greeting;

@RestController 
public class GreetingController {
	private static final String template = "Hello, %s";  
    private final AtomicLong counter = new AtomicLong();  
  
    @RequestMapping("/greeting")  
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "world") String name) {  
        Greeting greeting = new Greeting(counter.incrementAndGet(), String.format(template, name));  
        return greeting;  
    }  
      
	@RequestMapping("/greeting/show")  
    public Greeting show(){  
        RestTemplate template = new RestTemplate();  
        Greeting greeting = template.getForObject("http://localhost:7002/greeting?name=323", Greeting.class);  
        System.err.println(greeting);  
        return greeting;  
    }  
}
