package com.kolos.bookstore.web.view;

import com.kolos.bookstore.service.dto.UserPrincipal;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal userPrincipal) {
            session.setAttribute("user", userPrincipal);
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
