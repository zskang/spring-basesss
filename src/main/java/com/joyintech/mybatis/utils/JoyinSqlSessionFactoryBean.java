package com.joyintech.mybatis.utils;


import static org.springframework.util.Assert.notNull;
import static org.springframework.util.Assert.state;
import static org.springframework.util.ObjectUtils.isEmpty;
import static org.springframework.util.StringUtils.hasLength;
import static org.springframework.util.StringUtils.tokenizeToStringArray;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.io.VFS;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.NestedIOException;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import com.joyintech.mybatis.session.JoyinSqlSessionFactoryBuilder;


/**
 * 名称：JoyinSqlSessionFactoryBean<br>
 * 描述：〈功能详细描述〉<br>
 * @author 杨松柏
 * @version 1.0
 * @since 1.0.0
 */
public class JoyinSqlSessionFactoryBean extends SqlSessionFactoryBean {

    private static final Log LOGGER = LogFactory.getLog(JoyinSqlSessionFactoryBean.class);

    private Resource configLocation;

    private Configuration configuration;

    private Resource[] mapperLocations;

    private DataSource dataSource;

    private TransactionFactory transactionFactory;

    private Properties configurationProperties;

    private SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new JoyinSqlSessionFactoryBuilder();

    private SqlSessionFactory sqlSessionFactory;

    // EnvironmentAware requires spring 3.1
    private String environment = SqlSessionFactoryBean.class.getSimpleName();

    private Interceptor[] plugins;

    private TypeHandler<?>[] typeHandlers;

    private String typeHandlersPackage;

    private Class<?>[] typeAliases;

    private String typeAliasesPackage;

    private Class<?> typeAliasesSuperType;

    // issue #19. No default provider.
    private DatabaseIdProvider databaseIdProvider;

    private Class<? extends VFS> vfs;

    private Cache cache;

    private ObjectFactory objectFactory;

    private ObjectWrapperFactory objectWrapperFactory;

    /*
     * (non-Javadoc)
     * @see org.mybatis.spring.SqlSessionFactoryBean#setCache(org.apache.ibatis.cache.Cache)
     */
    @Override
    public void setCache(Cache cache) {
        super.setCache(cache);
        this.cache = cache;
    }

    /* (non-Javadoc)
     * @see org.mybatis.spring.SqlSessionFactoryBean#setPlugins(org.apache.ibatis.plugin.Interceptor[])
     */
    @Override
    public void setPlugins(Interceptor[] plugins) {
        super.setPlugins(plugins);
        this.plugins = plugins;
    }

    /* (non-Javadoc)
     * @see org.mybatis.spring.SqlSessionFactoryBean#setTypeAliasesPackage(java.lang.String)
     */
    @Override
    public void setTypeAliasesPackage(String typeAliasesPackage) {
        super.setTypeAliasesPackage(typeAliasesPackage);
        this.typeAliasesPackage = typeAliasesPackage;
    }

    /* (non-Javadoc)
     * @see org.mybatis.spring.SqlSessionFactoryBean#setTypeAliasesSuperType(java.lang.Class)
     */
    @Override
    public void setTypeAliasesSuperType(Class<?> typeAliasesSuperType) {
        super.setTypeAliasesSuperType(typeAliasesSuperType);
        this.typeAliasesSuperType = typeAliasesSuperType;
    }

    /* (non-Javadoc)
     * @see org.mybatis.spring.SqlSessionFactoryBean#setTypeHandlersPackage(java.lang.String)
     */
    @Override
    public void setTypeHandlersPackage(String typeHandlersPackage) {
        super.setTypeHandlersPackage(typeHandlersPackage);
        this.typeHandlersPackage = typeHandlersPackage;
    }

    /* (non-Javadoc)
     * @see org.mybatis.spring.SqlSessionFactoryBean#setTypeHandlers(org.apache.ibatis.type.TypeHandler[])
     */
    @Override
    public void setTypeHandlers(TypeHandler<?>[] typeHandlers) {
        super.setTypeHandlers(typeHandlers);
        this.typeHandlers = typeHandlers;
    }

    /* (non-Javadoc)
     * @see org.mybatis.spring.SqlSessionFactoryBean#setTypeAliases(java.lang.Class[])
     */
    @Override
    public void setTypeAliases(Class<?>[] typeAliases) {
        super.setTypeAliases(typeAliases);
        this.typeAliases = typeAliases;
    }

    /* (non-Javadoc)
     * @see org.mybatis.spring.SqlSessionFactoryBean#setConfigLocation(org.springframework.core.io.Resource)
     */
    @Override
    public void setConfigLocation(Resource configLocation) {
        super.setConfigLocation(configLocation);
        this.configLocation = configLocation;
    }

    /* (non-Javadoc)
     * @see org.mybatis.spring.SqlSessionFactoryBean#setConfiguration(org.apache.ibatis.session.Configuration)
     */
    @Override
    public void setConfiguration(Configuration configuration) {
        super.setConfiguration(configuration);
        this.configuration = configuration;
    }

    /* (non-Javadoc)
     * @see org.mybatis.spring.SqlSessionFactoryBean#setMapperLocations(org.springframework.core.io.Resource[])
     */
    @Override
    public void setMapperLocations(Resource[] mapperLocations) {
        super.setMapperLocations(mapperLocations);
        this.mapperLocations = mapperLocations;
    }

    /* (non-Javadoc)
     * @see org.mybatis.spring.SqlSessionFactoryBean#setConfigurationProperties(java.util.Properties)
     */
    @Override
    public void setConfigurationProperties(Properties sqlSessionFactoryProperties) {
        super.setConfigurationProperties(sqlSessionFactoryProperties);
        this.configurationProperties = sqlSessionFactoryProperties;
    }

    /* (non-Javadoc)
     * @see org.mybatis.spring.SqlSessionFactoryBean#setDataSource(javax.sql.DataSource)
     */
    @Override
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
        if (dataSource instanceof TransactionAwareDataSourceProxy) {
            this.dataSource = ((TransactionAwareDataSourceProxy)dataSource).getTargetDataSource();
        } else {
            this.dataSource = dataSource;
        }
    }

    /* (non-Javadoc)
     * @see org.mybatis.spring.SqlSessionFactoryBean#setSqlSessionFactoryBuilder(org.apache.ibatis.session.SqlSessionFactoryBuilder)
     */
    @Override
    public void setSqlSessionFactoryBuilder(SqlSessionFactoryBuilder sqlSessionFactoryBuilder) {
        super.setSqlSessionFactoryBuilder(sqlSessionFactoryBuilder);
        this.sqlSessionFactoryBuilder = sqlSessionFactoryBuilder;
    }

    /* (non-Javadoc)
     * @see org.mybatis.spring.SqlSessionFactoryBean#setTransactionFactory(org.apache.ibatis.transaction.TransactionFactory)
     */
    @Override
    public void setTransactionFactory(TransactionFactory transactionFactory) {
        super.setTransactionFactory(transactionFactory);
        this.transactionFactory = transactionFactory;
    }

    /*
     * (non-Javadoc)
     * @see org.mybatis.spring.SqlSessionFactoryBean#setEnvironment(java.lang.String)
     */
    @Override
    public void setEnvironment(String environment) {
        super.setEnvironment(environment);
        this.environment = environment;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterPropertiesSet()
        throws Exception {
        notNull(dataSource, "Property 'dataSource' is required");
        notNull(sqlSessionFactoryBuilder, "Property 'sqlSessionFactoryBuilder' is required");
        state((configuration==null&&configLocation==null)||!(configuration!=null&&configLocation!=null),
            "Property 'configuration' and 'configLocation' can not specified with together");

        this.sqlSessionFactory = buildSqlSessionFactory();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SqlSessionFactory getObject()
        throws Exception {
        if (this.sqlSessionFactory==null) {
            afterPropertiesSet();
        }

        return this.sqlSessionFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<? extends SqlSessionFactory> getObjectType() {
        return this.sqlSessionFactory==null ? SqlSessionFactory.class : this.sqlSessionFactory.getClass();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSingleton() {
        return true;
    }

    /**
     * Build a {@code SqlSessionFactory} instance.
     *
     * The default implementation uses the standard MyBatis {@code XMLConfigBuilder} API to build a
     * {@code SqlSessionFactory} instance based on an Reader.
     * Since 1.3.0, it can be specified a {@link Configuration} instance directly(without config file).
     *
     * @return SqlSessionFactory
     * @throws IOException if loading the config file failed
     */
    protected SqlSessionFactory buildSqlSessionFactory()
        throws IOException {

        Configuration configuration;

        XMLConfigBuilder xmlConfigBuilder = null;
        if (this.configuration!=null) {
            configuration = this.configuration;
            if (configuration.getVariables()==null) {
                configuration.setVariables(this.configurationProperties);
            } else if (this.configurationProperties!=null) {
                configuration.getVariables().putAll(this.configurationProperties);
            }
        } else if (this.configLocation!=null) {
            xmlConfigBuilder = new XMLConfigBuilder(this.configLocation.getInputStream(), null, this.configurationProperties);
            configuration = xmlConfigBuilder.getConfiguration();
        } else {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Property 'configuration' or 'configLocation' not specified, using default MyBatis Configuration");
            }
            configuration = new Configuration();
            if (this.configurationProperties!=null) {
                configuration.setVariables(this.configurationProperties);
            }
        }

        if (this.objectFactory!=null) {
            configuration.setObjectFactory(this.objectFactory);
        }

        if (this.objectWrapperFactory!=null) {
            configuration.setObjectWrapperFactory(this.objectWrapperFactory);
        }

        if (this.vfs!=null) {
            configuration.setVfsImpl(this.vfs);
        }

        if (hasLength(this.typeAliasesPackage)) {
            String[] typeAliasPackageArray = tokenizeToStringArray(this.typeAliasesPackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
            for (String packageToScan: typeAliasPackageArray) {
                configuration.getTypeAliasRegistry().registerAliases(packageToScan, typeAliasesSuperType==null ? Object.class : typeAliasesSuperType);
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Scanned package: '"+packageToScan+"' for aliases");
                }
            }
        }

        if (!isEmpty(this.typeAliases)) {
            for (Class<?> typeAlias: this.typeAliases) {
                configuration.getTypeAliasRegistry().registerAlias(typeAlias);
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Registered type alias: '"+typeAlias+"'");
                }
            }
        }

        if (!isEmpty(this.plugins)) {
            for (Interceptor plugin: this.plugins) {
                configuration.addInterceptor(plugin);
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Registered plugin: '"+plugin+"'");
                }
            }
        }

        if (hasLength(this.typeHandlersPackage)) {
            String[] typeHandlersPackageArray = tokenizeToStringArray(this.typeHandlersPackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
            for (String packageToScan: typeHandlersPackageArray) {
                configuration.getTypeHandlerRegistry().register(packageToScan);
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Scanned package: '"+packageToScan+"' for type handlers");
                }
            }
        }

        if (!isEmpty(this.typeHandlers)) {
            for (TypeHandler<?> typeHandler: this.typeHandlers) {
                configuration.getTypeHandlerRegistry().register(typeHandler);
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Registered type handler: '"+typeHandler+"'");
                }
            }
        }

        if (this.databaseIdProvider!=null) {// fix #64 set databaseId before parse mapper xmls
            try {
                configuration.setDatabaseId(this.databaseIdProvider.getDatabaseId(this.dataSource));
            } catch (SQLException e) {
                throw new NestedIOException("Failed getting a databaseId", e);
            }
        }

        if (this.cache!=null) {
            configuration.addCache(this.cache);
        }

        if (xmlConfigBuilder!=null) {
            try {
                xmlConfigBuilder.parse();

                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Parsed configuration file: '"+this.configLocation+"'");
                }
            } catch (Exception ex) {
                throw new NestedIOException("Failed to parse config resource: "+this.configLocation, ex);
            } finally {
                ErrorContext.instance().reset();
            }
        }

        if (this.transactionFactory==null) {
            this.transactionFactory = new SpringManagedTransactionFactory();
        }

        configuration.setEnvironment(new Environment(this.environment, this.transactionFactory, this.dataSource));

        if (!isEmpty(this.mapperLocations)) {
            for (Resource mapperLocation: this.mapperLocations) {
                if (mapperLocation==null) {
                    continue;
                }

                try {
                    XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(mapperLocation.getInputStream(), configuration, mapperLocation.toString(),
                        configuration.getSqlFragments());
                    xmlMapperBuilder.parse();
                } catch (Exception e) {
                    throw new NestedIOException("Failed to parse mapping resource: '"+mapperLocation+"'", e);
                } finally {
                    ErrorContext.instance().reset();
                }

                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Parsed mapper file: '"+mapperLocation+"'");
                }
            }
        } else {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Property 'mapperLocations' was not specified or no matching resources found");
            }
        }

        return this.sqlSessionFactoryBuilder.build(configuration);
    }
}
