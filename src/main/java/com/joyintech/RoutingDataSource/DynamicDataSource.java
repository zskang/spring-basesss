/**
 * 项目名称:   项目名称         <br>
 * 包  名 称:    java包名称       <br>
 * 类 名  称:    java类名称     <br>
 *
 * 修改履历:
 *       日期                            修正者        主要内容   <br>
 *       2017年2月24日          yaojie     初版做成    <br>
 *
 * Copyright (c) 2007-2017 兆尹科技
 */
package com.joyintech.RoutingDataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;  

/* 
 * 配置多数据源 
 */  
  
public class DynamicDataSource extends AbstractRoutingDataSource{  
  
    public static final String DATASOURCE_DEFAULT = "datasource_default";
    
    public static final String DATASOURCE_A = "datasource_A";  
      
    public static final String DATASOURCE_B = "datasource_B";  
    
    public static final String DATASOURCE_C = "datasource_C";  
    //本地线程，获取当前正在执行的currentThread  
    public static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();   
      
    public static void setCustomerType(String customerType) {  
        //把当前请求的参数，存入当前线程，这个参数是我们定义的DATASOURCE_TEST 或者 DATASOURCE_LEARN_SYSTEM  
        //我们来看一下源码：  
        //public void set(T value) {  
        //    Thread t = Thread.currentThread();  
        //    ThreadLocalMap map = getMap(t);  
        //    if (map != null)  
        //        map.set(this, value);  
        //    else  
        //        createMap(t, value);  
        //这段代码的官方说明如下  
        /** 
         * Sets the current thread's copy of this thread-local variable 
         * to the specified value.  Most subclasses will have no need to  
         * override this method, relying solely on the {@link #initialValue} 
         * method to set the values of thread-locals. 
         * 
         * @param value the value to be stored in the current thread's copy of 
         *        this thread-local. 
         */  
          
        //测试输出  
        System.out.println("当前切换的数据源 = " + customerType);  
  
        contextHolder.set(customerType);  
          
    }  
  
    public static String getCustomerType() {  
  
        //官方文档如下：  
  
        /** 
         * Returns the value in the current thread's copy of this 
         * thread-local variable.  If the variable has no value for the 
         * current thread, it is first initialized to the value returned 
         * by an invocation of the {@link #initialValue} method. 
         * 
         * @return the current thread's value of this thread-local 
         */  
        //public T get() {  
            //Thread t = Thread.currentThread();  
            //ThreadLocalMap map = getMap(t);  
            //if (map != null) {  
                //ThreadLocalMap.Entry e = map.getEntry(this);  
                //if (e != null)  
              //      return (T)e.value;  
            //}  
          //  return setInitialValue();  
        //}  
        return contextHolder.get();  
           
    }  
  
    public static void clearCustomerType() {  
  
        /** 
         * Removes the current thread's value for this thread-local 
         * variable.  If this thread-local variable is subsequently 
         * {@linkplain #get read} by the current thread, its value will be 
         * reinitialized by invoking its {@link #initialValue} method, 
         * unless its value is {@linkplain #set set} by the current thread 
         * in the interim.  This may result in multiple invocations of the 
         * <tt>initialValue</tt> method in the current thread. 
         * 
         * @since 1.5 
         */  
        //源代码如下：  
         //public void remove() {  
           //  ThreadLocalMap m = getMap(Thread.currentThread());  
             //if (m != null)  
               //  m.remove(this);  
         //}  
        contextHolder.remove();  
      
    }  
  
    @Override  
    protected Object determineCurrentLookupKey() {  
          
        return getCustomerType();  
          
    }  
  
}