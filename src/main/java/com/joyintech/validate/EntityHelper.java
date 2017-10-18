/**
 * 项目名称:   spring_base        	<br>
 * 包  名 称:   com.joyintech.validate   	<br>
 * 文件名称:   EntityValidator.java     <br>
 *
 * 修改履历:
 *       日期                            修正者                                                                                        主要内容   <br>
 *       2017年3月2日   daiweiwei@joyintech.com       初版做成    <br>
 *
 * Copyright (c) 2007-2017 兆尹科技
 */
package com.joyintech.validate;


import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.joyintech.base.dao.annotation.JoyinColumn;
import com.joyintech.base.dao.annotation.JoyinForm;
import com.joyintech.base.dao.annotation.JoyinForm.FORM_TYPE;
import com.joyintech.utils.FormColumn;


/**
 * 名称：〈一句话功能简述〉<br>
 * 描述：〈功能详细描述〉<br>
 * @author daiweiwei@joyintech.com
 * @version 1.0
 * @since 1.0.0
 */
public class EntityHelper {

    /** 验证字段是否非空，字段长度是否大于允许长度,是否唯一,值范围等
    * 
    * @param obj 实体对象
    * @throws RuntimeException 运行时异常
    */
    public static void validate(Object obj) {
        if (obj==null) {
            throw new RuntimeException("提交的内容为空");
        }
        @SuppressWarnings("rawtypes")
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        valid(obj, fields);
        // 判断此类是否集成有父类
        if (clazz.getSuperclass()!=null) {
            valid(obj, clazz.getSuperclass().getDeclaredFields());
        }
    }

    /**
     * 生成form表单属性数组
     * @param clazz 实体
     * @param prefix 实体form表单属性name前缀,如果不加前缀，那么生成的formName 就没有前缀
     * @return [{formName:'xxx.name',formType:'input'},{formName:'xxx.birthday',formType:'date'}]
     */
    public static List<FormColumn> formAttrs(Class clazz,String prefix) {
        List<FormColumn> columns = new ArrayList<FormColumn>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field: fields) {
            FormColumn formAttr = new FormColumn();
            field.setAccessible(true);
            JoyinColumn joyinColumn = null;
            JoyinForm joyinForm = null;
            // 获取字段相关信息
            if (field.isAnnotationPresent(JoyinColumn.class)) {
                joyinColumn = (JoyinColumn)field.getAnnotation(JoyinColumn.class);
                if (null==prefix) {
                    prefix = "";
                }
                formAttr.setFormName(prefix+joyinColumn.fieldName());
                formAttr.setLabel(joyinColumn.alias());
            } else {
                continue;
            }
            if (field.isAnnotationPresent(JoyinForm.class)) {

                joyinForm = (JoyinForm)field.getAnnotation(JoyinForm.class);

                formAttr.setCol(joyinForm.col());
                formAttr.setRow(joyinForm.row());

                FORM_TYPE formType = joyinForm.formType();
                formAttr.setFormType(formType.toString());
                switch (formType) {
                    case input:
                        break;
                    case date:

                        formAttr.put("format", joyinForm.format());
                        break;
                    case number:
                        formAttr.put("unit", joyinForm.unit());
                        formAttr.put("cent", joyinForm.cent());
                        break;
                    case select:
                        break;
                    case checkbox:
                        break;
                    case radio:
                        break;
                    case textarea:
                        break;
                    default:
                        break;
                }

            } else {
                continue;
            }
            columns.add(formAttr);
        }
        return columns;
    }
    /**
    * 定义验证规则
    * 
    * @param obj 实体对象
    * @param fields 实体对象的字段数组
    * @throws RuntimeException 运行时异常
    */
    private static void valid(Object obj, Field[] fields)
        throws RuntimeException {
        // Map<String, Object> map = get(obj);
        for (Field field: fields) {
            field.setAccessible(true);
            JoyinColumn joyinColumn = null;
            // 获取字段相关信息
            System.out.println(field.getName());
            if (field.isAnnotationPresent(JoyinColumn.class)) {
                joyinColumn = (JoyinColumn)field.getAnnotation(JoyinColumn.class);
                System.out.println(joyinColumn.alias());

                Object value = null;
                try {
                    value = field.get(obj);
                } catch (IllegalAccessException e) {

                    e.printStackTrace();
                }
                // 如果是否可空为false,则判断值是否为空。
                if(!joyinColumn.nullable()){
                    if(value==null){
                        throw new RuntimeException(
                            joyinColumn.alias()+" 不允许为空!");
                    }
                }else if(joyinColumn.nullable()&&value==null){
                    continue;
                }else if(joyinColumn.nullable()&&value.toString().trim().length()==0){
                    continue;
                }   

                if (String.class==field.getType()) { 
                    // 长度，正则
                    
                    length(joyinColumn, field, obj, value);
                    pattern(joyinColumn, field, obj, value);
                } else if (Long.class==field.getType()||Integer.class==field.getType()) {  
                    // 长度范围
                    range(joyinColumn, field, obj, value);

                } else if (Date.class==field.getType()) {
                    pattern(joyinColumn,field,obj,value);
                    
                } else if (BigDecimal.class==field.getType()) {
                    //精度，总长度
                    // digital(joyinColumn, field, obj, value);
                    // 长度范围
                    range(joyinColumn, field, obj, value);

                }

            }

        }
    }

    /**
     * 
     * 主要功能:金额、利率精度大小判断     <br>
     * 注意事项:无  <br>
     * 
     * @param joyinColumn 自定义注解
     * @param field 字段java对象
     * @param obj 实体对象
     * @param value 字段字面值
     */
    private static void digital(JoyinColumn joyinColumn, Field field, Object obj,
                                Object value) {
        BigDecimal tef = new BigDecimal(value.toString());
        if (tef.precision()>joyinColumn.precision()) {
            throw new RuntimeException(
                joyinColumn.alias()+"的数据总长度为 "+tef.precision()+"/n 设置的大小为:"
                                                  +joyinColumn.precision()+" !");
        }
        if (tef.scale()>joyinColumn.scale()) {
            throw new RuntimeException(
                joyinColumn.alias()+"的数据精度为 "+tef.scale()+"/n 设置的大小为:"
                                                  +joyinColumn.scale()+" !");
        }
    }

    /**
    * 
    * 主要功能: 值范围判断    <br>
    * 注意事项:无  <br>
    * 
     * @param joyinColumn 自定义注解
     * @param field 字段java对象
     * @param obj 实体对象
     * @param value 字段字面值
    */
    private static void range(JoyinColumn joyinColumn, Field field, Object obj,
                             Object value) {
        if (value.toString().length()<joyinColumn.min()) {
            throw new RuntimeException(
                joyinColumn.alias()+"的长度必须大于 "+joyinColumn.min()+" !");
        } else if (value.toString().length()>joyinColumn.max()) {
            throw new RuntimeException(
                joyinColumn.alias()+"的长度必须小于 "+joyinColumn.max()+" !");
        }
    }

    /**
    * 
    * 主要功能:正则匹配     <br>
    * 注意事项:无  <br>
    * 
    * @param joyinColumn 自定义注解
     * @param field 字段java对象
     * @param obj 实体对象
     * @param value 字段字面值
    */
    private static void pattern(JoyinColumn joyinColumn, Field field, Object obj,
                               Object value) {
        String reg = joyinColumn.regexp();
        if(Date.class==field.getType()){
            reg = "^(^(\\d{4}|\\d{2})(\\-|\\/|\\.)\\d{1,2}\\3\\d{1,2})\\s+([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
            Pattern p = Pattern.compile(reg);    
            Matcher m = p.matcher(value.toString());
            if(m.matches()){
                String[] datas = value.toString().split(" ");
                String[] days = datas[0].split("-");
                Integer[] bigMonth = {1,3,5,7,8,10,12};
                List<Integer> list = Arrays.asList(bigMonth);
                int year = Integer.parseInt(days[0]);
                int month = Integer.parseInt(days[1]);
                int day = Integer.parseInt(days[2]);
                if(year % 4 == 0 && year % 100 != 0 || year % 400 == 0){
                    if(month ==2 && day>29){
                        throw new RuntimeException(
                            joyinColumn.alias()+"输入值非法!");
                    }
                }
                if(month ==2 && day>28){
                    throw new RuntimeException(
                        joyinColumn.alias()+"输入值非法!");
                }
                if(list.contains(month)&&day>31){
                    throw new RuntimeException(
                        joyinColumn.alias()+"输入值非法!");
                }
                if(!list.contains(month)&&day>30){
                    throw new RuntimeException(
                        joyinColumn.alias()+"输入值非法!");
                }
            }
        }
        if(reg==null||reg.length()==0){
            return;
        }
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(value.toString());
        if (matcher.matches()) {
            throw new RuntimeException(
                joyinColumn.alias()+"的正则没有匹配上 "+joyinColumn.regexp()+" !");
        }
    }

    /**
    * 
    * 主要功能: 长度判断    <br>
    * 注意事项:无  <br>
    * 
    * @param joyinColumn 自定义注解
     * @param field 字段java对象
     * @param obj 实体对象
     * @param value 字段字面值
    */
    private static void length(JoyinColumn joyinColumn, Field field, Object obj,
                              Object value) {

        if (value.toString().toCharArray().length>joyinColumn.length()) {
            throw new RuntimeException(
                joyinColumn.alias()+" 长度超过"+joyinColumn.length()+" !");
        }
    }


    /**
     * 对象的map 键值对对象
     * 
     * @param obj 实体对象
     * @return Map<String, Object> 实体的键值对
     * @throws RuntimeException 运行时异常
     */
    public static Map<String, Object> bean2Map(Object obj)
        throws RuntimeException {
        Map<String, Object> map = new HashMap<String, Object>();
        @SuppressWarnings("rawtypes")
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        put(obj, fields, map);
        return map;
    }

    public static Object map2Bean(Class clazz, Map map) {
        return clazz;
    }

    /**
     * 主要功能:将父类IdEntity,BaseEntity的id等属性放入map中 注意事项:无
     * 
     * @param obj
     *            实体对象
     * @param fields
     *            字段数组
     * @param map
     *            键值对
     */
    private static void put(Object obj, Field[] fields, Map<String, Object> map) {
        for (Field field: fields) {
            field.setAccessible(true);
            JoyinColumn anno = field.getAnnotation(JoyinColumn.class);
            Object joyinColumnValue = null;
            try {
                joyinColumnValue = field.get(obj);
            } catch (IllegalAccessException e) {

                e.printStackTrace();
            }
            if (anno==null) {
                continue;
            }
            map.put(anno.fieldName(), joyinColumnValue);
        }
    }

}
