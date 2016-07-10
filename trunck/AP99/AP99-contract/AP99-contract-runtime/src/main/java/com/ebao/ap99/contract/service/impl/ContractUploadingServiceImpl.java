package com.ebao.ap99.contract.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.contract.model.OperationResult;
import com.ebao.ap99.contract.model.BusinessModel.ContractBO;
import com.ebao.ap99.contract.service.ContractDS;
import com.ebao.ap99.contract.service.ContractUploadingService;
import com.ebao.ap99.contract.util.ContractCst;
import com.ebao.ap99.contract.util.OperationStatus;
import com.ebao.ap99.file.model.ItemVO;
import com.ebao.ap99.file.model.MessageVO;
import com.ebao.ap99.file.service.BizService;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

public class ContractUploadingServiceImpl implements BizService, ContractUploadingService {
    private static Logger logger = LoggerFactory.getLogger();

    @Autowired
    private ContractDS contractDS;

    @Override
    public void save(ItemVO itemVo, Long documentId) throws Exception {
        ContractBO bizVO = (ContractBO) itemVo.getBizVO();
        try {
            resetContractBO(bizVO, documentId);
            contractDS.saveContractBO(bizVO, true);
            itemVo.setResultFlag(true);
            itemVo.setMsg("success");
        } catch (Exception e) {
            itemVo.setMsg("Contract Code:" + bizVO.getContractCode() + "Contract Name:" + bizVO.getContractName()
                    + ",has an error message:" + e.getMessage() + ",error cause:" + e.getCause());
            itemVo.setResultFlag(false);
            logger.error("Contract Code:" + bizVO.getContractCode() + "Contract Name:" + bizVO.getContractName()
                    + ",has an error message:" + e.getMessage() + ",error cause:" + e.getCause());
        }

    }

    @Override
    public OperationResult saveWithCheck(ItemVO itemVo) throws Exception {
        if (logger.isInfoEnabled()) {
            logger.info("ContractUploadingServiceImpl.saveWithCheck");
        }
        OperationResult result = new OperationResult();
        //TODO do some basic validation
        if (result.getStatus() != OperationStatus.FAILED.getValue()) {
            //TODO
        }
        return result;
    }

    @Override
    public void saveAll(List itemVoList, Long documentId) throws Exception {
        if (itemVoList != null && itemVoList.size() > 0) {
            //checkList(contractBOList);
            int i = 0;
            for (Object obj : itemVoList) {
                i++;
                ItemVO<ContractBO> itemVO = (ItemVO<ContractBO>) obj;
                itemVO.setRowNo(String.valueOf(i));
                save(itemVO, documentId);
            }
        }
    }

    private void checkList(List contractBOList) throws Exception {
        //TODO
    }

    @Override
    public MessageVO bizProcess(List itemVOList, Long documentId, Long businessId) {
        if (itemVOList != null && itemVOList.size() > 0) {
            try {
                saveAll(itemVOList, documentId);
            } catch (Exception e) {
                logger.error("error message:" + e.getMessage() + ",error cause:" + e.getCause());
            }
        }
        return new MessageVO();//it may be unnecessary
    }

    private void resetContractBO(ContractBO bizVO, Long documentId) throws Exception {

        //retset docunmentId and latestStatus
        bizVO.setDocumentId(documentId);
        bizVO.setLatestStatus(ContractCst.CONTRACT_STATUS_DATA_INPUT);

    }

}
