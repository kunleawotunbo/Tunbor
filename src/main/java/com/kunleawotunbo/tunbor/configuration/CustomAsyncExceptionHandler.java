/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kunleawotunbo.tunbor.configuration;

import java.lang.reflect.Method;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

// http://www.baeldung.com/spring-async
/**
 *
 * @author OLAKUNLE
 */
public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
 
    /**
     * Custom Async exception handler for void methods.
     * @param throwable
     * @param method
     * @param obj 
     */
    @Override
    public void handleUncaughtException(
      Throwable throwable, Method method, Object... obj) {
  
        System.out.println("Exception message - " + throwable.getMessage());
        System.out.println("Method name - " + method.getName());
        for (Object param : obj) {
            System.out.println("Parameter value - " + param);
        }
    }
     
}
