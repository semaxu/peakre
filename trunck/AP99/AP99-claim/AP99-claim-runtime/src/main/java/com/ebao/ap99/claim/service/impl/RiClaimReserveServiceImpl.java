/**
 * 
 */
package com.ebao.ap99.claim.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.accounting.AccountingService;
import com.ebao.ap99.accounting.model.ClaimEntryVO;
import com.ebao.ap99.arap.service.CurrencyExchangeService;
import com.ebao.ap99.arap.service.FinanceService;
import com.ebao.ap99.arap.vo.BusinessFeeModel;
import com.ebao.ap99.claim.constant.ClaimConstant;
import com.ebao.ap99.claim.convertor.ReserveInfoConvertor;
import com.ebao.ap99.claim.convertor.ReserveLogConverter;
import com.ebao.ap99.claim.dao.RiClaimReserveDao;
import com.ebao.ap99.claim.dao.RiClaimSectionDao;
import com.ebao.ap99.claim.entity.TRiClaimReserve;
import com.ebao.ap99.claim.entity.TRiClaimReserveLog;
import com.ebao.ap99.claim.model.ReserveInfo;
import com.ebao.ap99.claim.model.ReserveSummaryInfo;
import com.ebao.ap99.claim.service.RiClaimReserveLogService;
import com.ebao.ap99.claim.service.RiClaimReserveService;
import com.ebao.ap99.parent.BeanUtils;
import com.ebao.ap99.parent.context.AppContext;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

/**
 * @author gang.wang
 *
 */
public class RiClaimReserveServiceImpl implements RiClaimReserveService {
	private static Logger logger = LoggerFactory.getLogger();

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private RiClaimReserveDao reserveDao;

	@Autowired
	private ReserveInfoConvertor reserveConventor;

	@Autowired
	public ReserveLogConverter reserveLogConverter;
	@Autowired
	public RiClaimReserveLogService reserveLogService;
	@Autowired
	public CurrencyExchangeService currencyExchangeService;
	@Autowired
	public FinanceService financeService;

	@Autowired
	public AccountingService accountingService;
	@Autowired
	public RiClaimSectionDao sectionDao;
	@Override
	public void createClaimReserve(TRiClaimReserve reserveEntity) {
		reserveDao.insert(reserveEntity);
	}

	@SuppressWarnings("deprecation")
	@Override
	public TRiClaimReserve updateClaimReserve(TRiClaimReserve reserveEntity) {
		return reserveDao.merge(reserveEntity);
	}

	@Override
	public TRiClaimReserve getClaimReserve(long reserveId) {
		return null;
	}

	@Override
	public void deleteClaimReserve(TRiClaimReserve reserveEntity) {

	}

	@Override
	public List<TRiClaimReserve> getAllClaimReserves() {
		return null;
	}

	@Override
	public void createClaimReserveList(List<TRiClaimReserve> reserveEntityList) {
		for (TRiClaimReserve reserveEntity : reserveEntityList) {
			createClaimReserve(reserveEntity);
		}
	}

	@Override
	public List<TRiClaimReserve> getReserveList(long claimId) {

		List<TRiClaimReserve> reservelist = reserveDao.getReserveList(claimId);
		return reservelist;
	}

	public List<ReserveSummaryInfo> calcReserveSummary(List<ReserveInfo> reserveList) {

		if (null == reserveList || reserveList.size() == 0)
			return null;

		List<ReserveSummaryInfo> reserveSummary = new ArrayList<ReserveSummaryInfo>();

		List<String> currencyList = reserveList.stream().filter(r -> r.getOriginalCurrency() != null)
				.map(r -> r.getOriginalCurrency()).distinct().collect(Collectors.toList());

		for (String currency : currencyList) {
			Map<String, Double> resultOfOneCurrency = reserveList.parallelStream()
					.filter(r -> r.getOriginalCurrency() != null && r.getOriginalCurrency().equals(currency))
					.collect(Collectors.groupingBy(ReserveInfo::getReserveType,
							Collectors.summingDouble(ReserveInfo::getAmountOcDouble)));

			ReserveSummaryInfo reserveSummaryInfo = new ReserveSummaryInfo(currency, resultOfOneCurrency);
			reserveSummary.add(reserveSummaryInfo);
		}

		Map<String, Double> resultOfUsdEquivalent = reserveList.stream().filter(r -> r.getOriginalCurrency() != null)
				.collect(Collectors.groupingBy(ReserveInfo::getReserveType,
						Collectors.summingDouble(ReserveInfo::getAmountUsdDouble)));
		ReserveSummaryInfo reserveSummaryInfo = new ReserveSummaryInfo("USD Equivalent", resultOfUsdEquivalent);
		reserveSummary.add(reserveSummaryInfo);

		return reserveSummary;
	}

	// @Override
	// public void submitReserve(TRiClaimReserve reserve, ClaimInfo claimInfo) {
	//
	// // reserve
	// TRiClaimReserve reserveEntity = reserveConventor.changeReserve(reserve);
	// if
	// (reserveEntity.getBusinessDirection().equals(ClaimConstant.CLAIM_BUSINESS_DIRECTION__FINANCIAL))
	// {
	// reserveEntity.setRemark(claimInfo.getReserveUpdateRemark());
	// } else if
	// (reserve.getBusinessDirection().equals(ClaimConstant.CLAIM_BUSINESS_DIRECTION__RETROCESSION))
	// {
	// reserveEntity.setRemark(claimInfo.getReserveUpdateRemarkRetro());
	//
	// }
	//
	// // reserveLog
	// TRiClaimReserveLog reserveLog = new TRiClaimReserveLog();
	// reserveLog = reserveLogConverter.reserveToLog(reserve);
	// if
	// (reserve.getBusinessDirection().equals(ClaimConstant.CLAIM_BUSINESS_DIRECTION__FINANCIAL))
	// {
	// reserveLog.setRemark(claimInfo.getReserveUpdateRemark());
	// } else if
	// (reserve.getBusinessDirection().equals(ClaimConstant.CLAIM_BUSINESS_DIRECTION__RETROCESSION))
	// {
	// reserveLog.setRemark(claimInfo.getReserveUpdateRemarkRetro());
	// }
	// reserveLog.setTRiClaimReserve(reserveEntity);
	// List<TRiClaimReserveLog> reserveLogList = new ArrayList<>();
	// reserveLogList.add(reserveLog);
	// reserveEntity.setTRiClaimReserveLogs(reserveLogList);
	//
	// reserveDao.merge(reserveEntity);
	// }

	// @Override
	// public void dealReserve(ClaimInfo claimInfo) {
	// long claimId = claimInfo.getClaimId();
	// // 1.find all reserve by claimId
	// List<TRiClaimReserve> reserveList = this.getReserveList(claimId);
	// logger.info("ClaimInfoRestfulService.TRiClaimReserve");
	//
	// for (TRiClaimReserve re : reserveList) {
	// // 2.compare PostingFlag and OrgPostingFlag
	// if (!re.getPostingFlag().equals(re.getOrgPostingFlag())) {
	// logger.info("re.getPostingFlag() != re.getOrgPostingFlag()");
	//
	// if (re.getPostingFlag().equals(ClaimConstant.CLAIM_POSTING_FLAG_YES)) {//
	// PostingFlag=1
	// // new reserveSend re Converter to reserveSend
	// // reserveSend.setAmount = 0
	// // reserveSend.setStatus = opening
	// // insert(reserveSend)
	//
	// // new reserveSend re Converter to reserveSend
	// // reserveSend.setAmount = amountOc
	// // reserveSend.setStatus = closing
	// // insert(reserveSend)
	// } else if
	// (re.getPostingFlag().equals(ClaimConstant.CLAIM_POSTING_FLAG_NO)) {//
	// PostingFlag=0
	// // new reserveSend re Converter to reserveSend
	// // reserveSend.setAmount = - OrgamountOc
	// // reserveSend.setStatus = opening
	// // insert(reserveSend)
	//
	// // new reserveSend re Converter to reserveSend
	// // reserveSend.setAmount = 0
	// // reserveSend.setStatus = closing
	// // insert(reserveSend)
	//
	// }
	//
	// // 4.save reserveLog
	// //reserveLogService.submitReserveLog(re, claimInfo);
	// submitReserve(re, claimInfo);
	//
	// } else if (!re.getAmountOc().equals(re.getOrgAmountOc())) {
	// // 3. compare AmountOc and OrgAmountOc
	// logger.info("re.getAmountOc() != re.getOrgAmountOc()");
	//
	// if (re.getPostingFlag().equals(ClaimConstant.CLAIM_POSTING_FLAG_YES)) {//
	// PostingFlag=1
	// // new reserveSend re Converter to reserveSend
	// // reserveSend.setAmount = - OrgamountOc
	// // reserveSend.setStatus = opening
	// // insert(reserveSend)
	//
	// // new reserveSend re Converter to reserveSend
	// // reserveSend.setAmount = amountOc
	// // reserveSend.setStatus = closing
	// // insert(reserveSend)
	// }
	// // 4.save reserveLog
	// //reserveLogService.submitReserveLog(re, claimInfo);
	// submitReserve(re, claimInfo);
	//
	// } else if
	// (re.getStatus().equals(ClaimConstant.CLAIM_RESERVE_STATUS__NEW)) {
	// // PostingFlag=OrgPostingFlag and AmountOc=OrgAmountOc
	//
	// // 4.save reserveLog
	// //reserveLogService.submitReserveLog(re, claimInfo);
	// submitReserve(re, claimInfo);
	//
	// }
	// }
	//
	// dealPostingReserve(claimId);
	//
	// }

	@Override
	public BigDecimal calculateUsdAmount(String orgCurrency, BigDecimal orgAmount) {
		BigDecimal reportingAmount = new BigDecimal(0);
		try {
			reportingAmount = currencyExchangeService.exchangeAmount(orgAmount, orgCurrency,
					ClaimConstant.REPORTING_CURRENCY, AppContext.getSysDate());
		} catch (Exception e) {
			logger.info("reportingAmount change fail orgCurrency==" + orgCurrency);

			e.printStackTrace();
		}
		return reportingAmount;
	}

	@Override
	public BigDecimal getExchangeRate(String orgCurrency, String reoprtingCurrency) {
		// String reportingCurrency = "USD";
		return getExchangeRateToReportingCurrency(reoprtingCurrency, orgCurrency, AppContext.getSysDate());
	}

	@Override
	public BigDecimal getExchangeRateToReportingCurrency(String reportingCurrency, String orgCurrency, Date date) {
		// call API provide by arap module
		BigDecimal exchangeRate = new BigDecimal(0);

		try {
			exchangeRate = currencyExchangeService.getExchangeRate(reportingCurrency, orgCurrency, date);
		} catch (Exception e) {
			logger.info("reportingCurrency====" + reportingCurrency + "==orgCurrency==" + orgCurrency);
			e.printStackTrace();
		}
		logger.info("exchangeRate====" + exchangeRate + "==orgCurrency==" + orgCurrency);

		return exchangeRate;
	}

	@Override
	public Map<String, String> getAllCurrencyOnClaim(long claimId) {
		return reserveDao.getAllCurrencyOnClaim(claimId);
	}

	@Override
	public Map<String, Double> getLatestReserveAmountGroupByCurrency(long claimId) {
		return reserveDao.getLatestReserveAmountGroupByCurrency(claimId);
	}

	@Override
	public Double getReserveUsdEquivalent(long claimId) {
		return reserveDao.getReserveUsdEquivalent(claimId);
	}

	public void dealPostingReserve(List<BusinessFeeModel> businessModelList) throws Exception {
		// posting reserve
		logger.info("dealPostingReserve====starttime=" + AppContext.getSysDate() + "=businessModelList="
				+ businessModelList.size());
		financeService.writeToFinance(businessModelList);
		logger.info("dealPostingReserve====endtime=" + AppContext.getSysDate());

	}

	@SuppressWarnings("deprecation")
	@Override
	public void deleteReserveList(List<ReserveInfo> reserveList) throws Exception {
		List<TRiClaimReserve> DeleteReserveEntityList = BeanUtils.convertList(reserveList, TRiClaimReserve.class);
		for (TRiClaimReserve d : DeleteReserveEntityList) {
			reserveDao.delete(reserveDao.merge(d));
		}
	}

	@Override
	public void dealReserveSubmit(long refId, String reserveUpdateRemark, String reserveUpdateRemarkRetro)
			throws Exception {

		// find all reserve by claimId
		List<TRiClaimReserve> reserveList = this.getReserveList(refId);
		logger.info("ClaimInfoRestfulService.TRiClaimReserve");
		List<BusinessFeeModel> businessModelList = new ArrayList<>();
		List<ClaimEntryVO> claimEntryVO = new ArrayList<>();
		for (TRiClaimReserve re : reserveList) {

			if (re.getStatus().equals(ClaimConstant.CLAIM_RESERVE_STATUS__NEW)) {
				// PostingFlag=OrgPostingFlag and AmountOc=OrgAmountOc

				if (re.getPostingFlag().equals(ClaimConstant.CLAIM_POSTING_FLAG_YES)) {// PostingFlag=1
					// 1. new reserve submit: opening:0 closing:amountOc
					BusinessFeeModel businessFeeModel = reserveConventor.reserveConvertorPosting(re,
							ClaimConstant.NEW_RESERVE_SUBMIT);
					businessModelList.add(businessFeeModel);

					//if (re.getReserveType().equals(ClaimConstant.LOSS)) {
						List<ClaimEntryVO> claimEntryList = reserveConventor.reservePostAccounting(re,
								ClaimConstant.NEW_RESERVE_SUBMIT);
						claimEntryVO.addAll(claimEntryList);
					//}
				}
				submitReserve(re, reserveUpdateRemark, reserveUpdateRemarkRetro);

			} else if (!re.getPostingFlag().equals(re.getOrgPostingFlag())) {
				logger.info("re.getPostingFlag() != re.getOrgPostingFlag()");

				if (re.getPostingFlag().equals(ClaimConstant.CLAIM_POSTING_FLAG_YES)) {// PostingFlag=1
					// 2. update reserve : posting change No to yes
					BusinessFeeModel businessFeeModel = reserveConventor.reserveConvertorPosting(re,
							ClaimConstant.UPDATE_RESERVE_POSTING_NO_TO_YES);
					businessModelList.add(businessFeeModel);

					//if (re.getReserveType().equals(ClaimConstant.LOSS)) {
						List<ClaimEntryVO> claimEntryList = reserveConventor.reservePostAccounting(re,
								ClaimConstant.UPDATE_RESERVE_POSTING_NO_TO_YES);
						claimEntryVO.addAll(claimEntryList);
				//	}

				} else if (re.getPostingFlag().equals(ClaimConstant.CLAIM_POSTING_FLAG_NO)) {// PostingFlag=0
					// 3. update reserve : posting change Yes to No
					// opening:-orgAmountOc closing:0
					BusinessFeeModel businessFeeModel = reserveConventor.reserveConvertorPosting(re,
							ClaimConstant.UPDATE_RESERVE_POSTING_YES_TO_NO);
					businessModelList.add(businessFeeModel);

				//	if (re.getReserveType().equals(ClaimConstant.LOSS)) {
						List<ClaimEntryVO> claimEntryList = reserveConventor.reservePostAccounting(re,
								ClaimConstant.UPDATE_RESERVE_POSTING_YES_TO_NO);
						claimEntryVO.addAll(claimEntryList);
					//}

				}

				// save reserveLog
				// reserveLogService.submitReserveLog(re, claimInfo);
				submitReserve(re, reserveUpdateRemark, reserveUpdateRemarkRetro);

			} else if (!re.getAmountOc().equals(re.getOrgAmountOc())) {
				logger.info("re.getAmountOc() != re.getOrgAmountOc()");

				if (re.getPostingFlag().equals(ClaimConstant.CLAIM_POSTING_FLAG_YES)) {// PostingFlag=1
					// 4. update reserve : amountOc change
					// opening:-orgAmountOc closing:amountOc
					BusinessFeeModel businessFeeModel = reserveConventor.reserveConvertorPosting(re,
							ClaimConstant.UPDATE_RESERVE_AMOUNT_CHANGE_ONLY);
					businessModelList.add(businessFeeModel);

				//	if (re.getReserveType().equals(ClaimConstant.LOSS)) {
						List<ClaimEntryVO> claimEntryList = reserveConventor.reservePostAccounting(re,
								ClaimConstant.UPDATE_RESERVE_AMOUNT_CHANGE_ONLY);
						claimEntryVO.addAll(claimEntryList);
				//	}

				}
				// save reserveLog
				// reserveLogService.submitReserveLog(re, claimInfo);
				submitReserve(re, reserveUpdateRemark, reserveUpdateRemarkRetro);

			}

		}

		logger.info("dealPostingReserve==refId==" + refId);

		// 5.deal posting
		if (businessModelList.size() > 0) {
			dealPostingReserve(businessModelList);
		}

		// 6.posting Accounting
		if (claimEntryVO.size() > 0) {
			dealPostingAccounting(claimEntryVO);
		}

	}

	public void dealPostingAccounting(List<ClaimEntryVO> claimEntryVO) throws Exception {
		// posting reserve
		logger.info("dealPostingAccounting====starttime=" + AppContext.getSysDate() + "=claimEntryVO="
				+ claimEntryVO.size());
		List<ClaimEntryVO>  claimEntryPosting = new ArrayList<>();
		for(ClaimEntryVO c:claimEntryVO ){
			if(sectionDao.getContractNatrueBySectionId(c.getSectionId()).equals("2")||
					sectionDao.getContractNatrueBySectionId(c.getSectionId())=="2"){
				//claimEntryVO.remove(c);
			}else{
				claimEntryPosting.add(c);
			}
		}
		
		if (claimEntryPosting.size() > 0) {
			try {
				accountingService.afterClaimSubmit(claimEntryPosting);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new Exception("Posting to accounting failed, please contact the accounting team! ");
			}

		}
 

		logger.info("dealPostingAccounting====endtime=" + AppContext.getSysDate());

	}

	@SuppressWarnings("deprecation")
	public void submitReserve(TRiClaimReserve reserve, String reserveUpdateRemark, String reserveUpdateRemarkRetro) {

		// reserve
		TRiClaimReserve reserveEntity = reserveConventor.changeReserve(reserve);
		if (reserveEntity.getBusinessDirection().equals(ClaimConstant.CLAIM_BUSINESS_DIRECTION__FINANCIAL)) {
			reserveEntity.setRemark(reserveUpdateRemark);
		} else if (reserve.getBusinessDirection().equals(ClaimConstant.CLAIM_BUSINESS_DIRECTION__RETROCESSION)) {
			reserveEntity.setRemark(reserveUpdateRemarkRetro);

		}

		// reserveLog
		TRiClaimReserveLog reserveLog = new TRiClaimReserveLog();
		reserveLog = reserveLogConverter.reserveToLog(reserve);
		if (reserve.getBusinessDirection().equals(ClaimConstant.CLAIM_BUSINESS_DIRECTION__FINANCIAL)) {
			reserveLog.setRemark(reserveUpdateRemark);
		} else if (reserve.getBusinessDirection().equals(ClaimConstant.CLAIM_BUSINESS_DIRECTION__RETROCESSION)) {
			reserveLog.setRemark(reserveUpdateRemarkRetro);
		}
		reserveLog.setTRiClaimReserve(reserveEntity);
		List<TRiClaimReserveLog> reserveLogList = new ArrayList<>();
		reserveLogList.add(reserveLog);
		reserveEntity.setTRiClaimReserveLogs(reserveLogList);

		reserveDao.merge(reserveEntity);
	}
}
