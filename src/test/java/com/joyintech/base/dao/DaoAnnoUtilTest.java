/*
 * 项目名称:springbase
 * 类名称:DaoAnnoUtilTest.java
 * 包名称:com.joyintech.base.dao
 *
 * 修改履历:
 *       日期                            修正者        主要内容<br>
 *       2016年11月21日          张中伟         初版做成
 *
 * Copyright (c) 2016-2017 兆尹科技
 */

package com.joyintech.base.dao;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.util.Assert;

import com.joyintech.base.dao.annotation.JoyinColumn;


/**
 * DaoAnnoUtil 测试类<br>
 * 
 * @author 张中伟
 * @version 1.0
 */
public class DaoAnnoUtilTest {

    private static final Log LOGGER = LogFactory.getLog(DaoAnnoUtilTest.class);

    /**
     * Test method for {@link com.joyintech.base.dao.DaoAnnoUtil#parseQueryCondition(java.util.List, java.lang.Class)}.
     */
    @Test
    public final void testParseQueryConditionListOfQueryConditionClassOfQ() {
        List<QueryCondition> lqc = new ArrayList<QueryCondition>();

        QueryCondition qc = new QueryCondition();

        qc.setFieldName("userName");
        qc.setCondition("=");
        qc.setConditionValue("aaa");

        lqc.add(qc);

        QueryCombo com = DaoAnnoUtil.parseQueryCondition(lqc,
            TestJoyinColumn.class);

        // QueryCombo com=DaoAnnoUtil.parseOneConditon(qc, TestJoyinColumn.class, 1, true);

        Assert.notNull(com);

        LOGGER.info(com.getQueryString());
    }

    /**
     * Test method for {@link com.joyintech.base.dao.DaoAnnoUtil#parseQueryCondition(java.util.List, java.lang.Class, boolean)}.
     */
    @Test
    public final void testParseQueryConditionListOfQueryConditionClassOfQBoolean() {

        List<QueryCondition> lqc = new ArrayList<QueryCondition>();

        QueryCondition qc = new QueryCondition();

        qc.setFieldName("userName");
        qc.setCondition("=");
        qc.setConditionValue("aaa");

        lqc.add(qc);

        QueryCombo com = DaoAnnoUtil.parseQueryCondition(lqc,
            TestJoyinColumn.class, true);

        Assert.notNull(com);

        LOGGER.info(com.getQueryString());
    }

    /**
     * Test method for {@link com.joyintech.base.dao.DaoAnnoUtil#parseOneConditon(com.joyintech.base.dao.QueryCondition, java.lang.Class, int, boolean)}.
     */
    @Test
    public final void testParseOneConditon() {

        QueryCondition qc = new QueryCondition();

        qc.setFieldName("userName");
        qc.setCondition("=");
        qc.setConditionValue("aaa");

        // DaoAnnoUtil.parseOneConditon(qc, TestJoyinColumn.class, 1, true);
        QueryCombo com = DaoAnnoUtil.parseOneConditon(qc, TestJoyinColumn.class,
            1, true);

        Assert.notNull(com);

        LOGGER.info(com.getQueryString());
    }

    /**
     * Test method for {@link com.joyintech.base.dao.DaoAnnoUtil#parseDateCondition(com.joyintech.base.dao.annotation.JoyinColumn, com.joyintech.base.dao.QueryCondition, int, boolean)}.
     */
    @Test
    public final void testParseDateCondition() {

        QueryCondition qc = new QueryCondition();

        qc.setFieldName("userBirthday");
        qc.setCondition("=");
        qc.setConditionValue("2016-11-21");

        // DaoAnnoUtil.parseOneConditon(qc, TestJoyinColumn.class, 1, true);
        QueryCombo com = DaoAnnoUtil.parseOneConditon(qc, TestJoyinColumn.class,
            1, true);

        Assert.notNull(com);

        LOGGER.info(com.getQueryString());
    }

    /**
     * Test method for {@link com.joyintech.base.dao.DaoAnnoUtil#parseNumberCondition(com.joyintech.base.dao.annotation.JoyinColumn, com.joyintech.base.dao.QueryCondition, int, boolean)}.
     */
    @Test
    public final void testParseNumberCondition() {
        QueryCondition qc = new QueryCondition();

        qc.setFieldName("userSalary");
        qc.setCondition("=");
        qc.setConditionValue("12345");

        // DaoAnnoUtil.parseOneConditon(qc, TestJoyinColumn.class, 1, true);

        QueryCombo com = DaoAnnoUtil.parseOneConditon(qc, TestJoyinColumn.class,
            1, true);

        Assert.notNull(com);

        LOGGER.info(com.getQueryString());
    }

    /**
     * Test method for {@link com.joyintech.base.dao.DaoAnnoUtil#parseStringCondition(com.joyintech.base.dao.annotation.JoyinColumn, com.joyintech.base.dao.QueryCondition, int, boolean)}.
     */
    @Test
    public final void testParseStringCondition() {

        QueryCondition qc = new QueryCondition();

        qc.setFieldName("userAddr");
        qc.setCondition("LIKE");
        qc.setConditionValue("ROAD");

        QueryCombo com = DaoAnnoUtil.parseOneConditon(qc, TestJoyinColumn.class,
            1, true);

        Assert.notNull(com);

        LOGGER.info(com.getQueryString());

    }

    /**
     * Test method for {@link com.joyintech.base.dao.DaoAnnoUtil#getConditionByField(java.util.List, java.lang.String)}.
     */
    @Test
    public final void testGetConditionByField() {

        List<QueryCondition> list = new ArrayList<QueryCondition>();

        QueryCondition qc = new QueryCondition();
        qc.setFieldName("fieldName");
        qc.setCondition("=");
        qc.setConditionValue("123");

        list.add(qc);

        QueryCondition rtn = DaoAnnoUtil.getConditionByField(list, "fieldName");
        Assert.notNull(rtn);

        QueryCondition rtn1 = DaoAnnoUtil.getConditionByField(list, "unknown");
        Assert.isNull(rtn1);

    }

    /**
     * Test method for {@link com.joyintech.base.dao.DaoAnnoUtil#isEmpty(com.joyintech.base.dao.QueryCondition)}.
     */
    @Test
    public final void testIsEmpty() {
        Assert.isTrue(DaoAnnoUtil.isEmpty(null));
        Assert.isTrue(DaoAnnoUtil.isEmpty(new QueryCondition()));
    }

}


/**
 * 
 *  JoyinColumn 列 测试类
 * 〈功能详细描述〉
 * @author 张中伟
 * @version 1.0
 */
class TestJoyinColumn {

    // 测试字段未添加 unique nullable insertable updateable 不用于查询解析

    @JoyinColumn(fieldName = "USER_NAME", fieldType = "VARCHAR2", length = 30, scale = 0, format = "")
    private String userName;

    @JoyinColumn(fieldName = "USER_ADDR", fieldType = "VARCHAR2", length = 30, scale = 0, format = "")
    private String userAddr;

    @JoyinColumn(fieldName = "USER_BIRTHDAY", fieldType = "DATE", length = 30, scale = 0, format = "yyyy-mm-dd")
    private Date userBirthday;

    @JoyinColumn(fieldName = "USER_SALARY", fieldType = "NUMBER", length = 8, scale = 2, format = "0.00")
    private int userSalary;

    @JoyinColumn(fieldName = "USER_LEADER", fieldType = "NUMBER", length = 4, scale = 0)
    private int userLeader;

}
