package com.kolos.bookstore.web.view;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/changeLanguage")
    public String changeLanguage(@RequestParam String lang, HttpSession session) {
        session.setAttribute("lang", lang);
        return "redirect:/";
    }

    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }


}
