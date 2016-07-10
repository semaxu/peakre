package com.ebao.ap99.file.util;

import java.util.List;

import org.springframework.context.ApplicationContext;

import com.ebao.ap99.file.entity.TDocument;
import com.ebao.ap99.file.service.DocumentService;
import com.ebao.unicorn.platform.foundation.context.impl.SpringContextManager;

public class DocumentUtil {
  
public static List<TDocument> getDocumentList(Long businessId){
    ApplicationContext applicationContext = SpringContextManager.getApplicationContext();
    DocumentService biz = (DocumentService) applicationContext.getBean("com.ebao.ap99.file.service.impl.DocumentService");
    return biz.getDocumentList(businessId);
}
    
}
