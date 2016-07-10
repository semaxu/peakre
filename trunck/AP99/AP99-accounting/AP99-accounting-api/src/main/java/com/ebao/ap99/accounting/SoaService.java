package com.ebao.ap99.accounting;

import com.ebao.ap99.accounting.model.SoaNonPropVO;

public interface SoaService {
	/**
     * create the Non-Proportional Contract
     * revision
     * 
     * @param contractPremiumModel
     * @throws Exception
     */
    public void createSoaModelForNonProportionalContract(SoaNonPropVO SoaVO) throws Exception;
   
    public boolean validSoaBySectionId(Long sectionID) throws Exception;
}
