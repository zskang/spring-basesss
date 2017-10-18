/**
 * 项目名称:   spring_base        	<br>
 * 包  名 称:   com.joyintech.base.exception   	<br>
 * 文件名称:   EntityValidatationException.java     <br>
 *
 * 修改履历:
 *       日期                            修正者                                                                                        主要内容   <br>
 *       2017年3月3日   daiweiwei       初版做成    <br>
 *
 * Copyright (c) 2007-2017 兆尹科技
 */
package com.joyintech.base.exception;

/**
 * 名称：springmvc注入模型参数验证异常<br>
 * @author daiweiwei
 * @version 1.0
 * @since 1.0.0
 */
public class EntityValidatationException extends BaseException {

    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = 3949195352648417289L;
    
    public EntityValidatationException() {
        super();
        this.initCodeTitle();
    }
    
    public EntityValidatationException(String message) {
        super(message);
        this.initCodeTitle();
    }
    
   
    public EntityValidatationException(String message, Throwable cause) {
        super(message, cause);

        this.initCodeTitle();
    }

    @Override
    public void initCodeTitle() {
        this.setExceptionCode("JYN-01002");
        this.setExceptionTitle("springmvc注入模型参数验证异常");
    }
    
    
    
      

}
