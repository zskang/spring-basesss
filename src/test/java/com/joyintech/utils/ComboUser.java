/**
 * 项目名称:   demo_web        	<br>
 * 包  名 称:   com.joyintech.demoweb.controller   	<br>
 * 文件名称:   ComoBoUser.java     <br>
 *
 * 修改履历:
 *       日期                            修正者        主要内容   <br>
 *       2017年2月17日            张中伟        初版做成    <br>
 *
 * Copyright (c) 2007-2017 兆尹科技
 */
package com.joyintech.utils;


import java.io.Serializable;
import java.util.List;


/**
 * 
 * 名称：ComboUser <br>
 * 描述：混合User提交<br>
 * @author 张中伟
 * @version 1.0
 * @since 1.0.0
 */
@SuppressWarnings("serial")
public class ComboUser implements Serializable {

    private User user;

    private List<User> list;

    /**
     * 取得list的值
     *
     * @return list值.
     */
    public List<User> getList() {
        return list;
    }

    /**
     * 设定list的值
     *
     * @param list 设定值
     */
    public void setList(List<User> list) {
        this.list = list;
    }

    /**
     * 取得user的值
     *
     * @return user值.
     */
    public User getUser() {
        return user;
    }

    /**
     * 设定user的值
     *
     * @param user 设定值
     */
    public void setUser(User user) {
        this.user = user;
    }

}
