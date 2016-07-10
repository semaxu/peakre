package com.ebao.ap99.file.service;


import java.util.List;
import java.util.Map;

import com.ebao.ap99.file.entity.DocumentField;
import com.ebao.ap99.file.entity.DocumentRule;
import com.ebao.ap99.file.entity.TDocument;
import com.ebao.ap99.file.model.Document;
import com.ebao.ap99.file.model.DocumentRecord;
import com.ebao.ap99.file.model.ItemVO;
import com.ebao.ap99.parent.Page;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface DocumentService {
    public Page<Document> queryDocumentList(Document document);
    
    public Page<DocumentRecord> queryDocumentRecordList(DocumentRecord documentRecord);
    
    public Long saveDocument(TDocument document);
    
    public List parseDocument(TDocument document) throws Exception;
    
    public DocumentRule findDocumentRule(String businessType);
    
    public Object processBusiness(List models,String serviceName,Long documentId,Long businessId) throws Exception;
    
    public void saveParseResult(List<ItemVO> itemVOs,Long documentId);
    
    public String  writeDocument(List itemVOs,DocumentRule documentRule) throws Exception;
    
    public TDocument load(Long documentId);
    
    public void remove(TDocument document);
    
    public List<TDocument> getDocumentList(Long businessId);
    
    public List<DocumentField> queryDocumentFieldList(String businessType);

	public TDocument createNewDocument(String businessType, Map<String, String> paramMap) throws JsonProcessingException;
}
