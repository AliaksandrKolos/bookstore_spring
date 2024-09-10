package com.kolos.bookstore.controller.command.impl;

import com.kolos.bookstore.service.dto.PageableDto;
import jakarta.servlet.http.HttpServletRequest;

public class PagingUtil {

    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_PAGE_SIZE = 5;

    public static PageableDto getPageable(HttpServletRequest request) {
        String pageParam = request.getParameter("page");
        int page;
        if (pageParam == null) {
            page = DEFAULT_PAGE;
        } else {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = DEFAULT_PAGE;
            }
        }

        String pageSizeParam = request.getParameter("page_size");
        int pageSize;
        if (pageSizeParam == null) {
            pageSize = DEFAULT_PAGE_SIZE;
        } else {
            try {
                pageSize = Integer.parseInt(pageSizeParam);
            } catch (NumberFormatException e) {
                pageSize = DEFAULT_PAGE_SIZE;
            }
        }

        return new PageableDto(page, pageSize);
    }
}
