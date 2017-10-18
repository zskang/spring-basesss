/**
 * 项目名称:   spring_base        	<br>
 * 包  名 称:   com.joyintech.base.exception   	<br>
 * 文件名称:   PersistenceExceptionHandler.java     <br>
 *
 * 修改履历:
 *       日期                            修正者        主要内容   <br>
 *       2017年2月9日            张中伟        初版做成    <br>
 *
 * Copyright (c) 2007-2017 兆尹科技
 */
package com.joyintech.base.exception;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.builder.IncompleteElementException;
import org.apache.ibatis.cache.CacheException;
import org.apache.ibatis.datasource.DataSourceException;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.apache.ibatis.executor.BatchExecutorException;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.result.ResultMapException;
import org.apache.ibatis.logging.LogException;
import org.apache.ibatis.parsing.ParsingException;
import org.apache.ibatis.plugin.PluginException;
import org.apache.ibatis.scripting.ScriptingException;
import org.apache.ibatis.session.SqlSessionException;
import org.apache.ibatis.transaction.TransactionException;
import org.apache.ibatis.type.TypeException;


/**
 * 名称：PersistenceExceptionHandler <br>
 * 描述：转换MyBatis异常为基本异常 BaseDaoException<br>
 * @author 张中伟
 * @version 1.0
 * @since 1.0.0
 */
public class PersistenceExceptionHandler {

    /**
     * 日志记录
     */
    private static final Log LOGGER = LogFactory.getLog(
        PersistenceExceptionHandler.class);

    /**
     * 
     * 主要功能: 解析Mybatis的异常，并分类处理生成异常编码和信息    <br>
     * 注意事项: 这些异常很多是因为开发阶段产生的异常。 少数是运行阶段拼写SQL生成的异常。  <br>
     * bug处理完以后，很多异常是不会传递到用户那边的。 <br>
     * 
     * @param pe  mybatis的PersistenceException 
     * @return MybatisException 转换为MybatisException
     */
    public static MybatisException handlerException(PersistenceException pe) {

        // 异常记录到日志 （格式待优化）
        LOGGER.error(pe.getStackTrace());

        // 构造MybatisException

        /**
         * 按PersistenceException的子类  从下往上分析类，转换异常信息
         */

        if (pe instanceof TypeException) {
            return genExceptionInfo("类型错误", "JYN-02003", "mybatis的类型转换异常", pe);
        }

        if (pe instanceof TransactionException) {
            return genExceptionInfo("事务异常", "JYN-02004", "mybatis的事务异常", pe);

        }

        if (pe instanceof TooManyResultsException) {
            return genExceptionInfo("返回结果过多", "JYN-02005", "返回结果多于预期结果", pe);
        }

        if (pe instanceof SqlSessionException) {
            return genExceptionInfo("sql会话异常", "JYN-02006", "sql会话出现异常", pe);
        }

        if (pe instanceof PluginException) {
            return genExceptionInfo("反射异常", "JYN-02007", "JAVA反射异常", pe);
        }

        if (pe instanceof ParsingException) {
            return genExceptionInfo("解析异常", "JYN-02008", "结果解析异常", pe);
        }

        if (pe instanceof LogException) {
            return genExceptionInfo("记录异常", "JYN-02009", "mybatis Log异常", pe);
        }

        // 是 ExecutorException 的子类
        if (pe instanceof BatchExecutorException) {
            return genExceptionInfo("批量执行异常", "JYN-02010", "批量执行异常", pe);
        }

        if (pe instanceof ExecutorException) {
            return genExceptionInfo("执行异常", "JYN-02011", "执行异常", pe);
        }

        // 理论上数据源异常包括网络断开、连接池已满等 （mybatis 未细分）
        if (pe instanceof DataSourceException) {
            return genExceptionInfo("数据源异常", "JYN-02012", "数据源异常", pe);
        }

        // 未配置mybatis 缓存，所以缓存异常理论上不存在。
        if (pe instanceof CacheException) {
            return genExceptionInfo("缓存异常", "JYN-02013", "mybatis缓存异常", pe);
        }

        if (pe instanceof IncompleteElementException) {
            return genExceptionInfo("元素不完整", "JYN-02014", "元素不完整异常", pe);
        }

        if (pe instanceof BuilderException) {
            return genExceptionInfo("SQL构建异常", "JYN-02015", "SQL构建异常", pe);
        }

        if (pe instanceof BindingException) {
            return genExceptionInfo("参数绑定异常", "JYN-02016", "参数绑定异常", pe);
        }

        if (pe instanceof ScriptingException) {
            return genExceptionInfo("脚本异常", "JYN-02017", "脚本异常", pe);
        }

        if (pe instanceof ResultMapException) {
            return genExceptionInfo("结果映射异常", "JYN-02018", "结果映射异常", pe);
        }

        // 如果没处理到，生成一个未知异常。
        return genExceptionInfo("mybatis异常", "JYN-02999", "mybatis的其他异常", pe);

    }

    /**
     * 
     * 主要功能: 生成 Mybatis异常    <br>
     * 注意事项:无  <br>
     * 
     * @param title   简单标题
     * @param code    编码
     * @param message  提示信息
     * @param exception 原异常
     * @return  MybatisException
     */
    private static MybatisException genExceptionInfo(String title, String code,
                                                     String message,
                                                     Throwable exception) {

        MybatisException mybatisException = new MybatisException(message,
            exception);
        mybatisException.setExceptionCode(code);
        mybatisException.setExceptionTitle(title);

        return mybatisException;

    }

}
