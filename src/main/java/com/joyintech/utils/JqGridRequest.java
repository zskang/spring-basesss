package com.joyintech.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JqGridRequest implements Serializable {

    /**
     */
    private static final long serialVersionUID = 6539475779190988315L;

    /**
     * 每页行数
     */
    private Integer rows = 10;

    /**
     *  当前页
     */
    private Integer page = 1;

    /**
     * 默认排序字段
     */
    private String sidx = "id";

    /**
     *  排序规则
     */
    private String sord = "desc";
    /**
     * 查询参数
     */
    private List<JqGridSearcher> searchers = new ArrayList<JqGridSearcher>();



    /**
     * @return the searchers
     */
    public List<JqGridSearcher> getSearchers() {
        return searchers;
    }

    /**
     * @param searchers the searchers to set
     */
    public void setSearchers(List<JqGridSearcher> searchers) {
        this.searchers = searchers;
    }

    /**
     * @return the rows
     */
    public Integer getRows() {
        return rows;
    }

    /**
     * @param rows the rows to set
     */
    public void setRows(Integer rows) {
        this.rows = rows;
    }

    /**
     * @return the page
     */
    public Integer getPage() {
        return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * @return the sidx
     */
    public String getSidx() {
        return sidx;
    }

    /**
     * @param sidx the sidx to set
     */
    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    /**
     * @return the sord
     */
    public String getSord() {
        return sord;
    }

    /**
     * @param sord the sord to set
     */
    public void setSord(String sord) {
        this.sord = sord;
    }

}
