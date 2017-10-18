/**
 * 项目名称:   web_base        	<br>
 * 包  名 称:   com.joyintech.webbase.interceptor   	<br>
 * 文件名称:   NoPriviligeException.java     <br>
 *
 * 修改履历:
 *       日期                            修正者        主要内容   <br>
 *       2017年2月24日            张中伟        初版做成    <br>
 *
 * Copyright (c) 2007-2017 兆尹科技
 */
package com.joyintech.base.exception;

/**
 * 名称：NoPriviligeException <br>
 * 描述：〈功能详细描述〉<br>
 * @author 张中伟
 * @version 1.0
 * @since 1.0.0
 */
@SuppressWarnings("serial")
public class NoPriviligeException extends BaseException {

    /**
     * 构造方法 
     */
    public NoPriviligeException() {

        super();
        this.initCodeTitle();

    }

    /**
     * 构造方法
     * @param message 错误信息
     */
    public NoPriviligeException(String message) {

        super(message);
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
        this.setExceptionCode("JYN-00002");
        this.setExceptionTitle("无权限异常");

    }
}
