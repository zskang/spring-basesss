/**
 * 项目名称:   spring_base        	<br>
 * 包  名 称:   com.joyintech.utils   	<br>
 * 文件名称:   JsonConvertorTest.java     <br>
 *
 * 修改履历:
 *       日期                            修正者        主要内容   <br>
 *       2017年2月23日            张中伟        初版做成    <br>
 *
 * Copyright (c) 2007-2017 兆尹科技
 */
package com.joyintech.utils;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;


/**
 * 名称：JsonConvertorTest <br>
 * 描述：〈功能详细描述〉<br>
 * @author 张中伟
 * @version 1.0
 * @since 1.0.0
 */
public class JsonConvertorTest {

    /**
     * Test method for {@link com.joyintech.utils.JsonConvertor#jsonEncode(java.lang.Object)}.
     */
    @Test
    public void testJsonEncode() {

        User user = new User();
        user.setUserName("张三");
        user.setAddress("王府井大街");
        user.setBirth(new Date());

        BigDecimal bd = new BigDecimal(1234567890.123456789);
        user.setSalary(bd);

        String json = JsonConvertor.jsonEncode(user);

        Assert.assertNotNull(json);

        System.out.println(json);

    }

    /**
     * Test method for {@link com.joyintech.utils.JsonConvertor#jsonDecode(java.lang.String, java.lang.Class)}.
     */
    @Test
    public void testJsonDecodeStringClassOfT() {
        User user = new User();
        user.setUserName("张三");
        user.setAddress("王府井大街");
        // java.util.Date 默认转换格式为 毫秒数 。
        user.setBirth(new Date());

        BigDecimal bd = new BigDecimal(1234567890.123456789);

        user.setSalary(bd);

        String json = JsonConvertor.jsonEncode(user);

        // {"userName":"张三","address":"王府井大街","birth":1487898237664,"salary":1234567890.1234567165374755859375}
        Assert.assertNotNull(json);

        System.out.println(json);

        User newUser = JsonConvertor.jsonDecode(json, User.class);

        Assert.assertNotNull(newUser);
        Assert.assertNotNull(newUser.getUserName());

    }

    /**
     * Test method for {@link com.joyintech.utils.JsonConvertor#jsonDecode(java.lang.String, com.fasterxml.jackson.core.type.TypeReference)}.
     */
    @Test
    public void testJsonDecodeStringTypeReferenceOfT() {

        User user = new User();
        user.setUserName("张三");
        user.setAddress("王府井大街");
        // java.util.Date 默认转换格式为 毫秒数 。
        // 注意要转换类的 @Jsonformat 注解，用来标识 日期格式
        user.setBirth(new Date());

        BigDecimal bd = new BigDecimal(1234567890.123456789);

        user.setSalary(bd);

        // 注意要转换类的 @Jsonformat 注解，用来标识 日期格式
        // {"userName":"张三","address":"王府井大街","birth":1487898237664,"salary":1234567890.1234567165374755859375}
        String json = JsonConvertor.jsonEncode(user);

        Assert.assertNotNull(json);

        System.out.println(json);

        User newUser = JsonConvertor.jsonDecode(json,
            new TypeReference<User>() {});

        Assert.assertNotNull(newUser);
        Assert.assertNotNull(newUser.getUserName());

    }

    /**
     * Test method for {@link com.joyintech.utils.JsonConvertor#jsonDecode(java.lang.String, com.fasterxml.jackson.core.type.TypeReference, java.text.SimpleDateFormat)}.
     */
    @Test
    public void testJsonDecodeStringTypeReferenceOfTSimpleDateFormat() {
        User user = new User();
        user.setUserName("张三");
        user.setAddress("王府井大街");
        // java.util.Date 默认转换格式为 毫秒数 。
        user.setBirth(new Date());

        BigDecimal bd = new BigDecimal(1234567890.123456789);

        user.setSalary(bd);

        // {"userName":"张三","address":"王府井大街","birth":1487898237664,"salary":1234567890.1234567165374755859375}
        String json = JsonConvertor.jsonEncode(user);

        Assert.assertNotNull(json);

        System.out.println(json);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        User newUser = JsonConvertor.jsonDecode(json,
            new TypeReference<User>() {}, sdf);

        Assert.assertNotNull(newUser);
        Assert.assertNotNull(newUser.getUserName());
    }

}
