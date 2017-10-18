/*
 * 项目名称:springbase
 * 类名称:QueryCondition.java
 * 包名称:com.joyintech.base.dao
 *
 * 修改履历:
 *       日期                            修正者        主要内容
 *       2016年11月21日          张中伟         初版做成
 *
 * Copyright (c) 2016-2017 兆尹科技
 */

package com.joyintech.base.dao;

/**
 *   SQL查询条件
 *   组合用的SQL查询条件。  可以将条件、组合方式修改为枚举
 * @author 张中伟
 * @version 1.0
 */
public class QueryCondition {

    /**
     * 组合方式（默认为AND。 如果使用OR,在组合时要考虑加括号<br>
     * 单个条件组合的方式现在添加OR不太好处理
     */
    private String comboWay = " AND ";

    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 条件（固定条件 = > <  >= <=  包含  包括  等 。。。）
     */
    private String condition; // 可选值限制

    /**
     * 条件值
     */
    private String conditionValue; // 条件值 条件值需要根据条件进行处理

    /**
     * 是否忽略整体解析（忽略整体解析，由开发人员在程序中自己处理）
     */
    private Boolean ignore = false;// 是否忽略该条件，该条件由用户自定义实现查询

    /**
     * 默认构造方法
     */
    public QueryCondition() {

    }

    /**
     * 构造方法
     * @param comboWay  组合方式
     * @param fieldName    字段名（成员变量名）
     * @param condition     条件
     * @param conditionValue  条件值 
     */
    public QueryCondition(String comboWay, String fieldName, String condition,
                          String conditionValue) {
        this.comboWay = comboWay;
        this.fieldName = fieldName;
        this.condition = condition;
        this.conditionValue = conditionValue;
    }

    /**
     * 增加构造方法
     * @param comboWay  组合方式
     * @param fieldName    字段名（成员变量名）
     * @param condition     条件
     * @param conditionValue  条件值 
     * @param ignore   是否忽略
     */
    public QueryCondition(String comboWay, String fieldName, String condition,
                          String conditionValue, Boolean ignore) {
        this.comboWay = comboWay;
        this.fieldName = fieldName;
        this.condition = condition;
        this.conditionValue = conditionValue;
        this.ignore = ignore;
    }

    /**
     * 取得comboWay的值
     *
     * @return comboWay值.
     */
    public String getComboWay() {
        return comboWay;
    }

    /**
     * 设定comboWay的值
     *
     * @param comboWay 设定值
     */
    public void setComboWay(String comboWay) {
        this.comboWay = comboWay;
    }

    /**
     * 取得fieldName的值
     *
     * @return fieldName值.
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * 设定fieldName的值
     *
     * @param fieldName 设定值
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * 取得condition的值
     *
     * @return condition值.
     */
    public String getCondition() {
        return condition;
    }

    /**
     * 设定condition的值
     *
     * @param condition 设定值
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }

    /**
     * 取得conditionValue的值
     *
     * @return conditionValue值.
     */
    public String getConditionValue() {
        return conditionValue;
    }

    /**
     * 设定conditionValue的值
     *
     * @param conditionValue 设定值
     */
    public void setConditionValue(String conditionValue) {
        this.conditionValue = conditionValue;
    }

    /**
     * 取得ignore的值
     *
     * @return ignore值.
     */
    public Boolean getIgnore() {
        return ignore;
    }

    /**
     * 设定ignore的值
     *
     * @param ignore 设定值
     */
    public void setIgnore(Boolean ignore) {
        this.ignore = ignore;
    }

}
