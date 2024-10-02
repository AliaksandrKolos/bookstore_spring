package com.kolos.bookstore.web.errorHandler;

import com.kolos.bookstore.service.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@ControllerAdvice("com.kolos.bookstore.web.view")
public class ErrorController {

    @GetMapping("/error")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String error() {
        return "error";
    }

    @ExceptionHandler(UserInputValidationException.class)
    public String handleUserInputValidationException(UserInputValidationException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("dataError", e.getMessage());
        return "redirect:/users/registration";
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(NotFoundException e, Model model) {
        model.addAttribute("dataError", e.getMessage());
        return "NotFoundError";
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public String handleAuthenticationFailedException(AuthenticationFailedException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("dataError", e.getMessage());
        return "redirect:/login";
    }

    @ExceptionHandler(InvalidOrderStatusTransitionException.class)
    public String handleInvalidOrderStatusTransitionException(InvalidOrderStatusTransitionException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("dataError", e.getMessage());
        return "redirect:/orders/ordersUser";
    }

    @ExceptionHandler(UpdateFailedException.class)
    public String handleUpdateFailedException(UpdateFailedException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("dataError", e.getMessage());
        return "redirect:/error";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleUpdateFailedException(Exception e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("dataError", "Error occurred while processing your request.");
        return "error";
    }


}

