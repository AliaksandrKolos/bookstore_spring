package com.kolos.bookstore.web.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(HttpSession session) {
        if (session.getAttribute("lang") == null) {
            session.setAttribute("lang", "en");
        }
        return "home";
    }


    @GetMapping("/changeLanguage")
    public String changeLanguage(@RequestParam(required = false) String lang, HttpSession session) {
        List<String> allowedLanguages = Arrays.asList("en", "ru", "de");
        if (lang == null || !allowedLanguages.contains(lang)) {
            lang = "en";
        }
        session.setAttribute("lang", lang);
        return "redirect:/";
    }


    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }


}
