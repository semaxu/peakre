package com.ebao.ap99.file.util;


import org.springframework.context.ApplicationContext;


import com.ebao.unicorn.platform.foundation.context.impl.SpringContextManager;

public class BeanUtil {
  
public static Object getStringBean(String beanName){
    ApplicationContext applicationContext = SpringContextManager.getApplicationContext();
    Object obj =  applicationContext.getBean(beanName);
    return obj;
}
    
}
