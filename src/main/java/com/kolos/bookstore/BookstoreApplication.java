package com.kolos.bookstore;

import com.kolos.bookstore.web.filter.AuthorizationRoleFilter;
import com.kolos.bookstore.web.interceptor.AuthenticationInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@RequiredArgsConstructor
public class BookstoreApplication implements WebMvcConfigurer {

    private final AuthenticationInterceptor authenticationInterceptor;

    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class);
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/books/getAll", "/books/{id:\\d+}", "/login",
                        "/users/registration", "/cart", "/books/addToCart",
                        "/changeLanguage", "/books/search_title");
    }

    @Bean
    public FilterRegistrationBean<AuthorizationRoleFilter> filterRegistrationBean() {
        FilterRegistrationBean<AuthorizationRoleFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuthorizationRoleFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
