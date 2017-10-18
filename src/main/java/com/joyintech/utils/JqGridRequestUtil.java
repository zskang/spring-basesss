/*
 * 创建时间：2017/3/28 11:32
 * 项目名称:spring_base
 * 类名称:JqGridRequestUtil.java
 * 包名称:com.joyintech.utils
 *
 * 修改履历:
 *          日期              修正者        主要内容
 *                                      
 *
 * Copyright (c) 2016-2017 兆尹科技
 */
package com.joyintech.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 名称：JqGridRequestUtil <br>
 * 描述：JqGridRequest工具类<br>
 *
 * @author 李鹏军
 * @version 1.0
 * @since 1.0.0
 */
public class JqGridRequestUtil {


    /**
     * 根据jqGridRequest参数拼装查询项
     *
     * @param jqGridRequest 表格参数
     * @return
     */
    public static Map<String, Object> jqGridSearcher2Map(JqGridRequest jqGridRequest) {
        Map<String, Object> params = new HashMap<String, Object>();
        List<JqGridSearcher> jqGridSearchers = jqGridRequest.getSearchers();
        if (null != jqGridSearchers && jqGridSearchers.size() > 0) {
            for (JqGridSearcher jqGridSearcher : jqGridSearchers) {
                if (!StringUtils.isEmpty(jqGridSearcher.getSearchString())) {//过滤空值
                    params.put(jqGridSearcher.getSearchField(), jqGridSearcher.getSearchString());
                }
            }
        }
        return params;
    }
}
