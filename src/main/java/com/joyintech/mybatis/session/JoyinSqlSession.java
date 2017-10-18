/**
 *    Copyright 2009-2015 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.joyintech.mybatis.session;


import java.util.Collection;
import java.util.List;

import org.apache.ibatis.exceptions.ExceptionFactory;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.defaults.DefaultSqlSession;


/**
 * 名称：JoyinSqlSession<br>
 * 描述：〈功能详细描述〉<br>
 * @author 杨松柏
 * @version 1.0
 * @since 1.0.0
 */
public class JoyinSqlSession extends DefaultSqlSession {

    private Executor executor;

    public JoyinSqlSession(Configuration configuration, Executor executor) {
        this(configuration, executor, false);
    }

    public JoyinSqlSession(Configuration configuration, Executor executor, boolean autoCommit) {
        super(configuration, executor, autoCommit);
        this.executor = executor;
    }

    /**
     * 主要功能:列表数据检索 <br>
     * 注意事项:无  <br>
     * 
     * @param statement 状态
     * @param parameter 参数
     * @param ms 参数mapper
     * @param <E> 返回对象类型
     * @return  结果列表
     */
    public <E> List<E> selectList(String statement, Object parameter, MappedStatement ms) {
        try {
            return this.executor.query(ms, wrapCollection(parameter), RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER);
        } catch (Exception e) {
            throw ExceptionFactory.wrapException("Error querying database.  Cause: "+e, e);
        } finally {
            ErrorContext.instance().reset();
        }
    }

    /**
     * 主要功能: 参数封装<br>
     * 注意事项:无  <br>
     * 
     * @param object 参数
     * @return 封装后的参数
     */
    private Object wrapCollection(final Object object) {
        if (object instanceof Collection) {
            StrictMap<Object> map = new StrictMap<Object>();
            map.put("collection", object);
            if (object instanceof List) {
                map.put("list", object);
            }
            return map;
        } else if (object!=null&&object.getClass().isArray()) {
            StrictMap<Object> map = new StrictMap<Object>();
            map.put("array", object);
            return map;
        }
        return object;
    }
}
