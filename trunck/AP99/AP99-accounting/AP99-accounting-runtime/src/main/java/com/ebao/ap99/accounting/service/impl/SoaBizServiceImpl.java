package com.ebao.ap99.accounting.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.ebao.ap99.accounting.SoaService;
import com.ebao.ap99.accounting.YearQuarter;
import com.ebao.ap99.accounting.UI.model.Soa4UpdateVO;
import com.ebao.ap99.accounting.UI.model.SoaCurrency4UpdateVO;
import com.ebao.ap99.accounting.UI.model.SoaLayerVO;
import com.ebao.ap99.accounting.UI.model.SoaLineViewVO;
import com.ebao.ap99.accounting.UI.model.SoaSearchVO;
import com.ebao.ap99.accounting.UI.model.SoaSection4UpdateVO;
import com.ebao.ap99.accounting.UI.model.SoaViewPage;
import com.ebao.ap99.accounting.constant.EstimateStatusEnum;
import com.ebao.ap99.accounting.dao.SoaCurrencyDao;
import com.ebao.ap99.accounting.dao.SoaDao;
import com.ebao.ap99.accounting.dao.SoaSectionDao;
import com.ebao.ap99.accounting.dao.SoaSectionItemDao;
import com.ebao.ap99.accounting.dao.TRiAcctEstimateDao;
import com.ebao.ap99.accounting.entity.TRiAcctEstimate;
import com.ebao.ap99.accounting.entity.TRiSoa;
import com.ebao.ap99.accounting.entity.TRiSoaCurrency;
import com.ebao.ap99.accounting.entity.TRiSoaSection;
import com.ebao.ap99.accounting.entity.TRiSoaSectionItem;
import com.ebao.ap99.accounting.model.ActualItem;
import com.ebao.ap99.accounting.model.SoaCurrencyModel;
import com.ebao.ap99.accounting.model.SoaEntry;
import com.ebao.ap99.accounting.model.SoaExportExcelModel;
import com.ebao.ap99.accounting.model.SoaModel;
import com.ebao.ap99.accounting.model.SoaNonPropVO;
import com.ebao.ap99.accounting.model.SoaSectionItemModel;
import com.ebao.ap99.accounting.model.SoaSectionModel;
import com.ebao.ap99.accounting.model.SoaValidateModel;
import com.ebao.ap99.accounting.service.ChooseSectionBizService;
import com.ebao.ap99.accounting.service.SoaBizService;
import com.ebao.ap99.accounting.util.SoaBuilderImpl;
import com.ebao.ap99.accounting.util.SoaConstant;
import com.ebao.ap99.accounting.util.SoaModelBuilderImpl;
import com.ebao.ap99.arap.constant.BizTransactionType;
import com.ebao.ap99.arap.entity.EntryCode;
import com.ebao.ap99.arap.service.EntryCodeService;
import com.ebao.ap99.arap.service.FinanceService;
import com.ebao.ap99.arap.service.GLPostingCallBackService;
import com.ebao.ap99.arap.vo.BusinessFee;
import com.ebao.ap99.arap.vo.BusinessFeeModel;
import com.ebao.ap99.contract.entity.ContractModel;
import com.ebao.ap99.contract.service.ContractService;
import com.ebao.ap99.file.service.CalService;
import com.ebao.ap99.parent.Page;
import com.ebao.ap99.parent.constant.NumberingType;
import com.ebao.ap99.parent.context.AppContext;
import com.ebao.ap99.parent.model.TreeModel;
import com.ebao.ap99.partner.entity.MessageVO;
import com.ebao.ap99.partner.service.CheckAmlService;
import com.ebao.ap99.partner.service.PartnerAPI;
import com.ebao.unicorn.platform.data.code.CodeService;
import com.ebao.unicorn.platform.foundation.context.SpringContextUtils;
import com.ebao.unicorn.platform.foundation.numbering.NumberingService;
import com.ebao.unicorn.platform.foundation.utils.BeanUtils;

public class SoaBizServiceImpl implements SoaBizService, SoaService, GLPostingCallBackService, CalService {

    @Autowired
    private SoaDao soaDao;

    @Autowired
    private SoaCurrencyDao soaCurrencyDao;

    @Autowired
    private SoaSectionDao soaSectionDao;

    @Autowired
    private SoaSectionItemDao soaSectionItemDao;

    @Autowired
    SoaModelBuilderImpl soaModelBuilder;

    @Autowired
    SoaBuilderImpl soaBuulder;

    @Autowired
    FinanceService financeService;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private NumberingService numService;

    @Autowired
    EntryCodeService entryCodeService;

    @Autowired
    private EstimateDSImpl estimateDSImpl;

    @Autowired
    public ContractService contractService;

    @Autowired
    private AccountingServiceImpl accountingServiceImpl;

    @Autowired
    private ChooseSectionBizService chooseSectionBizService;

    @Autowired
    private TRiAcctEstimateDao acctEstimateDao;

    @Autowired
    private CheckAmlService checkAmlService;

    @Autowired
    PartnerAPI partnerService;

    @Autowired
    private CodeService codeTableService;

    public SoaBizServiceImpl() {
    }

    @Override
    public Page<SoaModel> getSoaInfo(SoaSearchVO soaSearchVO) throws Exception {
        Page<SoaModel> page = buildSoaInfo4SearchPage(soaSearchVO);

        return page;
    }

    @Override
    public SoaModel loadSoaInfo(Long statementId) throws Exception {
        SoaModel soaM = null;
        TRiSoa soa = new TRiSoa();
        soa = soaDao.load(statementId);
        try {
            soaM = (SoaModel) soaModelBuilder.buildSoaModel(soa);
            //set the  cutoff date period flag
            validateCutOffDatePeriod(soaM);
            //set the contract Nature and category and settlement days
            setContractNature(soaM);
            //
            // order the section of currencies
            for (SoaCurrencyModel soaCurrencyModel : soaM.getCurrencies()) {
                orderSectionOfCurrencies(soaCurrencyModel);
            }

        } catch (Exception e) {
            throw e;
        }
        return soaM;
    }

    @Override
    public TRiSoa createSoaAndCurrencyLayerInfo(SoaLayerVO soaV) throws Exception {
        TRiSoa soa = null;
        try {
            // create soa layer info
            soa = soaBuulder.buildSoaObjFromVO(soaV);
            // init the soa number
            String soaNumber = numService.generateNumber(NumberingType.ACCOUNTING_SOA_NUMBER,
                    new HashMap<String, String>());
            soa.setSoaNumber(soaNumber);
            // init the statement type 
            soa.setStatementType(soaV.getStatementType().toString());
            //init Broker and Cedent
            ContractModel contractModel = contractService.getInforceAndCancelledContractIdByCode(soaV.getContractCode(),
                    Long.parseLong(soaV.getUwYear().toString()));
            if (contractModel != null) {
                soa.setBroke(contractModel.getBroker());
                soa.setCedent(contractModel.getCedent());
                soa.setContractId(contractModel.getContCompId());
                //init due date 
                initSoaDueDate(soa, contractModel.getSettlementDays());
                soa.setContractName(contractModel.getContractName());
                soa.setWithdrawIgnoringCutoffDate(false);//0:not special submit
                soaDao.insert(soa);
                //create currencies layer info
                ContractModel contractVO = contractService.getContractInfoByCompId(contractModel.getContCompId());
                if (contractVO.getMainCurrency() != null && contractVO.getMainCurrency() != "") {
                    SoaCurrencyModel soaCurrencyM = new SoaCurrencyModel();
                    soaCurrencyM.setCurrencyType(contractVO.getMainCurrency());
                    soaCurrencyM.setSoaId(String.valueOf(soa.getSoaId()));
                    initCurrecyLayerInfo(soa, soaCurrencyM);
                }
            }

        } catch (Exception e) {
            throw e;
        }
        return soa;
    }

    @Override
    public Soa4UpdateVO saveAndUpdateSoaInfo(Soa4UpdateVO soaU) throws Exception {
        try {
            SoaModel soaCurrencyMapValidModel = loadSoaInfo(Long.parseLong(soaU.getSoaId()));
            List<String> soaCurrencyMatchList = new ArrayList<String>();// get data from DB
            List<String> soaSectionMatchList = new ArrayList<String>();// get data from DB
            List<String> soaSectionItemMatchList = new ArrayList<String>();// get data from DB
            // init the soa info list from DB
            initSoaInfoList(soaCurrencyMapValidModel, soaCurrencyMatchList, soaSectionMatchList,
                    soaSectionItemMatchList);
            // save or update soa info
            saveOrUpdateSoaInfo(soaU, soaCurrencyMatchList, soaSectionMatchList, soaSectionItemMatchList);
            // update soaSectionItem the delete data of UI for DB
            deleteSoaSectionItem4DB(soaSectionItemMatchList);
            // update soaSection the delete data of UI for DB
            deleteSoaSection4DB(soaSectionMatchList);
            // update soaCurrency the delete data of UI for DB
            deleteSoaCurrency4DB(soaCurrencyMatchList);
            em.flush();
            //return the UI Model Data
            return loadSoaInfoForUI(Long.parseLong(soaU.getSoaId()));
        } catch (Exception e) {
            throw e;
        }
    }

    public Soa4UpdateVO loadSoaInfoForUI(Long statementId) throws Exception {
        SoaModel soaM = null;
        Soa4UpdateVO soaVO = null;
        try {
            soaM = loadSoaInfo(statementId);
            soaVO = soaBuulder.buildSoaVO(soaM);
        } catch (Exception e) {
            throw e;
        }
        return soaVO;
    }

    @Override
    public void withdraInCutOffDate(long statementId) {

    }

    @Override
    public void cancelSoaInfo(Long statementId) {
        TRiSoa soa = soaDao.load(statementId);
        soaDao.delete(soa);
    }

    @Override
    public void deleteSoaCurrency(long soaCurrencyId) {

        TRiSoaCurrency soaCurrency = new TRiSoaCurrency();
        if (soaCurrencyId != 0) {
            soaCurrency = soaCurrencyDao.load(soaCurrencyId);
            soaCurrencyDao.delete(soaCurrency);
        }
    }

    @Override
    public void deleteSoaSection(long soaSectionId) {

        TRiSoaSection soaSection = new TRiSoaSection();
        if (soaSectionId != 0) {
            soaSection = soaSectionDao.load(soaSectionId);
            soaSectionDao.delete(soaSection);
        }
    }

    @Override
    public void deleteSoaSectionItem(long soaSectionItemId) {

        TRiSoaSectionItem soaSectionItem = new TRiSoaSectionItem();
        if (soaSectionItemId != 0) {
            soaSectionItem = soaSectionItemDao.load(soaSectionItemId);
            soaSectionItemDao.delete(soaSectionItem);
        }
    }

    public void deleteSoaCurrency4DB(List<String> soaCurrencyMapList) {
        for (String soaCurrencyId : soaCurrencyMapList) {
            deleteSoaCurrency(Long.parseLong(soaCurrencyId));
        }
    }

    public void deleteSoaSection4DB(List<String> soaSectionMapList) {
        for (String soaSectionId : soaSectionMapList) {
            deleteSoaSection(Long.parseLong(soaSectionId));
        }
    }

    public void deleteSoaSectionItem4DB(List<String> soaSectiontemMapList) {
        for (String soaSectiontemId : soaSectiontemMapList) {
            deleteSoaSectionItem(Long.parseLong(soaSectiontemId));
        }
    }

    @Override
    public List<SoaViewPage<SoaLineViewVO>> loadSoaViewInfo(Long statementId) {
        SoaModel soaM = null;
        List<SoaViewPage<SoaLineViewVO>> SoaViewPageList = new ArrayList<SoaViewPage<SoaLineViewVO>>();
        TRiSoa soa = new TRiSoa();
        int sectionMaxCount = 1;
        soa = soaDao.load(statementId);
        try {
            soaM = (SoaModel) soaModelBuilder.buildSoaModel(soa);

            // deal with the same entryItem of the same section
            soaM = calculateSameEntryItem4SameSectionForView(soaM);
            // generate the reserve entryItem for Soa
            collectReserveEntryItemForSummary(soaM);
            // get max sections count
            sectionMaxCount = getMaxSectionCount(soaM);
            //set the  cutoff date period flag
            validateCutOffDatePeriod(soaM);
            //reset soa text 
            if (soa.getSoaLatestText() != null && !"".equals(soa.getSoaLatestText())) {
                soaM.setSoaText(soa.getSoaLatestText());
            }
            //get the contract category 
            String contractCategory = getContractategory(soaM);
            for (SoaCurrencyModel soaCurrencyMapValid : soaM.getCurrencies()) {
                // order the section of currencies
                orderSectionOfCurrencies(soaCurrencyMapValid);
                // calculate the soaCurrencyView data				
                List<SoaLineViewVO> soaViewList = loadSoaCurrencyViewInfo(soaCurrencyMapValid, contractCategory);
                SoaViewPage<SoaLineViewVO> soaViewPage = new SoaViewPage<SoaLineViewVO>();
                soaViewPage.setRows(soaViewList);
                if ("1".equals(soa.getReviewedFlag())) {
                    soaViewPage.setReviewedFlag(true);
                } else {
                    soaViewPage.setReviewedFlag(false);
                }
                soaViewPage.setReviewedBy(String.valueOf(AppContext.getCurrentUser().getUserName()));
                soaViewPage.setTaskcreatorName(soa.getTaskcreatorName());
                soaViewPage.setTaskreleaserName(soa.getTaskreleaserName());
                soaViewPage.setTakswithdrawName(soa.getTakswithdrawName());
                soaViewPage.setContractCurrecy(soaCurrencyMapValid.getCurrencyType());
                soaViewPage.setSectionCount(String.valueOf(sectionMaxCount));
                soaViewPage.setSoaM(soaM);
                SoaViewPageList.add(soaViewPage);
            }
            if (soaM.getCurrencies().size() == 0) {
                SoaViewPage<SoaLineViewVO> soaViewPage = new SoaViewPage<SoaLineViewVO>();
                if ("1".equals(soa.getReviewedFlag())) {
                    soaViewPage.setReviewedFlag(true);
                } else {
                    soaViewPage.setReviewedFlag(false);
                }
                soaViewPage.setReviewedBy(String.valueOf(AppContext.getCurrentUser().getUserName()));
                soaViewPage.setTaskcreatorName(soa.getTaskcreatorName());
                soaViewPage.setTaskreleaserName(soa.getTaskreleaserName());
                soaViewPage.setTakswithdrawName(soa.getTakswithdrawName());
                soaViewPage.setSectionCount("NO");
                soaViewPage.setSoaM(soaM);
                SoaViewPageList.add(soaViewPage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SoaViewPageList;
    }

    public List<SoaLineViewVO> loadSoaCurrencyViewInfo(SoaCurrencyModel currencyModel, String contractCategory)
            throws Exception {
        // calculate the lines of soaView table
        List<SoaLineViewVO> soaViewList = new ArrayList<SoaLineViewVO>();
        soaViewList.addAll(generateFixedLines(currencyModel));
        soaViewList.addAll(generateLinesForEntyItems(currencyModel, contractCategory));
        List<SoaLineViewVO> soaViewPageList = new ArrayList<SoaLineViewVO>();
        for (SoaLineViewVO soaViewVO : soaViewList) {
            if (soaViewVO.getLineTitle() != null) {
                soaViewPageList.add(soaViewVO);
            }
        }
        initOrderOfSoaViews(soaViewPageList);
        return soaViewPageList;
    }

    public int getMaxSectionCount(SoaModel soaM) {
        int maxSectionCount = 0;
        int sectionCount = 0;
        for (SoaCurrencyModel soaCurrencyMapValid : soaM.getCurrencies()) {
            sectionCount = soaCurrencyMapValid.getSections().size();
            if (sectionCount > maxSectionCount) {
                maxSectionCount = sectionCount;
            }
        }
        return maxSectionCount;
    }

    public List<SoaLineViewVO> initOrderOfSoaViews(List<SoaLineViewVO> soaViewList) {
        Comparator<SoaLineViewVO> comparator = new Comparator<SoaLineViewVO>() {
            public int compare(SoaLineViewVO s1, SoaLineViewVO s2) {
                if (s1.getOrderOfSoaViews() != s2.getOrderOfSoaViews()) {
                    return s1.getOrderOfSoaViews() - s2.getOrderOfSoaViews();
                }
                return 0;
            }
        };
        Collections.sort(soaViewList, comparator);
        return soaViewList;
    }

    public void initSoaInfoList(SoaModel soaCurrencyMapValidModel, List<String> soaCurrencyMatchList,
                                List<String> soaSectionMatchList, List<String> soaSectionItemMatchList) {
        for (SoaCurrencyModel soaCurrencyMapValid : soaCurrencyMapValidModel.getCurrencies()) {
            soaCurrencyMatchList.add(soaCurrencyMapValid.getSoaCurrencyId());
            for (SoaSectionModel soaSectionMapValid : soaCurrencyMapValid.getSections()) {
                soaSectionMatchList.add(soaSectionMapValid.getSoaSectionId());
                for (SoaSectionItemModel soaSectionItemMapValid : soaSectionMapValid.getEntryItems()) {
                    soaSectionItemMatchList.add(soaSectionItemMapValid.getItemId());
                }
            }
        }
    }

    public void saveOrUpdateSoaInfo(Soa4UpdateVO soaU, List<String> soaCurrencyMatchList,
                                    List<String> soaSectionMatchList, List<String> soaSectionItemMatchList)
                                            throws Exception {
        SoaModel soaM = (SoaModel) soaModelBuilder.buildSoaModelFromSoa4UpdateVO(soaU);
        TRiSoa soa = (TRiSoa) soaBuulder.buildSoaObjFromModel(soaM);
        soa.setSoaId(Long.parseLong(soaU.getSoaId()));
        // update SoaLayer Info
        TRiSoa existSoa = soaDao.load(Long.parseLong(soaU.getSoaId()));
        soa.setTaskcreator(existSoa.getTaskcreator());
        soa.setTaskreleaser(existSoa.getTaskreleaser());
        soa.setTakswithdraw(existSoa.getTakswithdraw());
        soa.setTaskcreatorName(existSoa.getTaskcreatorName());
        soa.setTaskreleaserName(existSoa.getTaskreleaserName());
        soa.setTakswithdrawName(existSoa.getTakswithdrawName());
        soa.setReviewedBy(existSoa.getReviewedBy());
        soa.setWithdrawIgnoringCutoffDate(existSoa.isWithdrawIgnoringCutoffDate());
        saveOrUpdateSoaCurrencyLayerInof(soaM, soa, soaCurrencyMatchList, soaSectionMatchList, soaSectionItemMatchList);
        soaDao.merge(soa);

    }

    public void saveOrUpdateSoaCurrencyLayerInof(SoaModel soaM, TRiSoa soa, List<String> soaCurrencyMatchList,
                                                 List<String> soaSectionMatchList, List<String> soaSectionItemMatchList)
                                                         throws Exception {
        for (SoaCurrencyModel soaCurrencyM : soaM.getCurrencies()) {
            TRiSoaCurrency soaCurrency = (TRiSoaCurrency) soaBuulder.buildSoaCurrencyObjFromModel(soaCurrencyM);
            soaCurrency.setTRiSoa(soa);
            soa.getTRiSoaCurrencies().add(soaCurrency);
            soaCurrencyMatchList.remove(soaCurrency.getSoaCurrencyId());
            saveOrUpdateSoaSectionLayerInof(soaCurrencyM, soaCurrency, soa, soaSectionMatchList,
                    soaSectionItemMatchList);
        }
    }

    public void saveOrUpdateSoaCurrencyLayerInofForUploading(SoaModel soaM, TRiSoa soa) throws Exception {
        for (SoaCurrencyModel soaCurrencyM : soaM.getCurrencies()) {
            TRiSoaCurrency soaCurrency = (TRiSoaCurrency) soaBuulder
                    .buildSoaCurrencyObjFromModelForUploading(soaCurrencyM);
            soaCurrency.setTRiSoa(soa);
            soa.getTRiSoaCurrencies().add(soaCurrency);
            saveOrUpdateSoaSectionLayerInofForUploading(soaCurrencyM, soaCurrency, soa);
        }
    }

    public void saveOrUpdateSoaSectionLayerInof(SoaCurrencyModel soaCurrencyM, TRiSoaCurrency soaCurrency, TRiSoa soa,
                                                List<String> soaSectionMatchList, List<String> soaSectionItemMatchList)
                                                        throws Exception {
        for (SoaSectionModel soaSectionM : soaCurrencyM.getSections()) {
            TRiSoaSection soaSection = (TRiSoaSection) soaBuulder.buildSoaSectionObjFromModel(soaSectionM);
            soaSection.setTRiSoaCurrency(soaCurrency);
            soaSection.setTRiSoa(soa);
            soa.getTRiSoaSections().add(soaSection);
            soaCurrency.getTRiSoaSections().add(soaSection);
            soaSectionMatchList.remove(soaSection.getSoaSectionId());
            saveOrUpdateSoaSectionItemLayerInof(soaSectionM, soaSection, soaSectionItemMatchList);
        }
    }

    public void saveOrUpdateSoaSectionLayerInofForUploading(SoaCurrencyModel soaCurrencyM, TRiSoaCurrency soaCurrency,
                                                            TRiSoa soa) throws Exception {
        for (SoaSectionModel soaSectionM : soaCurrencyM.getSections()) {
            TRiSoaSection soaSection = (TRiSoaSection) soaBuulder.buildSoaSectionObjFromModelForUploading(soaSectionM);
            soaSection.setTRiSoaCurrency(soaCurrency);
            soaSection.setTRiSoa(soa);
            soa.getTRiSoaSections().add(soaSection);
            soaCurrency.getTRiSoaSections().add(soaSection);
            saveOrUpdateSoaSectionItemLayerInofForUploading(soaSectionM, soaSection);
        }
    }

    public void saveOrUpdateSoaSectionItemLayerInof(SoaSectionModel soaSectionM, TRiSoaSection soaSection,
                                                    List<String> soaSectionItemMatchList) throws Exception {
        for (SoaSectionItemModel soaSectionItemM : soaSectionM.getEntryItems()) {
            TRiSoaSectionItem soaSectionItem = (TRiSoaSectionItem) soaBuulder
                    .buildSoaSectionItemObjFromModel(soaSectionItemM);
            soaSectionItem.setTRiSoaSection(soaSection);
            soaSection.getTRiSoaSectionItems().add(soaSectionItem);
            soaSectionItemMatchList.remove(soaSectionItem.getItemId());
        }
    }

    public void saveOrUpdateSoaSectionItemLayerInofForUploading(SoaSectionModel soaSectionM, TRiSoaSection soaSection)
            throws Exception {
        for (SoaSectionItemModel soaSectionItemM : soaSectionM.getEntryItems()) {
            TRiSoaSectionItem soaSectionItem = (TRiSoaSectionItem) soaBuulder
                    .buildSoaSectionItemObjFromModelForUploading(soaSectionItemM);
            soaSectionItem.setTRiSoaSection(soaSection);
            soaSection.getTRiSoaSectionItems().add(soaSectionItem);
        }
    }

    @Override
    public void submitSoaInfo(Soa4UpdateVO soaU) throws Exception {
        //save and update SoaInfo
        saveAndUpdateSoaInfo(soaU);
        long SoaIdRead = Long.parseLong(soaU.getSoaId());
        // generate the reserve entryItem for Soa
        collectReserveEntryItem(SoaIdRead);
        // load the latest Soa Info
        SoaModel soaM = loadSoaInfo(SoaIdRead);
        // set the portfolio transfer status 
        setPTFStatusForEachSection(soaM, true);
        // deal with the same entryItem of the same section
        soaM = calculateSameEntryItem4SameSection(soaM);
        // set the soaStatus to released
        TRiSoa soa = soaDao.load(SoaIdRead);
        //task release
        soa.setTaskreleaser(String.valueOf(AppContext.getCurrentUser().getUserId()));
        soa.setTaskreleaserName(String.valueOf(AppContext.getCurrentUser().getUserName()));
        soa.setSoaStatus(SoaConstant.RELEASED);
        soaDao.merge(soa);
        // init the reserve entryItem data for FN
        //initReserveEntryItemData(soaM);
        // general the ARAP record
        generalARAPData(soaM);
        // reversal the estimation entry item data
        prepareSoaForEstimation(soaM);

    }

    @Override
    public void PTFSubmitSoaInfo(Soa4UpdateVO soaU) throws Exception {
        //save and update SoaInfo
        saveAndUpdateSoaInfo(soaU);
        long SoaIdRead = Long.parseLong(soaU.getSoaId());
        // load the latest Soa Info
        SoaModel soaM = loadSoaInfo(SoaIdRead);
        // set the portfolio transfer status 
        setPTFStatusForEachSection(soaM, true);
        // deal with the same entryItem of the same section
        soaM = calculateSameEntryItem4SameSection(soaM);
        // set the soaStatus to released
        TRiSoa soa = soaDao.load(SoaIdRead);
        //task release
        soa.setTaskreleaser(String.valueOf(AppContext.getCurrentUser().getUserId()));
        soa.setTaskreleaserName(String.valueOf(AppContext.getCurrentUser().getUserName()));
        soa.setSoaStatus(SoaConstant.RELEASED);
        soaDao.merge(soa);
        // general the ARAP record
        generalPTFARAPData(soaM);
        // reversal the estimation entry item data
        prepareSoaForEstimation(soaM);

    }

    @Override
    public void ignoringSubmintSoa(Soa4UpdateVO soaU) throws Exception {
        //save and update SoaInfo
        saveAndUpdateSoaInfo(soaU);

        long SoaIdRead = Long.parseLong(soaU.getSoaId());
        //update the soa ignoring cut off date flag
        updateIgnoringFlag(SoaIdRead);
        // generate the reserve entryItem for Soa
        collectReserveEntryItem(SoaIdRead);
        // load the latest Soa Info
        SoaModel soaM = loadSoaInfo(SoaIdRead);
        // deal with the same entryItem of the same section
        soaM = calculateSameEntryItem4SameSection(soaM);
        // set the soaStatus to released
        TRiSoa soa = soaDao.load(SoaIdRead);
        //task release
        soa.setTaskreleaser(String.valueOf(AppContext.getCurrentUser().getUserId()));
        soa.setTaskreleaserName(String.valueOf(AppContext.getCurrentUser().getUserName()));
        soa.setSoaStatus(SoaConstant.RELEASED);
        soaDao.merge(soa);
        // init the reserve entryItem data for FN
        //  initReserveEntryItemData(soaM);
        // general the ARAP record
        generalARAPData(soaM);
        // reversal the estimation entry item data
        prepareSoaForEstimationBySpecialSubmit(soaM);

    }

    public SoaModel calculateSameEntryItem4EachSection(SoaModel soaM) {
        for (SoaCurrencyModel soaCurrencyM : soaM.getCurrencies()) {
            if (soaCurrencyM.getSections() != null) {
                for (SoaSectionModel soaSectionM : soaCurrencyM.getSections()) {
                    if (soaSectionM.getEntryItems() != null) {
                        // calculate the entryItem for each Soa Section
                        Map<String, String> soaSectionMatchMap = new HashMap<String, String>();
                        List<SoaSectionItemModel> soaSectionItemRemovelist = new ArrayList<SoaSectionItemModel>();
                        for (SoaSectionItemModel soaSectionItemMapValid : soaSectionM.getEntryItems()) {
                            // if soaSectionMatchList contains the entryItem,
                            // combine the same entryItem and calculate the
                            // total share amount
                            if (soaSectionMatchMap.containsKey(soaSectionItemMapValid.getEntryItem())) {
                                // get the first same entryItem record
                                String itemId = (String) soaSectionMatchMap.get(soaSectionItemMapValid.getEntryItem());
                                for (SoaSectionItemModel soaSectionItemCalObj : soaSectionM.getEntryItems()) {
                                    if (soaSectionItemCalObj.getItemId().equals(itemId)) {
                                        // add the amount and shareAmount to the
                                        // first entryItem
                                        soaSectionItemCalObj.setAmount(soaSectionItemCalObj.getAmount()
                                                .add(soaSectionItemMapValid.getAmount()));
                                        soaSectionItemCalObj.setShareAmount(soaSectionItemCalObj.getShareAmount()
                                                .add(soaSectionItemMapValid.getShareAmount()));
                                    }
                                }
                                // collect the entryItem for removing
                                soaSectionItemRemovelist.add(soaSectionItemMapValid);
                            } else {
                                soaSectionMatchMap.put(soaSectionItemMapValid.getEntryItem(),
                                        soaSectionItemMapValid.getItemId());
                            }
                        }
                        soaSectionM.getEntryItems().removeAll(soaSectionItemRemovelist);
                    }
                }
            }
        }
        return soaM;
    }

    public SoaModel calculateSameEntryItem4SameSection(SoaModel soaM) {
        for (SoaCurrencyModel soaCurrencyM : soaM.getCurrencies()) {
            combineSoaSectionByContSection(soaCurrencyM);
        }
        return soaM;
    }

    public SoaModel calculateSameEntryItem4SameSectionForView(SoaModel soaM) {
        for (SoaCurrencyModel soaCurrencyM : soaM.getCurrencies()) {
            combineSoaSectionByContSectionForView(soaCurrencyM);
        }
        return soaM;
    }

    private void combineSoaSectionByContSection(SoaCurrencyModel soaCurrencyM) {

        //collect entry items by each contract section
        Map<String, SoaSectionModel> resultSectionMap = new HashMap<String, SoaSectionModel>();
        for (SoaSectionModel soaSectionM : soaCurrencyM.getSections()) {

            SoaSectionModel resultSection = resultSectionMap.get(soaSectionM.getContracCompId());
            if (resultSection != null) {
                resultSection.getEntryItems().addAll(soaSectionM.getEntryItems());
            } else {
                resultSectionMap.put(soaSectionM.getContracCompId(), soaSectionM);
            }
        }

        //combine entry code
        ArrayList<SoaSectionModel> sections = new ArrayList<SoaSectionModel>();
        for (SoaSectionModel section : resultSectionMap.values()) {
            sections.add(combineEntryCode(section));
        }

        soaCurrencyM.setSections(sections);
    }

    private void combineSoaSectionByContSectionForView(SoaCurrencyModel soaCurrencyM) {

        //collect entry items by each contract section
        Map<String, SoaSectionModel> resultSectionMap = new HashMap<String, SoaSectionModel>();
        for (SoaSectionModel soaSectionM : soaCurrencyM.getSections()) {

            SoaSectionModel resultSection = resultSectionMap.get(soaSectionM.getSoaSectionId());
            if (resultSection != null) {
                resultSection.getEntryItems().addAll(soaSectionM.getEntryItems());
            } else {
                resultSectionMap.put(soaSectionM.getSoaSectionId(), soaSectionM);
            }
        }

        //combine entry code
        ArrayList<SoaSectionModel> sections = new ArrayList<SoaSectionModel>();
        for (SoaSectionModel section : resultSectionMap.values()) {
            sections.add(combineEntryCode(section));
        }

        soaCurrencyM.setSections(sections);
    }

    private SoaSectionModel combineEntryCode(SoaSectionModel section) {
        Map<String, SoaSectionItemModel> resultItemMap = new HashMap<String, SoaSectionItemModel>();
        for (SoaSectionItemModel loopEntry : section.getEntryItems()) {
            SoaSectionItemModel item = resultItemMap.get(loopEntry.getEntryCode());
            if (item != null) {
                item.setShareAmount(item.getShareAmount().add(loopEntry.getShareAmount()));
            } else {
                resultItemMap.put(loopEntry.getEntryCode(), loopEntry);
            }
        }
        section.setEntryItems(new ArrayList<SoaSectionItemModel>(resultItemMap.values()));
        return section;

    }

    public void generalARAPData(SoaModel soaM) throws Exception {
        // the modelList contains all currencies data
        List<BusinessFeeModel> modelList = new ArrayList<BusinessFeeModel>();
        // build the data for ARAP
        for (SoaCurrencyModel soaCurrencyM : soaM.getCurrencies()) {
            for (SoaSectionModel soaSectionM : soaCurrencyM.getSections()) {
                // collect the record for each section					
                for (SoaSectionItemModel soaSectionItemMapValid : soaSectionM.getEntryItems()) {
                    // build the fee info
                    List<BusinessFee> bizFeeList = new ArrayList<BusinessFee>();
                    generalFeeInfo(soaM, soaSectionM, soaCurrencyM, soaSectionItemMapValid, modelList, bizFeeList);
                }
            }
        }
        try {
            // Write data to finace
            financeService.writeToFinance(modelList);
        } catch (Exception e) {
            throw e;
        }
    }

    public void generalPTFARAPData(SoaModel soaM) throws Exception {
        // the modelList contains all currencies data
        List<BusinessFeeModel> modelList = new ArrayList<BusinessFeeModel>();
        // build the data for ARAP
        for (SoaCurrencyModel soaCurrencyM : soaM.getCurrencies()) {
            for (SoaSectionModel soaSectionM : soaCurrencyM.getSections()) {
                // collect the record for each section					
                for (SoaSectionItemModel soaSectionItemMapValid : soaSectionM.getEntryItems()) {
                    // build the fee info
                    List<BusinessFee> bizFeeList = new ArrayList<BusinessFee>();
                    generalPTFFeeInfo(soaM, soaSectionM, soaCurrencyM, soaSectionItemMapValid, modelList, bizFeeList);
                }
            }
        }
        try {
            // Write data to finace
            financeService.writeToFinance(modelList);
        } catch (Exception e) {
            throw e;
        }
    }

    public void setPTFStatusForEachSection(SoaModel soaM, Boolean isFullyTransfer) throws Exception {
        // the modelList contains all currencies data
        for (SoaCurrencyModel soaCurrencyM : soaM.getCurrencies()) {
            for (SoaSectionModel soaSectionM : soaCurrencyM.getSections()) {
                setPTFStatus(soaSectionM, isFullyTransfer);
            }
        }

    }

    public void setPTFStatus(SoaSectionModel soaSectionM, Boolean isFullyTransfer) {
        if (soaSectionM.getIsFullyTransfer()) {
            contractService.resetTheCleanCutFlag(Long.parseLong(soaSectionM.getContracCompId()), isFullyTransfer);
        }
    }

    public String getPTFStatusForEachSection(Soa4UpdateVO soaM) throws Exception {
        // the modelList contains all currencies data
        for (SoaCurrency4UpdateVO soaCurrencyM : soaM.getCurrencies()) {
            for (SoaSection4UpdateVO soaSectionM : soaCurrencyM.getSections()) {
                if (getPTFStatus(soaSectionM)) {
                    return "true";
                }
            }
        }
        return "false";

    }

    public boolean getPTFStatus(SoaSection4UpdateVO soaSectionM) {
        return contractService.isCleanCutContractSection(Long.parseLong(soaSectionM.getContracCompId()));
    }

    public void initReserveEntryItemData(SoaModel soaM) throws Exception {
        List<String> entryCodelist = (List<String>) Arrays.asList(SoaConstant.RESERVE_ENTRY_CODE.split(","));
        for (SoaCurrencyModel soaCurrency : soaM.getCurrencies()) {
            for (String entryCode : entryCodelist) {
                generatInitReserveEntryItem(soaCurrency, entryCode, soaCurrency.getCurrencyType());
            }
        }
    }

    @Override
    public void withdrawSoaInfo(long statementId) throws Exception {
        // load the latest Soa Info
        SoaModel soaM = loadSoaInfo(statementId);
        // set the portfolio transfer status 
        setPTFStatusForEachSection(soaM, false);
        //set the not special withdraw submit
        soaM.setWithdrawIgnoringCutoffDate("0");
        // deal with the same entryItem of the same section
        soaM = calculateSameEntryItem4SameSection(soaM);
        // general the withdraw ARAP record
        generalWithdrawARAPData(soaM);
        //withDraw Soa For Estimation
        prepareSoaForEstimationWithDraw(soaM);
        //set the soaStatus to withdrawal
        TRiSoa soa = soaDao.load(statementId);
        //task withdraw
        soa.setTakswithdraw((String.valueOf(AppContext.getCurrentUser().getUserId())));
        soa.setTakswithdrawName((String.valueOf(AppContext.getCurrentUser().getUserName())));
        soa.setSoaStatus(SoaConstant.WITHDRAWAL);
        soaDao.merge(soa);

    }

    @Override
    public void withdrawIgnoringCutOffDateSoaInfo(long statementId) throws Exception {
        //update the soa ignoring cut off date flag
        updateIgnoringFlag(statementId);
        // load the latest Soa Info
        SoaModel soaM = loadSoaInfo(statementId);
        // set the portfolio transfer status 
        setPTFStatusForEachSection(soaM, false);
        // deal with the same entryItem of the same section
        soaM = calculateSameEntryItem4SameSection(soaM);
        // general the withdraw ARAP record
        generalWithdrawARAPData(soaM);
        //withDraw Soa For Estimation
        prepareSoaForEstimationSpecialWithDraw(soaM);
        //set the soaStatus to withdrawal
        TRiSoa soa = soaDao.load(statementId);
        //task withdraw
        soa.setTakswithdraw((String.valueOf(AppContext.getCurrentUser().getUserId())));
        soa.setTakswithdrawName((String.valueOf(AppContext.getCurrentUser().getUserName())));
        soa.setSoaStatus(SoaConstant.WITHDRAWAL);
        soaDao.merge(soa);
    }

    public void generalWithdrawARAPData(SoaModel soaM) throws Exception {
        // the modelList contains all currencies data
        List<BusinessFeeModel> modelList = new ArrayList<BusinessFeeModel>();
        // build the data for ARAP
        for (SoaCurrencyModel soaCurrencyM : soaM.getCurrencies()) {
            for (SoaSectionModel soaSectionM : soaCurrencyM.getSections()) {
                // collect the record for each section

                for (SoaSectionItemModel soaSectionItemMapValid : soaSectionM.getEntryItems()) {
                    // build the fee info
                    List<BusinessFee> bizFeeList = new ArrayList<BusinessFee>();
                    generalWithdrawFeeInfo(soaM, soaSectionM, soaCurrencyM, soaSectionItemMapValid, modelList,
                            bizFeeList);
                }
            }
        }
        try {
            // Write data to finace
            financeService.writeToFinance(modelList);
        } catch (Exception e) {
            // e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void submitAndReverseInfo(Soa4UpdateVO soaU) throws Exception {
        //save and update SoaInfo
        saveAndUpdateSoaInfo(soaU);
        long SoaIdRead = Long.parseLong(soaU.getSoaId());
        // generate the reserve entryItem for Soa
        collectReserveEntryItem(SoaIdRead);
        // set the reserve status for each section
        setReserveStatus(SoaIdRead);
        // load the latest Soa Info
        SoaModel soaM = loadSoaInfo(SoaIdRead);
        // deal with the same entryItem of the same section
        soaM = calculateSameEntryItem4SameSection(soaM);
        // set the soaStatus to released
        TRiSoa soa = soaDao.load(SoaIdRead);
        //task release
        soa.setTaskreleaser(String.valueOf(AppContext.getCurrentUser().getUserId()));
        soa.setTaskreleaserName(String.valueOf(AppContext.getCurrentUser().getUserName()));
        soa.setSoaStatus(SoaConstant.RELEASED);
        soa.setReversal(true);
        soaDao.merge(soa);
        // init the reserve entryItem data for FN
        // initReserveEntryItemData(soaM);
        // general the reversal ARAP record
        generalReversaARAPData(soaM);

    }

    @Override
    public void ignoringSubmitAndReverseInfo(Soa4UpdateVO soaU) throws Exception {
        //save and update SoaInfo
        saveAndUpdateSoaInfo(soaU);
        long SoaIdRead = Long.parseLong(soaU.getSoaId());
        //update the soa ignoring cut off date flag
        updateIgnoringFlag(SoaIdRead);
        // generate the reserve entryItem for Soa
        collectReserveEntryItem(SoaIdRead);
        // set the reserve status for each section
        setReserveStatus(SoaIdRead);
        // load the latest Soa Info
        SoaModel soaM = loadSoaInfo(SoaIdRead);
        // deal with the same entryItem of the same section
        soaM = calculateSameEntryItem4SameSection(soaM);
        // set the soaStatus to released
        TRiSoa soa = soaDao.load(SoaIdRead);
        //task release
        soa.setTaskreleaser(String.valueOf(AppContext.getCurrentUser().getUserId()));
        soa.setTaskreleaserName(String.valueOf(AppContext.getCurrentUser().getUserName()));
        soa.setSoaStatus(SoaConstant.RELEASED);
        soa.setReversal(true);
        soaDao.merge(soa);
        // init the reserve entryItem data for FN
        // initReserveEntryItemData(soaM);
        // general the reversal ARAP record
        generateIgnoringReversaARAPData(soaM);

    }

    public void generalReversaARAPData(SoaModel soaM) throws Exception {
        // the modelList contains all currencies data
        List<BusinessFeeModel> modelList = new ArrayList<BusinessFeeModel>();
        // build the normal data for ARAP
        buildNormalARAP(soaM, modelList);
        try {
            // Write data to FN
            financeService.writeToFinance(modelList);
        } catch (Exception e) {
            // e.printStackTrace();
            throw e;
        }
        // reversal the estimation entry item data
        reversaSoaForEstimation(soaM);
    }

    public void generateIgnoringReversaARAPData(SoaModel soaM) throws Exception {
        // the modelList contains all currencies data
        List<BusinessFeeModel> modelList = new ArrayList<BusinessFeeModel>();
        // build the normal data for ARAP
        buildNormalARAP(soaM, modelList);
        try {
            // Write data to FN
            financeService.writeToFinance(modelList);
        } catch (Exception e) {
            //  e.printStackTrace();
            throw e;
        }
        // reversal the estimation entry item data
        ignoringReversaSoaForEstimation(soaM);
    }

    public void generalFeeInfo(SoaModel soaM, SoaSectionModel soaSectionM, SoaCurrencyModel soaCurrencyM,
                               SoaSectionItemModel soaSectionItemMapValid, List<BusinessFeeModel> modelList,
                               List<BusinessFee> bizFeeList) throws Exception {
        BusinessFeeModel feeModel = new BusinessFeeModel();
        BusinessFee fee = new BusinessFee();
        feeModel.setBizTransType(BizTransactionType.STATEMENT.getType());
        //get the contracCompId from contract model
        ContractModel contractModel = contractService.getInforceAndCancelledContractIdByCode(soaM.getContractCode(),
                Long.parseLong(soaM.getUwYear().toString()));
        feeModel.setContractCategory(Integer.parseInt(contractModel.getContractCategory()));
        feeModel.setContractId(contractModel.getContCompId());
        feeModel.setBizTransNo(soaM.getSoaId());
        feeModel.setBizTransId(Long.valueOf(soaSectionM.getSoaSectionId().toString()));
        feeModel.setFeeList(bizFeeList);
        feeModel.setPartnerCode("");
        if ("1".equals(soaM.getWithdrawIgnoringCutoffDate())) {
            feeModel.setSpecialSubmitInCutoffPeriod(true);
        } else {
            feeModel.setSpecialSubmitInCutoffPeriod(false);
        }
        //TODO ME
        fee.setEntryCode(soaSectionItemMapValid.getEntryCode());
        fee.setCurrencyCode(soaCurrencyM.getCurrencyType());
        fee.setTotalPeriod(1);
        fee.setCurrentPeriod(1);
        fee.setAmount(soaSectionItemMapValid.getShareAmount());
        fee.setDueDate(soaM.getDueDate());
        fee.setSectionId(Long.valueOf(soaSectionM.getContracCompId().toString()));
        fee.setEstimation(false);
        //set the  cutoff date period flag
        //        if ("0".equals(soaM.getWithdrawIgnoringCutoffDate()) && getCutOffDatePeriodFlag()) {
        //            fee.setNeedPost(false);
        //        } else {
        //            fee.setNeedPost(true);
        //        }
        fee.setNeedPost(true);
        fee.setBizTransDesc(soaM.getSoaText());
        fee.setBizOperatorId(AppContext.getCurrentUser().getUserId());
        modelList.add(feeModel);
        bizFeeList.add(fee);
        //        //deal with the reserve entryItem 
        //        List<String> entryCodelist = (List<String>) Arrays.asList(SoaConstant.RESERVE_ENTRY_CODE.split(","));
        //        if (entryCodelist.contains(soaSectionItemMapValid.getEntryCode())) {
        //            //deal with the opening entryItem
        //            List<String> closingEntryCodelist = (List<String>) Arrays.asList(SoaConstant.CLOSING_ENTRY_CODE.split(","));
        //            if (closingEntryCodelist.contains(soaSectionItemMapValid.getEntryCode())) {
        //                BigDecimal reserveOpeningValue = findReserveOpeningValueBySectionIDAndEntryCode(
        //                        Long.parseLong(soaSectionM.getContracCompId()), soaSectionItemMapValid.getEntryCode(),
        //                        soaCurrencyM.getCurrencyType());
        //                if (reserveOpeningValue != null) {
        //                    BusinessFeeModel feeOpeningModel = new BusinessFeeModel();
        //                    BusinessFee feeOpening = new BusinessFee();
        //                    BeanUtils.copyProperties(feeOpeningModel, feeModel);
        //                    BeanUtils.copyProperties(feeOpening, fee);
        //                    feeOpening.setEntryCode(getOpeningEntryCodeMatchMap(soaSectionItemMapValid.getEntryCode()));
        //                    feeOpening.setAmount(reserveOpeningValue);
        //                    modelList.add(feeOpeningModel);
        //                    feeModel.setFeeList(bizFeeList);
        //                    bizFeeList.add(feeOpening);
        //                } else {
        //                    BusinessFeeModel feeOpeningModel = new BusinessFeeModel();
        //                    BusinessFee feeOpening = new BusinessFee();
        //                    BeanUtils.copyProperties(feeOpeningModel, feeModel);
        //                    BeanUtils.copyProperties(feeOpening, fee);
        //                    feeOpening.setEntryCode(getOpeningEntryCodeMatchMap(soaSectionItemMapValid.getEntryCode()));
        //                    feeOpening.setNeedPost(false);
        //                    feeOpening.setAmount(BigDecimal.ZERO);
        //                    modelList.add(feeOpeningModel);
        //                    feeModel.setFeeList(bizFeeList);
        //                    bizFeeList.add(feeOpening);
        //                }
        //            }
        //        }

    }

    public void generalPTFFeeInfo(SoaModel soaM, SoaSectionModel soaSectionM, SoaCurrencyModel soaCurrencyM,
                                  SoaSectionItemModel soaSectionItemMapValid, List<BusinessFeeModel> modelList,
                                  List<BusinessFee> bizFeeList) throws Exception {
        BusinessFeeModel feeModel = new BusinessFeeModel();
        BusinessFee fee = new BusinessFee();
        feeModel.setBizTransType(BizTransactionType.STATEMENT.getType());
        // get the contracCompId from contract model
        ContractModel contractModel = contractService.getInforceAndCancelledContractIdByCode(soaM.getContractCode(),
                Long.parseLong(soaM.getUwYear().toString()));
        feeModel.setContractCategory(Integer.parseInt(contractModel.getContractCategory()));
        feeModel.setContractId(contractModel.getContCompId());
        feeModel.setBizTransNo(soaM.getSoaId());
        feeModel.setBizTransId(Long.valueOf(soaSectionM.getSoaSectionId().toString()));
        feeModel.setFeeList(bizFeeList);
        feeModel.setPartnerCode("");
        if ("1".equals(soaM.getWithdrawIgnoringCutoffDate())) {
            feeModel.setSpecialSubmitInCutoffPeriod(true);
        } else {
            feeModel.setSpecialSubmitInCutoffPeriod(false);
        }
        // TODO ME
        fee.setEntryCode(soaSectionItemMapValid.getEntryCode());
        fee.setCurrencyCode(soaCurrencyM.getCurrencyType());
        fee.setTotalPeriod(1);
        fee.setCurrentPeriod(1);
        fee.setAmount(soaSectionItemMapValid.getShareAmount());
        fee.setDueDate(soaM.getDueDate());
        fee.setSectionId(Long.valueOf(soaSectionM.getContracCompId().toString()));
        fee.setEstimation(false);
        fee.setBizOperatorId(AppContext.getCurrentUser().getUserId());
        // set the cutoff date period flag
        //		if ("0".equals(soaM.getWithdrawIgnoringCutoffDate()) && getCutOffDatePeriodFlag()) {
        //			fee.setNeedPost(false);
        //		} else {
        //			fee.setNeedPost(true);
        //		}
        fee.setNeedPost(true);
        fee.setBizTransDesc(soaM.getSoaText());
        modelList.add(feeModel);
        bizFeeList.add(fee);

    }

    public void generalWithdrawFeeInfo(SoaModel soaM, SoaSectionModel soaSectionM, SoaCurrencyModel soaCurrencyM,
                                       SoaSectionItemModel soaSectionItemMapValid, List<BusinessFeeModel> modelList,
                                       List<BusinessFee> bizFeeList) throws Exception {
        List<String> entryCodelist = (List<String>) Arrays.asList(SoaConstant.RESERVE_ENTRY_CODE.split(","));
        BusinessFeeModel feeModel = new BusinessFeeModel();
        BusinessFee fee = new BusinessFee();
        feeModel.setBizTransType(BizTransactionType.STATEMENT.getType());
        // get the contracCompId from contract model
        ContractModel contractModel = contractService.getInforceAndCancelledContractIdByCode(soaM.getContractCode(),
                Long.parseLong(soaM.getUwYear().toString()));
        feeModel.setContractCategory(Integer.parseInt(contractModel.getContractCategory()));
        feeModel.setContractId(contractModel.getContCompId());
        feeModel.setBizTransNo(soaM.getSoaId());
        feeModel.setBizTransId(Long.valueOf(soaSectionM.getSoaSectionId().toString()));

        feeModel.setFeeList(bizFeeList);
        feeModel.setPartnerCode("");
        if ("1".equals(soaM.getWithdrawIgnoringCutoffDate())) {
            feeModel.setSpecialSubmitInCutoffPeriod(true);
        } else {
            feeModel.setSpecialSubmitInCutoffPeriod(false);
        }
        //set the  cutoff date period flag
        //        if ("0".equals(soaM.getWithdrawIgnoringCutoffDate()) && getCutOffDatePeriodFlag()) {
        //            fee.setNeedPost(false);
        //        } else {
        //            fee.setNeedPost(true);
        //        }
        fee.setNeedPost(true);
        fee.setSectionId(Long.valueOf(soaSectionM.getContracCompId().toString()));
        fee.setBizOperatorId(AppContext.getCurrentUser().getUserId());
        // deal with the reserve entryItem	
        if (entryCodelist.contains(soaSectionItemMapValid.getEntryCode())) {
            //            Boolean validWithdrawFlag = validLatesSoa4Withdraw(soaSectionM.getSoaSectionId(),
            //                    soaSectionM.getContracCompId(), soaSectionItemMapValid.getEntryCode());
            //if it is the latest soa
            //   if (validWithdrawFlag) {
            // TODO ME
            fee.setEntryCode(soaSectionItemMapValid.getEntryCode());
            fee.setCurrencyCode(soaCurrencyM.getCurrencyType());
            fee.setTotalPeriod(1);
            fee.setBizTransDesc(soaM.getSoaText());
            fee.setCurrentPeriod(1);
            fee.setAmount(soaSectionItemMapValid.getShareAmount().negate());
            fee.setDueDate(soaM.getDueDate());
            fee.setEstimation(false);
            //set the  cutoff date period flag
            //            if ("0".equals(soaM.getWithdrawIgnoringCutoffDate()) && getCutOffDatePeriodFlag()) {
            //                fee.setNeedPost(false);
            //            } else {
            //                fee.setNeedPost(true);
            //            }
            fee.setNeedPost(true);
            modelList.add(feeModel);
            bizFeeList.add(fee);
            // deal with the opening entryItem
            List<String> closingEntryCodelist = (List<String>) Arrays.asList(SoaConstant.CLOSING_ENTRY_CODE.split(","));
            if (closingEntryCodelist.contains(soaSectionItemMapValid.getEntryCode())) {
                //  	if (closingEntryCodelist.contains(soaSectionItemMapValid.getEntryCode()) && validWithdrawFlag) {
                BigDecimal reserveOpeningValue = findOpeningValueBySectionIDAndEntryCode(
                        Long.parseLong(soaSectionM.getSoaSectionId()),
                        getOpeningEntryCodeMatchMap(soaSectionItemMapValid.getEntryCode()));
                BusinessFeeModel feeOpeningModel = new BusinessFeeModel();
                BusinessFee feeOpening = new BusinessFee();
                BeanUtils.copyProperties(feeOpeningModel, feeModel);
                BeanUtils.copyProperties(feeOpening, fee);
                feeOpening.setEntryCode(getOpeningEntryCodeMatchMap(soaSectionItemMapValid.getEntryCode()));
                feeOpening.setAmount(reserveOpeningValue);
                modelList.add(feeOpeningModel);
                feeModel.setFeeList(bizFeeList);
                bizFeeList.add(feeOpening);
            }
            //   }

        } else {
            fee.setEntryCode(soaSectionItemMapValid.getEntryCode());
            fee.setCurrencyCode(soaCurrencyM.getCurrencyType());
            fee.setBizTransDesc(soaM.getSoaText());
            fee.setTotalPeriod(1);
            fee.setCurrentPeriod(1);
            fee.setAmount(soaSectionItemMapValid.getShareAmount().negate());
            fee.setDueDate(soaM.getDueDate());
            fee.setEstimation(false);
            //set the  cutoff date period flag
            //            if ("0".equals(soaM.getWithdrawIgnoringCutoffDate()) && getCutOffDatePeriodFlag()) {
            //                fee.setNeedPost(false);
            //            } else {
            //                fee.setNeedPost(true);
            //            }
            fee.setNeedPost(true);
            modelList.add(feeModel);
            bizFeeList.add(fee);
        }

    }

    public List<String> getEstimationEntryItemList() {
        List<String> estimationEntryItemList = new ArrayList<String>();
        estimationEntryItemList.add(SoaConstant.PREMIUM_PROP);
        estimationEntryItemList.add(SoaConstant.COMMISSION);
        estimationEntryItemList.add(SoaConstant.TAX_OTHERS);
        estimationEntryItemList.add(SoaConstant.BROKERAGE);
        estimationEntryItemList.add(SoaConstant.LOSS_PAID);
        estimationEntryItemList.add(SoaConstant.LOSS_RESERVE_OPENING);
        estimationEntryItemList.add(SoaConstant.HKAS_LOSS_RESERVE_CLOSING);
        return estimationEntryItemList;
    }

    public void buildNormalARAP(SoaModel soaM, List<BusinessFeeModel> modelList) throws Exception {
        for (SoaCurrencyModel soaCurrencyM : soaM.getCurrencies()) {
            for (SoaSectionModel soaSectionM : soaCurrencyM.getSections()) {
                // collect the record for each currency				
                for (SoaSectionItemModel soaSectionItemMapValid : soaSectionM.getEntryItems()) {
                    // build the fee info
                    List<BusinessFee> bizFeeList = new ArrayList<BusinessFee>();
                    generalFeeInfo(soaM, soaSectionM, soaCurrencyM, soaSectionItemMapValid, modelList, bizFeeList);
                }
            }
        }
    }

    public void reversaSoaForEstimation(SoaModel soaM) throws Exception {
        YearQuarter yearQuarter = new YearQuarter(Integer.parseInt(String.valueOf(soaM.getCedentYear())),
                Integer.parseInt(String.valueOf(soaM.getCedentQuarter())));
  
        estimateDSImpl.afterSOASubmit(Long.parseLong(soaM.getSoaId()), false, yearQuarter);
    }

    public void ignoringReversaSoaForEstimation(SoaModel soaM) throws Exception {
        YearQuarter yearQuarter = new YearQuarter(Integer.parseInt(String.valueOf(soaM.getCedentYear())),
                Integer.parseInt(String.valueOf(soaM.getCedentQuarter())));
        estimateDSImpl.afterSOASubmit(Long.parseLong(soaM.getSoaId()), true, yearQuarter);
    }

    public List<String> getContractCompIdList4EachCurrency(List<String> contracCompIdMatchList,
                                                           List<SoaSectionModel> sectionList) {
        for (SoaSectionModel soaSectionM : sectionList) {
            if (soaSectionM.getEntryItems() != null) {
                contracCompIdMatchList.add(soaSectionM.getContracCompId());
            }
        }
        return contracCompIdMatchList;
    }

    public void initCurrecyLayerInfo(TRiSoa soa, SoaCurrencyModel soaCurrencyM) throws Exception {
        TRiSoaCurrency soaCurrency = (TRiSoaCurrency) soaBuulder.buildSoaCurrencyObjFromModel(soaCurrencyM);
        soaCurrency.setTRiSoa(soa);
        soaCurrencyDao.insert(soaCurrency);
    }

    public void saveSoaViewInfo(long SoaIdRead, String ReviewedFlag, String SoaText) {
        // set the soaStatus to released
        TRiSoa soa = soaDao.load(SoaIdRead);
        if ("true".equals(ReviewedFlag)) {
            soa.setReviewedFlag("1");
            soa.setReviewedBy(String.valueOf(AppContext.getCurrentUser().getUserName()));
        } else {
            soa.setReviewedFlag("2");
        }
        soa.setSoaLatestText(SoaText);
        soaDao.merge(soa);
    }

    public Page<SoaModel> buildSoaInfo4SearchPage(SoaSearchVO soaSearchVO) throws Exception {
        Page<SoaModel> page = new Page<SoaModel>();
        int start = (soaSearchVO.getPageIndex() - 1) * soaSearchVO.getCountPerPage();
        page.setStart(start);
        page.setCountPerPage(soaSearchVO.getCountPerPage());
        return findPageList(page, soaSearchVO);
    }

    public Page<SoaModel> findPageList(Page<SoaModel> page, SoaSearchVO soaSearchVO) throws Exception {
        long results = soaDao.getSoaInfoCountByConditions(soaSearchVO);
        List<TRiSoa> resultList = soaDao.getSoaCurrentPage(soaSearchVO, page.getStart(), page.getCountPerPage());
        List<SoaModel> soaMRList = new ArrayList<SoaModel>();
        for (TRiSoa soaT : resultList) {
            SoaModel soaMR = null;
            soaMR = (SoaModel) soaModelBuilder.buildSoaModelObj(soaT);
            soaMR.setCedent(partnerService.loadPartnerNameByPartnerCode(soaMR.getCedent()));
            soaMRList.add(soaMR);
        }
        page.setRows(soaMRList);
        int pageIndex = page.getStart() / page.getCountPerPage() + 1;
        page.setPageIndex(pageIndex);
        Long pageCount = results % page.getCountPerPage() == 0 ? results / page.getCountPerPage()
                : results / page.getCountPerPage() + 1;
        page.setPageCount(pageCount);
        if (results < page.getCountPerPage()) {
            page.setCountPerPage(Integer.parseInt(String.valueOf(results)));
        }
        return page;
    }

    //add by ammon.zhou
    public List<SoaModel> findSoaList(SoaSearchVO soaSearchVO) throws Exception {
        List<TRiSoa> resultList = soaDao.getSoaCurrentPage(soaSearchVO, -1, -1);
        List<SoaModel> soaMRList = new ArrayList<SoaModel>();
        for (TRiSoa soaT : resultList) {
            SoaModel soaMR = null;
            soaMR = (SoaModel) soaModelBuilder.buildSoaModelObj(soaT);
            // meiliang.zou add 2016.5.23 start
            soaMR.setCedent(partnerService.loadPartnerNameByPartnerCode(soaMR.getCedent()));
            // meiliang.zou add 2016.5.23 end
            soaMRList.add(soaMR);
        }

        return soaMRList;
    }

    @Override
    public SoaValidateModel validateCurrentUser(Long statementId) {
        String result = "fail";
        TRiSoa soa = soaDao.load(statementId);
        if (soa.getTaskcreator().equals(String.valueOf(AppContext.getCurrentUser().getUserId()))) {
            result = "success";
        }
        SoaValidateModel soaValidateModel = new SoaValidateModel();
        soaValidateModel.setResult(result);
        return soaValidateModel;
    }

    @Override
    public SoaValidateModel validateSoa(String ContractCode, Integer UWYear, Integer CendantYear,
                                        Integer CendantQuarter) throws NumberFormatException, Exception {
        String result = "new";
        SoaSearchVO soaSearchVO = new SoaSearchVO();
        soaSearchVO.setContractCode(ContractCode);
        soaSearchVO.setUwYear(UWYear);
        soaSearchVO.setCedentYear(CendantYear);
        soaSearchVO.setCedentQuarter(CendantQuarter);
        //validate if the contract exist
        Boolean existContract = false;
        List<Long> UWYearList = contractService.getContractUWYearByContractCode(ContractCode);
        if (UWYearList.contains(Long.parseLong(UWYear.toString()))) {
            existContract = true;
        }
        //validate Soa revered 
        boolean sectionReversalFlag = validateContractSectionReversal(ContractCode, UWYear, CendantYear,
                CendantQuarter);
        if (!existContract) {
            result = "wrongContract";
        } else {

            long results = soaDao.getSoaInfoCountByConditionsAndStatus(soaSearchVO);
            if (results > 0) {
                result = "duplicate";
            }
            soaSearchVO.setCedentQuarter(0);
            ContractModel contractModel = contractService.getInforceAndCancelledContractIdByCode(ContractCode,
                    Long.parseLong(UWYear.toString()));
            int maxCendantQuarter = 1;
            List<YearQuarter> qList = new ArrayList<YearQuarter>();
            if (contractModel != null) {
                qList = calcContractQuarterByPOI(contractModel);
                YearQuarter yqInit = qList.get(0);
                maxCendantQuarter = (yqInit.getYear() * 4 + yqInit.getQuarter());
            }
            List<TRiSoa> resultList = soaDao.getSoaCurrentPage(soaSearchVO, 0, 10);

            int cedentQuarterCount = 1;
            for (TRiSoa soaT : resultList) {
                cedentQuarterCount = (soaT.getCedentYear() * 4 + soaT.getCedentQuarter());
                if (cedentQuarterCount > maxCendantQuarter) {
                    maxCendantQuarter = cedentQuarterCount;
                }
            }
            if ((CendantYear * 4 + CendantQuarter) > maxCendantQuarter + 1) {
                result = "warnQuarter";
            }

            if (!sectionReversalFlag) {
                result = "noEstimateInfo";
            }

            if (contractModel != null) {
                //                YearQuarter yqMin = qList.get(0);
                //                YearQuarter yqMax = qList.get(3);
                //                int min = (CendantYear * 4 + CendantQuarter) - (yqMin.getYear() * 4 + yqMin.getQuarter());
                //                int max = (CendantYear * 4 + CendantQuarter) - (yqMax.getYear() * 4 + yqMax.getQuarter());
                //                if (!(min >= 0 && max <= 0)) {
                //                    result = "wrongQuarter";
                //                }
                Calendar c1 = Calendar.getInstance();
                c1.setTime(contractModel.getReinsStarting());
                YearQuarter curQuarter = new YearQuarter(c1.getTime());
                int reinsStartYear = curQuarter.getYear();
                if (CendantYear < reinsStartYear) {
                    result = "wrongQuarter";
                }
            }
        }

        SoaValidateModel soaValidateModel = new SoaValidateModel();
        soaValidateModel.setResult(result);
        return soaValidateModel;
    }

    @Override
    public SoaValidateModel validatePTFSoa(String ContractCode, Integer UWYear, Integer CendantYear,
                                           Integer CendantQuarter) throws NumberFormatException, Exception {
        String result = "new";
        SoaSearchVO soaSearchVO = new SoaSearchVO();
        soaSearchVO.setContractCode(ContractCode);
        soaSearchVO.setUwYear(UWYear);
        soaSearchVO.setCedentYear(CendantYear);
        soaSearchVO.setCedentQuarter(CendantQuarter);
        //validate if the contract exist
        Boolean existContract = false;
        List<Long> UWYearList = contractService.getContractUWYearByContractCode(ContractCode);
        if (UWYearList.contains(Long.parseLong(UWYear.toString()))) {
            existContract = true;
        }
        if (!existContract) {
            result = "wrongContract";
        } else {
            long results = soaDao.getSoaInfoCountByConditionsAndStatus(soaSearchVO);
            if (results > 0) {
                result = "duplicate";
            }
        }
        SoaValidateModel soaValidateModel = new SoaValidateModel();
        soaValidateModel.setResult(result);
        return soaValidateModel;
    }

    public List<YearQuarter> calcContractQuarterByPOI(ContractModel contractModel) throws Exception {
        List<YearQuarter> qList = new ArrayList<YearQuarter>();
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(contractModel.getReinsStarting());
        c2.setTime(contractModel.getReinsEnding());
        // c2.add(Calendar.MONTH, 12);
        while (c1.before(c2)) {
            YearQuarter curQuarter = new YearQuarter(c1.getTime());
            qList.add(curQuarter);
            c1.add(Calendar.MONTH, 3);
        }
        return qList;
    }

    public Object validateSoaReversal(Soa4UpdateVO soaU) throws Exception {
        SoaValidateModel soaValidateModel = new SoaValidateModel();
        //valid the quarter closing date
        if (accountingServiceImpl.inClosingPeriod()) {
            soaValidateModel.setIsOverCutOffDate("overDate");
        } else {
            soaValidateModel.setIsOverCutOffDate("false");
        }
        YearQuarter yearQuarter = new YearQuarter(Integer.parseInt(String.valueOf(soaU.getCedentYear())),
                Integer.parseInt(String.valueOf(soaU.getCedentQuarter())));
        Boolean reversalFlag = estimateDSImpl.allSectionsReversed(yearQuarter, getSoaSectionIdListForReserve(soaU));
        if (reversalFlag) {
            soaValidateModel.setResult("true");
        } else {
            soaValidateModel.setResult("false");
        }
        // check the portfolio transfer satus 
        soaValidateModel.setIsFullyTransfer(getPTFStatusForEachSection(soaU));
        return soaValidateModel;
    }

    public boolean validateContractSectionReversal(String ContractCode, Integer UWYear, Integer CendantYear,
                                                   Integer CendantQuarter) throws Exception {
        List<TreeModel> sectionModel = chooseSectionBizService.getContractStructureByCode(ContractCode,
                Long.parseLong(String.valueOf(UWYear)), true);
        YearQuarter yearQuarter = new YearQuarter(CendantYear, CendantQuarter);
        List<Long> sectionCompIDList = new ArrayList<Long>();
        if (sectionModel == null || sectionModel.size() == 0) {
            return false;
        }
        for (TreeModel contractmodel : sectionModel) {
            for (TreeModel sectionmodel : contractmodel.getChildren()) {
                sectionCompIDList.add((Long) sectionmodel.getId());
                List<TRiAcctEstimate> acctEstimates = acctEstimateDao.listByYearQuarterAndSectionIds(yearQuarter,
                        sectionCompIDList);
                if (CollectionUtils.isEmpty(acctEstimates)) {
                    return false;
                } else {
                    for (TRiAcctEstimate tRiAcctEstimate : acctEstimates) {
                        if (tRiAcctEstimate.getStatus() == EstimateStatusEnum.Forecast.getValue()
                                || tRiAcctEstimate.getStatus() == EstimateStatusEnum.Upr.getValue()) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public List<Long> getSoaSectionIdListForReserve(Soa4UpdateVO soaU) {
        List<Long> sectionCompIDList = new ArrayList<Long>();
        for (SoaCurrency4UpdateVO soaCurrencyMapValid : soaU.getCurrencies()) {
            for (SoaSection4UpdateVO soaSectionMapValid : soaCurrencyMapValid.getSections()) {
                if (!sectionCompIDList.contains(Long.parseLong(soaSectionMapValid.getContracCompId()))) {
                    sectionCompIDList.add(Long.parseLong(soaSectionMapValid.getContracCompId()));
                }
            }
        }
        return sectionCompIDList;
    }

    public List<Long> getSoaSectionIdList(SoaModel soaM) {
        List<Long> sectionCompIDList = new ArrayList<Long>();
        for (SoaCurrencyModel soaCurrencyMapValid : soaM.getCurrencies()) {
            for (SoaSectionModel soaSectionMapValid : soaCurrencyMapValid.getSections()) {
                if (!sectionCompIDList.contains(Long.parseLong(soaSectionMapValid.getContracCompId()))) {
                    sectionCompIDList.add(Long.parseLong(soaSectionMapValid.getContracCompId()));
                }
            }
        }
        return sectionCompIDList;
    }

    public List<SoaLineViewVO> generateFixedLines(SoaCurrencyModel currencyModel) throws Exception {

        List<SoaLineViewVO> soaViewList = new ArrayList<SoaLineViewVO>();
        SoaLineViewVO cobLine = new SoaLineViewVO("Main Sub CoB");
        SoaLineViewVO shareTypeLine = new SoaLineViewVO("Share Type");
        SoaLineViewVO cedentCountryLine = new SoaLineViewVO("Cedent Country");
        cobLine.setTotal(null);
        shareTypeLine.setTotal(null);
        cedentCountryLine.setTotal(null);
        soaViewList.add(cobLine);
        soaViewList.add(shareTypeLine);
        soaViewList.add(cedentCountryLine);

        int indexOfSections = 1;

        for (SoaSectionModel section : currencyModel.getSections()) {
            cobLine.setColumnValue(indexOfSections,
                    this.getContractCodeDescription(SoaConstant.CODE_TABLE_CONTRACT_SUBCOB_TYPE, section.getCob()));
            shareTypeLine.setColumnValue(indexOfSections,
                    this.getContractCodeDescription(SoaConstant.CODE_TABLE_CONT_SHARE_TYPE, section.getShareType()));
            cedentCountryLine.setColumnValue(indexOfSections,
                    this.getContractCodeDescription(SoaConstant.CODE_TABLE_CONTRACT_COUNTRYE, section.getUwArea()));
            indexOfSections++;
        }

        return soaViewList;
    }

    public List<SoaLineViewVO> generateLinesForEntyItems(SoaCurrencyModel currencyModel, String contractCategory)
            throws Exception {
        //  <entry, line>
        Map<String, SoaLineViewVO> entryLineMap = new HashMap<String, SoaLineViewVO>();
        SoaLineViewVO totalLine = new SoaLineViewVO("Tech.Result");
        SoaLineViewVO cashBalanceLine = new SoaLineViewVO("Cash Balance");
        int colIndex = 0;
        for (SoaSectionModel section : currencyModel.getSections()) {
            colIndex++;
            BigDecimal sectionTotal = BigDecimal.ZERO;
            BigDecimal sectionCashBalanceTotal = BigDecimal.ZERO;
            for (SoaSectionItemModel secEntry : section.getEntryItems()) {
                if (secEntry.getEntryCode() != null && secEntry.getEntryCode() != "") {
                    if (contractCategory != null && "2".equals(contractCategory)) {// Retro Contract
                        secEntry.setShareAmount(secEntry.getShareAmount().negate());
                    }
                    int sign = entryCodeService.getByEntryCode(secEntry.getEntryCode()).getSign();
                    sectionTotal = sectionTotal.add(secEntry.getShareAmount().multiply(BigDecimal.valueOf(sign)));
                    if (entryCodeService.getByEntryCode(secEntry.getEntryCode()).getCashBalance() == 1) {
                        sectionCashBalanceTotal = sectionCashBalanceTotal
                                .add(secEntry.getShareAmount().multiply(BigDecimal.valueOf(sign)));
                    }
                    SoaLineViewVO entryLine = entryLineMap.get(secEntry.getEntryCode());
                    if (entryLine == null) {
                        entryLine = new SoaLineViewVO(
                                entryCodeService.getByEntryCode(secEntry.getEntryCode()).getEntryCodeName());
                        entryLineMap.put(secEntry.getEntryCode(), entryLine);
                    }
                    BigDecimal shareAmountBD = secEntry.getShareAmount().multiply(BigDecimal.valueOf(sign));
                    String shareAmountStr = NumberFormat.getNumberInstance().format(shareAmountBD);
                    entryLine.setColumnValue(colIndex, shareAmountStr);
                }
            }
            BigDecimal sectionTotalBD = new BigDecimal(sectionTotal.toString());
            String sectionTotalStr = NumberFormat.getNumberInstance().format(sectionTotalBD);
            BigDecimal sectionCashBalanceTotalBD = new BigDecimal(sectionCashBalanceTotal.toString());
            String sectionCashBalanceTotalStr = NumberFormat.getNumberInstance().format(sectionCashBalanceTotalBD);
            totalLine.setColumnValue(colIndex, sectionTotalStr);
            cashBalanceLine.setColumnValue(colIndex, sectionCashBalanceTotalStr);
        }

        List<SoaLineViewVO> returnList = new ArrayList<SoaLineViewVO>(entryLineMap.values());

        //   returnList.add(totalLine);
        returnList.add(cashBalanceLine);

        //calculate total for each line
        for (SoaLineViewVO lineItem : returnList) {
            BigDecimal lineTotal = BigDecimal.ZERO;
            for (String sAmount : lineItem.getColValueMap().values()) {
                double dAmount = new DecimalFormat().parse(sAmount).doubleValue();
                BigDecimal entryAmount = StringUtils.isEmpty(sAmount) ? BigDecimal.ZERO : new BigDecimal(dAmount);
                lineTotal = lineTotal.add(entryAmount);
            }
            BigDecimal lineTotalBD = new BigDecimal(lineTotal.toString());
            String lineTotalStr = NumberFormat.getNumberInstance().format(lineTotalBD);
            lineItem.setTotal(lineTotalStr.toString());
        }
        return returnList;
    }

    public void collectReserveEntryItem(long SoaIdRead) throws Exception {
        TRiSoa soa = soaDao.load(SoaIdRead);
        List<String> entryCodelist = (List<String>) Arrays.asList(SoaConstant.RESERVE_CLOSING_ENTRY_CODE.split(","));
        for (TRiSoaCurrency soaCurrency : soa.getTRiSoaCurrencies()) {
            for (String entryCode : entryCodelist) {
                generateReserveEntryItem(soaCurrency, entryCode, soaCurrency.getCurrencyCode());
            }
        }
        soaDao.merge(soa);
        em.flush();
    }

    public void setReserveStatus(long SoaIdRead) throws Exception {
        TRiSoa soa = soaDao.load(SoaIdRead);
        for (TRiSoaCurrency soaCurrency : soa.getTRiSoaCurrencies()) {
            setReserveStatusForSections(soaCurrency, soa);
        }
        soaDao.merge(soa);
        em.flush();
    }

    public void collectReserveEntryItemForSummary(SoaModel soaM) {
        List<String> entryCodelist = (List<String>) Arrays.asList(SoaConstant.RESERVE_ENTRY_CODE.split(","));
        for (SoaCurrencyModel soaCurrency : soaM.getCurrencies()) {
            for (String entryCode : entryCodelist) {
                generateReserveEntryItemForSummary(soaCurrency, entryCode, soaCurrency.getCurrencyType());
            }
        }

    }

    public void generateReserveEntryItem(TRiSoaCurrency soaCurrency, String entryCode, String currencyCode)
            throws Exception {
        Map<Long, String> resultSectionMap = new HashMap<Long, String>();
        for (TRiSoaSection soaSection : soaCurrency.getTRiSoaSections()) {
            String validSection = resultSectionMap.get(soaSection.getContracCompId());
            if (validSection == null) {
                resultSectionMap.put(soaSection.getContracCompId(), "valid");
                //deal with closing item
                Boolean entryItemExist = validEnterEntryItem(soaSection.getSoaSectionId(), entryCode);
                if (!entryItemExist) {
                    BigDecimal reserveValue = findReserveValueBySectionIDAndEntryCode(soaSection.getContracCompId(),
                            entryCode, currencyCode);
                    if (reserveValue != null) {
                        TRiSoaSectionItem genSoaSectionItem = new TRiSoaSectionItem();
                        genSoaSectionItem.setAmount(reserveValue);
                        genSoaSectionItem.setEntryCode(entryCode);
                        genSoaSectionItem.setShareAmount(reserveValue);
                        genSoaSectionItem.setPercentage(BigDecimal.ONE);
                        genSoaSectionItem.setTRiSoaSection(soaSection);
                        soaSection.addTRiSoaSectionItem(genSoaSectionItem);
                    }
                }
                //deal with opening item
                BigDecimal reserveOpeningValue = findReserveOpeningValueBySectionIDAndEntryCode(
                        soaSection.getContracCompId(), entryCode, currencyCode);
                if (reserveOpeningValue != null) {
                    TRiSoaSectionItem genSoaSectionItem = new TRiSoaSectionItem();
                    genSoaSectionItem.setAmount(reserveOpeningValue);
                    genSoaSectionItem.setEntryCode(getOpeningEntryCodeMatchMap(entryCode));
                    genSoaSectionItem.setShareAmount(reserveOpeningValue);
                    genSoaSectionItem.setPercentage(BigDecimal.ONE);
                    genSoaSectionItem.setTRiSoaSection(soaSection);
                    soaSection.addTRiSoaSectionItem(genSoaSectionItem);
                }
            }
        }
    }

    public void setReserveStatusForSections(TRiSoaCurrency soaCurrency, TRiSoa soa) throws Exception {
        for (TRiSoaSection soaSection : soaCurrency.getTRiSoaSections()) {
            boolean hasNotReversed = soaSectionDao.sectionHasNotReversed(soaSection.getContracCompId(),
                    soa.getCedentYear(), soa.getCedentQuarter());
            soaSection.setReversal(hasNotReversed);
        }
    }

    public boolean validateContractSectionReversal(Integer CendantYear, Integer CendantQuarter, Long contracCompId)
            throws Exception {
        boolean isReveral = true;
        JdbcTemplate jt = SpringContextUtils.getJdbcTemplate();
        StringBuilder sqlBuild = new StringBuilder(" ");
        sqlBuild.append("  select ss.contrac_comp_id, ss.is_reversal, s.soa_id ");
        sqlBuild.append("    from t_ri_soa s, t_ri_soa_section ss              ");
        sqlBuild.append("   where ss.contrac_comp_id =  " + contracCompId + "                        ");
        sqlBuild.append("     and s.soa_id = ss.soa_id                         ");
        sqlBuild.append("     and s.cedent_year = " + CendantYear + "                         ");
        sqlBuild.append("     and s.cedent_quarter = " + CendantQuarter + "                        ");

        List<Map<String, Object>> result = jt.queryForList(sqlBuild.toString());
        if (result == null || result.size() == 0) {
            isReveral = true;
        }
        if (result != null && result.size() != 0 && result.get(0).get("is_reversal") != null) {
            String reversalValue = (String) result.get(0).get("is_reversal");
            if ("1".equals(reversalValue)) {
                isReveral = false;
            }
        }
        return isReveral;
    }

    public void generatInitReserveEntryItem(SoaCurrencyModel soaCurrency, String entryCode, String currencyCode) {
        Map<Long, String> resultSectionMap = new HashMap<Long, String>();
        for (SoaSectionModel soaSection : soaCurrency.getSections()) {
            String validSection = resultSectionMap.get(soaSection.getContracCompId());
            if (validSection == null) {
                resultSectionMap.put(Long.parseLong(soaSection.getContracCompId()), "valid");
                Boolean entryItemExist = validEnterEntryItem(Long.parseLong(soaSection.getSoaSectionId()), entryCode);
                if (!entryItemExist) {
                    BigDecimal reserveValue = findReserveValueBySectionIDAndEntryCode(
                            Long.parseLong(soaSection.getContracCompId()), entryCode, currencyCode);
                    if (reserveValue == null) {
                        SoaSectionItemModel genSoaSectionItem = new SoaSectionItemModel();
                        genSoaSectionItem.setAmount(BigDecimal.ZERO);
                        genSoaSectionItem.setEntryCode(entryCode);
                        genSoaSectionItem.setShareAmount(BigDecimal.ZERO);
                        genSoaSectionItem.setPercentage(BigDecimal.ONE);
                        soaSection.getEntryItems().add(genSoaSectionItem);
                    }
                }
            }
        }
    }

    public void generateReserveEntryItemForSummary(SoaCurrencyModel soaCurrency, String entryCode,
                                                   String currencyCode) {
        Map<Long, String> resultSectionMap = new HashMap<Long, String>();
        for (SoaSectionModel soaSection : soaCurrency.getSections()) {
            String validSection = resultSectionMap.get(soaSection.getContracCompId());
            if (validSection == null) {
                resultSectionMap.put(Long.parseLong(soaSection.getContracCompId()), "valid");
                Boolean entryItemExist = validEnterEntryItem(Long.parseLong(soaSection.getSoaSectionId()), entryCode);
                if (!entryItemExist) {
                    BigDecimal reserveValue = findReserveValueBySectionIDAndEntryCode(
                            Long.parseLong(soaSection.getContracCompId()), entryCode, currencyCode);
                    if (reserveValue != null) {
                        SoaSectionItemModel genSoaSectionItem = new SoaSectionItemModel();
                        genSoaSectionItem.setAmount(reserveValue);
                        genSoaSectionItem.setEntryCode(entryCode);
                        genSoaSectionItem.setShareAmount(reserveValue);
                        genSoaSectionItem.setPercentage(BigDecimal.ONE);
                        soaSection.getEntryItems().add(genSoaSectionItem);
                    }
                }
            }
        }
    }

    public Boolean validEnterEntryItem(long sectionID, String entryCode) {
        Boolean entryItemExist = false;
        JdbcTemplate jt = SpringContextUtils.getJdbcTemplate();
        StringBuilder sqlBuild = new StringBuilder(" ");
        sqlBuild.append(" SELECT item.entry_code                                  ");
        sqlBuild.append("           FROM t_ri_soa_section      sec,               ");
        sqlBuild.append("                t_ri_soa_section_item item               ");
        sqlBuild.append("          WHERE sec.soa_section_id = item.soa_section_id ");
        sqlBuild.append("            and item.entry_code = '" + entryCode + "'    ");
        sqlBuild.append("            and sec.soa_section_id = " + sectionID + " ");
        List<Map<String, Object>> result = jt.queryForList(sqlBuild.toString());
        if (result != null && result.size() != 0 && (String) result.get(0).get("entry_code") != null) {
            entryItemExist = true;
        }
        return entryItemExist;
    }

    public BigDecimal findReserveValueBySectionIDAndEntryCode(long sectionCompID, String entryCode,
                                                              String currencyCode) {
        BigDecimal reserveValue = BigDecimal.ZERO;
        JdbcTemplate jt = SpringContextUtils.getJdbcTemplate();
        StringBuilder sqlBuild = new StringBuilder(" ");
        sqlBuild.append(" select e.share_amount as amount                      ");
        sqlBuild.append("   from (SELECT d.*                                   ");
        sqlBuild.append("           FROM t_ri_soa              a,              ");
        sqlBuild.append("                t_ri_soa_currency     b,              ");
        sqlBuild.append("                t_ri_soa_section      c,              ");
        sqlBuild.append("                t_ri_soa_section_item d               ");
        sqlBuild.append("          WHERE a.soa_id = b.soa_id                   ");
        sqlBuild.append("            AND b.soa_currency_id = c.soa_currency_id ");
        sqlBuild.append("            AND c.soa_section_id = d.soa_section_id   ");
        sqlBuild.append("            AND a.Soa_Status = 2                      "); // release
        sqlBuild.append("            and d.entry_code = '" + entryCode + "'        ");
        sqlBuild.append("            and c.contrac_comp_id = " + sectionCompID + " ");
        sqlBuild.append("            and b.currency_code = '" + currencyCode + "' ");
        sqlBuild.append("          order by d.insert_time desc) e              ");
        sqlBuild.append("  where rownum = 1                                    ");
        List<Map<String, Object>> result = jt.queryForList(sqlBuild.toString());
        if (result == null || result.size() == 0) {
            return null;
        }
        if (result != null && result.size() != 0 && (BigDecimal) result.get(0).get("amount") != null) {
            reserveValue = (BigDecimal) result.get(0).get("amount");
        }
        return reserveValue;
    }

    public Boolean IsSoaWithdrawOrder(String soaID) {
        Boolean IsSoaWithdrawOrder = true;
        JdbcTemplate jt = SpringContextUtils.getJdbcTemplate();
        StringBuilder sqlBuild = new StringBuilder(" ");
        sqlBuild.append(" select s1.soa_id                                                  ");
        sqlBuild.append("          from t_ri_soa s1,                                        ");
        sqlBuild.append("               t_ri_soa_section ss1,                               ");
        sqlBuild.append("               t_ri_bcp_fee f1,                                    ");
        sqlBuild.append("               (select *                                           ");
        sqlBuild.append("                  from (select s.soa_id,                           ");
        sqlBuild.append("                  ss.contrac_comp_id,                              ");
        sqlBuild.append("                  s.cedent_year,                                   ");
        sqlBuild.append("                  s.cedent_quarter,                                ");
        sqlBuild.append("                  min(f.insert_time) as submit_time                ");
        sqlBuild.append("             from t_ri_soa s, t_ri_soa_section ss, t_ri_bcp_fee f  ");
        sqlBuild.append("            where s.soa_id = ss.soa_id                             ");
        sqlBuild.append("              and f.biz_trans_no = to_char(s.soa_id)               ");
        sqlBuild.append("              and s.soa_id = '" + soaID + "'                   ");
        sqlBuild.append("            group by s.soa_id,                                     ");
        sqlBuild.append("                     s.cedent_year,                                ");
        sqlBuild.append("                     s.cedent_quarter,                             ");
        sqlBuild.append("                     ss.contrac_comp_id)) ss2                      ");
        sqlBuild.append("         where s1.soa_id = ss1.soa_id                              ");
        sqlBuild.append("           and s1.soa_id != ss2.soa_id                             ");
        //		sqlBuild.append("           and s1.cedent_year = ss2.cedent_year                    ");
        //	  sqlBuild.append("           and s1.cedent_quarter = ss2.cedent_quarter                ");
        sqlBuild.append("           and ss1.contrac_comp_id = ss2.contrac_comp_id           ");
        sqlBuild.append("           and f1.biz_trans_no = to_char(s1.soa_id)                ");
        sqlBuild.append("           and f1.insert_time > ss2.submit_time                    ");
        sqlBuild.append("           and s1.soa_status =2                                    ");
        List<Map<String, Object>> result = jt.queryForList(sqlBuild.toString());
        if (result == null || result.size() == 0) {
            IsSoaWithdrawOrder = true;
        }
        if (result != null && result.size() != 0 && (BigDecimal) result.get(0).get("soa_id") != null) {
            IsSoaWithdrawOrder = false;
        }
        return IsSoaWithdrawOrder;
    }

    public BigDecimal findReserveOpeningValueBySectionIDAndEntryCode(long sectionCompID, String entryCode,
                                                                     String currencyCode) {
        BigDecimal reserveOpeningValue = BigDecimal.ZERO;
        JdbcTemplate jt = SpringContextUtils.getJdbcTemplate();
        StringBuilder sqlBuild = new StringBuilder(" ");
        sqlBuild.append(" select e.share_amount as amount                      ");
        sqlBuild.append("   from (SELECT d.*                                   ");
        sqlBuild.append("           FROM t_ri_soa              a,              ");
        sqlBuild.append("                t_ri_soa_currency     b,              ");
        sqlBuild.append("                t_ri_soa_section      c,              ");
        sqlBuild.append("                t_ri_soa_section_item d               ");
        sqlBuild.append("          WHERE a.soa_id = b.soa_id                   ");
        sqlBuild.append("            AND b.soa_currency_id = c.soa_currency_id ");
        sqlBuild.append("            AND c.soa_section_id = d.soa_section_id   ");
        sqlBuild.append("            AND a.Soa_Status = 2                      "); // release
        sqlBuild.append("            and d.entry_code = '" + entryCode + "'        ");
        sqlBuild.append("            and c.contrac_comp_id = " + sectionCompID + " ");
        sqlBuild.append("            and b.currency_code = '" + currencyCode + "' ");
        sqlBuild.append("          order by d.insert_time desc) e              ");
        sqlBuild.append("  where rownum = 1                                    ");
        List<Map<String, Object>> result = jt.queryForList(sqlBuild.toString());
        if (result == null || result.size() == 0) {
            return null;
        }
        if (result != null && result.size() != 0 && (BigDecimal) result.get(0).get("amount") != null) {
            reserveOpeningValue = (BigDecimal) result.get(0).get("amount");
        }
        return reserveOpeningValue;
    }

    public BigDecimal findOpeningValueBySectionIDAndEntryCode(long sectionID, String entryCode) {
        BigDecimal reserveOpeningValue = BigDecimal.ZERO;
        JdbcTemplate jt = SpringContextUtils.getJdbcTemplate();
        StringBuilder sqlBuild = new StringBuilder(" ");
        sqlBuild.append(" select bf.amount from t_ri_bcp_fee bf                     ");
        sqlBuild.append("    where bf.biz_trans_id = '" + sectionID + "'        ");
        sqlBuild.append("    and bf.entry_code =   '" + entryCode + "'          ");

        List<Map<String, Object>> result = jt.queryForList(sqlBuild.toString());
        if (result != null && result.size() != 0 && (BigDecimal) result.get(0).get("amount") != null) {
            reserveOpeningValue = (BigDecimal) result.get(0).get("amount");
        }
        return reserveOpeningValue.negate();
    }

    private String getOpeningEntryCodeMatchMap(String closeingEntryCode) throws Exception {
        Map<String, String> openingEntryCodeMap = SoaConstant.getInitOpeningEntryCodeMatchMap();
        return openingEntryCodeMap.get(closeingEntryCode);
    }

    private void updateIgnoringFlag(long SoaIdRead) throws Exception {
        TRiSoa soaIgnoring = soaDao.load(SoaIdRead);
        soaIgnoring.setWithdrawIgnoringCutoffDate(true);
        soaDao.merge(soaIgnoring);
        em.flush();
    }

    public Boolean validLatesSoa4Withdraw(String soaSectionID, String soaCompCont, String entryCode) {
        Boolean validWithdrawFlag = true;
        JdbcTemplate jt = SpringContextUtils.getJdbcTemplate();
        StringBuilder sqlBuild = new StringBuilder(" ");
        sqlBuild.append("  select t1.insert_time                                 ");
        sqlBuild.append("        from (select max(bf.insert_time) as insert_time ");
        sqlBuild.append("                from t_ri_bcp_fee bf                    ");
        sqlBuild.append("               where bf.biz_trans_type = 3              ");
        sqlBuild.append("                 and bf.section_id = '" + soaCompCont + "'             ");
        sqlBuild.append("                 and bf.entry_code = '" + entryCode + "' ) t1,          ");
        sqlBuild.append("             (select bf.insert_time                     ");
        sqlBuild.append("                from t_ri_bcp_fee bf                    ");
        sqlBuild.append("               where bf.biz_trans_type = 3              ");
        sqlBuild.append("                 and bf.section_id = '" + soaCompCont + "'             ");
        sqlBuild.append("                 and bf.biz_trans_id = '" + soaSectionID + "'          ");
        sqlBuild.append("                 and bf.entry_code = '" + entryCode + "') t2           ");
        sqlBuild.append("       where t1.insert_time > t2.insert_time            ");
        List<Map<String, Object>> result = jt.queryForList(sqlBuild.toString());
        if (result != null && result.size() != 0 && result.get(0).get("insert_time") != null) {
            validWithdrawFlag = false;
        }
        return validWithdrawFlag;
    }

    @Override
    public List<SoaEntry> findSoaEntryListBySectionIdAndFNQuarter(Long sectionId, YearQuarter yearQuarter) {
        return soaDao.findSoaEntryListBySectionIdAndFNQuarter(sectionId, yearQuarter);
    }

    @Override
    public List<SoaEntry> findSoaEntryListBySoaId(Long soaId) {
        return soaDao.findSoaEntryListBySoaId(soaId);
    }

    public void validateCutOffDatePeriod(SoaModel soaM) throws Exception {
        if (accountingServiceImpl.inClosingPeriod()) {
            soaM.setIsCutOffPeriod("true");
        } else {
            soaM.setIsCutOffPeriod("false");
        }
    }

    public String getContractategory(SoaModel soaM) throws Exception, Exception {
        ContractModel contractVO = contractService.getContractInfoByCompId(soaM.getContractId());
        if (contractVO == null) {
            return "1";
        }
        return contractVO.getContractCategory();
    }

    public void setContractNature(SoaModel soaM) throws Exception, Exception {
        ContractModel contractModel = contractService.getContractInfoByCompId(soaM.getContractId());
        soaM.setContractNature(contractModel.getContractNature());
        soaM.setContractCategory(contractModel.getContractCategory());
        BigDecimal settlementDays = contractModel.getSettlementDays();
        if (settlementDays == null) {
            settlementDays = BigDecimal.ZERO;
        }
        soaM.setSettlementDays(settlementDays.toString());
    }

    public boolean getCutOffDatePeriodFlag() throws Exception {
        if (accountingServiceImpl.inClosingPeriod()) {
            return true;
        } else {
            return false;
        }
    }

    public void prepareSoaForEstimationWithDraw(SoaModel soaM) throws Exception {
        YearQuarter yearQuarter = new YearQuarter(Integer.parseInt(String.valueOf(soaM.getCedentYear())),
                Integer.parseInt(String.valueOf(soaM.getCedentQuarter())));
        estimateDSImpl.withdrawSOA(Long.parseLong(soaM.getSoaId()), false, yearQuarter);
    }

    public void prepareSoaForEstimationSpecialWithDraw(SoaModel soaM) throws Exception {
        YearQuarter yearQuarter = new YearQuarter(Integer.parseInt(String.valueOf(soaM.getCedentYear())),
                Integer.parseInt(String.valueOf(soaM.getCedentQuarter())));
        estimateDSImpl.withdrawSOA(Long.parseLong(soaM.getSoaId()), true, yearQuarter);
    }

    public void prepareSoaForEstimation(SoaModel soaM) throws Exception {
        YearQuarter yearQuarter = new YearQuarter(Integer.parseInt(String.valueOf(soaM.getCedentYear())),
                Integer.parseInt(String.valueOf(soaM.getCedentQuarter())));
        estimateDSImpl.afterSOASubmit(Long.parseLong(soaM.getSoaId()), false, yearQuarter);
    }

    public void prepareSoaForEstimationBySpecialSubmit(SoaModel soaM) throws Exception {
        YearQuarter yearQuarter = new YearQuarter(Integer.parseInt(String.valueOf(soaM.getCedentYear())),
                Integer.parseInt(String.valueOf(soaM.getCedentQuarter())));
        estimateDSImpl.afterSOASubmit(Long.parseLong(soaM.getSoaId()),true, yearQuarter);
    }

    @Override
    public void createSoaModelForNonProportionalContract(SoaNonPropVO soaVO) throws Exception {
        try {
            SoaModel soaM = (SoaModel) soaModelBuilder.buildSoaModelFromSoa4NonPropContVO(soaVO);
            createSpecialSoaInfo(soaM);
            em.flush();
        } catch (Exception e) {
            // e.printStackTrace();
            throw e;
        }

    }

    public void createSpecialSoaInfo(SoaModel soaM) throws Exception {
        TRiSoa soa = (TRiSoa) soaBuulder.buildSoaObjFromSpecialVO(soaM);
        // init the soa number
        String soaNumber = numService.generateNumber(NumberingType.ACCOUNTING_SOA_NUMBER,
                new HashMap<String, String>());
        soa.setSoaNumber(soaNumber);
        createSpecialSoaCurrencyLayerInof(soaM, soa);
        soaDao.merge(soa);
    }

    public void createSpecialSoaCurrencyLayerInof(SoaModel soaM, TRiSoa soa) throws Exception {
        for (SoaCurrencyModel soaCurrencyM : soaM.getCurrencies()) {
            TRiSoaCurrency soaCurrency = (TRiSoaCurrency) soaBuulder.buildSoaCurrencyObjFromModel(soaCurrencyM);
            soaCurrency.setTRiSoa(soa);
            soa.getTRiSoaCurrencies().add(soaCurrency);
            createSpecialSoaSectionLayerInof(soaCurrencyM, soaCurrency, soa);
        }
    }

    public void createSpecialSoaSectionLayerInof(SoaCurrencyModel soaCurrencyM, TRiSoaCurrency soaCurrency, TRiSoa soa)
            throws Exception {
        for (SoaSectionModel soaSectionM : soaCurrencyM.getSections()) {
            TRiSoaSection soaSection = (TRiSoaSection) soaBuulder.buildSoaSectionObjFromModel(soaSectionM);
            soaSection.setTRiSoaCurrency(soaCurrency);
            soaSection.setTRiSoa(soa);
            soa.getTRiSoaSections().add(soaSection);
            soaCurrency.getTRiSoaSections().add(soaSection);
            createSpecialSoaSectionItemLayerInof(soaSectionM, soaSection);
        }
    }

    public void createSpecialSoaSectionItemLayerInof(SoaSectionModel soaSectionM, TRiSoaSection soaSection)
            throws Exception {
        for (SoaSectionItemModel soaSectionItemM : soaSectionM.getEntryItems()) {
            TRiSoaSectionItem soaSectionItem = (TRiSoaSectionItem) soaBuulder
                    .buildSoaSectionItemObjFromModel(soaSectionItemM);
            soaSectionItem.setTRiSoaSection(soaSection);
            soaSection.getTRiSoaSectionItems().add(soaSectionItem);
        }
    }

    @Override
    public void callBack(Date postDate, List<String> bizTransIdList) throws Exception {
        soaDao.setFNYearAndQuaterBySectionID(postDate, bizTransIdList);
    }

    public SoaValidateModel validateCendentQuarter(String ContractCode, Integer UWYear, Integer CendantYear,
                                                   Integer CendantQuarter, String Cedent, String Broker)
                                                           throws Exception, Exception {

        String result = "true";
        SoaSearchVO soaSearchVO = new SoaSearchVO();
        soaSearchVO.setContractCode(ContractCode);
        soaSearchVO.setUwYear(UWYear);
        soaSearchVO.setCedentYear(CendantYear);
        soaSearchVO.setCedentQuarter(0);
        SoaValidateModel soaValidateModel = new SoaValidateModel();
        ContractModel contractModel = contractService.getInforceAndCancelledContractIdByCode(ContractCode,
                Long.parseLong(UWYear.toString()));
        //validate if the contract exist
        Boolean existContract = false;
        List<Long> UWYearList = contractService.getContractUWYearByContractCode(ContractCode);
        if (UWYearList.contains(Long.parseLong(UWYear.toString()))) {
            existContract = true;
        }
        if (existContract) {
            soaValidateModel.setInContractUWYear("true");
        } else {
            soaValidateModel.setInContractUWYear("false");
        }

        int maxCendantQuarter = 1;
        List<YearQuarter> qList = new ArrayList<YearQuarter>();
        if (contractModel != null) {
            qList = calcContractQuarterByPOI(contractModel);
            YearQuarter yqInit = qList.get(0);
            maxCendantQuarter = (yqInit.getYear() * 4 + yqInit.getQuarter());
        }
        List<TRiSoa> resultList = soaDao.getSoaCurrentPage(soaSearchVO, 0, 10);
        int cedentQuarterCount = 1;
        for (TRiSoa soaT : resultList) {
            cedentQuarterCount = (soaT.getCedentYear() * 4 + soaT.getCedentQuarter());
            if (cedentQuarterCount > maxCendantQuarter) {
                maxCendantQuarter = cedentQuarterCount;
            }
        }
        if ((CendantYear * 4 + CendantQuarter) > maxCendantQuarter + 1) {
            result = "false";
        }
        //        if (contractModel != null) {
        //            YearQuarter yqMin = qList.get(0);
        //            YearQuarter yqMax = qList.get(3);
        //            int min = (CendantYear * 4 + CendantQuarter) - (yqMin.getYear() * 4 + yqMin.getQuarter());
        //            int max = (CendantYear * 4 + CendantQuarter) - (yqMax.getYear() * 4 + yqMax.getQuarter());
        //            if (!(min >= 0 && max <= 0)) {
        //                result = "wrongQuarter";
        //            }
        //            soaValidateModel.setInCendentYearAndQuarter(result);
        //        }

        //validate Soa revered 
        boolean sectionReversalFlag = validateContractSectionReversal(ContractCode, UWYear, CendantYear,
                CendantQuarter);
        if (!sectionReversalFlag) {
            result = "noEstimateInfo";
            soaValidateModel.setInCendentYearAndQuarter(result);
        }

        if (contractModel != null) {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(contractModel.getReinsStarting());
            YearQuarter curQuarter = new YearQuarter(c1.getTime());
            int reinsStartYear = curQuarter.getYear();
            if (CendantYear < reinsStartYear) {
                result = "wrongQuarter";
            }
            soaValidateModel.setInCendentYearAndQuarter(result);
        }

        return soaValidateModel;
    }

    public void orderSectionOfCurrencies(SoaCurrencyModel currencyModel) throws Exception {
        initOrderOfSectionViews(currencyModel.getSections());
    }

    public List<SoaSectionModel> initOrderOfSectionViews(List<SoaSectionModel> sectionViewList) {
        Comparator<SoaSectionModel> comparator = new Comparator<SoaSectionModel>() {
            public int compare(SoaSectionModel s1, SoaSectionModel s2) {
                if (s1.getSoaSectionId() != s2.getSoaSectionId()) {
                    return Integer.parseInt(s1.getSoaSectionId()) - Integer.parseInt(s2.getSoaSectionId());
                }
                return 0;
            }
        };
        Collections.sort(sectionViewList, comparator);
        return sectionViewList;
    }

    @Override
    public boolean validSoaBySectionId(Long sectionID) throws Exception {
        return soaDao.validSoaByContractSectionID(sectionID);
    }

    @Override
    public List<TRiSoaSection> findSoaSectionListBySoaId(Long soaId) throws Exception {
        return soaSectionDao.findSoaSectionListBySoaId(soaId);
    }

    @Override
    public <T> List bizProcess(Map model) throws Exception {
        String soaID = (String) model.get("refId");
        return exportSoaViewInfo(Long.parseLong(soaID));
    }

    public List<SoaExportExcelModel> exportSoaViewInfo(Long statementId) throws Exception {
        SoaModel soaM = null;
        TRiSoa soa = new TRiSoa();
        soa = soaDao.load(statementId);
        soaM = (SoaModel) soaModelBuilder.buildSoaModel(soa);
        // generate the reserve entryItem for Soa
        collectReserveEntryItemForSummary(soaM);
        // deal with the same entryItem of the same section
        soaM = calculateSameEntryItem4SameSectionForView(soaM);
        // generate export excel data
        return generateExcelData(soaM);

    }

    public List<SoaExportExcelModel> generateExcelData(SoaModel soaM) throws Exception {
        List<SoaExportExcelModel> soaExportExcelList = new ArrayList<SoaExportExcelModel>();
        EntryCode entryItem = null;
        for (SoaCurrencyModel soaCurrencyM : soaM.getCurrencies()) {
            for (SoaSectionModel soaSectionM : soaCurrencyM.getSections()) {
                for (SoaSectionItemModel itemM : soaSectionM.getEntryItems()) {
                    entryItem = entryCodeService.getByEntryCode(itemM.getEntryCode());
                    SoaExportExcelModel soaExpExcelM = new SoaExportExcelModel();
                    soaExpExcelM.setStatementNo(soaM.getSoaId());
                    soaExpExcelM.setContractNo(soaM.getContractCode());
                    soaExpExcelM.setAccountText(soaM.getSoaText());
                    soaExpExcelM.setUWY(soaM.getUwYear());
                    soaExpExcelM.setContractSection(soaSectionM.getContractSection());
                    soaExpExcelM.setMainSubCOB(this.getContractCodeDescription(
                            SoaConstant.CODE_TABLE_CONTRACT_SUBCOB_TYPE, soaSectionM.getCob()));
                    soaExpExcelM.setShareType(this.getContractCodeDescription(SoaConstant.CODE_TABLE_CONT_SHARE_TYPE,
                            soaSectionM.getShareType()));
                    soaExpExcelM.setCedentCountry(this.getContractCodeDescription(
                            SoaConstant.CODE_TABLE_CONTRACT_COUNTRYE, soaSectionM.getUwArea()));
                    soaExpExcelM.setCedentYear(soaM.getCedentYear());
                    soaExpExcelM.setCedentQuarter("Q"+soaM.getCedentQuarter());
                    soaExpExcelM.setEntryCode(itemM.getEntryCode());
                    soaExpExcelM.setEntryItem(entryItem.getEntryCodeName());
                    soaExpExcelM.setCurrency(soaCurrencyM.getCurrencyType());
                    if (soaM.getContractCategory() != null && "2".equals(soaM.getContractCategory())) {// Retro Contract
                        itemM.setShareAmount(itemM.getShareAmount().negate());
                    }
                    int sign = entryItem.getSign();
                    BigDecimal shareAmountBD = itemM.getShareAmount().multiply(BigDecimal.valueOf(sign));

                    if (entryItem.getCashBalance() == 1) {
                        soaExpExcelM.setCashBalance(shareAmountBD);
                    } else {
                        soaExpExcelM.setCashBalance(BigDecimal.ZERO);
                    }
                    soaExpExcelM.setTechResult(shareAmountBD);
                    soaExportExcelList.add(soaExpExcelM);
                }
            }
        }
        return soaExportExcelList;
    }

    public void initSoaDueDate(TRiSoa soa, BigDecimal settlementDays) throws Exception {
        // SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        if (settlementDays == null) {
            settlementDays = BigDecimal.ZERO;
        }
        c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(settlementDays.toString()));
        soa.setDueDate(c.getTime());
    }

    private String getContractCodeDescription(long codeTable, String codeId) {
        if (StringUtils.isNotEmpty(codeId)) {
            try {
                return codeTableService.findCodeTableValueByCode$CodeTableId(codeId, Long.valueOf(codeTable), null)
                        .getDescription();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Object AMLCheck(String cedent, String broker) throws Exception {
        List<MessageVO> messageList = new ArrayList<>();
        if (!"0".equals(cedent)) {
            MessageVO message = checkAmlService.amlCheck(cedent, "1");
            if (!message.isCheckFlag()) {
                messageList.add(message);
            }
        }
        if (!"0".equals(broker)) {
            MessageVO message = checkAmlService.amlCheck(broker, "2");
            if (!message.isCheckFlag()) {
                messageList.add(message);
            }
        }
        return messageList;
    }

    public SoaValidateModel ValidEntryItemForStatementType(String entryCode, String statementType) throws Exception {
        String result = "valid";
        String statementTypeStrList = "";
        try {
            statementTypeStrList = entryCodeService.getByEntryCode(entryCode).getStatementType();
            String[] arr = statementTypeStrList.split(",");
            List<String> statementTypeList = (List<String>) Arrays.asList(arr);
            if (!statementTypeList.contains(statementType)) {
                result = "wrong";
            }
        } catch (Exception e) {
            result = "wrong";
        }

        SoaValidateModel soaValidateModel = new SoaValidateModel();
        soaValidateModel.setResult(result);
        return soaValidateModel;
    }

    public List<ActualItem> findActualItemByCondition(long contCompId, int cedentYear, int cedentQuarter,
                                                      String entryCode) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void saveSoaBOForUploading(SoaModel soaM) throws Exception {
        try {
            // save or update soa info
            saveOrUpdateSoaInfoForUploading(soaM);
            em.flush();
        } catch (Exception e) {
            throw e;
        }
    }

    public void saveOrUpdateSoaInfoForUploading(SoaModel soaM) throws Exception {
        TRiSoa soa = (TRiSoa) soaBuulder.buildSoaObjFromModelForUploading(soaM);
        // update SoaLayer Info
        soa.setTaskcreator(String.valueOf(AppContext.getCurrentUser().getUserId()));
        soa.setTaskcreatorName(String.valueOf(AppContext.getCurrentUser().getUserName()));
        saveOrUpdateSoaCurrencyLayerInofForUploading(soaM, soa);
        soaDao.merge(soa);

    }

}
