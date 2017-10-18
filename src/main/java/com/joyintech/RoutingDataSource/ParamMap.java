/**
 * 项目名称:   项目名称        	<br>
 * 包  名 称:    java包名称   	<br>
 * 类 名  称:    java类名称     <br>
 *
 * 修改履历:
 *       日期                            修正者        主要内容   <br>
 *       2017年2月24日          yaojie     初版做成    <br>
 *
 * Copyright (c) 2007-2017 兆尹科技
 */
package com.joyintech.RoutingDataSource;

import java.util.HashMap;

/**
 * 名称：用于service层参数传递<br>
 * 描述：〈功能详细描述〉<br>
 * @author yaojie
 * @version 1.0
 * @since 1.0.0
 */
public class ParamMap extends HashMap<Object, Object>{
    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = -276497755629276081L;
    
    public String routeKey = "default";
    public String routeValue = "dataSource";
    
    public ParamMap() {
        // TODO Auto-generated constructor stub
        this.put("routeKey", routeValue);
        this.put("routeValue", routeValue);
    }

    /**
     * 取得routeKey的值
     *
     * @return routeKey值.
     */
    public String getRouteKey() {
        return routeKey;
    }

    /**
     * 设定routeKey的值
     *
     * @param routeKey 设定值
     */
    public void setRouteKey(String routeKey) {
        //切换数据源 datasource_test  
        //这段代码相当于，把String类型的参数 datasource_test 放在了保存到了本地线程的当前线程中，也就是当前正在执行的线程。  
//        DynamicDataSource.setCustomerType(DynamicDataSource.DATASOURCE_A);
        this.routeKey = routeKey;
    }

    /**
     * 取得routeValue的值
     *
     * @return routeValue值.
     */
    public String getRouteValue() {
        return routeValue;
    }

    /**
     * 设定routeValue的值
     *
     * @param routeValue 设定值
     */
    public void setRouteValue(String routeValue) {
        this.routeValue = routeValue;
    }
}
