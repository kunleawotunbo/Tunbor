package com.kunleawotunbo.tunbor.utility;

import com.kunleawotunbo.tunbor.configuration.AppConfig;
import com.kunleawotunbo.tunbor.model.User;
import com.kunleawotunbo.tunbor.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;



public class SampleEmailApplication {
       // @Autowired
	//MailService mailService;
        
	public static void main(String[] args) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
                
                /*
		UserService orderService = (OrderService) context.getBean("orderService");
		orderService.sendOrderConfirmation(getDummyOrder());
                */
                MailService mailService = (MailService) context.getBean("mailService");
                
                User user = new User();
                
                user.setEmail("kunleawotunbo@gmail.com");
                user.setFirstName("Olakunle");
                user.setLastName("Awotunbo");
                
                mailService.sendMail(user);
		((AbstractApplicationContext) context).close();
	}
	
	
}
