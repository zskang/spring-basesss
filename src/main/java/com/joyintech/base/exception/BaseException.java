/*
 * 项目名称:spring_base
 * 类名称:BaseException.java
 * 包名称:com.joyintech.base.exception
 *
 * 修改履历:
 *       日期                            修正者        主要内容
 *       2016年12月20日          zzw88         初版做成
 *
 * Copyright (c) 2016-2017 兆尹科技
 */

package com.joyintech.base.exception;

/**
 * 名称：基本异常类<br>
 * 描述：平台所有自定义异常应继承此异常类<br>
 * 每个自定义异常类应有独立的exceptionCode和exceptionTitle<br>
 * 暂时以字符串的形式写入。<br>
 * 后续考虑分散以静态变量形式或枚举形式定义到一个或几个文件中，但要考虑SVN多人编辑冲突的影响<br>
 * 先简单使用自定义异常类的方式来处理。<br>
 * @author 张中伟
 * @version 1.0
 * @since 1.0.0
 */
public abstract class BaseException extends RuntimeException {

    /**
     * 添加默认serrialVersionUID<br>
     */
    private static final long serialVersionUID = 1L;

    /**
     * 异常编码
     */
    private String exceptionCode = "JYN-00001";

    /**
     * 基本异常标题。
     */
    private String exceptionTitle = "基本异常";

    /**
     * 基本构造方法
     */
    public BaseException() {
        super();

    }

    /**
     * 使用信息构造异常
     * @param message 异常信息
     */
    public BaseException(String message) {

        super(message);
    }

    /**
     * 使用信息和原始异常构造信息
     * @param message  异常信息
     * @param cause    原始异常和堆栈信息
     */
    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 
     * 主要功能: 取得标准的异常信息详细标题。 返回类似于  "JYN-00001:基本异常"  这样的字符串    <br>
     * 注意事项: 未处理 exceptionCode或title 为空的情况。 正常不允许此两项为空。  <br>
     * 
     * @return  标准异常标题
     */
    public String getFullTitle() {

        return this.getExceptionCode()+":"+this.getExceptionTitle();
    }

    /**
     * 
     * 主要功能: 取得异常编码    <br>
     * 注意事项: 异常编码规则  <br>
     * 
     * @return  异常编码
     */
    public String getExceptionCode() {

        return exceptionCode;
    }

    /**
     * 设定exceptionCode的值
     *
     * @param exceptionCode 设定值
     */
    public void setExceptionCode(String exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    /**
     * 取得exceptionTitle的值
     *
     * @return exceptionTitle值.
     */
    public String getExceptionTitle() {
        return exceptionTitle;
    }

    /**
     * 设定exceptionTitle的值
     *
     * @param exceptionTitle 设定值
     */
    public void setExceptionTitle(String exceptionTitle) {
        this.exceptionTitle = exceptionTitle;
    }

    public abstract void initCodeTitle();
}
