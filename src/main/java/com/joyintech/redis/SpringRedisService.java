/**
 * 项目名称:   spring_base          <br>
 * 包  名 称:   com.joyintech.redis    <br>
 * 文件名称:   SpringRedisService.java     <br>
 *
 * 修改履历:
 *       日期                            修正者        主要内容   <br>
 *       2017年2月24日            daiweiwei        初版做成    <br>
 *
 * Copyright (c) 2007-2017 兆尹科技
 */
package com.joyintech.redis;


import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 
 * 名称：SpringRedisService <br>
 * 描述：redis 服务接口 <br>
 * serialKey 必须用SpringRedisService.SERIAL_KEY.DICT.toString()使用<br>
 * @author daiweiwei
 * @version 1.0
 * @since 1.0.0
 */
public interface SpringRedisService {

    /**
     *定义分组序列化key 枚举值
     *
     */
    public enum SERIALKEY {
        DICT, // 字典
        ORGAN, // 组织机构
        FUNCTION, // 功能菜单
        USER, // 用户
        TABLECOLS,//列表列
        OTHER, // 其他
    };

    /**
     * 从redis中serialKey指定的map中根据key获取value
     * @param serialKey 分组序列化key
     * @param hashKey map的指定键
     * @return Object 返回对象 
     */
    Object getVal(String serialKey, String hashKey);

    /**
     * 从redis中根据serialKey指定的map
     * @param serialKey 分组序列化key
     * @return Map
     */
    Map<String, Object> getMap(String serialKey);

    /**
     * 从redis中根据serialKey指定的map 的所有key
     * @param serialKey 分组序列化key
     * @return Set
     */
    Set<String> getHashKeys(String serialKey);

    /**
     * 从redis中根据serialKey指定的map 的所有值数组
     * @param serialKey 分组序列化key
     * @return List
     */
    List<Object> getValuesList(String serialKey);

    /**
     * 根据serialKey指定的map存放到redis中
     * @param serialKey 分组序列化key
     * @param resultMap 需要存放的map对象
     */
    void putAll(String serialKey, Map<String, Object> resultMap);

    /**
     * 向redis中根据serialKey指定的map中存入一条记录
     * @param serialKey  分组序列化key
     * @param hashKey map的键
     * @param value map的值
     */
    void put(String serialKey, String hashKey, Object value);

    /**
     * 从redis中根据serialKey指定map的删除一个
     * @param serialKey  分组序列化key
     * @param hashKey map的键
     */
    void delete(String serialKey, String hashKey);

    /**
     * 从redis中根据serialKey指定map
     * @param serialKey  分组序列化key
     */
    void delete(String serialKey);

}
