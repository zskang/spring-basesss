/**
 * 项目名称:   spring_base        	<br>
 * 包  名 称:   com.joyintech.base.dao.annotation   	<br>
 * 文件名称:   JoyinForm.java     <br>
 *
 * 修改履历:
 *       日期                            修正者        主要内容   <br>
 *       2017年3月22日            daiweiwei@joyintech.com        初版做成    <br>
 *
 * Copyright (c) 2007-2017 兆尹科技
 */
package com.joyintech.base.dao.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author daiweiwei@joyintech.com
 *
 */
@Documented
@Retention(RUNTIME)
@Target({FIELD, METHOD})
public @interface JoyinForm {
    
    //form表单字段类型
    //"input",'date','number','select','checkbox','radio','textarea'
    //
    FORM_TYPE formType() default FORM_TYPE.input;
    
    //第几行
    int row() default 0;
    //第几列
    int col() default 0;
    
    public static enum FORM_TYPE {
        input, date, number, select, checkbox, radio, textarea
    }

    public static enum UNIT {
        元, 万元, 百万元, 亿元
    }

    // 如果类型为date 那么需要给一个格式化 默认为 yyyy-mm-dd
    String format() default "yyyy-mm-dd";

    UNIT unit() default UNIT.元;

    int cent() default 0;

}
