package com.joyintech.mybatis.utils;


import java.lang.reflect.Field;
import java.util.List;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;

import com.joyintech.base.exception.MybatisException;


/**
 * 名称：Mybatis配置设置工具类<br>
 * @author 杨松柏
 * @version 1.0
 * @since 1.0.0
 */
public class MappedStatementUtil {

    /**
     * 主要功能:设置sql的返回值 <br>
     * 注意事项:无  <br>
     * 
     * @param mappedStatement 配置类
     * @param resultMapsIn 返回结果map
     */
    public static void setResultMaps(MappedStatement mappedStatement, List<ResultMap> resultMapsIn) {
        Field field;
        try {
            field = mappedStatement.getClass().getDeclaredField("resultMaps");
            field.setAccessible(true);
            field.set(mappedStatement, resultMapsIn);
        } catch (IllegalAccessException e) {
            throw new MybatisException(e.getMessage());
        } catch (NoSuchFieldException e1) {
            throw new MybatisException(e1.getMessage());
        }
    }
}
