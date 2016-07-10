package com.ebao.ap99.file.restful;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.file.entity.TDocument;
import com.ebao.ap99.file.entity.DocumentType;
import com.ebao.ap99.file.entity.DocumentRule;
import com.ebao.ap99.file.model.Document;
import com.ebao.ap99.file.model.DocumentRecord;
import com.ebao.ap99.file.model.MessageVO;
import com.ebao.ap99.file.service.DocumentService;
import com.ebao.ap99.file.service.FileService;
import com.ebao.ap99.parent.Page;
import com.ebao.ap99.parent.context.AppContext;
import com.ebao.ap99.parent.model.TreeModel;
import com.ebao.unicorn.platform.foundation.restful.annotation.Module;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;
import com.ebao.unicorn.platform.foundation.utils.json.JSONUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Module(Module.PUBLIC)
@Path("/document")
public class DocumentResource {

    private static Logger logger = LoggerFactory.getLogger();
    
    public DocumentResource() {
    }

    @Autowired
    private DocumentService  documentService;
    @Autowired
    private FileResource fileResource;

    @POST
    @Path("/create/{businessType}")
    public  Map<String,String> createExport(@PathParam("businessType") String businessType,Map model) throws Exception {        
//        Long documentId = 0L;
        
        TDocument doc = documentService.createNewDocument(businessType, model);
        
        DocumentRule documentRule = documentService.findDocumentRule(doc.getBusinessType()); 
        Map map = new HashMap();
        map.put("documentId",doc.getDocumentId());
        map.put("fileFormat", documentRule.getParseType());
        if ("PDF".equals(documentRule.getParseType()) && "3".equals(documentRule.getProcessType())) {
        	//if need generate PDF link
        	map.put("fileURL", fileResource.queryPrintURL(doc.getDocumentId()).get("fileUrl"));
        }
        	
        return map;
    }

    @POST
    @Path("/queryDocument")
    public Object queryHistoryList(Document document) {
        Page<Document> page = new Page<Document>();
        logger.info("documentQuery.queryByConditions");
        page = documentService.queryDocumentList(document);
        return page;
    }
    
    @POST
    @Path("/queryDocumentRecord")
    public Object queryRecordList(DocumentRecord document) {
        Page<DocumentRecord> page = new Page<DocumentRecord>();
        logger.info("documentQuery.queryByConditions");
        page = documentService.queryDocumentRecordList(document);
        return page;
    }
    
    @POST
    @Path("/saveDocumentInfo")
    public Map<String,String> saveDocumentInfo(TDocument paraDoc) throws Exception{
        logger.info("documentService.save" + JSONUtils.toJSON(paraDoc));
        
        TDocument document = documentService.load(paraDoc.getDocumentId());
        document.setDocumentType(paraDoc.getDocumentType());
        document.setDocumentTypeSelf(paraDoc.getDocumentTypeSelf());
        document.setRemarks(paraDoc.getRemarks());        
     
        DocumentRule documentRule = documentService.findDocumentRule(document.getBusinessType());        
        //if need parse file
        if ("2".equals(documentRule.getProcessType())) {
            List itemVOs = documentService.parseDocument(document);
            documentService.processBusiness(itemVOs, documentRule.getParseService(), document.getDocumentId(),document.getBusinessId());
            
        }
        
        document.setUploadPerson(AppContext.getCurrentUser().getUserId());
        document.setStatus("1");
        documentService.saveDocument(document);
        
        Map<String, String> mp = new HashMap();
        mp.put("ProcessType", documentRule.getProcessType());
        mp.put("BusnessType", documentRule.getBusinessType());
        mp.put("BusinessId", String.valueOf(document.getBusinessId()));
        mp.put("DocumentId", String.valueOf(document.getDocumentId()));
        return mp;
    } 
    
    
    @GET
    @Path("/getFileExtionsion/{businessType}")
    public Map<String,String[]> getFileExtension(@PathParam("businessType") String businessType){
        DocumentRule rule = documentService.findDocumentRule(businessType);
        String parseType = rule.getParseType();
        String[] extension = null;
        if(parseType == null){
         extension  = new String[]{};
        }else if(parseType.equalsIgnoreCase("excel")){
             extension  = new String[]{"xlsx","xls"};
        }else if(parseType.equalsIgnoreCase("pdf")){
             extension  = new String[]{"pdf"};
        }else if(parseType.equalsIgnoreCase("xml")){
             extension  = new String[]{"xml"};
        }
        Map<String, String[]> mp = new HashMap();
        mp.put("extension", extension);
        return mp;
        
    }
    
    @GET
    @Path("/remove/{documentId}")
    public  void fileRemove(@PathParam("documentId") Long documentId) throws Exception {        
        TDocument document = documentService.load(documentId); 
        documentService.remove(document);
    }
}
