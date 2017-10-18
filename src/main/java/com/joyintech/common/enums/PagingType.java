package com.joyintech.common.enums;

/**
 * 
 * 名称：PagingType <br>
 * 描述：分页方式<br>
 * @author 汪瀚超
 * @version 1.0
 * @since 1.0.0
 */
public enum PagingType {
    /**
     * 默认的分页方式<br>
     * 总数据条数在分页处理中计算<br>
     * 替换SQL的方式，不适用于特殊SQL，如外层带GroupBy的查询等
     */
    DEFAULT_PAGING,
    /**
     * 适用于特殊的分页方式（如外层带有GroupBy的SQL查询）<br>
     * 总数据条数在分页处理中计算<br>
     * 外部嵌套Count的方式查询，性能较低
     */
    ADVANCED_PAGING,
    /**
     * 自定义分页方式<br>
     * 总数据条数不在分页中处理，通过外部自定义Count方法计算
     */
    CUSTOM_PAGING
}
