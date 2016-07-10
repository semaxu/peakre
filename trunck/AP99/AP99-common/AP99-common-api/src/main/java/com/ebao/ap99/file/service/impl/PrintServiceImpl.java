package com.ebao.ap99.file.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.file.entity.TDocument;
import com.ebao.ap99.file.entity.DocumentRule;
import com.ebao.ap99.file.model.ItemVO;
import com.ebao.ap99.file.service.DocumentService;
import com.ebao.ap99.file.service.FileService;
import com.ebao.ap99.file.service.PrintBizService;
import com.ebao.ap99.file.service.PrintService;
import com.ebao.ap99.file.util.BeanUtil;
import com.ebao.ap99.file.util.JaxbXMLParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PrintServiceImpl implements PrintService {

    @Autowired
    private DocumentService documentService;
    
    @Autowired
	private FileService fileService;

    public void getData(ItemVO itemVO, String serviceName) throws Exception {
        PrintBizService biz = (PrintBizService)BeanUtil.getStringBean(serviceName);

        biz.loadBusinessVO(itemVO);

    }

    public String getPrintDataResource(Long documentId) throws Exception {

        String xmlString = null;
        String businessType = null;

        TDocument document = documentService.load(documentId);
        if (document == null) {
            return null;
        } else {
            businessType = document.getBusinessType();
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> paramMap = mapper.readValue(document.getPara(),
                    new TypeReference<Map<String, String>>() {
                    });

            DocumentRule documentRule = documentService.findDocumentRule(businessType);
            if (documentRule != null) {
                ItemVO<Object> itemVO = new ItemVO<Object>();
                itemVO.setParam(paramMap);
                this.getData(itemVO, documentRule.getParseService());
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                Class cls = Class.forName(documentRule.getParseClass());
                xmlString = JaxbXMLParser.marshallXml(out, itemVO.getBizVO(), cls);
            }

            return xmlString;
        }
    }
    
    @Override
	public void generateXMLFile(Long documentId, String pathname)
            throws Exception {
        String xmlString = getPrintDataResource(documentId);
        fileService.writePhysicalFile(new ByteArrayInputStream(xmlString.getBytes()), pathname);
    }
    

    @Override
	public String generatePrintURL(TDocument document) throws Exception {      
        String relativePath = fileService.generateRelativePath(document.getBusinessType(), null, FileService.FILE_PATH_DOCUMENT_INPUT);
        String fileName = document.getDocumentId() + ".xml";
        String relativeFileName = relativePath + File.separator + fileName;
        String fullPathName = FileService.FILE_ROOT_PATH + File.separator+relativeFileName;

        generateXMLFile(document.getDocumentId(), fullPathName);
        //replace "\" to "/"
        String replacedRelativeFileName = relativeFileName.replace("\\", "/");
        
        String fileUrl = BIRT_PRINT_URL.replace(":templateName", documentService.findDocumentRule(document.getBusinessType()).getTemplate())
                .replace(":relativeFileName", replacedRelativeFileName);
        return fileUrl;
    }
    
    @Override
    public String printPath(String businessType, Map paramMap)
            throws Exception {
        TDocument document = documentService.createNewDocument(businessType, paramMap);

        String fileUrl = generatePrintURL(document);        
        document.setFileUrl(fileUrl);
        
        String relativeFileName = fileService.generateFileByURL(document);
        document.setFilePath(relativeFileName);
        documentService.saveDocument(document);
        return document.getFilePath();
    }

}
