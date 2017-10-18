/**
 * 项目名称:   spring_base          <br>
 * 包  名 称:   com.joyintech.page       <br>
 * 文件名称:   PageException.java     <br>
 *
 * 修改履历:
 *       日期                            修正者        主要内容   <br>
 *       2017年2月17日           汪瀚超        初版做成    <br>
 *
 * Copyright (c) 2007-2017 兆尹科技
 */
package com.joyintech.page;


import com.joyintech.base.exception.BaseException;


/**
 * 
 * 名称：分页异常类 <br>
 * 描述：分页处理中自定义异常<br>
 * @author 汪瀚超
 * @version 1.0
 * @since 1.0.0
 */
public class PageException extends BaseException {

    /**
     * 添加默认serrialVersionUID<br>
     */
    private static final long serialVersionUID = 1L;

    /**
     * 基本构造方法
     */
    public PageException() {
        super();

        this.initCodeTitle();
    }

    /**
     * 使用信息构造异常
     * @param message 异常信息
     */
    public PageException(String message) {

        super(message);

        this.initCodeTitle();
    }

    /**
     * 使用信息和原始异常构造新异常
     * @param message  异常信息
     * @param cause    原始抛出异常（堆栈）
     */
    public PageException(String message, Throwable cause) {
        super(message, cause);

        this.initCodeTitle();
    }

    /**
     * 
     * 主要功能: 指定当前异常类的编码和标题     <br>
     * 注意事项:无  <br>
     *
     */
    public void initCodeTitle() {
        // 异常编码和信息可以考虑提取到通用的类或配置文件
        this.setExceptionCode("JYN-02019");
        this.setExceptionTitle("分页处理异常");
    }

}
