/*
 * 项目名称:springbase
 * 类名称:BaseServiceTest.java
 * 包名称:com.joyintech.base
 *
 * 修改履历:
 *       日期                            修正者        主要内容
 *       2016年11月21日          张中伟         初版做成
 *
 * Copyright (c) 2016-2017 兆尹科技
 */

package com.joyintech.base;


import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.util.Assert;


/**
 * Service测试基础类
 * 〈功能详细描述〉
 * @author 张中伟
 * @version 1.0
 */
@ContextHierarchy({
    @ContextConfiguration(locations = "/spring-config/spring-*.xml")})
// , @ContextConfiguration(locations = "/spring-config/spring-transaction.xml")
// 加载其他配置文件

public class BaseServiceTest extends BaseTest {

    /**
     * 
     * 主要功能:空的测试方法<br>
     * 注意事项:无<br>
     *
     */
    @Test
    public void testBaseService() {
        Assert.isNull(null);
    }

}
