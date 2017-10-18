/*
 * 项目名称:springbase
 * 类名称:JoyinColumn.java
 * 包名称:com.joyintech.base.dao.annotation
 *
 * 修改履历:
 *       日期                            修正者        主要内容
 *       2016年11月21日          张中伟         初版做成
 *
 * Copyright (c) 2016-2017 兆尹科技
 */

package com.joyintech.base.dao.annotation;


import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;


/**
 *  映射到表字段的注解 <br>
 *  映射到表字段的注解（字段名、字段类型、长度、精度、校验类型） <br>
 * @author 张中伟
 * @version 1.0
 */
@Documented
@Retention(RUNTIME)
@Target({FIELD, METHOD})
public @interface JoyinColumn {
    
    
    /**
     * 
     * 主要功能:中文名称     <br>
     * 注意事项:无  <br>
     * 
     * @return
     */
    String alias() default "";
    /**
     * 字段名
     * 
     * @return 字段名
     */
    String fieldName();

    /**
     * 字段类型
     * 
     * @return 字段类型 VARCHAR2 NUMBER DATE
     */
    String fieldType() default "VARCHAR2";

    /**
     * 字段数据长度
     * 
     * @return 字段长度
     */
    int length() default 255;

    /**
     * 字段精度
     * 
     * @return 字段精度（小数位数）
     */
    int scale() default 0;

    /**
     * NUMBER类型字段总位数
     * @return NUMBER类型总位数（包含整数位数）
     */
    int precision() default 0;

    /**
     * 数据格式 多用 日期时间型则用日期时间格式 数字可以用数字格式   <br>
     * 如果是日期，此格式应是数据库转换时对应的格式？ <br>
     * 
     * @return 数据格式
     */
    String format() default "";

    /**
     * 所属表名
     * @return 所属表名
     */
    String tableName() default "";

    /**
     * 所属表别名
     * @return 所属表别名
     */
    String tableAliasName() default "";

    /**
    * 
    * 主要功能: 标识是否唯一键
    * 注意事项:无
    * 
    * @return 唯一返回真
    */
    boolean unique() default false;

    /**
     * 
     * 主要功能: 标识是否可为空 <br>
     * 注意事项:无
     * 
     * @return 可为空返回真
     */
    boolean nullable() default true;

    /**
    * 
    * 主要功能: 插入数据时是否包含该字段 <br>
    * 注意事项:无 <br>
    * 
    * @return  包含该字段时为真，默认为真
    */
    boolean insertable() default true;

    /**
     * 
     * 主要功能: 更新时是否包含该字段 <br>
     * 注意事项:无 <br>
     * 
     * @return 包含时为真，默认为真
     */
    boolean updatable() default true;
    
    /**
     * 
     * 主要功能:正则表达式匹配     <br>
     * 注意事项:无  <br>
     * 
     * @return
     */
    String regexp() default "";
    
    //最小值
    int min() default 0;
    
    //最大值
    int max() default 255 ;
    

}
