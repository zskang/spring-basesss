/**
 * 项目名称:   spring_base        	<br>
 * 包  名 称:   com.joyintech.base.constants   	<br>
 * 文件名称:   CommonSession.java     <br>
 *
 * 修改履历:
 *       日期                            修正者        主要内容   <br>
 *       2017年5月8日            张中伟        初版做成    <br>
 *
 * Copyright (c) 2007-2017 兆尹科技
 */
package com.joyintech.base.constants;

/**
 * 名称：CommonSession <br>
 * 描述：通用的Session存储的数据名称<br>
 *       Session下存储的内容主要是用户登录数据，例如用户相关信息、部门信息、权限信息等。<br>
 *       如果配置了spring-session,也是使用redis对session数据进行管理的。 <br>
 *      
 * @author 张中伟
 * @version 1.0
 * @since 1.0.0
 */
public class CommonSession {

    // 添加session前缀以标识SESSION信息。

    /**
     * 登录用户的变量名。 非变量类型。 取值时需要转换。
     */
    public static final String LOGIN_USER = "SESSION_LOGIN_USER";

    /**
     * 登录用户的部门。 非变量类型。 取值时需要转换。
     */
    public static final String LOGIN_DEPART = "SESSION_LOGIN_ORG";

    /**
     * 登录用户的权限。  权限使用Map<String,Object> 类型。
     */
    public static final String USER_PRIVS = "SESSION_USER_PRIVS";

}
