package com.kolos.bookstore;

import com.kolos.bookstore.web.filter.AuthorizationRoleFilter;
import com.kolos.bookstore.web.interceptor.AuthenticationInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

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
        registry.addInterceptor(localeChangeInterceptor())
                .addPathPatterns("/**");
    }

    @Bean
    public FilterRegistrationBean<AuthorizationRoleFilter> filterRegistrationBean() {
        FilterRegistrationBean<AuthorizationRoleFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuthorizationRoleFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setLocaleAttributeName("locale");
        localeResolver.setDefaultLocale(Locale.US);
        return localeResolver;
    }
}
