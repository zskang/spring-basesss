package com.joyintech.auth;


import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joyintech.common.enums.PagingType;
import com.joyintech.mapper.DataAuthMapper;
import com.joyintech.mapper.PageMapper;
import com.joyintech.mybatis.session.JoyinSqlSession;
import com.joyintech.mybatis.utils.MappedStatementUtil;
import com.joyintech.page.Page;
import com.joyintech.page.PageService;
import com.joyintech.utils.StringUtils;


/**
 * 
 * 名称：数据权限服务类<br>
 * 描述：实现数据权限过滤功能<br>
 * 
 * @author 杨松柏
 * @version 1.0
 * @since 1.0.0
 */
@Service
public class DataAuthService {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private PageService pageService;

    // @Autowired
    private HttpSession session;

    /**
     * session里面保存的数据权限信息。
     */
    private AuthInfo authInfo;

    /**
     * 属性文件配置的数据权限字段。
     */
    private List<String> lstFiledName;

    public DataAuthService() {
        if (session!=null) {
            authInfo = (AuthInfo)session.getAttribute("loginInfo");
        }
    }

    /**
     * 数据权限过滤检索。<br>
     * 
     * @param <T>
     *            返回结果类型。
     * @param mapper
     *            检索mapper。
     * @param sqlId
     *            检索sqlId。
     * @param params
     *            传入的检索参宿。
     * @param mainTableDto
     *            需要权限过滤的主表。
     * @return List 数据权限过滤后的数据。
     */
    public <T> List<T> select(Class<?> mapper, String sqlId, Map<String, Object> params, Class<?> mainTableDto) {
        // 取得原Statement
        MappedStatement originalMappedStatement = sqlSessionFactory.getConfiguration().getMappedStatement(mapper.getName()+"."+sqlId);

        Map<String, Object> mapParams = new HashMap<String, Object>();

        // 拼接权限SQL字符串
        String sql = getDataAuthSql(originalMappedStatement, params, mapParams, mainTableDto);

        // 取得权限Statement
        MappedStatement pageMappedStatement = sqlSessionFactory.getConfiguration().getMappedStatement(PageMapper.class.getName()+".selectByAuthInfo");

        // 每次操作创建一个新的MappedStatement，避免冲突
        MappedStatement mappedStatement = new MappedStatement.Builder(sqlSessionFactory.getConfiguration(), pageMappedStatement.getId(),
            pageMappedStatement.getSqlSource(), pageMappedStatement.getSqlCommandType()).build();

        // 取得原ResultMaps
        List<ResultMap> originalMapList = originalMappedStatement.getResultMaps();
        // 设置ResultMaps为原ResultMaps
        // mappedStatement.setResultMaps(originalMapList);
        MappedStatementUtil.setResultMaps(mappedStatement, originalMapList);

        // 传入拼接后的sql
        params.put("sql", sql);

        // 取得SqlSession
        JoyinSqlSession session = (JoyinSqlSession)sqlSessionFactory.openSession();
        // 执行查询操作
        List<T> list = session.selectList(DataAuthMapper.class.getName()+".selectByAuthInfo", params, mappedStatement);

        return list;
    }

    /**
     * 数据权限过滤的分页检索。<br>
     * 
     * @param <T>
     *            返回结果类型。
     * @param mapper
     *            检索mapper。
     * @param sqlId
     *            检索sqlId。
     * @param params
     *            传入的检索参宿。
     * @param mainTableDto
     *            需要权限过滤的主表。
     * @param page
     *            分页配置
     * @param pgType
     *            分页类型
     * @return 数据权限过滤后的分页列表。
     */
    public <T> List<T> selectByPage(Class<?> mapper, String sqlId, String countSqlId, Map<String, Object> params, Class<?> mainTableDto, Page page,
                                    PagingType pgType) {
        // 取得原Statement
        MappedStatement originalMappedStatement = sqlSessionFactory.getConfiguration().getMappedStatement(mapper.getName()+"."+sqlId);

        Map<String, Object> mapParams = new HashMap<String, Object>();

        // 拼接权限SQL字符串
        String sql = getDataAuthSql(originalMappedStatement, params, mapParams, mainTableDto);

        // 取得权限Statement
        MappedStatement pageMappedStatement = sqlSessionFactory.getConfiguration().getMappedStatement(PageMapper.class.getName()+".selectByAuthInfo");

        // 每次操作创建一个新的MappedStatement，避免冲突
        MappedStatement mappedStatement = new MappedStatement.Builder(sqlSessionFactory.getConfiguration(), pageMappedStatement.getId(),
            pageMappedStatement.getSqlSource(), pageMappedStatement.getSqlCommandType()).build();

        // 取得原ResultMaps
        List<ResultMap> originalMapList = originalMappedStatement.getResultMaps();
        // 设置ResultMaps为原ResultMaps
        // mappedStatement.setResultMaps(originalMapList);
        MappedStatementUtil.setResultMaps(mappedStatement, originalMapList);

        // 执行查询操作
        List<T> list = pageService.selectByPageForAuth(sql, mappedStatement, mapper, countSqlId, mapParams, page, pgType);
        return list;
    }

    /**
     * 
     * 主要功能:默认权限过滤的分页查询<br>
     * 注意事项:取得权限过滤后的分页数据，并返回分页配置 <br>
     * 
     * @param <T>
     *            返回结果类型
     * @param mapper
     *            映射类
     * @param sqlId
     *            映射配置文件中的sqlID
     * @param params
     *            参数Map
     * @param mainTableDto
     *            权限数据过滤主表
     * @param page
     *            分页配置
     * @return 分页列表
     */
    public <T> List<T> selectByPage(Class<?> mapper, String sqlId, Map<String, Object> params, Class<?> mainTableDto, Page page) {
        // 使用默认分页方式
        return this.selectByPage(mapper, sqlId, null, params, mainTableDto, page, PagingType.DEFAULT_PAGING);
    }

    /**
     * 
     * 主要功能:权限过滤的分页查询<br>
     * 注意事项:取得权限过滤后的分页数据，并返回分页配置 <br>
     * 
     * @param <T>
     *            返回结果类型
     * @param mapper
     *            映射类
     * @param sqlId
     *            映射配置文件中的sqlID
     * @param params
     *            参数Map
     * @param mainTableDto
     *            权限数据过滤主表
     * @param page
     *            分页配置
     * @param pgType
     *            分页方式
     * @return 分页列表
     */
    public <T> List<T> selectByPage(Class<?> mapper, String sqlId, Map<String, Object> params, Class<?> mainTableDto, Page page, PagingType pgType) {
        return this.selectByPage(mapper, sqlId, null, params, mainTableDto, page, pgType);
    }

    /**
     * 取得追加数据权限过滤的sql。
     * 
     * @param originalMappedStatement
     *            mapper信息类。
     * @param inParams
     *            输入的参数。
     * @param outParams
     *            输出的参数。
     * @param mainTableDto
     *            需要权限过滤的主类。
     * @return 加工后的Sql。
     */
    private String getDataAuthSql(MappedStatement originalMappedStatement, Map<String, Object> inParams, Map<String, Object> outParams, Class<?> mainTableDto) {

        String paramSql = getParamSql(originalMappedStatement, inParams);

        // 拷贝新的map
        copyMap(outParams, inParams);

        List<String> lstField = matchParam(mainTableDto);

        // 根据传入的主表，匹配主表存在的权限字段
        getBeanValueToMap(authInfo, outParams, lstField);

        // 拼接权限SQL字符串
        String sql = editDataAuthSql(paramSql, StringUtils.getTableNameByDto(mainTableDto), lstField, outParams);

        return sql;
    }

    /**
     * 拼接数据权限过滤sql。<br>
     * 
     * @param strSql
     *            传入的数据检索Sql语句。
     * @param mainTableName
     *            需要做数据权限过滤的主表。
     * @param lstField
     *            数据权限字段。
     * @param paramMap
     *            sql执行参数。
     * @return 数据权限过滤sql。
     */
    private String editDataAuthSql(String strSql, String mainTableName, List<String> lstField, Map<String, Object> paramMap) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("(SELECT * FROM ");
        buffer.append(mainTableName);
        buffer.append(" WHERE ");
        buffer.append(getWhere(lstField, paramMap));
        buffer.append(" ) ");
        return strSql.replace(mainTableName, buffer.toString());
    }

    /**
     * 拼接数据权限过滤条件。<br>
     * 
     * @param lstField
     *            权限字段列表
     * @param paramMap
     *            sql参数
     * @return 权限过滤语句。
     */
    private String getWhere(List<String> lstField, Map<String, Object> paramMap) {
        StringBuffer buffer = new StringBuffer();
        int i = 0;
        for (String field: lstField) {
            if (i!=0) {
                buffer.append(" AND ");
            }
            String type = paramMap.get(field).getClass().getName();
            if (isList(type)) {
                @SuppressWarnings("unchecked")
                List<Object> lstValues = (List<Object>)paramMap.get(field);
                buffer.append(StringUtils.chgCapLetterToUnderLine(field));
                buffer.append(" IN (");
                int size = lstValues.size();
                int indexList = 0;
                for (Object obj: lstValues) {
                    if (indexList!=0&&indexList%1000==0) {
                        buffer.append(") OR ");
                        buffer.append(StringUtils.chgCapLetterToUnderLine(field));
                        buffer.append(" IN (");
                    } else if (indexList!=0&&indexList<size) {
                        buffer.append(" , ");
                    }
                    buffer.append(getWhereJdbcType(field.concat(String.valueOf(indexList)), obj.getClass().getName()));

                    indexList++;
                }
                buffer.append(" )");
            } else {
                buffer.append(StringUtils.chgCapLetterToUnderLine(field));
                buffer.append(" = ");
                buffer.append(getWhereJdbcType(field, type));
            }
            i++;
        }
        return buffer.toString();

    }

    /**
     * 拼接sql里where字段语句。
     * 
     * @param fieldName
     *            条件字段
     * @param type
     *            字段类型
     * @return where字段语句
     */
    private String getWhereJdbcType(String fieldName, String type) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(" #{");
        buffer.append(fieldName);
        buffer.append(",jdbcType=");
        buffer.append(getJdbcType(type));
        buffer.append("}");
        return buffer.toString();
    }

    /**
     * 判断类型是否为List。
     * 
     * @param type
     *            字段类型
     * @return boolean 是/否
     */
    private boolean isList(String type) {
        try {
            return List.class.isAssignableFrom(Class.forName(type));
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * 通过java类型返回jdbc类型。
     * 
     * @param type
     *            java类型
     * @return jdbc类型
     */
    private String getJdbcType(String type) {
        if (isTypeEq(int.class, type)||isTypeEq(Integer.class, type)) {
            return JdbcType.INTEGER.name();
        } else if (isTypeEq(long.class, type)||isTypeEq(Long.class, type)) {
            return JdbcType.NUMERIC.name();
        } else if (isTypeEq(BigDecimal.class, type)) {
            return JdbcType.NUMERIC.name();
        } else if (isTypeEq(Date.class, type)) {
            return JdbcType.DATE.name();
        } else if (isTypeEq(String.class, type)) {
            return JdbcType.VARCHAR.name();
        } else {
            return JdbcType.VARCHAR.name();
        }
    }

    /**
     * 字段类型相等判断。
     * 
     * @param typeClass
     *            类型类。
     * @param type
     *            字段类型。
     * @return boolean 是/否
     */
    private boolean isTypeEq(Class<?> typeClass, String type) {
        return typeClass.getName().equals(type);
    }

    /**
     * 取得需要数据权限过滤的sql，并拼接字段条件。<br>
     * 
     * @param originalMappedStatement
     *            mapper数据情报。
     * @param params
     *            传入参数。
     * @return 拼接字段参数的sql
     */
    private String getParamSql(MappedStatement originalMappedStatement, Map<String, Object> params) {
        BoundSql boundSql = originalMappedStatement.getBoundSql(params);
        // 取得原SQL字符串
        String originalSql = boundSql.getSql();
        // 取得参数列表
        List<ParameterMapping> listParam = boundSql.getParameterMappings();

        int paramNum = listParam.size();
        String strSql = originalSql;
        if (paramNum!=0) {
            StringBuffer returnSQL = new StringBuffer();
            String[] subSQL = originalSql.split("[?]");
            for (int i = 0; i<paramNum; i++) {
                returnSQL.append(subSQL[i]);
                returnSQL.append(getWhereJdbcType(listParam.get(i).getProperty(), listParam.get(i).getJdbcType().name()));
            }

            if (subSQL.length>listParam.size()) {
                returnSQL.append(subSQL[subSQL.length-1]);
            }
            strSql = returnSQL.toString();
        }
        return strSql;
    }

    /**
     * 匹配属性文件配置的权限字段哪些在主表里面存在。<br>
     * 
     * @param mainTableDto
     *            主表DTO
     * @return 主表拥有的权限字段
     */
    private List<String> matchParam(Class<?> mainTableDto) {
        List<String> lstField = new ArrayList<String>();
        Field[] fields = mainTableDto.getDeclaredFields();
        for (Field field: fields) {
            if (lstFiledName.contains(field.getName())) {
                lstField.add(field.getName());
            }
        }
        return lstField;
    }

    /**
     * 匹配权限信息，过滤主表的权限字段，编辑参数信息。
     * 
     * @param authInfo
     *            权限情报
     * @param outMap
     *            out 返回的sql参数
     * @param lstFiledName
     *            in/out 过滤实际使用的权限数据。
     */
    private void getBeanValueToMap(AuthInfo authInfo, Map<String, Object> outMap, List<String> lstFiledName) {
        Field[] fields = authInfo.getClass().getDeclaredFields();
        for (Field field: fields) {
            String strFieldName = field.getName();
            if (lstFiledName.contains(strFieldName)) {
                field.setAccessible(true);
                Object obj = null;
                try {
                    obj = field.get(authInfo);
                } catch (IllegalArgumentException e) {
                    throw e;
                } catch (IllegalAccessException e) {
                    lstFiledName.remove(strFieldName);
                }
                if (obj==null) {
                    lstFiledName.remove(strFieldName);
                } else {
                    if (field.getType().getName().equals(List.class.getName())) {
                        @SuppressWarnings("unchecked")
                        List<Object> lstObj = (List<Object>)obj;
                        if (lstObj.size()==0) {
                            lstFiledName.remove(strFieldName);
                        } else {
                            int i = 0;
                            for (Object tmpObj: lstObj) {
                                outMap.put(strFieldName.concat(String.valueOf(i)), tmpObj);
                                i++;
                            }
                        }

                    }
                    outMap.put(field.getName(), obj);
                }

            }
        }
    }

    /**
     * 参数拷贝
     * 
     * @param outMap
     *            输出map。
     * @param inMap
     *            输入map。
     */
    private void copyMap(Map<String, Object> outMap, Map<String, Object> inMap) {
        for (String key: inMap.keySet()) {
            outMap.put(key, inMap.get(key));
        }
    }

    // public static void main(String[] str) {
    // // AuthInfo authInfo = new AuthInfo();
    // // authInfo.setUserId("asdfsdf");
    // // Map<String, Object> outMap = new HashMap<String, Object>();
    // // DataAuthService authService = new DataAuthService();
    // // authService.lstFiledName = new ArrayList<String>();
    // // authService.lstFiledName.add("userId");
    // // authService.getBeanValueToMap(authInfo, outMap,
    // // authService.lstFiledName);
    // // System.out.println(outMap.get(outMap.keySet().iterator().next()));
    // // System.out.println(StringUtils.getTableNameByDto(AuthInfo.class));
    // // Field[] fields = Page.class.getDeclaredFields();
    // // for (Field field : fields) {
    // // System.out.println(field.getType().getName());
    // // }
    // Map<String, String> mapType = new HashMap<String, String>();
    // mapType.put("aaaa0Tensgt", String.class.getName());
    // mapType.put("aaaa1Tensgt", Integer.class.getName());
    // mapType.put("aaaa2Tensgt", Date.class.getName());
    // mapType.put("aaaa3Tensgt", BigDecimal.class.getName());
    // List<String> lstValue = new ArrayList<String>();
    // lstValue.add("aaaa0Tensgt");
    // lstValue.add("aaaa1Tensgt");
    // lstValue.add("aaaa2Tensgt");
    // lstValue.add("aaaa3Tensgt");
    // lstValue.add("aaaa4Tensgt");
    // List<Integer> lstValueInt = new ArrayList<Integer>();
    // for (int i = 0; i < 2001; i++) {
    // lstValueInt.add(i);
    // }
    //
    // lstValueInt.add(123123);
    // lstValueInt.add(41241241);
    // lstValueInt.add(123241123);
    // lstValueInt.add(12412312);
    // lstValueInt.add(1421312);
    // mapType.put("aaaa4Tensgt", List.class.getName());
    //
    // Map<String, Object> mapParam = new HashMap<String, Object>();
    // mapParam.put("aaaa4Tensgt", lstValueInt);
    // mapParam.put("aaaa0Tensgt", String.class.getName());
    // mapParam.put("aaaa1Tensgt", Integer.class.getName());
    // mapParam.put("aaaa2Tensgt", Date.class.getName());
    // mapParam.put("aaaa3Tensgt", BigDecimal.class.getName());
    // DataAuthService authService = new DataAuthService();
    // System.out.println(authService.getWhere(lstValue, mapParam));
    // }

}
