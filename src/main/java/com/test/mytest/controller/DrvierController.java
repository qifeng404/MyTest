package com.test.mytest.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.test.mytest.model.Drvier;

@RestController
public class DrvierController {

	private static final String template = "Drvier, %s";  
    private final AtomicLong counter = new AtomicLong();  
  
    @RequestMapping("/drvier")  
    public Drvier drvier(@RequestParam(value = "name", defaultValue = "world") String name) {  
    	Drvier drvier = new Drvier(counter.incrementAndGet(), String.format(template, name));  
        return drvier;  
    }  
      
	@RequestMapping("/drvier/show")  
    public Drvier show(){  
        RestTemplate template = new RestTemplate();  
        Drvier drvier = template.getForObject("http://localhost:7002/drvier?name=323", Drvier.class);  
        System.err.println(drvier);  
        return drvier;  
    }  


}
