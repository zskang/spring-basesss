/**
 * 项目名称:   spring_base        	<br>
 * 包  名 称:   com.joyintech.utils   	<br>
 * 文件名称:   FormColumn.java     <br>
 *
 * 修改履历:
 *       日期                            修正者        主要内容   <br>
 *       2017年3月22日            daiweiwei@joyintech.com        初版做成    <br>
 *
 * Copyright (c) 2007-2017 兆尹科技
 */
package com.joyintech.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 表单字段属性类型实体
 * @author daiweiwei@joyintech.com
 *
 */
public class FormColumn implements Serializable {

    public static enum TYPE {
        INTPUT, SELECT, DATE,
    }
    /**
     * 
     */
    private static final long serialVersionUID = 7117267424042767590L;
    
    //form表单字段类型
    private String formType="input";
    //form表单字段name
    private String formName;
    //form 表单字段label
    private String label;
    
    //所在行
    private Integer row;
    //所在列
    private Integer col;
    
    private Map<String, Object> attrs = new HashMap<String, Object>();

    public FormColumn put(String key, Object value) {
        this.attrs.put(key, value);
        return this;
    }

    public Map<String, Object> getAttrs() {
        return attrs;
    }


    //占用列数
    private Integer occCol=2;

    public FormColumn() {
        // TODO Auto-generated constructor stub
    }

    public FormColumn(String formType, String formName, String label) {
        super();
        this.formType = formType;
        this.formName = formName;
        this.label = label;
    }

    /**
     * @return the formType
     */
    public String getFormType() {
        return formType;
    }

    /**
     * @param formType the formType to set
     */
    public void setFormType(String formType) {
        this.formType = formType;
    }

    /**
     * @return the formName
     */
    public String getFormName() {
        return formName;
    }

    /**
     * @param formName the formName to set
     */
    public void setFormName(String formName) {
        this.formName = formName;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return the row
     */
    public Integer getRow() {
        return row;
    }

    /**
     * @param row the row to set
     */
    public void setRow(Integer row) {
        this.row = row;
    }

    /**
     * @return the col
     */
    public Integer getCol() {
        return col;
    }

    /**
     * @param col the col to set
     */
    public void setCol(Integer col) {
        this.col = col;
    }

    /**
     * @return the occCol
     */
    public Integer getOccCol() {
        return occCol;
    }

    /**
     * @param occCol the occCol to set
     */
    public void setOccCol(Integer occCol) {
        this.occCol = occCol;
    }

    

}
