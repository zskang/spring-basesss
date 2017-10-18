/**
 * 项目名称:   spring_base          <br>
 * 包  名 称:   com.joyintech.redis    <br>
 * 文件名称:   SpringRedisServiceImpl.java     <br>
 *
 * 修改履历:
 *       日期                            修正者        主要内容   <br>
 *       2017年2月24日            daiweiwei        初版做成    <br>
 *
 * Copyright (c) 2007-2017 兆尹科技
 */
package com.joyintech.redis;


import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;


/**
 * redis增删改查工具服务
 * @author daiweiwei
 *
 */
@SuppressWarnings("all")
public class SpringRedisServiceImpl implements SpringRedisService {

    // 加入redisTemplate
    /*@Resource*/
    private RedisTemplate redisTemplate;

    /*
     * (non-Javadoc)
     * @see com.joyintech.redis.SpringRedisService#getVal(java.lang.String, java.lang.String)
     */
    @Override
    public Object getVal(String serialKey, String key) {
        return redisTemplate.opsForHash().get(serialKey, key);
    }

    /*
     * (non-Javadoc)
     * @see com.joyintech.redis.SpringRedisService#getMap(java.lang.String)
     */

    @Override
    public Map getMap(String serialKey) {
        return redisTemplate.opsForHash().entries(serialKey);
    }

    /*
     * (non-Javadoc)
     * @see com.joyintech.redis.SpringRedisService#getHashKeys(java.lang.String)
     */
    @Override
    public Set getHashKeys(String serialKey) {
        return redisTemplate.opsForHash().keys(serialKey);
    }

    /*
     * (non-Javadoc)
     * @see com.joyintech.redis.SpringRedisService#getValuesList(java.lang.String)
     */
    @Override
    public List<Object> getValuesList(String serialKey) {
        return redisTemplate.opsForHash().values(serialKey);
    }

    /*
     * (non-Javadoc)
     * @see com.joyintech.redis.SpringRedisService#putAll(java.lang.String, java.util.Map)
     */
    @Override
    public void putAll(String serialKey, Map resultMap) {

        redisTemplate.opsForHash().putAll(serialKey, resultMap);
    }

    /*
     * (non-Javadoc)
     * @see com.joyintech.redis.SpringRedisService#put(java.lang.String, java.lang.String,
     * java.lang.Object)
     */
    @Override
    public void put(String serialKey, String hashKey, Object value) {
        if(value==null){
            return;
        }
        redisTemplate.opsForHash().put(serialKey, hashKey, value.toString());
    }

    /*
     * (non-Javadoc)
     * @see com.joyintech.redis.SpringRedisService#delete(java.lang.String, java.lang.String)
     */
    @Override
    public void delete(String serialKey, String hashKey) {
        redisTemplate.opsForHash().delete(serialKey, hashKey);
    }

    /*
     * (non-Javadoc)
     * @see com.joyintech.redis.SpringRedisService#delete(java.lang.String)
     */
    @Override
    public void delete(String serialKey) {
        Set keys = redisTemplate.opsForHash().keys(serialKey);
        Iterator<String> it = keys.iterator();
        while (it.hasNext()) {
            redisTemplate.opsForHash().delete(serialKey, it.next());
        }
    }

    /**
     * @return the redisTemplate
     */
    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    /**
     * @param redisTemplate the redisTemplate to set
     */
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

}
