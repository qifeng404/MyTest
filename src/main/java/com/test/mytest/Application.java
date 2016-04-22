package com.test.mytest;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;



/**
 * 
* @ClassName: Application 
* @Description: TODO 描述
* @author qifeng
* @date 2016年4月18日 下午3:37:36 
*
 */
@Controller
@EnableWebMvc
@SpringBootApplication
public class Application  extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    
    @RequestMapping("/")
    String home() {
        return "redirect:/checkpreload.htm";
    }
    
}