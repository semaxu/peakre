package com.ebao.ap99.accounting.service;

import java.util.List;

import com.ebao.ap99.accounting.YearQuarter;
import com.ebao.ap99.accounting.UI.model.Soa4UpdateVO;
import com.ebao.ap99.accounting.UI.model.SoaLayerVO;
import com.ebao.ap99.accounting.UI.model.SoaSearchVO;
import com.ebao.ap99.accounting.entity.TRiSoaSection;
import com.ebao.ap99.accounting.model.SoaEntry;
import com.ebao.ap99.accounting.model.SoaModel;
import com.ebao.ap99.parent.Page;

public interface SoaBizService {

    //Soa Search Page
    Page<SoaModel> getSoaInfo(SoaSearchVO soaSearchV) throws Exception;

    //link by statementId
    SoaModel loadSoaInfo(Long statementId) throws Exception;

    //create SoaLayer
    Object createSoaAndCurrencyLayerInfo(SoaLayerVO soaLayerV) throws Exception;

    //save or update 
    Object saveAndUpdateSoaInfo(Soa4UpdateVO soaU) throws Exception;

    // link by withdraw button 	
    void withdrawSoaInfo(long statementId) throws Exception;

    // link by withdrawIngoring Cut Off Date
    void withdraInCutOffDate(long statementId);

    // link by cancel button	
    void cancelSoaInfo(Long statementId);

    // link by delete currency group button	
    void deleteSoaCurrency(long soaCurrencyId);

    // link by delete section button	
    void deleteSoaSection(long soaSectionId);

    // link by delete item button	
    void deleteSoaSectionItem(long soaSectionItemId);

    //link by view button
    Object loadSoaViewInfo(Long statementId);

    //link by submit button 
    void submitSoaInfo(Soa4UpdateVO soaU) throws Exception;

    void PTFSubmitSoaInfo(Soa4UpdateVO soaU) throws Exception;

    void ignoringSubmintSoa(Soa4UpdateVO soaU) throws Exception;

    void withdrawIgnoringCutOffDateSoaInfo(long SoaIdRead) throws Exception;

    void submitAndReverseInfo(Soa4UpdateVO soaU) throws Exception;

    void ignoringSubmitAndReverseInfo(Soa4UpdateVO soaU) throws Exception;

    //link by save button of soa view page 
    void saveSoaViewInfo(long SoaIdRead, String ReviewedFlag, String SoaText);

    Object validateCurrentUser(Long statementId);

    Object validateSoa(String ContractCode, Integer UWYear, Integer CendantYear, Integer CendantQuarter)
            throws NumberFormatException, Exception;

    Object validatePTFSoa(String ContractCode, Integer UWYear, Integer CendantYear, Integer CendantQuarter)
            throws NumberFormatException, Exception;

    Object validateCendentQuarter(String ContractCode, Integer UWYear, Integer CendantYear, Integer CendantQuarter,
                                  String Cedent, String Broker) throws Exception, Exception;

    //   void revaluationJob();

    Object validateSoaReversal(Soa4UpdateVO soaU) throws Exception;

    Object AMLCheck(String cedent, String broker) throws Exception;

    Object IsSoaWithdrawOrder(String soaID) throws Exception;

    Object ValidEntryItemForStatementType(String entryCode, String statementType) throws Exception;

    //   List<TRiSoaSection> findTRiSoaSectionList(TRiSoa condition);

    List<SoaEntry> findSoaEntryListBySectionIdAndFNQuarter(Long sectionId, YearQuarter yearQuarter);

    List<SoaEntry> findSoaEntryListBySoaId(Long soaId);

    //   GeneralQueryView for StatementView

    List<SoaModel> findSoaList(SoaSearchVO soaSearchVO) throws Exception;

    List<TRiSoaSection> findSoaSectionListBySoaId(Long soaId) throws Exception;

    void saveSoaBOForUploading(SoaModel soaM) throws Exception;

}
