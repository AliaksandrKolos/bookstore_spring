package com.kolos.bookstore.controller;

import com.kolos.bookstore.AppConfig;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.servlet.annotation.WebListener;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
@WebListener
public class AppListener {

    @Getter
    public static AnnotationConfigApplicationContext context;

    @PostConstruct
    public void init() {
        context =  new AnnotationConfigApplicationContext(AppConfig.class);
        log.info("Application started");
    }


    @PreDestroy
    public void destroy() {
        if(context != null) {
            log.info("Application closed");
            context.close();
        }
    }
}
