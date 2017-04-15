package com.maplinking;

import com.maplinking.endpoint.LoggingInterceptor;
import com.maplinking.endpoint.Validator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Validator getValidator() {
        return Validator.getInstance();
    }

    @Bean
    public LoggingInterceptor getLoggingInterceptor() {
        return new LoggingInterceptor();
    }

}
