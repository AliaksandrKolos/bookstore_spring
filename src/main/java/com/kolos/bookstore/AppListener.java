package com.kolos.bookstore;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
@WebListener
public class AppListener implements ServletContextListener {

    @Getter
    public static AnnotationConfigApplicationContext context;


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        log.info("Application started");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (context != null) {
            context.close();
            log.info("Application closed");
        }
    }
}
