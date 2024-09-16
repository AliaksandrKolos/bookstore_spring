package com.kolos.bookstore.service.util;

import com.kolos.bookstore.service.dto.PageableDto;

public class PageUtil {

    public static long getTotalPages(PageableDto pageableDto, long count) {
        long pages = count / pageableDto.getPageSize();
        if (count % pageableDto.getPageSize() != 0) {
            pages ++;
        }
        return pages;
    }
}
