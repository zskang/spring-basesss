/**
 * 项目名称:   spring_base        	<br>
 * 包  名 称:   com.joyintech.mybatis.utils   	<br>
 * 文件名称:   MyGenerateServiceImpl.java     <br>
 *
 * 修改履历:
 *       日期                            修正者        主要内容   <br>
 *       2017年3月28日            daiweiwei@joyintech.com        初版做成    <br>
 *
 * Copyright (c) 2007-2017 兆尹科技
 */
package com.joyintech.mybatis.utils;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joyintech.validate.EntityHelper;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


/**
 * 自动生成服务实现类
 * @author daiweiwei@joyintech.com
 *
 */
public class MyGenerateServiceImpl implements MyGenerateService {

    private String serialId;

    private String formftl;

    private String jsftl;

    private int col = 2;

    private String entity;

    private String ftlPath = "src/main/resources/ftl/";

    private String resultPath;

    public String getSerialId() {
        return serialId;
    }

    public void setSerialId(String serialId) {
        this.serialId = serialId;
    }

    public String getFormftl() {
        return formftl;
    }

    public void setFormftl(String formftl) {
        this.formftl = formftl;
    }

    public String getJsftl() {
        return jsftl;
    }

    public void setJsftl(String jsftl) {
        this.jsftl = jsftl;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getFtlPath() {
        return ftlPath;
    }

    public void setFtlPath(String ftlPath) {
        this.ftlPath = ftlPath;
    }

    public String getResultPath() {
        return resultPath;
    }

    public void setResultPath(String resultPath) {
        this.resultPath = resultPath;
    }
    /* (non-Javadoc)
     * @see com.joyintech.mybatis.utils.MyGenerateService#generate()
     */
    @Override
    public void generate() {
        // TODO Auto-generated method stub
        Class clazz = null;
        List formAttrs = null;
        try {
            clazz = ClassLoader.getSystemClassLoader().loadClass(this.entity);
            formAttrs = EntityHelper.formAttrs(clazz, null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("formAttrs", formAttrs);
        dataMap.put("serialId", this.serialId);
        if (this.formftl!=null) {
            dataMap.put("col", this.col);
            generate(this.ftlPath, this.formftl, this.resultPath, this.serialId+"Form.html", dataMap);
        }

        if (this.jsftl!=null) {
            dataMap.put("genFileName", this.serialId+"Form.js");
            generate(this.ftlPath, this.jsftl, this.resultPath, this.serialId+"Form.js", dataMap);
        }
    }

    private static void generate(String ftlPath, String ftlName, String genFilePath, String genFileName, Map<String, Object> dataMap) {
        System.out.println("正在构建"+genFileName+"文件.....");
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
        File f = new File(ftlPath);
        try {
            configuration.setDirectoryForTemplateLoading(f);
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        Template t = null;
        try {
            t = configuration.getTemplate(ftlName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 文件名称取当前时间 保存地址+文件名称
        String fileName = genFilePath+genFileName;

        // 输出文档路径及名称
        File file = new File(fileName);

        Writer out = null;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            OutputStreamWriter oWriter = new OutputStreamWriter(fos, "UTF-8");
            // 这个地方对流的编码不可或缺，使用main（）单独调用时，应该可以，但是如果是web请求导出时导出后word文档就会打不开，并且包XML文件错误。主要是编码格式不正确，无法解析。
            out = new BufferedWriter(oWriter);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // 生成word文件
        try {
            t.process(dataMap, out);
            out.close();
            fos.close();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
