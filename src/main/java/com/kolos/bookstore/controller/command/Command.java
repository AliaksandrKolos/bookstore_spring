package com.kolos.bookstore.controller.command;

import jakarta.servlet.http.HttpServletRequest;

public interface Command {
    String process(HttpServletRequest request);
}
