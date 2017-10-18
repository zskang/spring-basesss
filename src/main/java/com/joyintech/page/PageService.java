package com.joyintech.page;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.druid.pool.DruidDataSource;
import com.joyintech.common.enums.PagingType;
import com.joyintech.mapper.PageMapper;
import com.joyintech.mybatis.session.JoyinSqlSession;
import com.joyintech.mybatis.utils.MappedStatementUtil;


/**
 * 
 * 名称：分页服务类<br>
 * 描述：实现分页功能<br>
 * 
 * @author 汪瀚超
 * @version 1.0
 * @since 1.0.0
 */
@Service
public class PageService {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /**
     * SQL字符串参数
     */
    private static String SQL = "sql";

    /**
     * 开始行
     */
    private static String START_ROW = "startRow";

    /**
     * 结束行
     */
    private static String END_ROW = "endRow";

    /**
     * 
     * 主要功能:分页查询<br>
     * 注意事项:取得分页数据，并返回分页配置  <br>
     * 
     * @param <T> 返回结果类型
     * @param mapper 映射类
     * @param sqlId 映射配置文件中的sqlID
     * @param countSqlId 总件数查询sqlId（自定义分页方式）
     * @param params 参数Map
     * @param page 分页配置
     * @param pgType 分页方式
     * @return 分页列表
     */
    public <T> List<T> selectByPage(Class<?> mapper, String sqlId, String countSqlId, Map<String, Object> params, Page page, PagingType pgType) {

        List<T> list = null;

        JoyinSqlSession session = null;

        try {
            // 取得SqlSession
            session = (JoyinSqlSession)sqlSessionFactory.openSession();

            // 取得原Statement
            MappedStatement originalMappedStatement = sqlSessionFactory.getConfiguration().getMappedStatement(mapper.getName()+"."+sqlId);

            String paramSql = getParamSql(originalMappedStatement, params);

            // 取得总条数
            int total = getTotalCount(paramSql, mapper, countSqlId, params, pgType, session);

            // 设置总数据条数，并判断起始行是否大于总行数，如果是，返回最后一页数据
            page.setTotalRows(total);

            // 取得分页List
            list = getPageList(params, page, session, originalMappedStatement, paramSql);
        } finally {
            // 关闭session
            session.close();
        }

        return list;

    }

    /**
     * 
     * 主要功能:根据权限分页查询<br>
     * 注意事项:取得分页数据，并返回分页配置  <br>
     * 
     * @param <T> 返回结果类型
     * @param sql SQL字符串
     * @param mappedStatement statement
     * @param mapper 映射类
     * @param countSqlId 总件数查询sqlId（自定义分页方式）
     * @param params 参数Map
     * @param page 分页配置
     * @param pgType 分页方式
     * @return 分页列表
     */
    public <T> List<T> selectByPageForAuth(String sql, MappedStatement mappedStatement, Class<?> mapper, String countSqlId, Map<String, Object> params,
                                           Page page, PagingType pgType) {

        List<T> list = null;

        JoyinSqlSession session = null;

        try {
            // 取得SqlSession
            session = (JoyinSqlSession)sqlSessionFactory.openSession();

            // 取得总条数
            int total = getTotalCount(sql, mapper, countSqlId, params, pgType, session);

            // 设置总数据条数，并判断起始行是否大于总行数，如果是，返回最后一页数据
            page.setTotalRows(total);

            // 取得分页List
            list = getPageListForAuth(params, page, session, mappedStatement, sql);
        } finally {
            // 关闭session
            session.close();
        }

        return list;

    }

    /**
     * 主要功能:根据不同分页方式计算总数据条数<br>
     * 注意事项:无  <br>
     * 
     * @param sql SQL字符串
     * @param mapper 映射类
     * @param countSqlId 总件数查询sqlId
     * @param params 参数Map
     * @param pgType 分页方式
     * @param session session
     * @return 总条数
     */
    private int getTotalCount(String sql, Class<?> mapper, String countSqlId, Map<String, Object> params, PagingType pgType, DefaultSqlSession session) {
        // 总条数
        int total = 0;
        if (PagingType.DEFAULT_PAGING.equals(pgType)) {
            // 替换SQL的方式计算总数据条数
            total = getDefaultCount(params, sql, session);
        } else if (PagingType.ADVANCED_PAGING.equals(pgType)) {
            // 外层嵌套Count语句方式计算总数据条数
            total = getAdvancedCount(params, sql, session);
        } else if (PagingType.CUSTOM_PAGING.equals(pgType)) {
            // 自定义分页方式
            // 总件数查询Sql是否为空
            if (StringUtils.isEmpty(countSqlId)) {
                throw new PageException("自定义分页需要传入总件数查询SqlId");
            } else {
                // 取得查询件数的Statement
                MappedStatement countStatement = sqlSessionFactory.getConfiguration().getMappedStatement(mapper.getName()+"."+countSqlId);
                String countSql = getParamSql(countStatement, params);
                // 取得总数据条数
                total = getCustomCount(params, countSql, session);
            }
        }
        return total;
    }

    /**
     * 
     * 主要功能:默认分页查询<br>
     * 注意事项:取得分页数据，并返回分页配置  <br>
     * 
     * @param <T> 返回结果类型
     * @param mapper 映射类
     * @param sqlId 映射配置文件中的sqlID
     * @param params 参数Map
     * @param page 分页配置
     * @return 分页列表
     */
    public <T> List<T> selectByPage(Class<?> mapper, String sqlId, Map<String, Object> params, Page page) {
        // 使用默认分页方式
        return this.selectByPage(mapper, sqlId, null, params, page, PagingType.DEFAULT_PAGING);
    }

    /**
     * 
     * 主要功能:分页查询<br>
     * 注意事项:取得分页数据，并返回分页配置   <br>
     * 
     * @param <T> 返回结果类型
     * @param mapper 映射类
     * @param sqlId 映射配置文件中的sqlID
     * @param params 参数Map
     * @param page 分页配置
     * @param pgType 分页方式
     * @return 分页列表
     */
    public <T> List<T> selectByPage(Class<?> mapper, String sqlId, Map<String, Object> params, Page page, PagingType pgType) {
        return this.selectByPage(mapper, sqlId, null, params, page, pgType);
    }

    /**
     * 
     * 主要功能:获取分页列表<br>
     * 注意事项:根据分页配置及mapper中的sqlid，取得当前页的数据列表  <br>
     * 
     * @param <T> 返回结果类型
     * @param params 参数Map
     * @param page 分页配置
     * @param session session
     * @param originalMappedStatement Statement
     * @param paramSql SQL字符串
     * @return 分页列表
     */
    private <T> List<T> getPageList(Map<String, Object> params, Page page, JoyinSqlSession session, MappedStatement originalMappedStatement, String paramSql) {
        // 取得数据库类型
        String dbType = getDbType();
        // 取得分页Statement
        MappedStatement pageMappedStatement = sqlSessionFactory.getConfiguration().getMappedStatement(PageMapper.class.getName()+".selectByPage");

        // 取得原ResultMaps
        List<ResultMap> originalMapList = originalMappedStatement.getResultMaps();

        // 每次操作创建一个新的MappedStatement，避免冲突
        MappedStatement mappedStatement = new MappedStatement.Builder(sqlSessionFactory.getConfiguration(), pageMappedStatement.getId(),
            pageMappedStatement.getSqlSource(), pageMappedStatement.getSqlCommandType()).build();

        // 设置ResultMaps为原ResultMaps
        MappedStatementUtil.setResultMaps(mappedStatement, originalMapList);

        // 拼接分页SQL字符串
        String sql = getPagingSql(paramSql, dbType);

        if (null==params) {
            params = new HashMap<String, Object>();
        }
        // 增加分页参数
        params.put(SQL, sql);
        params.put(START_ROW, page.getStartRow());
        params.put(END_ROW, page.getEndRow());

        // 执行查询操作
        List<T> list = session.selectList(PageMapper.class.getName()+".selectByPage", params, mappedStatement);

        // 移除分页参数
        params.remove(SQL);
        params.remove(START_ROW);
        params.remove(END_ROW);

        return list;
    }

    /**
     * 
     * 主要功能:获取数据库类型<br>
     * 注意事项:无  <br>
     * 
     * @return 数据库类型
     */
    private String getDbType() {
        if (null==sqlSessionFactory) {
            return "";
        }
        DruidDataSource datasource = (DruidDataSource)sqlSessionFactory.getConfiguration().getEnvironment().getDataSource();
        if (null==datasource||StringUtils.isEmpty(datasource.getDbType())) {
            return "";
        }
        return datasource.getDbType().toUpperCase();
    }

    /**
     * 
     * 主要功能: 获取权限分页列表<br>
     * 注意事项:无  <br>
     * 
     * @param <T> 返回结果类型
     * @param params 参数Map
     * @param page 分页配置
     * @param session session
     * @param mappedStatement Statement
     * @param paramSql SQL字符串
     * @return 分页列表
     */
    private <T> List<T> getPageListForAuth(Map<String, Object> params, Page page, JoyinSqlSession session, MappedStatement mappedStatement, String paramSql) {
        // 取得数据库类型
        String dbType = getDbType();

        // 拼接分页SQL字符串
        String sql = getPagingSql(paramSql, dbType);

        if (null==params) {
            params = new HashMap<String, Object>();
        }
        // 增加分页参数
        params.put(SQL, sql);
        params.put(START_ROW, page.getStartRow());
        params.put(END_ROW, page.getEndRow());

        // 执行查询操作
        List<T> list = session.selectList(PageMapper.class.getName()+".selectByPage", params, mappedStatement);

        // 移除分页参数
        params.remove(SQL);
        params.remove(START_ROW);
        params.remove(END_ROW);

        return list;
    }

    /**
     * 
     * 主要功能:取得带参数的SQL字符串<br>
     * 注意事项:将原始SQL中的"?"重新替换为参数名称，如"{#id,jdbcType=VARCHAR}"  <br>
     * 
     * @param originalMappedStatement Statement
     * @param params 参数
     * @return 带参数的SQL字符串
     */
    private String getParamSql(MappedStatement originalMappedStatement, Map<String, Object> params) {
        // 取得原SQL字符串
        String originalSql = originalMappedStatement.getBoundSql(params).getSql();
        // 取得参数列表
        List<ParameterMapping> listParam = originalMappedStatement.getBoundSql(params).getParameterMappings();

        // 1 如果没有参数，说明是不是动态SQL语句
        int paramNum = 0;
        if (null!=listParam) {
            paramNum = listParam.size();
        }
        if (1>paramNum) {
            return originalSql;
        }
        // 2 如果有参数，则是动态SQL语句
        StringBuffer returnSQL = new StringBuffer();
        String[] subSQL = originalSql.split("[?]");
        for (int i = 0; i<paramNum; i++) {
            returnSQL.append(subSQL[i]);
            returnSQL.append("#{").append(listParam.get(i).getProperty());
            returnSQL.append(",jdbcType=");
            returnSQL.append(listParam.get(i).getJdbcType());
            returnSQL.append("}");
        }

        if (subSQL.length>listParam.size()) {
            returnSQL.append(subSQL[subSQL.length-1]);
        }
        return returnSQL.toString();
    }

    /**
     * 主要功能:取得总数据条数<br>
     * 注意事项:替换SQL的方式计算数据条数 <br>
     * 
     * @param params 参数
     * @param paramSql SQL字符串
     * @param session session
     * @return 数据条数
     */
    private int getDefaultCount(Map<String, Object> params, String paramSql, DefaultSqlSession session) {
        // 替换原SQL为检索件数

        paramSql = paramSql.replaceAll("\n", " ").replaceAll("\t", " ");
        int index = paramSql.toUpperCase().indexOf(" FROM ")+5;
        paramSql = "SELECT COUNT(1) FROM ".concat(paramSql.substring(index+1));
        if (null==params) {
            params = new HashMap<String, Object>();
        }
        params.put(SQL, paramSql);
        // 执行查询总条数
        int total = session.selectOne(PageMapper.class.getName()+".selectCount", params);
        params.remove(SQL);
        return total;
    }

    /**
     * 主要功能:取得总数据条数<br>
     * 注意事项:外层嵌套Count语句方式计算数据条数   <br>
     * 
     * @param params 参数
     * @param paramSql SQL字符串
     * @param session session
     * @return 数据条数
     */
    private int getAdvancedCount(Map<String, Object> params, String paramSql, DefaultSqlSession session) {
        // 替换原SQL为检索件数
        paramSql = paramSql.replaceAll("\n", " ");
        paramSql = "SELECT COUNT(1) FROM (".concat(paramSql).concat(")");

        if (null==params) {
            params = new HashMap<String, Object>();
        }

        params.put(SQL, paramSql);
        // 执行查询总条数
        int total = session.selectOne(PageMapper.class.getName()+".selectCount", params);
        params.remove(SQL);
        return total;
    }

    /**
     * 主要功能:取得总数据条数<br>
     * 注意事项:根据传入的countSql取得数据条数   <br>
     * 
     * @param params 参数
     * @param countSql SQL字符串
     * @param session session
     * @return 数据条数
     */
    private int getCustomCount(Map<String, Object> params, String countSql, DefaultSqlSession session) {
        if (null==params) {
            params = new HashMap<String, Object>();
        }
        // 替换原SQL为检索件数
        params.put(SQL, countSql);
        // 执行查询总条数
        int total = session.selectOne(PageMapper.class.getName()+".selectCount", params);
        params.remove(SQL);
        return total;
    }

    /**
     * 
     * 主要功能:拼接分页SQL字符串<br>
     * 注意事项:无 <br>
     * 
     * @param originalSql
     *            初始SQL字符串
     * @param dbType
     *            数据库类型
     * @return 分页SQL字符串
     */
    private String getPagingSql(String originalSql, String dbType) {
        if (StringUtils.isEmpty(dbType)||StringUtils.isEmpty(originalSql)) {
            return "";
        }
        // 拼接的分页SQL
        String sqlQuery = "";
        // 根据不同数据库类型进行不同方式的拼接
        if ("ORACLE".equals(dbType)) {
            // 数据库类型为Oracle
            sqlQuery = "SELECT * FROM (SELECT JY_PAGE_TBL.*, ROWNUM RN  FROM ("+originalSql
                       +") JY_PAGE_TBL  WHERE ROWNUM <= #{endRow,jdbcType=INTEGER} ) WHERE RN > #{startRow,jdbcType=INTEGER}";

        } else if ("DB2".equals(dbType)) {
            // 数据库类型为DB2
            sqlQuery = "SELECT * FROM (SELECT JY_PAGE_TBL.* ,ROWNUMBER() OVER() AS ROWNUM FROM ("+originalSql
                       +") JY_PAGE_TBL) WHERE ROWNUM <= #{endRow,jdbcType=INTEGER} AND ROWNUM > #{startRow,jdbcType=INTEGER}";

        } else {
            // 其他类型数据库处理后续再进行添加
        }
        return sqlQuery;
    }
}
