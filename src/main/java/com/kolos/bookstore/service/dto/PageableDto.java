package com.kolos.bookstore.service.dto;

public class PageableDto {
    private final int page;
    private final int pageSize;
    private final int offset;
    private int totalItems;
    private int totalPages;

    public PageableDto(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
        this.offset = calculateOffset();
    }



    private int calculateOffset() {
        return pageSize * (page - 1);
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    private int calculateLimit() {
        return pageSize;
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getLimit() {
        return pageSize;
    }

    public int getOffset() {
        return offset;
    }
}
