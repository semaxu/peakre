package com.ebao.ap99.accounting.estimation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import com.ebao.ap99.accounting.YearQuarter;
import com.ebao.ap99.accounting.constant.EstimateStatusEnum;

public class EstimateModel {
    private Long                  contractId;
    private Long                  sectionId;
    private BigDecimal            lossRatio;
    private BigDecimal            totalExpenseRate;
    private String                currency;
    private List<EstimateQuarter> estQuarter;
    private Date                  periodStart;
    private Date                  periodEnd;

    public EstimateModel(Date periodStart, Date periodEnd, String currency) throws Exception {
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.currency = currency;
        initializeQuarter();
    }

    public EstimateModel(List<EstimateQuarter> estQuarter) {
        super();
        this.estQuarter = estQuarter;
    }

    public EstimateQuarter getEsitmateQuarter(YearQuarter q) {
        for (EstimateQuarter estimateQuarter : this.getEstQuarter()) {
            if (estimateQuarter.getYearQuarter().equals(q))
                return estimateQuarter;
        }
        return null;
    }

    public void initializeQuarter() throws Exception {
        List<YearQuarter> qList = YearQuarter.calcQuarterByPOI(periodStart, periodEnd);
        int i = 1;
        estQuarter = new ArrayList<EstimateQuarter>();
        for (YearQuarter q : qList) {
            EstimateQuarter estimateQuarter = new EstimateQuarter();
            estimateQuarter.setYearQuarter(q);
            estimateQuarter.setPeriodFrom(q.getPeriodFrom());
            estimateQuarter.setPeriodTo(q.getPeriodTo());
            estimateQuarter.setCurrency(this.currency);
            estimateQuarter.setQuarterStatus(EstimateStatusEnum.Forecast);
            estimateQuarter.setQuarterSeq(i++);
            estQuarter.add(estimateQuarter);
        }
    }

    public void addUPRQuarter() throws Exception {
        EstimateQuarter lastQuarter = estQuarter.get(estQuarter.size() - 1);
        EstimateQuarter estimateQuarter = new EstimateQuarter();
        estimateQuarter.setYearQuarter(lastQuarter.getYearQuarter().nextQuarter());
        estimateQuarter.setCurrency(this.currency);
        Date periodFrom = DateUtils.addMonths(lastQuarter.getPeriodFrom(), 3);
        Date periodTo = DateUtils.addMonths(periodFrom, 3);
        periodTo = DateUtils.addDays(periodTo, -1);
        estimateQuarter.setPeriodFrom(periodFrom);
        estimateQuarter.setPeriodTo(periodTo);

        estimateQuarter.setQuarterStatus(EstimateStatusEnum.Upr);
        estimateQuarter.setQuarterSeq(estQuarter.size() + 1);
        estQuarter.add(estimateQuarter);
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public BigDecimal getLossRatio() {
        return lossRatio;
    }

    public void setLossRatio(BigDecimal lossRatio) {
        this.lossRatio = lossRatio;
    }

    public BigDecimal getTotalExpenseRate() {
        return totalExpenseRate;
    }

    public void setTotalExpenseRate(BigDecimal totalExpenseRate) {
        this.totalExpenseRate = totalExpenseRate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<EstimateQuarter> getEstQuarter() {
        return estQuarter;
    }

    public void setEstQuarter(List<EstimateQuarter> estQuarter) {
        this.estQuarter = estQuarter;
    }

    public Date getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(Date periodStart) {
        this.periodStart = periodStart;
    }

    public Date getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(Date periodEnd) {
        this.periodEnd = periodEnd;
    }

}
