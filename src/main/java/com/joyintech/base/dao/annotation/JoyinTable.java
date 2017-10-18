/*
 * 项目名称:springbase
 * 类名称:Table.java
 * 包名称:com.joyintech.base.dao.annotation
 *
 * 修改履历:
 *       日期                            修正者        主要内容
 *       2016年11月21日          张中伟         初版做成
 *
 * Copyright (c) 2016-2017 兆尹科技
 */

package com.joyintech.base.dao.annotation;


import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;


/**
 *  用于注解数据库表 <br>
 *  注解数据库表到POJO类的映射 <br>
 * @author 张中伟
 * @version 1.0
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface JoyinTable {

    /**
     * 
     * 主要功能: 表名 <br>
     * 注意事项:无 <br>
     * 
     * @return 表名
     */
    String tableName();

    /**
     * 
     * 主要功能: 通用别名 <br>
     * 注意事项:无 <br>
     * 
     * @return 通用别名
     */
    String aliasName();

    /**
     * 
     * 主要功能: schema方案 <br>
     * 注意事项:无 <br>
     * 
     * @return schema
     */
    String schema();

}
