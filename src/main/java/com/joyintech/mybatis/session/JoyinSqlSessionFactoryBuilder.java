package com.joyintech.mybatis.session;


import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


/**
 * 名称：JoyinSqlSessionFactoryBuilder<br>
 * 描述：〈功能详细描述〉<br>
 * @author 杨松柏
 * @version 1.0
 * @since 1.0.0
 */
public class JoyinSqlSessionFactoryBuilder extends SqlSessionFactoryBuilder {
    @Override
    public SqlSessionFactory build(Configuration config) {
        return new JoyinSqlSessionFactory(config);
    }
}
