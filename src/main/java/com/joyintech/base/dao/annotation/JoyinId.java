/*
 * 项目名称:common_utils
 * 类名称:SystemException.java
 * 包名称:com.joyintech.platform.exception
 *
 * 修改履历:
 *       日期                   修正者      主要内容
 *  2016年7月19日      高聪       初版做成
 *
 * Copyright (c) 2016-2017 兆尹科技
 */

package com.joyintech.base.dao.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 
 * 用于JoyinColumn注解的ID注解，标识表主键
 * @author daiweiwei
 * @version 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JoyinId {

    public static enum UD_KEY_GEN_TYPE {
        NONE, // 自定义主键值，不需要自动生成
        UUID,// uuid
        SEQ,//sequence 索引
        TABLE, 
        SEQUENCE,
        IDENTITY,
        AUTO
    }
    /**
    * (Optional) The name of the column. Defaults to 
    * the property or field name.
    */
    String name() default "";

    UD_KEY_GEN_TYPE genType() default UD_KEY_GEN_TYPE.AUTO;
}