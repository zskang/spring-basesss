/*
 * 项目名称:common_utils
 * 类名称:JoyinColumnValidator.java
 * 包名称:com.joyintech.platform.validator
 *
 * 修改履历:
 *       日期                   修正者      主要内容
 *  2016年9月20日           daiweiwei         初版做成
 *
 * Copyright (c) 2016-2017 兆尹科技
 */
package com.joyintech.validate;


import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.joyintech.base.exception.EntityValidatationException;


/**
 * 验证器,与jpautil连用，用于后端完整性验证
 * @author daiweiwei
 * @version 1.0
 *
 */
public class EntityValidator implements Validator {

    public boolean supports(Class<?> clazz) {
        return true;
    }

    public void validate(Object target, Errors errors) {
        try {
            // 进行验证
            EntityHelper.validate(target);
        } catch (Exception e) {
            throw new EntityValidatationException(e.getMessage());
        }
    }
 
}
