/*
 * 项目名称:springbase
 * 类名称:QueryCombo.java
 * 包名称:com.joyintech.base.dao
 *
 * 修改履历:
 *       日期                            修正者        主要内容
 *       2016年11月21日          张中伟         初版做成
 *
 * Copyright (c) 2016-2017 兆尹科技
 */

package com.joyintech.base.dao;


import java.util.HashMap;
import java.util.Map;


/**
 *  查询条件组合基本类
 *  查询条件组合基本类（把查询条件添加到SQL字符串，并把参数添加到参数Map）
 * @author 张中伟
 * @version 1.0
 */
public class QueryCombo {

    /**
     * 查询字符串条件
     */
    private String queryString = "";

    /**
     * 查询条件map
     */
    private Map<String, Object> map = new HashMap<String, Object>();

    /**
     *  多条件叠加
     *  注意：为了格式化，统一转换为大写，要求写SQL参数时以大写表示（待确定标准）
     * @param qc  查询条件
     * @return QueryCombo 返回一个QueryCombo
     */
    public QueryCombo overlay(QueryCombo qc) {

        String query = this.getQueryString()+" \n"+qc.getQueryString();

        // 统一转换为大写
        this.setQueryString(query.toUpperCase());
        this.getMap().putAll(qc.getMap());
        return this;
    }

    /**
     * 取得queryString的值
     *
     * @return queryString值.
     */
    public String getQueryString() {
        return queryString;
    }

    /**
     * 设定queryString的值
     *
     * @param queryString 设定值
     */
    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    /**
     * 取得map的值
     *
     * @return map值.
     */
    public Map<String, Object> getMap() {
        return map;
    }

    /**
     * 设定map的值
     *
     * @param map 设定值
     */
    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

}
