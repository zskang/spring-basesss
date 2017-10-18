/**
 * 项目名称:   spring_base        	<br>
 * 包  名 称:   com.joyintech.base.constants   	<br>
 * 文件名称:   CommonRedis.java     <br>
 *
 * 修改履历:
 *       日期                            修正者        主要内容   <br>
 *       2017年5月8日            张中伟        初版做成    <br>
 *
 * Copyright (c) 2007-2017 兆尹科技
 */
package com.joyintech.base.constants;

/**
 * 名称：CommonRedis <br>
 * 描述：通用的REDIS存储名<br>
 *      REDIS下存储公用数据的缓存数据。<br>
 *      如果缓存数据库数据，要注意数据库更新时的同步。<br>
 *      如果redis不在本机，注意对性能的影响<br>
 *       
 * @author 张中伟
 * @version 1.0
 * @since 1.0.0
 */
public class CommonRedis {

    // 添加COMMON前缀标识公共缓存信息。

    /**
     * 系统菜单列表
     */
    public static final String MODULE_MAP = "COMMON_MODULE_MAP";

}
