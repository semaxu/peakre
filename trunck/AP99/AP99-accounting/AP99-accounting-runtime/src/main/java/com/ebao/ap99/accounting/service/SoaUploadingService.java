package com.ebao.ap99.accounting.service;

import java.util.List;

import com.ebao.ap99.accounting.model.OperationResult;
import com.ebao.ap99.file.model.ItemVO;

public interface SoaUploadingService {

    public void save(ItemVO itemVo, Long documentId) throws Exception;

    public OperationResult saveWithCheck(ItemVO itemVo) throws Exception;

    public void saveAll(List itemVoList, Long documentId) throws Exception;

}
