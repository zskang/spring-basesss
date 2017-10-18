/**
 * 项目名称:   spring_base        	<br>
 * 包  名 称:   com.joyintech.base.exception   	<br>
 * 文件名称:   BaseBusiException.java     <br>
 *
 * 修改履历:
 *       日期                            修正者        主要内容   <br>
 *       2017年2月9日            张中伟        初版做成    <br>
 *
 * Copyright (c) 2007-2017 兆尹科技
 */
package com.joyintech.base.exception;

/**
 * 名称：BaseBusiException <br>
 * 描述：DAO基本异常定义 <br>
 * @author 张中伟
 * @version 1.0
 * @since 1.0.0
 */
public class BaseBusiException extends BaseException {

    /**
     * 默认serialVersionUID<br>
     */
    private static final long serialVersionUID = 1L;

    /**
     * 基本构造方法
     */
    public BaseBusiException() {
        super();

        this.initCodeTitle();

    }

    /**
     * 使用信息构造异常
     * @param message  异常描述
     */
    public BaseBusiException(String message) {
        super(message);

        this.initCodeTitle();

    }

    /**
     * 使用信息和原始异常构造新异常
     * @param message  异常信息
     * @param cause    原始抛出异常（堆栈）
     */
    public BaseBusiException(String message, Throwable cause) {
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
        this.setExceptionCode("JYN-10001");
        this.setExceptionTitle("业务基本异常");
    }

}
