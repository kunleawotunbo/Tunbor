/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kunleawotunbo.tunbor.configuration;

import static com.google.common.collect.Lists.newArrayList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author olakunle
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {                                    
   // http://www.baeldung.com/swagger-2-documentation-for-spring-rest-api
    // access to swagger 
    // http://localhost:8084/Tunbor/swagger-ui.html
    /*
      @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.regex("/api/.*"))
            .build()
            .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("TITLE")
            .description("DESCRIPTION")
            .version("VERSION")
            .termsOfServiceUrl("http://terms-of-services.url")
            .license("LICENSE")
            .licenseUrl("http://url-to-license.com")
            .build();
    }
    */
    
    /*
     @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.any())                          
          .build();                                           
    }
    */
    
    
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.kunleawotunbo.tunbor.controller"))
                //.paths(PathSelectors.ant("/test/*"))
                .paths(PathSelectors.any())
                .build();
    }
  
    /*
        @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select().
                apis(RequestHandlerSelectors.basePackage("com.kunleawotunbo.tunbor.controller"))
                //.paths(PathSelectors.ant("/test/*"))
                .paths(PathSelectors.ant("/*"))
                .build().apiInfo(apiInfo()).useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, newArrayList(new ResponseMessageBuilder()
                        .code(500).message("500 message").
                        responseModel(new ModelRef("Error")).build(), new ResponseMessageBuilder()
                                .code(403).message("Forbidden!!!!!").build()));
    }
    */
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
            .title("Tunbor APP")
            .description("This is a self built app for personal training")
            .version("0.0.1")
            .termsOfServiceUrl("http://terms-of-services.url")
            .license("Apache 2.0")
            .licenseUrl("http://url-to-license.com")
            .build();
    }
    
    
}