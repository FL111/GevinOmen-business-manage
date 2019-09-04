package com.neuedu.pojo;

import java.util.List;

public class PageModul<T> {
    private int currentPage;
    private int pageSize=4;
    private int beginIndex;
    private int endIndex;
    private int pageCount;
    private int userCount;

    public PageModul() {
    }

    public PageModul(int currentPage, int pageSize) {
        this.currentPage = (currentPage-1)*pageSize;
        this.pageSize = pageSize;
    }

    private List<T> pageList;
    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getBeginIndex() {
        return beginIndex;
    }

    public void setBeginIndex(int beginIndex) {
        this.beginIndex = beginIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public List<T> getPageList() {
        return pageList;
    }

    public void setPageList(List<T> pageList) {
        this.pageList = pageList;
    }


}
