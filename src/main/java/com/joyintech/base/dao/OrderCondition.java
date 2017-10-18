/*
 * 项目名称:springbase
 * 类名称:OrderCondition.java
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
 *  排序条件
 *  排序条件
 * @author 张中伟
 * @version 1.0
 */
public class OrderCondition {

    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 排序方向
     */
    private String direction;

    /**
     * 默认构造方法
     */
    public OrderCondition() {

    }

    public OrderCondition(String fieldName, String direction) {

        this.fieldName = fieldName;
        this.direction = direction;

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
     * 取得direction的值
     *
     * @return direction值.
     */
    public String getDirection() {
        return direction;
    }

    /**
     * 设定direction的值
     *
     * @param direction 设定值
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

}
