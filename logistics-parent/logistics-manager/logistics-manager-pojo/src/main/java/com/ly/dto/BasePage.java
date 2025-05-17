package com.ly.dto;

/**
 *
 */
public class BasePage {
    // 当前页
    protected int pageNum = 1;

    // 每页显示的条数
    protected int pageSize = 20;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
