/*
 * 项目名称:springbase
 * 类名称:DaoAnnoUtil.java
 * 包名称:com.joyintech.base.dao
 *
 * 修改履历:
 *       日期                            修正者        主要内容
 *       2016年11月21日          张中伟         初版做成
 *
 * Copyright (c) 2016-2017 兆尹科技
 */

package com.joyintech.base.dao;


import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import com.joyintech.base.dao.annotation.JoyinColumn;


/**
 *  DAO 条件添加解析
 *  目前更适合单表查询。   多表关联自定义可以多次添加单表的方式实现。
 *  表增加字段后如何适应？   业务逻辑和POJO类难以处理
 * @author 张中伟
 * @version 1.0
 */
public class DaoAnnoUtil {

    /**
     * 添加参数名前缀以防止参数重复
     */
    public static final String PARAM_NAME_PREFIX = "COMBOCON_";

    /**
     * 主要功能: 解析查询条件为SQL语句和Map参数组合QueryCombo<br>
     * 注意事项: 对空值 条件进行跳过处理。 注意查询结果<br>
     *
     * @param lqc   查询条件
     * @param clazz 对应注解的类
     * @return 查询条件组合
     */
    public static QueryCombo parseQueryCondition(List<QueryCondition> lqc,
                                                 Class<?> clazz) {

        QueryCombo rtn = new QueryCombo();

        // 无条件的直接返回
        if (lqc==null||lqc.size()<1) {
            return rtn;
        }

        try {
            int i = 0;
            for (QueryCondition qc: lqc) {
                // 传一个数字参数以处理同名参数取值不同的情况。
                // 不使用随机数，尽量保证同样的参数生成的SQL相同，以提高效率
                i++;
                // 有空值跳过 不添加筛选条件，以保证SQL可以正常运行。。。但无法保证结果
                // 要保证结果正确，需要抛出异常
                if (null==qc.getFieldName()
                    ||"".equals(qc.getFieldName().trim())) {
                    continue;
                }

                if (null==qc.getCondition()
                    ||"".equals(qc.getCondition().trim())) {
                    continue;
                }

                if (null==qc.getConditionValue()
                    ||"".equals(qc.getConditionValue().trim())) {
                    continue;
                }
                if (!qc.getIgnore()) {
                    // 叠加进结果
                    rtn.overlay(parseOneConditon(qc, clazz, i, false));
                }

            }
        } catch (Exception e) {
            // 出现异常则跳过查询条件，虽然不能保证结果一定正确，但可以略过输入错误的条件
            // 多数前台传入的按条件查询并不需要保证结果完全正确
            // 后台组合的条件应由开发人员保证条件正确
            e.printStackTrace();
        }

        return rtn;

    }

    /**
       * 主要功能: 解析查询条件为SQL语句和Map参数组合QueryCombo<br>
     * 注意事项: 对空值 条件进行跳过处理。 注意查询结果<br>
     * 
     * @param lqc   查询条件
     * @param clazz 对应注解的类
     * @param tablePrefix 表名前缀
     * @return 查询条件组合
     */
    public static QueryCombo parseQueryCondition(List<QueryCondition> lqc,
                                                 Class<?> clazz,
                                                 boolean tablePrefix) {

        if (!tablePrefix) {
            return parseQueryCondition(lqc, clazz);
        }

        QueryCombo rtn = new QueryCombo();

        // 无条件的直接返回
        if (lqc==null||lqc.size()<1) {
            return rtn;
        }

        try {
            int i = 0;
            for (QueryCondition qc: lqc) {
                // 传一个数字参数以处理同名参数取值不同的情况。
                // 不使用随机数，尽量保证同样的参数生成的SQL相同，以提高效率
                i++;
                // 有空值跳过 不添加筛选条件，以保证SQL可以正常运行。。。但无法保证结果
                // 要保证结果正确，需要抛出异常
                if (null==qc.getFieldName()
                    ||"".equals(qc.getFieldName().trim())) {
                    continue;
                }

                if (null==qc.getCondition()
                    ||"".equals(qc.getCondition().trim())) {
                    continue;
                }

                if (null==qc.getConditionValue()
                    ||"".equals(qc.getConditionValue().trim())) {
                    continue;
                }
                if (!qc.getIgnore()) {
                    // 叠加进结果
                    rtn.overlay(parseOneConditon(qc, clazz, i, tablePrefix));
                }

            }
        } catch (Exception e) {
            // 出现异常则跳过查询条件，虽然不能保证结果一定正确，但可以略过输入错误的条件
            // 多数前台传入的按条件查询并不需要保证结果完全正确
            // 后台组合的条件应由开发人员保证条件正确
            e.printStackTrace();
        }

        return rtn;

    }

    /**
     * 主要功能: 通过条件和类注解组合成查询SQL条件 的SQL和参数Map<br>
     * 注意事项:  传递多个条件及同名条件时，会自动添加增量区分条件名。 <br>
     * 例如返回： " AND NAME = :NAME "<br>
     * "NAME" "ZHANG"; <br>
     *
     * @param qc     条件
     * @param clazz  ORM类
     * @param conSeq 递增数，防止条件重复导致参数名重复
     * @param tablePrefix 表名前缀
     * @return 组合的SQL条件
     */
    public static QueryCombo parseOneConditon(QueryCondition qc, Class<?> clazz,
                                              int conSeq, boolean tablePrefix) {

        QueryCombo combo = new QueryCombo();
        // combo.setQueryString(qc.getComboWay());

        Field field = null;

        try {
            field = clazz.getDeclaredField(qc.getFieldName());

        } catch (SecurityException e) {
            // 不处理
        } catch (NoSuchFieldException e) {
            // 不处理
            e.printStackTrace();
        }

        if (field==null) {
            return combo;
        }

        /**
         * 字段解析仅处理了  VARCHAR2   DATE  NUMBER三种类型。  
         * 可以考虑添加 TIMESTAMP类型
         */
        if (field.isAnnotationPresent(JoyinColumn.class)) {
            JoyinColumn cm = field.getAnnotation(JoyinColumn.class);

            String value = cm.fieldType();

            if (value.equals("VARCHAR2")) {
                combo.overlay(
                    parseStringCondition(cm, qc, conSeq, tablePrefix));
            } else if (value.equals("DATE")) {
                combo.overlay(parseDateCondition(cm, qc, conSeq, tablePrefix));
            } else if (value.equals("NUMBER")) {
                parseNumberCondition(cm, qc, conSeq, tablePrefix);
            }

            /*
             * JDK1.7以上的语法 。。。。 switch (cm.fieldType()) { case "VARCHAR2": { combo.overlay(
             * parseStringCondition(cm, qc, conSeq, tablePrefix)); break; } case "DATE": {
             * combo.overlay( parseDateCondition(cm, qc, conSeq, tablePrefix)); break; } case
             * "NUMBER": { combo.overlay( parseNumberCondition(cm, qc, conSeq, tablePrefix));
             * break; } default: { } }
             */

        }

        return combo;
    }

    /**
     * 主要功能: 解析日期类型<br>
     * 注意事项: 仅对java.sql.Date进行解析<br>
     *
     * @param cm     JoyinColumn 注解
     * @param qc     查询条件
     * @param conSeq 递增的条件编号
     * @param tablePrefix 表名前缀
     * @return 解析后的条件和Map
     */
    public static QueryCombo parseDateCondition(JoyinColumn cm,
                                                QueryCondition qc, int conSeq,
                                                boolean tablePrefix) {
        QueryCombo combo = new QueryCombo();
        StringBuilder sb = new StringBuilder();

        // 转换失败则抛出异常
        try {

            // 替换为字符串转日期通用方法 此处处理需要扩展更大的兼容
            // java.sql.Date paramValue = java.sql.Date.valueOf(qc.getConditionValue());
            // String df=DateUtil.PATTERN_YYYY_MM_DD_3+"'T'" +"HH:mm:ss";
            Date paramValue = null;

            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                paramValue = new java.util.Date(
                    (sf.parse(qc.getConditionValue()).getTime()));
            } catch (ParseException e) {
                // 转换错误则不处理
                paramValue = null;
                e.printStackTrace();
            }

            sb.append(qc.getComboWay());

            if (tablePrefix&&!StringUtils.isEmpty(cm.tableAliasName())) {
                sb.append(cm.tableAliasName()).append(".");
            } else if (tablePrefix&&!StringUtils.isEmpty(cm.tableName())) {
                sb.append(cm.tableName()).append(".");
            }
            sb.append(cm.fieldName());

            sb.append(" "+qc.getCondition()+" ");

            String paramName = PARAM_NAME_PREFIX+cm.fieldName().toUpperCase()
                               +conSeq;

            sb.append(" :"+paramName);

            combo.getMap().put(paramName.trim(), paramValue);

            combo.setQueryString(sb.toString());

        } catch (RuntimeException re) {
            // 仅打印异常//查询结果不对。。。。暂不处理
            combo.setQueryString("");
            re.printStackTrace();
        }

        return combo;

    }

    /**
     * 主要功能: 解析数字类型<br>
     * 注意事项: 暂时未考虑 BigDecimal ,是否正常<br>
     *
     * @param cm     列注解
     * @param qc     查询条件
     * @param conSeq 递增的条件编号
     * @param tablePrefix 表名前缀
     * @return 解析后的条件和Map
     */
    public static QueryCombo parseNumberCondition(JoyinColumn cm,
                                                  QueryCondition qc, int conSeq,
                                                  boolean tablePrefix) {
        QueryCombo combo = new QueryCombo();
        StringBuilder sb = new StringBuilder();
        String paramName = "";

        sb.append(qc.getComboWay());

        if (tablePrefix&&!StringUtils.isEmpty(cm.tableAliasName())) {
            sb.append(cm.tableAliasName()).append(".");
        } else if (tablePrefix&&!StringUtils.isEmpty(cm.tableName())) {
            sb.append(cm.tableName()).append(".");
        }
        sb.append(cm.fieldName());

        String cond = qc.getCondition().toUpperCase();
        sb.append(" "+cond+" ");

        // 转换失败则抛出异常
        try {

            // 包含 IN 和 NOT IN
            if (cond.contains("IN")) {

                // if ("in".equals(qc.getCondition()) || "not
                // in".equals(qc.getCondition())) {
                String[] con = qc.getConditionValue().split(",");
                // modi by zev at 2015年12月04日 10:32:03
                // 修改in 条件传参方式
                paramName = PARAM_NAME_PREFIX+cm.fieldName().toUpperCase();

                sb.append(" ( :"+paramName+" ) ");
                combo.getMap().put(paramName.trim(), Arrays.asList(con));

                // end modi

                /*
                 * String[] con = qc.getConditionValue().split(","); String[] combCon = new
                 * String[con.length]; sb.append(" ("); for (int i = 0; i < con.length; i++) {
                 * paramName = PARAM_NAME_PREFIX + cm.fieldName().toUpperCase() + conSeq + i;
                 * combCon[i] = " :" + paramName; combo.getMap().put(paramName.trim(),
                 * Integer.parseInt(con[i])); } sb.append(StringUtils.join(combCon, ","));
                 * sb.append(" ) ");
                 */
            } else {
                double value = Double.parseDouble(qc.getConditionValue());

                paramName = PARAM_NAME_PREFIX+cm.fieldName().toUpperCase()
                            +conSeq;

                sb.append(" :"+paramName);

                combo.getMap().put(paramName.trim(), value);
            }

            combo.setQueryString(sb.toString());
        } catch (RuntimeException re) {
            // 仅打印异常//查询结果不对。。。。暂不处理
            combo.setQueryString("");;
            re.printStackTrace();
        }

        return combo;

    }

    /**
     * 主要功能: 解析字符型查询条件<br>
     * 注意事项: 暂无<br>    
     *
     * @param cm     JoyinColumn
     * @param qc     查询条件
     * @param conSeq 递增的条件编号
     * @param tablePrefix 表名前缀
     * @return 解析后的条件和Map
     */
    public static QueryCombo parseStringCondition(JoyinColumn cm,
                                                  QueryCondition qc, int conSeq,
                                                  boolean tablePrefix) {
        QueryCombo combo = new QueryCombo();
        StringBuilder sb = new StringBuilder();

        // 转换失败则抛出异常
        try {

            String s = qc.getConditionValue();

            sb.append(qc.getComboWay());

            if (tablePrefix&&!StringUtils.isEmpty(cm.tableAliasName())) {
                sb.append(cm.tableAliasName()).append(".");
            } else if (tablePrefix&&!StringUtils.isEmpty(cm.tableName())) {
                sb.append(cm.tableName()).append(".");
            }
            sb.append(cm.fieldName());

            String cond = qc.getCondition().toUpperCase();

            sb.append(" "+cond+" ");

            // 包含 IN 和 NOT IN
            if (cond.contains("IN")) {

                // if ("in".equals(qc.getCondition()) || "not
                // in".equals(qc.getCondition())) {

                // modi by zev at 2015年12月04日 10:32:03
                // 修改in 条件传参方式
                String[] con = qc.getConditionValue().split(",");

                String paramName = PARAM_NAME_PREFIX
                                   +cm.fieldName().toUpperCase();

                sb.append(" ( :"+paramName+" ) ");
                combo.getMap().put(paramName.trim(), Arrays.asList(con));

                // end modi

                /*
                 * sb.append(" ("); String[] con = qc.getConditionValue().split(","); String[]
                 * combCon = new String[con.length]; for (int i = 0; i < con.length; i++) { String
                 * paramName = PARAM_NAME_PREFIX + cm.fieldName().toUpperCase() + conSeq + i;
                 * combCon[i] = " :" + paramName; combo.getMap().put(paramName.trim(), con[i]); }
                 * sb.append(StringUtils.join(combCon, ",")); sb.append(" ) ");
                 */
            } else if (cond.contains("LIKE")) {
                // else if ("like".equals(qc.getCondition()) || "not
                // like".equals(qc.getCondition())) {

                String paramName = PARAM_NAME_PREFIX
                                   +cm.fieldName().toUpperCase()+conSeq;

                sb.append(" :"+paramName);

                combo.getMap().put(paramName.trim(),
                    !s.contains("%") ? ("%"+s+"%") : s);

            } else {
                String paramName = PARAM_NAME_PREFIX
                                   +cm.fieldName().toUpperCase()+conSeq;

                sb.append(" :"+paramName);

                combo.getMap().put(paramName.trim(), s);
            }

            combo.setQueryString(sb.toString());

        } catch (RuntimeException re) {
            // 仅打印异常//查询结果不对。 这里仅在开发阶段才会出现的问题，正式实施应该不会出现异常
            combo.setQueryString("");
            re.printStackTrace();

        }

        return combo;

    }

    /**
     * 主要功能: getConditionByField  根据名称获取条件<br>
     * 注意事项: 暂无<br>    
     *
     * @param lqc       查询条件集合
     * @param filedName 条件名称
     * @return QueryCondition  返回条件组合
     */
    public static QueryCondition getConditionByField(List<QueryCondition> lqc,
                                                     String filedName) {

        // 无条件的直接返回
        if (lqc==null||lqc.size()<1) {
            return null;
        }

        for (QueryCondition qc: lqc) {
            if (filedName.equals(qc.getFieldName())) {
                return qc;
            }
        }
        return null;
    }

    /**
     * 主要功能: isEmpty  判断查询条件是否为空<br>
     * 注意事项: 暂无<br>    
     *
     * @param qc  查询条件
     * @return boolean  查询条件是否为空
     */
    public static boolean isEmpty(QueryCondition qc) {
        boolean result = false;

        if (null==qc||StringUtils.isEmpty(qc.getConditionValue())) {
            result = true;
        }
        return result;
    }

}
