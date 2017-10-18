/*
 * 项目名称:springbase
 * 类名称:BaseDaoTest.java
 * 包名称:com.joyintech.base
 *
 * 修改履历:
 *       日期                            修正者        主要内容
 *       2016年11月21日          张中伟         初版做成
 *
 * Copyright (c) 2016-2017 兆尹科技
 */

package com.joyintech.base.dao;


import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

import com.joyintech.base.BaseTest;


/**
 *  DAO测试基类<br>
 *  DAO测试基类，所有DAO测试继承此类，可直接测试。
 * @author 张中伟
 * @version 1.0
 */
@ContextHierarchy({
    @ContextConfiguration(locations = "/spring-config/spring-datasource.xml")})
// , @ContextConfiguration(locations = "/spring-config/spring-transaction.xml")
// 加载其他配置文件

public class BaseDaoTest extends BaseTest {

    // 从配置文件中取得数据源
    @Autowired
    private DataSource dataSource;

    /**
     * 
     * 主要功能:添加空的测试方法<br>
     * 注意事项:无<br>
     *
     */
    @Test
    public void testBaseDao() {
        Assert.assertNull(null);
    }

    /**
     * 
     * 主要功能:测试数据源注入<br>
     * 注意事项:无<br>
     *
     */
    public void testInjectDataSource() {

        BaseDao baseDao = new BaseDao();

        baseDao.injiectDataSource(dataSource);

        Assert.assertNotNull(baseDao.getDataSource());

    }

}
