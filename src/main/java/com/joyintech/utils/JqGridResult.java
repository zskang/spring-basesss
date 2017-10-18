/*
 * 项目名称:joyintech_utils
 * 类名称:JqGridResult.java
 * 包名称:com.joyintech.utils
 *
 * 修改履历:
 *       日期                            修正者        主要内容
 *       2017年01月18日          daiweiwei         初版做成
 *
 * Copyright (c) 2016-2017 兆尹科技
 */
package com.joyintech.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 描述: 封装jqgrid的返回结果集
 * @author daiweiwei daiweiwei@joyintech.com
 * @date 2017年1月18日-下午1:18:43
 * @param <T>
 */
public class JqGridResult<T> {
    //当前页
    private Integer page=1;
    //每页记录数
    private Integer total=10;
    //总记录数
    private Integer records=0;
    
    //记录集
    private List<T>  rows = new ArrayList<T>() ;
    
    
    public JqGridResult() {}
    
    
    public JqGridResult(Integer page, Integer total) {
        super();
        this.page = page;
        this.total = total;
    }

    public JqGridResult(Integer page, Integer total, Integer records) {
        super();
        this.page = page;
        this.total = total;
        this.records = records;
    }


  
    
    public Integer getPage() {
        return page;
    }
    public void setPage(Integer page) {
        this.page = page;
    }
    public Integer getTotal() {
        return total;
    }
    public void setTotal(Integer total) {
        this.total = total;
    }
    public Integer getRecords() {
        return records;
    }
    public void setRecords(Integer records) {
        this.records = records;
    }
    public List<T> getRows() {
        return rows;
    }
    public void setRows(List<T> rows) {
        this.rows = rows;
    }
    
    public JqGridResult<T> addRows(List<T> rows) {
        this.rows = rows;
        return this;
    }
    
    public JqGridResult<T> addRows(T t) {
        this.rows.add(t);
        return this;
    }
    
    
    @Override
    public String toString() {
        return "{page:"+page+", total:"+total+", records:"+rows.size()
               +", rows:["+rows+"]}";
    }
    
}
