package com.kolos.bookstore.service.dto;

public class PageableDto {
    private final long page;
    private final long pageSize;
    private final long offset;
    private long totalItems;
    private long totalPages;

    public PageableDto(long page, long pageSize) {
        this.page = page;
        this.pageSize = pageSize;
        this.offset = calculateOffset();
    }



    private long calculateOffset() {
        return pageSize * (page - 1);
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public long getTotalPages() {
        return totalPages;
    }

    private long calculateLimit() {
        return pageSize;
    }

    public long getPage() {
        return page;
    }

    public long getPageSize() {
        return pageSize;
    }

    public long getLimit() {
        return pageSize;
    }

    public long getOffset() {
        return offset;
    }
}
