/*
 * 项目名称:joyintech_utils
 * 类名称:JqGridSearcher.java
 * 包名称:com.joyintech.utils
 *
 * 修改履历:
 *       日期                            修正者        主要内容
 *       2017年01月18日          daiweiwei         初版做成
 *
 * Copyright (c) 2016-2017 兆尹科技
 */
package com.joyintech.utils;

import java.io.Serializable;

/**
 * jqgrid搜索值存储实体
 * @author daiweiwei
 *
 */
public class JqGridSearcher implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1564452995927461432L;
    //搜索域
    private String searchField;
    //搜索值
    private String searchString;
    //搜索运算符
    /**
                 等于      eq         =              
                不等于    ne         <> 
                小于       lt          < 
                小于等于   le          <= 
                大于       gt          > 
                大于等于   ge          >= 
                以*开头    bw         like 
                不以*开头  bn         not like 
                在    in          in 
                不在    ni          not in 
                以*结尾    ew         like 
                不以*结尾  en         not like 
                包含    cn         like 
                不包含    nc         not like 
     */
    private String searchOper="eq";
    
     
    
    public String getSearchField() {
        return searchField;
    }
    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }
    public String getSearchString() {
        return searchString;
    }
    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
    public String getSearchOper() {
        return searchOper;
    }
    public void setSearchOper(String searchOper) {
        this.searchOper = searchOper;
    }
     
    
}
