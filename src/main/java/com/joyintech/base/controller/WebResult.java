/*
 * 项目名称:spring_base
 * 类名称:WebResult.java
 * 包名称:com.joyintech.base.controller
 *
 * 修改履历:
 *       日期                            修正者        主要内容
 *       2016年12月20日          zzw88         初版做成
 *
 * Copyright (c) 2016-2017 兆尹科技
 */

package com.joyintech.base.controller;


import java.util.HashMap;
import java.util.Map;


/**
 * 名称： Controller统一返回结果类<br>
 * 描述： 所有Controller的统一返回结果<br>
 * 注意： 此类不需要测试<br>
 * @author 张中伟
 * @version 1.0
 * @since 1.0.0
 */
public class WebResult extends HashMap<String, Object> {

    /**
     * 是否发生异常标识
     */
    public static final String IS_EXCEPTION = "isException";

    /**
     * 是否成功标识
     */
    public static final String IS_SUCCESS = "isSuccess";

    /**
     * 是否登录标识
     */
    public static final String IS_LOGOIN = "isLogin";

    /**
     * 异常信息标识(使用异常基本信息，作为异常标题)
     */
    public static final String EXP_INFO = "expInfo";

    /**
     * 异常详细信息标识。 使用异常详细信息，堆栈作为异常详细信息输出 
     */
    public static final String EXP_DETAIL = "expDetail";

    /**
     * 添加序列ID<br>
     */
    private static final long serialVersionUID = 1L;

    /**
     * 默认返回结果。 
     */
    private boolean success = true;


        /**
     * 返回信息标识
     */
    public static final String MESSAGE = "message";

    /**
     * 默认构造方法
     */
    public WebResult() {
        super();
        // 添加统一返回结果为true,仅代表请求正常返回，不代表业务处理正常
        super.put(IS_SUCCESS, success);
        // 默认添加已登录标志
        super.put(IS_LOGOIN, true);
        // 默认添加无异常标志
        super.put(IS_EXCEPTION, false);
        // 默认无异常信息
        super.put(EXP_INFO, "");
        // 默认无异常详细信息
        super.put(EXP_DETAIL, "");

    }

    /**
     * 默认构造方法
     */
    public WebResult(Boolean isSuccess, String message) {
        super();
        // 添加统一返回结果为true,仅代表请求正常返回，不代表业务处理正常
        super.put(IS_SUCCESS, isSuccess);
        super.put(MESSAGE, message);
        // 默认添加已登录标志
        super.put(IS_LOGOIN, true);
        // 默认添加无异常标志
        super.put(IS_EXCEPTION, false);
        // 默认无异常信息
        super.put(EXP_INFO, "");
        // 默认无异常详细信息
        super.put(EXP_DETAIL, "");

    }
    /**
     * 操作成功，操作失败!
     */
    public WebResult(Boolean isSuccess) {
        super();
        // 添加统一返回结果为true,仅代表请求正常返回，不代表业务处理正常
        super.put(IS_SUCCESS, isSuccess);
        if(isSuccess){
            super.put(MESSAGE, "操作成功!");
        }else{
            super.put(MESSAGE, "操作失败!");
        }
        // 默认添加已登录标志
        super.put(IS_LOGOIN, true);
        // 默认添加无异常标志
        super.put(IS_EXCEPTION, false);
        // 默认无异常信息
        super.put(EXP_INFO, "");
        // 默认无异常详细信息
        super.put(EXP_DETAIL, "");

    }

    /**
     * 自定义构造方法
     */
    public WebResult(Map<String, Object> map) {
        super();
        super.putAll(map);

    }

    /**
     * 自定义构造方法
     */
    public WebResult(String key, Object value) {
        super.put(key, value);

    }

    // 未登录、异常等返回结果 考虑使用重载构造方法或继承类实现
    // 例如

    /*
     * public WebResult(ResultException re) { super(); super.put("success", isSuccess);
     * super.put("hasException", true); super.put("exceptionTitle", re.exceptionTitle);
     * super.put("exceptionMessage", re.exceptionMessage); super.put("exceptionDetail",
     * re.exceptionDetail); }
     */

}
