package com.ebao.ap99.accounting.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.accounting.model.SoaCurrencyModel;
import com.ebao.ap99.accounting.model.SoaTreeVO;
import com.ebao.ap99.accounting.service.ChooseSectionBizService;
import com.ebao.ap99.contract.entity.ContractModel;
import com.ebao.ap99.contract.entity.SectionModel;
import com.ebao.ap99.contract.service.ContractService;
import com.ebao.ap99.parent.TreeVO;
import com.ebao.ap99.parent.model.TreeModel;

public class ChooseSectionBizServiceImpl implements ChooseSectionBizService {

    @Autowired
    public ContractService contractService;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void toChooseSecton(long statementId) {

    }

    @Override
    public Object loadSectionsInfo(String treeArray) throws Exception {
        String[] arr = treeArray.split(",");
        List<String> sectionIdlist = (List<String>) Arrays.asList(arr);
        List<SoaTreeVO> treeVOList = new ArrayList<SoaTreeVO>();
        SoaTreeVO treeVO = null;
        for (int i = 0; i < sectionIdlist.size(); i++) {
            ContractModel contactVO = new ContractModel();
            contactVO = contractService.getContractInfoByCompId(Long.valueOf(sectionIdlist.get(i)));
            treeVO = getSectionList(contactVO);
            treeVOList.add(treeVO);
        }
        return treeVOList;
    }

    public SoaTreeVO getSectionList(ContractModel contactVO) throws Exception {
        SoaTreeVO treeVO = new SoaTreeVO();
        treeVO.setId(contactVO.getContCompId());
        treeVO.setText(contactVO.getFullName());
        ContractModel contractVO = contractService.getContractInfoByCompId(contactVO.getContractId());
        treeVO.setCob(contractVO.getMainclass());
		treeVO.setUwArea(contractVO.getCedentCountry());
        for(SectionModel secM : contractVO.getSectionModel()){   	
        //	if(secM.getSectionId() ==contactVO.getContCompId()){
        		treeVO.setShareType(secM.getShareType());
        //	}
        }
        return treeVO;
    }

    @Override
    public List<TreeModel> getContractStructureByCode(String contractCode, Long uwYear, boolean isInforce) throws Exception {
        return contractService.getInforceAndCancelledContractStructureByCode(contractCode, uwYear);
    }

}
