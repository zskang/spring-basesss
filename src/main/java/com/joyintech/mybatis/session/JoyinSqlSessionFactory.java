package com.joyintech.mybatis.session;


import java.sql.Connection;
import java.sql.SQLException;

import org.apache.ibatis.exceptions.ExceptionFactory;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;


/**
 * 名称：JoyinSqlSessionFactory<br>
 * 描述：〈功能详细描述〉<br>
 * @author 杨松柏
 * @version 1.0
 * @since 1.0.0
 */
public class JoyinSqlSessionFactory implements SqlSessionFactory {

    public JoyinSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    private final Configuration configuration;

    @Override
    public SqlSession openSession() {
        return openSessionFromDataSource(configuration.getDefaultExecutorType(), null, false);
    }

    @Override
    public SqlSession openSession(boolean autoCommit) {
        return openSessionFromDataSource(configuration.getDefaultExecutorType(), null, autoCommit);
    }

    @Override
    public SqlSession openSession(ExecutorType execType) {
        return openSessionFromDataSource(execType, null, false);
    }

    @Override
    public SqlSession openSession(TransactionIsolationLevel level) {
        return openSessionFromDataSource(configuration.getDefaultExecutorType(), level, false);
    }

    @Override
    public SqlSession openSession(ExecutorType execType, TransactionIsolationLevel level) {
        return openSessionFromDataSource(execType, level, false);
    }

    @Override
    public SqlSession openSession(ExecutorType execType, boolean autoCommit) {
        return openSessionFromDataSource(execType, null, autoCommit);
    }

    @Override
    public SqlSession openSession(Connection connection) {
        return openSessionFromConnection(configuration.getDefaultExecutorType(), connection);
    }

    @Override
    public SqlSession openSession(ExecutorType execType, Connection connection) {
        return openSessionFromConnection(execType, connection);
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    /**
     * 主要功能:SqlSession取得<br>
     * 注意事项:无  <br>
     * 
     * @param execType 类型
     * @param level 级别
     * @param autoCommit 自动提交标识
     * @return SqlSession
     */
    private SqlSession openSessionFromDataSource(ExecutorType execType, TransactionIsolationLevel level, boolean autoCommit) {
        Transaction tx = null;
        try {
            final Environment environment = configuration.getEnvironment();
            final TransactionFactory transactionFactory = getTransactionFactoryFromEnvironment(environment);
            tx = transactionFactory.newTransaction(environment.getDataSource(), level, autoCommit);
            final Executor executor = configuration.newExecutor(tx, execType);
            return new JoyinSqlSession(configuration, executor, autoCommit);
        } catch (Exception e) {
            closeTransaction(tx); // may have fetched a connection so lets call close()
            throw ExceptionFactory.wrapException("Error opening session.  Cause: "+e, e);
        } finally {
            ErrorContext.instance().reset();
        }
    }

    /**
     * 主要功能:SqlSession取得 <br>
     * 注意事项:无  <br>
     * 
     * @param execType 类型
     * @param connection 连接
     * @return SqlSession
     */
    private SqlSession openSessionFromConnection(ExecutorType execType, Connection connection) {
        try {
            boolean autoCommit;
            try {
                autoCommit = connection.getAutoCommit();
            } catch (SQLException e) {
                // Failover to true, as most poor drivers
                // or databases won't support transactions
                autoCommit = true;
            }
            final Environment environment = configuration.getEnvironment();
            final TransactionFactory transactionFactory = getTransactionFactoryFromEnvironment(environment);
            final Transaction tx = transactionFactory.newTransaction(connection);
            final Executor executor = configuration.newExecutor(tx, execType);
            return new JoyinSqlSession(configuration, executor, autoCommit);
        } catch (Exception e) {
            throw ExceptionFactory.wrapException("Error opening session.  Cause: "+e, e);
        } finally {
            ErrorContext.instance().reset();
        }
    }

    /**
     * 主要功能:事务工厂取得 <br>
     * 注意事项:无  <br>
     * 
     * @param environment 变量
     * @return 事务工厂
     */
    private TransactionFactory getTransactionFactoryFromEnvironment(Environment environment) {
        if (environment==null||environment.getTransactionFactory()==null) {
            return new ManagedTransactionFactory();
        }
        return environment.getTransactionFactory();
    }

    /**
     * 主要功能:关闭事务 <br>
     * 注意事项:无  <br>
     * 
     * @param tx 事物
     */
    private void closeTransaction(Transaction tx) {
        if (tx!=null) {
            try {
                tx.close();
            } catch (SQLException ignore) {
                // Intentionally ignore. Prefer previous error.
            }
        }
    }
}
