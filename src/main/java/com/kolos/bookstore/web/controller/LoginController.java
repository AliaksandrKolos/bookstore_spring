package com.kolos.bookstore.web.controller;

import com.kolos.bookstore.service.UserService;
import com.kolos.bookstore.service.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginFom() {
        return "loginForm";
    }


    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session) {
        UserDto userDto = userService.login(email, password);
        session.setAttribute("user", userDto);
        return "redirect:/";
    }

    @GetMapping("/logOut")
    public String logout(HttpSession session, Model model) {
        String lang = (String) session.getAttribute("lang");
        session.invalidate();
        model.addAttribute("lang", lang);
        return "redirect:/";
    }

}
