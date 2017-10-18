/**
 * 项目名称:   web_base        	<br>
 * 包  名 称:   com.joyintech.webbase.domain   	<br>
 * 文件名称:   TreeNode.java     <br>
 *
 * 修改履历:
 *       日期                            修正者        主要内容   <br>
 *       2017年3月29日            daiweiwei@joyintech.com        初版做成    <br>
 *
 * Copyright (c) 2007-2017 兆尹科技
 */
package com.joyintech.utils;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;


/**
 * 树形节点实体类
 * @author daiweiwei@joyintech.com
 *
 */
public class TreeNode implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -142942223114651851L;

    // 编码id
    private String id;

    // 名称
    private String name;

    // 编码code
    private String code;

    // 层级
    private int level;

    // 是否为叶节点，为true时表示该节点下面没有子节点了
    private boolean leaf = true;

    // 父级节点
    private String parentId;

    // 是否已经加载过子节点（为false时点击节点会自动加载子节点）
    private boolean loaded = true;

    // 是否展开
    private boolean open = false;

    // 其他数据
    private Map<String, Object> map;

    // 子节点
    private List<TreeNode> children;
    
    
    private boolean flag;
    
    /**
     * 取得moduleId的值
     *
     * @return moduleId值.
     */
    public String getModuleId() {
        return moduleId;
    }

    /**
     * 设定moduleId的值
     *
     * @param moduleId 设定值
     */
    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    /**
     * 取得moduleName的值
     *
     * @return moduleName值.
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * 设定moduleName的值
     *
     * @param moduleName 设定值
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    // 菜单Id
    private String moduleId;
    
    // 菜单名称
    private String moduleName;
    

    public TreeNode() {}

    public TreeNode(String id, String name, String code, int level) {
        super();
        this.id = id;
        this.name = name;
        this.code = code;
        this.level = level;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    /**
     * 添加一个属性
     * @param key    键
     * @param target 值
     */
    public void addAttr(String key, Object target) {
        if (this.map==null) {
            this.map = new HashMap<String, Object>();
        }
        this.map.put(key, target);
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    /**
     * 添加一个孩子
     * @param child
     */
    public void addChild(TreeNode child) {
        if (this.children==null) {
            this.children = new ArrayList<TreeNode>();
        }
        this.children.add(child);
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    /**
     * 取得flag的值
     *
     * @return flag值.
     */
    public boolean isFlag() {
        return flag;
    }

    /**
     * 设定flag的值
     *
     * @param flag 设定值
     */
    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    
    
}
