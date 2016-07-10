package com.ebao.ap99.file.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Map;

import com.ebao.ap99.file.entity.TDocument;
import com.ebao.ap99.file.model.ItemVO;
import com.ebao.ap99.file.restful.FileResource;
import com.ebao.unicorn.platform.foundation.cfg.Env;

public interface PrintService {
	
    public static final String BIRT_HOME_URL = Env.getParameter("BIRT_HOME_URL");   
    public static final String BIRT_PRINT_URL = BIRT_HOME_URL + "preview?__report=:templateName&__format=pdf&xmlUrl=:relativeFileName";

    public void getData(ItemVO itemVO, String serviceName) throws Exception;

    public String getPrintDataResource(Long documentId) throws Exception;

    public void generateXMLFile(Long documentId, String pathname) throws Exception;

    public String generatePrintURL(TDocument document) throws Exception;

    public String printPath(String businessType, Map paramMap) throws Exception;

}
