/**
 * 
 */
package com.ebao.ap99.accounting.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.accounting.AccountingService;
import com.ebao.ap99.accounting.constant.IbnrConstant;
import com.ebao.ap99.accounting.constant.SegmentStatusEnum;
import com.ebao.ap99.accounting.convertor.IbnrConvertor;
import com.ebao.ap99.accounting.dao.IBNRrecordDao;
import com.ebao.ap99.accounting.dao.TRiAcctFeeDao;
import com.ebao.ap99.accounting.dao.TRiAcctIbnrInfoDao;
import com.ebao.ap99.accounting.dao.TRiAcctSegmentDao;
import com.ebao.ap99.accounting.entity.TRiAcctFee;
import com.ebao.ap99.accounting.entity.TRiAcctFeeDetail;
import com.ebao.ap99.accounting.entity.TRiAcctIbnrInfo;
import com.ebao.ap99.accounting.entity.TRiAcctSegment;
import com.ebao.ap99.accounting.estimation.EstimateItem;
import com.ebao.ap99.accounting.estimation.EstimateQuarter;
import com.ebao.ap99.accounting.model.IBNRUploadModel;
import com.ebao.ap99.accounting.model.IBNRrecord;
import com.ebao.ap99.arap.constant.BizTransactionType;
import com.ebao.ap99.arap.constant.ContractCategory;
import com.ebao.ap99.arap.service.FinanceService;
import com.ebao.ap99.arap.vo.BusinessFee;
import com.ebao.ap99.arap.vo.BusinessFeeModel;
import com.ebao.ap99.file.model.ItemVO;
import com.ebao.ap99.file.model.MessageVO;
import com.ebao.ap99.file.service.BizService;
import com.ebao.ap99.parent.Page;
import com.ebao.ap99.parent.context.AppContext;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

/**
 * @author xiaoyu.yang
 */
public class SegmentDSImpl implements BizService {

	private static Logger logger = LoggerFactory.getLogger();

	@Autowired
	private TRiAcctSegmentDao tRiAcctSegmentDao;

	@Autowired
	private IBNRrecordDao iBNRrecordDao;

	@Autowired
	private AccountingService accountingService;

	@Autowired
	private TRiAcctIbnrInfoDao tRiAcctIbnrInfoDao;

	@Autowired
	private TRiAcctFeeDao acctFeeDao;
	@Autowired
	private FinanceService financeService;

	@Autowired
	private IbnrConvertor ibnrConvertor;

	@SuppressWarnings("deprecation")
	public Long saveOrUpdateSegment(TRiAcctSegment segment) {
		if (segment.getStatus().equals(SegmentStatusEnum.Valid.getValue())
				&& tRiAcctSegmentDao.sameSegmentExisted(segment)) {
			logger.warn("Same segment existed, stop creating:" + ToStringBuilder.reflectionToString(segment));
			// tRiAcctSegmentDao.getEntityManager().detach(segment);
			return 0L;
		}
		if (segment.getSegmentId() == null) {
			tRiAcctSegmentDao.insert(segment);
		} else {
			tRiAcctSegmentDao.merge(segment);
		}
		return segment.getSegmentId();
	}

	public TRiAcctSegment getSegment(Long segmentId) {
		return tRiAcctSegmentDao.load(segmentId);
	}

	public void deleteSegment(TRiAcctSegment segment) {
		tRiAcctSegmentDao.delete(segment);
	}

	public List<TRiAcctSegment> getAllSegments() {
		return tRiAcctSegmentDao.loadAll();
	}

	public Page<TRiAcctSegment> findCurrentPage(Page<TRiAcctSegment> page) {
		TRiAcctSegment condition = page.getCondition();
		Long results = tRiAcctSegmentDao.getSegmentCount(condition);
		// page.setResults(results);
		List<TRiAcctSegment> list = tRiAcctSegmentDao.getCurrentPage(condition, page.getPageIndex(),
				page.getCountPerPage());
		page.setRows(list);
		Long pageCount = results % page.getCountPerPage() == 0 ? results / page.getCountPerPage()
				: results / page.getCountPerPage() + 1;
		page.setPageCount(pageCount);
		if (results < page.getCountPerPage()) {
			page.setCountPerPage(results.intValue());
		}
		return page;
	}

	public List<IBNRrecord> findIBNRbySegmentId(Long segmentId) {
		return iBNRrecordDao.findBySegmentId(segmentId);
	}

	@SuppressWarnings("deprecation")
	@Override
	public MessageVO bizProcess(List ItemVO, Long documentId, Long businessId) throws Exception {
		List<IBNRUploadModel> IBNRModelList = new ArrayList<>();
		// for (int i = 0; i < ItemVO.size(); i++) {
		// ItemVO vo = (ItemVO) ItemVO.get(i);
		// IBNRUploadModel IBNRModel = (IBNRUploadModel) vo.getBizVO();
		// IBNRModelList.add(IBNRModel);
		// }
		// if(IBNRModelList.size()>0){
		// for (int i = 0; i < IBNRModelList.size() - 1; i++)
		// {
		// String sgname = IBNRModelList.get(i).getSegmentName();
		// String cob = IBNRModelList.get(i).getCoB();
		// String ceCountry =IBNRModelList.get(i).getCedentCountry();
		// String cNature =IBNRModelList.get(i).getContractNature();
		//
		// for (int j = i+1; j < IBNRModelList.size(); j++){
		// if(sgname.equals(IBNRModelList.get(j).getSegmentName()) &&
		// cob.equals(IBNRModelList.get(j).getCoB()) &&
		// ceCountry.equals(IBNRModelList.get(j).getCedentCountry())&&
		// cNature.equals(IBNRModelList.get(j).getContractNature())){
		// throw new Exception(sgname+cob+ceCountry+cNature+"is duplicate ");
		//
		// }
		// }
		//
		// }
		// }
		boolean insertFlag = true;
		MessageVO message = new MessageVO();
		message.setBusinessId(businessId);
		message.setDocumentId(documentId);
		
		for (int i = 0; i < ItemVO.size(); i++) {
			@SuppressWarnings("rawtypes")
			ItemVO vo = (ItemVO) ItemVO.get(i);
			try {

				TRiAcctSegment condition = new TRiAcctSegment();
				IBNRUploadModel IBNRModel = (IBNRUploadModel) vo.getBizVO();

				String sgname = IBNRModel.getSegmentName();
				String cob = IBNRModel.getCoB();
				String ceCountry = IBNRModel.getCedentCountry();
				String cNature = IBNRModel.getContractNature();

				if (IBNRModelList.size() > 0) {
					for (int j = 0; j < IBNRModelList.size(); j++) {
						if (sgname.equals(IBNRModelList.get(j).getSegmentName())
								&& cob.equals(IBNRModelList.get(j).getCoB())
								&& ceCountry.equals(IBNRModelList.get(j).getCedentCountry())
								&& cNature.equals(IBNRModelList.get(j).getContractNature())) {
							insertFlag = false;
							vo.setMsg("Segment in the  row of the Excel is duplicated.");//Segment in the nth row of the Excel is duplicated.
							message.setMsg(" upload fail，details please refer to View Record ");
						}
					}

				}
				IBNRModelList.add(IBNRModel);

				condition.setCedentCountry(IBNRModel.getCedentCountry().split("-")[0]);
				//condition.setCedentCountry(ibnrConvertor.countryConvertor(IBNRModel.getCedentCountry()));
			    condition.setCob(IBNRModel.getCoB().split("-")[0]);
				//condition.setCob(ibnrConvertor.cobConvertor(IBNRModel.getCoB()));
				condition.setContractNature(IBNRModel.getContractNature().split("-")[0]);
				//condition.setContractNature(ibnrConvertor.contractNatureConvertor(IBNRModel.getContractNature()));
				condition.setSegmentName(IBNRModel.getSegmentName());
				boolean flag = tRiAcctSegmentDao.sameSegmentExisted(condition);
				if (flag) {
					// getsegmentId
					TRiAcctSegment acctSegment = tRiAcctSegmentDao.findByCondition(condition);
					if ("".equals(acctSegment) || acctSegment == null) {
						insertFlag = false;
						vo.setMsg("The information of "+IBNRModel.getSegmentName()+" is wrong. ");
						message.setMsg(" upload fail，details please refer to View Record ");
					}
				} else {
					vo.setMsg(IBNRModel.getSegmentName() + " not exits, please check it first! ");
					insertFlag = false;
					vo.setMsg(IBNRModel.getSegmentName()+" doesn't exist in the system.");
					message.setMsg(" upload fail，details please refer to View Record ");
				}
			} catch (Exception e) {
				message.setMsg(" upload fail，details please refer to View Record ");
				vo.setMsg("The format of Excel file is wrong.");
				insertFlag = false;
				e.printStackTrace();
			}
		}

		if (insertFlag) {
			List<BusinessFeeModel> businessFeeLists = new ArrayList<>();

			for (int i = 0; i < ItemVO.size(); i++) {
				@SuppressWarnings("rawtypes")
				ItemVO vo = (ItemVO) ItemVO.get(i);
				try {
					TRiAcctSegment condition = new TRiAcctSegment();
					IBNRUploadModel IBNRModel = (IBNRUploadModel) vo.getBizVO();

					condition.setCedentCountry(IBNRModel.getCedentCountry().split("-")[0]);
				    condition.setCob(IBNRModel.getCoB().split("-")[0]);
					condition.setContractNature(IBNRModel.getContractNature().split("-")[0]);
					condition.setSegmentName(IBNRModel.getSegmentName());
					boolean flag = tRiAcctSegmentDao.sameSegmentExisted(condition);

					if (flag) {
						// getsegmentId
						TRiAcctSegment acctSegment = tRiAcctSegmentDao.findByCondition(condition);
						if ("".equals(acctSegment) || acctSegment == null) {
							vo.setMsg("The information of "+IBNRModel.getSegmentName()+" is wrong. ");
						} else {
							Long SegmentId = acctSegment.getSegmentId();
							List<TRiAcctIbnrInfo> ibnrEntityList = getIbnrList(IBNRModel, SegmentId, documentId);
							// TODO insert ibnrEntityList
							Long OldUploadId = tRiAcctIbnrInfoDao.getMaxDocumentIdBySegmentId(SegmentId);
							Long OldQuarterUploadId = tRiAcctIbnrInfoDao.getMaxDocumentIdBySegmentIdAndQuarter(SegmentId, accountingService.currentFinancialQuarter().toString());

							for (TRiAcctIbnrInfo t : ibnrEntityList) {
								// tRiAcctIBNRinfoDao.insert(t);
								tRiAcctIbnrInfoDao.insert(t);
							}
							
							//accountingService.currentFinancialQuarter().toString()
							if(OldQuarterUploadId !=null && OldQuarterUploadId !=0){
								List<TRiAcctIbnrInfo> oldIbnr =  tRiAcctIbnrInfoDao.loadOldIbnrByUploadIdAndSegmentId(OldQuarterUploadId,SegmentId);
								for(TRiAcctIbnrInfo t : oldIbnr) {
									// tRiAcctIBNRinfoDao.insert(t);
									t.setStatus("0");
									tRiAcctIbnrInfoDao.merge(t);
								}
							}
							
							Long NewUploadId = tRiAcctIbnrInfoDao.getMaxDocumentIdBySegmentId(SegmentId);
							BusinessFeeModel businessFeeModel = getPostModel(SegmentId, OldUploadId, NewUploadId);
							businessFeeLists.add(businessFeeModel);
						}
					} else {
						vo.setMsg(IBNRModel.getSegmentName()+" doesn't exist in the system.");
					}
				} catch (Exception e) {
					vo.setMsg("The format of Excel file is wrong.");
					e.printStackTrace();
				}
			}
			if (businessFeeLists.size() > 0) {
				try {
					financeService.writeToFinance(businessFeeLists);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}

		return message;
	}

	
	public List<TRiAcctIbnrInfo> getIbnrList(IBNRUploadModel IBNRModel, Long segmentId, Long documentId)
			throws Exception {
		List<TRiAcctIbnrInfo> ibnrEntityList = new ArrayList<TRiAcctIbnrInfo>();
		@SuppressWarnings("rawtypes")
		Class cls = IBNRModel.getClass();
		logger.info(cls.getName());
		java.lang.reflect.Field[] flds = cls.getDeclaredFields();

		if (flds != null) {
			for (int i = 0; i < flds.length; i++) {
				String name = flds[i].getName();
				name = name.substring(0, 1).toUpperCase() + name.substring(1);
				String type = flds[i].getGenericType().toString();
				if (type.equals("class java.math.BigDecimal")) {
					java.lang.reflect.Method m = IBNRModel.getClass().getMethod("get" + name);
					BigDecimal amount = (BigDecimal) m.invoke(IBNRModel);
					if (amount != null && amount != new BigDecimal(-1) && !amount.equals(new BigDecimal(-1))) {
						logger.info("name ==" + name.substring(2, 6) + "==attribute value:" + amount);
						TRiAcctIbnrInfo ibnrEntity = new TRiAcctIbnrInfo();
						ibnrEntity.setSegmentId(segmentId);
						ibnrEntity.setUwYear(name.substring(2, 6));
						ibnrEntity.setFnQuarter(accountingService.currentFinancialQuarter().toString());
						ibnrEntity.setAmount(amount);
						ibnrEntity.setStatus("1");
						ibnrEntity.setUploadId(documentId);
						ibnrEntityList.add(ibnrEntity);
					}
				}
			}
		}
		return ibnrEntityList;

	}

	public BusinessFeeModel getPostModel(Long segmentId, Long OldUploadId, Long NewUploadId) {

		TRiAcctFee acctFee = new TRiAcctFee();
		acctFee.setBusiType(BizTransactionType.IBNR_UPLOAD.getType());
		generateAcctFee(acctFee, segmentId, OldUploadId, NewUploadId);
		// Post AcctFee
		acctFeeDao.insert(acctFee);
		BusinessFeeModel feeModel = getPostingModel(segmentId, OldUploadId, NewUploadId);
		return feeModel;
	}

	public void generateAcctFee(TRiAcctFee acctFee, Long segmentId, Long OldUploadId, Long NewUploadId) {
		double oldAmount = tRiAcctIbnrInfoDao.getAmountBySegmentIdAndUploadId(segmentId, OldUploadId);
		TRiAcctFeeDetail openFeeDetail = new TRiAcctFeeDetail(acctFee);
		openFeeDetail.setContCompId(segmentId);
		openFeeDetail.setCurrency(IbnrConstant.REPORTING_CURRENCY);
		if (oldAmount == 0) {
			openFeeDetail.setAmount(new BigDecimal(0));
		} else {
			openFeeDetail.setAmount(new BigDecimal(oldAmount).multiply(new BigDecimal(-1)));// amount
		}
		openFeeDetail.setEntryCode(IbnrConstant.IBNR_OPENING_ENTRYCODE);
		openFeeDetail.setPostingDate(AppContext.getSysDate());

		TRiAcctFeeDetail closeFeeDetail = new TRiAcctFeeDetail(acctFee);
		closeFeeDetail.setContCompId(segmentId);
		closeFeeDetail.setCurrency(IbnrConstant.REPORTING_CURRENCY);
		closeFeeDetail
				.setAmount(new BigDecimal(tRiAcctIbnrInfoDao.getAmountBySegmentIdAndUploadId(segmentId, NewUploadId)));
		closeFeeDetail.setEntryCode(IbnrConstant.IBNR_CLOSING_ENTRYCODE);
		closeFeeDetail.setPostingDate(AppContext.getSysDate());

	}

	public BusinessFeeModel getPostingModel(Long segmentId, Long OldUploadId, Long NewUploadId) {
		double oldAmount = tRiAcctIbnrInfoDao.getAmountBySegmentIdAndUploadId(segmentId, OldUploadId);

		BusinessFeeModel businessFeeModel = new BusinessFeeModel();
		businessFeeModel.setContractCategory(ContractCategory.ASSUMED.getType());
		businessFeeModel.setBizTransType(BizTransactionType.IBNR_UPLOAD.getType());
		businessFeeModel.setBizTransNo(segmentId + "");
		businessFeeModel.setBizTransId(segmentId);
		businessFeeModel.setSpecialSubmitInCutoffPeriod(false);
		// businessFeeModel.setContractCode("");
		// businessFeeModel.setPartnerCode();

		List<BusinessFee> businessFeeLists = new ArrayList<>();
		BusinessFee openingFee = new BusinessFee();
		openingFee.setEntryCode(IbnrConstant.IBNR_OPENING_ENTRYCODE);
		if (oldAmount == 0) {
			openingFee.setAmount(new BigDecimal(0));
		} else {
			openingFee.setAmount(new BigDecimal(oldAmount).multiply(new BigDecimal(-1)));// amount
		}
		openingFee.setCurrencyCode(IbnrConstant.REPORTING_CURRENCY);
		// openingFee.setCurrentPeriod();
		// openingFee.setTotalPeriod();
		openingFee.setDueDate(AppContext.getSysDate());
		// openingFee.isEstimation();
		openingFee.setNeedPost(true);
		businessFeeLists.add(openingFee);

		BusinessFee closingFee = new BusinessFee();
		closingFee.setEntryCode(IbnrConstant.IBNR_CLOSING_ENTRYCODE);
		closingFee
				.setAmount(new BigDecimal(tRiAcctIbnrInfoDao.getAmountBySegmentIdAndUploadId(segmentId, NewUploadId)));
		closingFee.setCurrencyCode(IbnrConstant.REPORTING_CURRENCY);
		// closingFee.setCurrentPeriod();
		// closingFee.setTotalPeriod();
		closingFee.setDueDate(AppContext.getSysDate());
		// closingFee.isEstimation();
		closingFee.setNeedPost(true);
		businessFeeLists.add(closingFee);

		businessFeeModel.setFeeList(businessFeeLists);

		return businessFeeModel;
	}

}
