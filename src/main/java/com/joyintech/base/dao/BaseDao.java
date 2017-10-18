/*
 * 项目名称:spring_base
 * 类名称:BaseDao.java
 * 包名称:com.joyintech.base
 *
 * 修改履历:
 *       日期                            修正者        主要内容
 *       2016年12月16日          zzw88         初版做成
 *
 * Copyright (c) 2016-2017 兆尹科技
 */

package com.joyintech.base.dao;


import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


/**
 * 名称： 基本DAO公共类<br>
 * 描述： 基本DAO公共类<br>
 * @author 张中伟
 * @version 1.0
 * @since 1.0.0
 */
public class BaseDao extends NamedParameterJdbcDaoSupport {

    /**
     * 日志记录
     */
    private static final Log LOGGER = LogFactory.getLog(BaseDao.class);

    /**
     *  简化类名  NamedParameterJdbcTemplate
     */
    private NamedParameterJdbcTemplate npjt;

    /**
    * 
    * 主要功能: 注入DataSource<br>
    * 注意事项: 使用Autowired注解自动注入<br>
    * 
    * @param dataSource  数据源bean, 配置到配置文件中
    * 
    */
    @Autowired
    public void injiectDataSource(DataSource dataSource) {

        super.setDataSource(dataSource);

        LOGGER.info("数据源已注入");

    }

    /**
     * 取得npjt的值  npjt为NamedParameterJdbcTemplate 简化写法
     *
     * @return npjt值.
     */
    public NamedParameterJdbcTemplate getNpjt() {
        this.npjt = super.getNamedParameterJdbcTemplate();
        return this.npjt;
    }

    /**
     * TODO: 添加相关公共类及方法
     *              1  分页方法 （通用分页、自定义count分页、全自定义分）
     *              2  SQL语句拼装方法（增、删、改、查）
     *              3  ORM注解( @Table @Column )
     */

}
