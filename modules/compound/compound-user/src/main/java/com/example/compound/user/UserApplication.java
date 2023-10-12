package com.example.compound.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.example.*")
@SpringBootApplication
public class UserApplication {
	private static ApplicationContext applicationContext;
	
    public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

	public static void displayAllBeans() {
        String[] allBeanNames = applicationContext.getBeanDefinitionNames();
        for(String beanName : allBeanNames) {
            System.out.println(beanName);
        }
    }
}
