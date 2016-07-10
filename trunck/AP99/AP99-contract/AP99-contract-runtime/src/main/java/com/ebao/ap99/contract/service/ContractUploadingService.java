package com.ebao.ap99.contract.service;

import java.util.List;

import com.ebao.ap99.contract.model.OperationResult;
import com.ebao.ap99.file.model.ItemVO;

public interface ContractUploadingService {

    public void save(ItemVO itemVo, Long documentId) throws Exception;

    public OperationResult saveWithCheck(ItemVO itemVo) throws Exception;

    public void saveAll(List itemVoList, Long documentId) throws Exception;

}
