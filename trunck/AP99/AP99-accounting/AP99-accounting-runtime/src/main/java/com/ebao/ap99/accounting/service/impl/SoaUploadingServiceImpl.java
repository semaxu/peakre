package com.ebao.ap99.accounting.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.accounting.entity.TRiSoa;
import com.ebao.ap99.accounting.model.OperationResult;
import com.ebao.ap99.accounting.model.SoaCurrencyModel;
import com.ebao.ap99.accounting.model.SoaModel;
import com.ebao.ap99.accounting.model.SoaSectionModel;
import com.ebao.ap99.accounting.model.SoaTreeVO;
import com.ebao.ap99.accounting.service.SoaBizService;
import com.ebao.ap99.accounting.service.SoaUploadingService;
import com.ebao.ap99.accounting.util.OperationStatus;
import com.ebao.ap99.contract.entity.ContractModel;
import com.ebao.ap99.contract.service.ContractService;
import com.ebao.ap99.file.model.ItemVO;
import com.ebao.ap99.file.model.MessageVO;
import com.ebao.ap99.file.service.BizService;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

public class SoaUploadingServiceImpl implements BizService, SoaUploadingService {
	private static Logger logger = LoggerFactory.getLogger();
	@Autowired
    private SoaBizService soaBizService;
	
	@Autowired
	public ContractService contractService;


    @Override
    public void save(ItemVO itemVo, Long documentId) throws Exception {
    	SoaModel bizVO = (SoaModel) itemVo.getBizVO();
        try {
            resetSoaModelBO(bizVO, documentId);
            soaBizService.saveSoaBOForUploading(bizVO);
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
            checkList(itemVoList);
            int i = 0;
            for (Object obj : itemVoList) {
                i++;
                ItemVO<SoaModel> itemVO = (ItemVO<SoaModel>) obj;
                itemVO.setRowNo(String.valueOf(i));
                save(itemVO, documentId);
            }
        }
    }

    private void checkList(List itemVoList) throws Exception {
//    	 for (Object obj : itemVoList) {
//             ItemVO<SoaModel> itemVO = (ItemVO<SoaModel>) obj;
//         }
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

    private void resetSoaModelBO(SoaModel bizVO, Long documentId) throws Exception {
    	 ContractModel contractModel = contractService.getInforceAndCancelledContractIdByCode(bizVO.getContractCode(),Long.parseLong(bizVO.getUwYear().toString()));    	 
    	 if (contractModel != null) {
    		 bizVO.setBroke(contractModel.getBroker());
    		 bizVO.setCedent(contractModel.getCedent());
    		 bizVO.setContractId(contractModel.getContCompId());
             // due date 
             initSoaDueDate(bizVO,contractModel.getSettlementDays());
             bizVO.setContractName(contractModel.getContractName());
             for(SoaCurrencyModel currencyModel : bizVO.getCurrencies()){
            	//section comp id
            	 for(SoaSectionModel sectionModel : currencyModel.getSections()){
            		    String[] arr = sectionModel.getContractSection().split(">");
            	        List<String> sectionIdlist = (List<String>) Arrays.asList(arr);
            	        
            		 Long sectionId =contractService.getSectionIdByContractCodeAndSectionInfo(bizVO.getContractCode()
            				 ,Long.parseLong(bizVO.getUwYear().toString()), contractModel.getReinsStarting(), sectionIdlist.get(2), sectionModel.getShareType());
            		 sectionModel.setContracCompId(sectionId.toString());
            	 }
             }
         }

    }
    
    public void initSoaDueDate(SoaModel bizVO, BigDecimal settlementDays) throws Exception {
		Calendar c = Calendar.getInstance();
		if (settlementDays == null) {
			settlementDays = BigDecimal.ZERO;
		}
		c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(settlementDays.toString()));
		bizVO.setDueDate(c.getTime());
	}

}
