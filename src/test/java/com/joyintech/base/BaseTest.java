/*
 * 项目名称:springbase
 * 类名称:BaseTest.java
 * 包名称:com.joyintech.base
 *
 * 修改履历:
 *       日期                            修正者        主要内容
 *       2016年11月16日          张中伟         初版做成
 *
 * Copyright (c) 2016-2017 兆尹科技
 */

package com.joyintech.base;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;


/**
 *   测试基础类<br>
 *  spring 相关测试基础类，提供基础和继承类和方法<br>
 * @author 张中伟
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({@ContextConfiguration(locations = "/spring-test.xml")})
public class BaseTest {

    /**
     * 
     * 主要功能: 添加一个空的测试方法，避免测试无方法时不能通过
     * 注意事项:无
     *
     */
    @Test
    public void testVoid() {
        Assert.isNull(null);
    }

}
