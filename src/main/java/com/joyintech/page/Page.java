package com.joyintech.page;

import com.joyintech.utils.JqGridRequest;

/**
 * 名称：分页参数类<br>
 * 描述：分页所需参数<br>
 *
 * @author 汪瀚超
 * @version 1.0
 * @since 1.0.0
 */
public class Page {
    private int totalRows; // 总行数

    private int pageSize = 10; // 每页显示的行数

    private int currentPage = 1; // 当前页号

    private int totalPages; // 总页数

    private int startRow = 0; // 当前页在数据库中的起始行

    private JqGridRequest jqGridRequest;

    public Page(int currentPage, int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.startRow = (currentPage - 1) * pageSize;
    }

    public Page(JqGridRequest jqGrid) {
        this.currentPage = jqGrid.getPage();
        this.pageSize = jqGrid.getRows();
        this.startRow = (currentPage - 1) * pageSize;
        this.jqGridRequest = jqGrid;

    }

    public Page() {
    }


    public Page(int _pageSize) {
        pageSize = _pageSize;
    }

    public int getStartRow() {
        return startRow;
    }

    public int getEndRow() {
        return startRow + pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
        totalPages = (totalRows + pageSize - 1) / pageSize;
        // 判断起始行是否大于总行数，如果是，返回最后一页
        if (this.startRow > this.totalRows) {
            last();
        }
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        this.startRow = (currentPage - 1) * pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void first() {
        currentPage = 1;
        startRow = 0;
    }

    public void previous() {
        if (currentPage == 1 || currentPage == 0) {
            return;
        }
        currentPage--;
        startRow = (currentPage - 1) * pageSize;
    }

    public void next() {
        if (currentPage < totalPages) {
            currentPage++;
        }
        startRow = (currentPage - 1) * pageSize;
    }

    public void last() {
        currentPage = totalPages;
        startRow = (currentPage - 1) * pageSize;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public JqGridRequest getJqGridRequest() {
        return jqGridRequest;
    }

    public void setJqGridRequest(JqGridRequest jqGridRequest) {
        this.jqGridRequest = jqGridRequest;
    }
}
