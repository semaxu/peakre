package com.ebao.ap99.file.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.ebao.ap99.file.dao.DocumentTypeDao;
import com.ebao.ap99.file.dao.DocumentDao;
import com.ebao.ap99.file.dao.DocumentRecordDao;
import com.ebao.ap99.file.dao.DocumentRuleDao;
import com.ebao.ap99.file.dao.QuerySqlDocument;
import com.ebao.ap99.file.entity.TDocument;
import com.ebao.ap99.file.entity.DocumentType;
import com.ebao.ap99.file.entity.DocumentField;
import com.ebao.ap99.file.entity.TDocumentRecord;
import com.ebao.ap99.file.entity.DocumentRule;
import com.ebao.ap99.file.model.Document;
import com.ebao.ap99.file.model.DocumentRecord;
import com.ebao.ap99.file.model.ItemVO;
import com.ebao.ap99.file.service.BizService;
import com.ebao.ap99.file.service.DocumentService;
import com.ebao.ap99.file.service.FileService;
import com.ebao.ap99.file.util.Constants;
import com.ebao.ap99.file.util.DocumentParser;
import com.ebao.ap99.file.util.ExcelGenerator;
import com.ebao.ap99.parent.Page;
import com.ebao.ap99.parent.PaginationHelper;
import com.ebao.ap99.parent.context.AppContext;
import com.ebao.unicorn.platform.foundation.context.SpringContextUtils;
import com.ebao.unicorn.platform.foundation.context.impl.SpringContextManager;
import com.ebao.unicorn.platform.foundation.exception.AppException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.util.CollectionUtils;

public class DocumentServiceImpl implements DocumentService {
    @Autowired
    private DocumentDao       documentDao;
    @Autowired
    private DocumentRuleDao   documenRuleDao;
    @Autowired
    private DocumentRecordDao documentRecordDao;
    @Autowired
    private DocumentTypeDao documentTypeDao;
    @Autowired 
    private FileService fileService;
    
    @Override
    public Long saveDocument(TDocument document) {
        if (document.getDocumentId() == 0) {
            documentDao.insert(document);
        } else {
            documentDao.merge(document);
        }

        return document.getDocumentId();
    }

    public Page<Document> queryDocumentList(Document document) {
        Page<Document> page = new Page<Document>();
        PaginationHelper<Document> ph = new PaginationHelper<Document>();
        QuerySqlDocument sqltmp = new QuerySqlDocument();


        String sql = sqltmp.getQueryDocumentSql(document);

        BeanPropertyRowMapper<Document> rowmapper = new BeanPropertyRowMapper<Document>(Document.class);

        page = ph.fetchPage(sql, new Object[] {}, document.getPageIndex(), document.getCountPerPage(), rowmapper);

        return page;
    }

    @Override
    public List parseDocument(TDocument document) throws Exception {
    	DocumentRule documentRule = this.findDocumentRule(document.getBusinessType()); 
        List<ItemVO<Object>> list = null;
        File file = fileService.readPhysicalFileContent(document.getFilePath());

        if (Constants.FileParseType.XML.equals(documentRule.getParseType())) {
            list = DocumentParser.documentXmlParser(file, documentRule);
        } else if (Constants.FileParseType.EXCEL.equals(documentRule.getParseType())) {
            list = DocumentParser.creatModelList(file, documentRule);
        } else {
        	throw new RuntimeException("Unknown parse type: " + documentRule.getParseType());
        }
        

        return list;
    }
    @Override
    public String  writeDocument(List itemVOs,DocumentRule documentRule) throws Exception{
        List <DocumentField> fields = queryDocumentFieldList(documentRule.getBusinessType());
    	//List <DocumentField> fields = documentRule.getTDocumentFields();
        return  ExcelGenerator.createModelList(itemVOs,documentRule,fields);        
    }
    
    static Map<String, DocumentRule> docRuleCache= new HashMap();
    @Override
    public DocumentRule findDocumentRule(String bizType) {
        DocumentRule docRule = docRuleCache.get(bizType);
        if (docRule == null) {
            docRule = documenRuleDao.load(bizType);
            docRuleCache.put(bizType, docRule);
        }
        return docRule;             
    }   
    
    @Override
    public Object processBusiness(List itemVOs, String serviceName, Long documentId,Long businessId) throws Exception {
        ApplicationContext applicationContext = SpringContextManager.getApplicationContext();
        BizService biz = (BizService) applicationContext.getBean(serviceName);
        
        biz.bizProcess(itemVOs, documentId,businessId);

        saveParseResult(itemVOs, documentId);
        
        return null;

    }

    @Override
    public void saveParseResult(List<ItemVO> itemVOs, Long documentId) {
        if (CollectionUtils.isEmpty(itemVOs)) 
        	return;
        
        for (ItemVO itemVO : itemVOs) {
            TDocumentRecord tDocumentRecord = new TDocumentRecord();
            tDocumentRecord.setParseResult(itemVO.getMsg());
            tDocumentRecord.setDocumentId(documentId);
            tDocumentRecord.setRecordContent("Row No :"+itemVO.getRowNo());
            //if (tDocumentRecord.getDocumentRecordId() == 0) {
                documentRecordDao.insert(tDocumentRecord);
            //} else {
            //    documentRecordDao.merge(tDocumentRecord);
            //}
        }       

    }

    @Override
    public Page<DocumentRecord> queryDocumentRecordList(DocumentRecord documentRecord) {
        Page<DocumentRecord> page = new Page<DocumentRecord>();
        PaginationHelper<DocumentRecord> ph = new PaginationHelper<DocumentRecord>();
        QuerySqlDocument sqltmp = new QuerySqlDocument();
        String sql = sqltmp.getDocumentRecordSql(documentRecord.getDocumentId());
        BeanPropertyRowMapper<DocumentRecord> rowmapper = new BeanPropertyRowMapper<DocumentRecord>(
                DocumentRecord.class);
        page = ph.fetchPage(sql, new Object[] {}, documentRecord.getPageIndex(), documentRecord.getCountPerPage(),
                rowmapper);
        return page;
    }

    @Override
    public TDocument load(Long documentId) {
        return documentDao.load(documentId);
    }

    @Override
    public List<TDocument> getDocumentList(Long businessId) {
        return documentDao.findList(businessId);
    }
    
    @Override
    public List<DocumentField> queryDocumentFieldList(String businessType) {
        QuerySqlDocument sqltmp = new QuerySqlDocument();
        JdbcTemplate jt = SpringContextUtils.getJdbcTemplate();
        List<DocumentField> itemList = jt.query(sqltmp.getDocumentFieldSql(businessType),
                new Object[] {  },
                new BeanPropertyRowMapper<DocumentField>(DocumentField.class));
        return  itemList;     
    }
    
    @Override
	public TDocument createNewDocument(String businessType, Map<String, String> paramMap)
	        throws JsonProcessingException {
	    ObjectMapper mapper = new ObjectMapper();       
	    String json = null;
	    if (paramMap!=null) json = mapper.writeValueAsString(paramMap);
	    
	    TDocument document = new TDocument();
	    document.setBusinessType(businessType);
	    document.setPara(json);
        document.setUploadPerson(AppContext.getAppUser().getUserId());
        document.setUploadTime(AppContext.getSysDate());
        document.setStatus("1");
	    saveDocument(document);
	    return document;
	}
    
    @Override
    public void remove(TDocument document){
        documentDao.delete(document);
    }
}
