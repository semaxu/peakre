package com.ebao.ap99.accounting.estimation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ebao.ap99.accounting.YearQuarter;
import com.ebao.ap99.accounting.constant.EstimateEntryEnum;
import com.ebao.ap99.accounting.constant.EstimateStatusEnum;
import com.ebao.unicorn.platform.foundation.utils.json.JSONUtils;

public class EstimateQuarter {
    private Long               estimateId;
    private Long               contId;
    private Long               contSectionId;
    private YearQuarter        yearQuarter;
    private int                quarterSeq;   //start from 1
    private EstimateStatusEnum quarterStatus;
    private String             currency;
    private Date               periodFrom;
    private Date               periodTo;

    private Map<String, EstimateItem> baseForecastItems = new HashMap<String, EstimateItem>();
    private Map<String, EstimateItem> forecastItems     = new HashMap<String, EstimateItem>();
    private Map<String, EstimateItem> estimateItems     = new HashMap<String, EstimateItem>();
    private Map<String, EstimateItem> reverseItems      = new HashMap<String, EstimateItem>();
    private Map<String, EstimateItem> actualItems       = new HashMap<String, EstimateItem>();

    public String printItem() {
        StringBuilder sb = new StringBuilder();
        sb.append(yearQuarter + ":").append(JSONUtils.toJSON(this));
        return sb.toString();
    }

    public Long getEstimateId() {
        return estimateId;
    }

    public void setEstimateId(Long estimateId) {
        this.estimateId = estimateId;
    }

    public Long getContId() {
        return contId;
    }

    public void setContId(Long contId) {
        this.contId = contId;
    }

    public Long getContSectionId() {
        return contSectionId;
    }

    public void setContSectionId(Long contSectionId) {
        this.contSectionId = contSectionId;
    }

    public int getYear() {
        return this.yearQuarter.getYear();
    }

    public int getQuarter() {
        return this.yearQuarter.getQuarter();
    }

    public int getQuarterSeq() {
        return quarterSeq;
    }

    public void setQuarterSeq(int quarterSeq) {
        this.quarterSeq = quarterSeq;
    }

    public YearQuarter getYearQuarter() {
        return yearQuarter;
    }

    public void setYearQuarter(YearQuarter yearQuarter) {
        this.yearQuarter = yearQuarter;
    }

    public EstimateStatusEnum getQuarterStatus() {
        return quarterStatus;
    }

    public void setQuarterStatus(EstimateStatusEnum quarterStatus) {
        this.quarterStatus = quarterStatus;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Map<String, EstimateItem> getBaseForecastItems() {
        return baseForecastItems;
    }

    public void setBaseForecastItems(Map<String, EstimateItem> baseForecastItems) {
        this.baseForecastItems = baseForecastItems;
    }

    public Map<String, EstimateItem> getForecastItems() {
        return forecastItems;
    }

    public void setForecastItems(Map<String, EstimateItem> forecastItems) {
        this.forecastItems = forecastItems;
    }

    public Map<String, EstimateItem> getEstimateItems() {
        return estimateItems;
    }

    public void setEstimateItems(Map<String, EstimateItem> estimateItems) {
        this.estimateItems = estimateItems;
    }

    public Map<String, EstimateItem> getReverseItems() {
        return reverseItems;
    }

    public void setReverseItems(Map<String, EstimateItem> reverseItems) {
        this.reverseItems = reverseItems;
    }

    public Map<String, EstimateItem> getActualItems() {
        return actualItems;
    }

    public void setActualItems(Map<String, EstimateItem> actualItems) {
        this.actualItems = actualItems;
    }

    public Map<String, EstimateItem> getEstMap(EstimateStatusEnum status) {
        //return map by status
        switch (status) {
            case BaseForecast:
                return this.baseForecastItems;
            case Forecast:
                return this.forecastItems;
            case Estimated:
                return this.estimateItems;
            case Reversed:
                return this.reverseItems;
            case Actual:
                return this.actualItems;
            default:
                return null;
        }
    }

    public EstimateItem getEstItemByEntryCode(String entryCode, EstimateStatusEnum status) {
        Map<String, EstimateItem> m = this.getEstMap(status);
        EstimateItem estimateItem = m.get(entryCode);
        if (estimateItem == null) {
            estimateItem = new EstimateItem();
            estimateItem.setEntryCode(entryCode);
            estimateItem.setStatus(status);
            estimateItem.setAmount(BigDecimal.ZERO);
            m.put(entryCode, estimateItem);
        }
        return estimateItem;
    }

    public BigDecimal getLatestAmount(String entryCode) {
        BigDecimal latestAmount = BigDecimal.ZERO;
        //EP doesn't generate Estimation, So always get lastestAmount of EP from ForecastItems
        if (quarterStatus == EstimateStatusEnum.Forecast || quarterStatus == EstimateStatusEnum.Upr
                || EstimateEntryEnum.EP_Closing.getEntryCode().equals(entryCode)) {
            EstimateItem forecastItem = forecastItems.get(entryCode);
            if (forecastItem != null) {
                latestAmount = latestAmount.add(forecastItem.getAmount());
            }
        } else {
            EstimateItem estimateItem = estimateItems.get(entryCode);
            EstimateItem reverseItem = reverseItems.get(entryCode);
            EstimateItem actualItem = actualItems.get(entryCode);
            if (estimateItem != null) {
                latestAmount = latestAmount.add(estimateItem.getAmount());
            }
            if (reverseItem != null) {
                latestAmount = latestAmount.add(reverseItem.getAmount());
            }
            if (actualItem != null) {
                latestAmount = latestAmount.add(actualItem.getAmount());
            }
        }
        return latestAmount;
    }

    private List<String> forecastItemsOfLossReserve = new ArrayList<String>(
            Arrays.asList(EstimateEntryEnum.LossReserve_Opening.getEntryCode(),
                    EstimateEntryEnum.LossReserve_Closing.getEntryCode()));

    private List<String> actualItemsOfLossReserve = new ArrayList<String>(Arrays.asList(
            EstimateEntryEnum.LossPaid.getEntryCode(), EstimateEntryEnum.LAEPaid.getEntryCode(),
            EstimateEntryEnum.CashCallPayment.getEntryCode(), EstimateEntryEnum.LossReserve_Opening.getEntryCode(),
            EstimateEntryEnum.LossReserve_Closing.getEntryCode(), EstimateEntryEnum.LAEReserve_Opening.getEntryCode(),
            EstimateEntryEnum.LAEReserve_Closing.getEntryCode()));

    public BigDecimal getForecastOfLossReserve() {
        BigDecimal latestAmount = BigDecimal.ZERO;
        for (String entryCode : forecastItemsOfLossReserve) {
            EstimateItem forecastItem = forecastItems.get(entryCode);
            if (forecastItem != null) {
                if (isOpeningItem(entryCode)) {
                    latestAmount = latestAmount.add(forecastItem.getAmount().negate());
                } else {
                    latestAmount = latestAmount.add(forecastItem.getAmount());
                }
            }
        }
        return latestAmount;
    }

    public BigDecimal getEstimateAndReversalOfLossReserve() {
        BigDecimal latestAmount = BigDecimal.ZERO;
        for (String entryCode : forecastItemsOfLossReserve) {
            EstimateItem estimateItem = estimateItems.get(entryCode);
            EstimateItem reverseItem = reverseItems.get(entryCode);
            if (estimateItem != null) {
                if (isOpeningItem(entryCode)) {
                    latestAmount = latestAmount.add(estimateItem.getAmount().negate());
                } else {
                    latestAmount = latestAmount.add(estimateItem.getAmount());
                }
            }
            if (reverseItem != null) {
                if (isOpeningItem(entryCode)) {
                    latestAmount = latestAmount.add(reverseItem.getAmount().negate());
                } else {
                    latestAmount = latestAmount.add(reverseItem.getAmount());
                }
            }
        }
        return latestAmount;
    }

    public BigDecimal getActualOfLossReserve() {
        BigDecimal latestAmount = BigDecimal.ZERO;
        for (String entryCode : actualItemsOfLossReserve) {
            EstimateItem actualItem = actualItems.get(entryCode);
            if (actualItem != null) {
                if (isOpeningItem(entryCode)) {
                    latestAmount = latestAmount.add(actualItem.getAmount().negate());
                } else {
                    latestAmount = latestAmount.add(actualItem.getAmount());
                }
            }
        }
        return latestAmount;
    }

    private boolean isOpeningItem(String entryCode) {
        return EstimateEntryEnum.LossReserve_Opening.getEntryCode().equals(entryCode)
                || EstimateEntryEnum.LAEReserve_Opening.getEntryCode().equals(entryCode);
    }

    public void setEstItemByEntryCode(String entryCode, EstimateItem estItem, EstimateStatusEnum status) {
        this.getEstMap(status).put(entryCode, estItem);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof EstimateQuarter) {
            EstimateQuarter other = (EstimateQuarter) obj;
            if (other.contSectionId.equals(this.contSectionId) && other.yearQuarter.equals(this.yearQuarter)) {
                return true;
            }
        }
        return false;
    }

    public boolean isClosedQuarter() {
        return this.getQuarterStatus() == EstimateStatusEnum.Estimating
                || this.getQuarterStatus() == EstimateStatusEnum.Estimated
                || this.getQuarterStatus() == EstimateStatusEnum.Reversed
                || this.getQuarterStatus() == EstimateStatusEnum.Actual;
    }

    public boolean isOpenQuarter() {
        return this.getQuarterStatus() == EstimateStatusEnum.Forecast;
    }

    public boolean isUPRQuarter() {
        return this.getQuarterStatus() == EstimateStatusEnum.Upr;
    }

    public Date getPeriodFrom() {
        return periodFrom;
    }

    public void setPeriodFrom(Date periodFrom) {
        this.periodFrom = periodFrom;
    }

    public Date getPeriodTo() {
        return periodTo;
    }

    public void setPeriodTo(Date periodTo) {
        this.periodTo = periodTo;
    }
}
