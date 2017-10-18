/**
 * 项目名称:   demo_web        	<br>
 * 包  名 称:   com.joyintech.demoweb.controller   	<br>
 * 文件名称:   User.java     <br>
 *
 * 修改履历:
 *       日期                            修正者        主要内容   <br>
 *       2017年2月17日            张中伟        初版做成    <br>
 *
 * Copyright (c) 2007-2017 兆尹科技
 */
package com.joyintech.utils;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 
 * 名称：User <br>
 * 描述：示例接收参数用的User<br>
 * @author 张中伟
 * @version 1.0
 * @since 1.0.0
 */
@SuppressWarnings("serial")
public class User implements Serializable {

    public User() {}

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户地址
     */
    private String address;

    /**
     * 生日
     */
    private Date birth;

    /**
     * 薪水
     */
    private BigDecimal salary;

    /**
     * 取得userName的值
     *
     * @return userName值.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设定userName的值
     *
     * @param userName 设定值
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 取得address的值
     *
     * @return address值.
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设定address的值
     *
     * @param address 设定值
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 取得birth的值
     *
     * @return birth值.
     */
    // @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date getBirth() {
        return birth;
    }

    /**
     * 设定birth的值
     *
     * @param birth 设定值
     */
    public void setBirth(Date birth) {
        this.birth = birth;
    }

    /**
     * 取得salary的值
     *
     * @return salary值.
     */
    public BigDecimal getSalary() {
        return salary;
    }

    /**
     * 设定salary的值
     *
     * @param salary 设定值
     */
    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

}