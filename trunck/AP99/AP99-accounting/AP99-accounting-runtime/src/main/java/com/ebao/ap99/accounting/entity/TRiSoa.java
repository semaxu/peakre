package com.ebao.ap99.accounting.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the T_RI_SOA database table.
 */
@Entity
@Table(name = "T_RI_SOA")
@NamedQuery(name = "TRiSoa.findAll", query = "SELECT t FROM TRiSoa t")
public class TRiSoa extends com.ebao.ap99.parent.BaseEntityImpl implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
    @Column(name = "SOA_ID")
    private Long soaId;

    @Column(name = "SOA_NUMBER", updatable = false)
    private String soaNumber;

    @Column(name = "ACCOUNT_LEVEL")
    private String accountLevel;

    @Column(name = "ACCOUNT_TYPE")
    private String accountType;

    @Column(name = "BIZ_TYPE")
    private String bizType;

    private String broke;

    private String cedent;

    @Column(name = "CEDENT_QUARTER")
    private Integer cedentQuarter;

    @Column(name = "CEDENT_YEAR")
    private Integer cedentYear;

    @Column(name = "FINANCIAL_QUARTER")
    private Integer financialQuarter;

    @Column(name = "TASK_RELEASER")
    private String taskreleaser;

    @Column(name = "TASK_CREATOR")
    private String taskcreator;

    @Column(name = "TASK_WITHDRAW")
    private String takswithdraw;

    @Column(name = "TASK_RELEASER_NAME")
    private String taskreleaserName;

    @Column(name = "TASK_CREATOR_NAME")
    private String taskcreatorName;

    @Column(name = "TASK_WITHDRAW_NAME")
    private String takswithdrawName;

    @Column(name = "FINANCIAL_YEAR")
    private Integer financialYear;

    @Column(name = "contract_code")
    private String contractCode;

    @Column(name = "contract_id")
    private Long contractId;

    @Column(name = "contract_name")
    private String contractName;

    @Temporal(TemporalType.DATE)
    @Column(name = "DUE_DATE")
    private Date dueDate;

    @Column(name = "ENTRY_TASK")
    private String entryTask;

    @Temporal(TemporalType.DATE)
    @Column(name = "RECEIVED_DATE")
    private Date receivedDate;

    private String remarks;

    @Column(name = "REVIEWED_BY")
    private String reviewedBy;

    @Column(name = "REVIEWED_FLAG")
    private String reviewedFlag;

    @Column(name = "SOA_STATUS")
    private String soaStatus;

    @Column(name = "SOA_TEXT")
    private String soaText;

    @Column(name = "SOA_LATEST_TEXT")
    private String soaLatestText;

    @Column(name = "UW_YEAR")
    private Integer uwYear;

    @Column(name = "WITHDRAW_IGNORING_CUTOFF_DATE")
    @org.hibernate.annotations.Type(type = "yes_no")
    private boolean withdrawIgnoringCutoffDate;

    @Column(name = "IS_REVERSAL")
    @org.hibernate.annotations.Type(type = "yes_no")
    private boolean isReversal;

    @Column(name = "STATEMENT_TYPE")
    private String statementType;

    @Column(name = "CEDENT_PERIOD")
    private Integer cedentPeriod;

    //bi-directional many-to-one association to TRiSoaCurrency
    @OneToMany(mappedBy = "TRiSoa", cascade = { CascadeType.ALL })
    private List<TRiSoaCurrency> TRiSoaCurrencies = new ArrayList<TRiSoaCurrency>();

    //bi-directional many-to-one association to TRiSoaSection
    @OneToMany(mappedBy = "TRiSoa", cascade = { CascadeType.ALL })
    private List<TRiSoaSection> TRiSoaSections = new ArrayList<TRiSoaSection>();

    public TRiSoa() {
    }

    public Long getSoaId() {
        return this.soaId;
    }

    public void setSoaId(Long soaId) {
        this.soaId = soaId;
    }

    public String getBroke() {
        return this.broke;
    }

    public void setBroke(String broke) {
        this.broke = broke;
    }

    public String getCedent() {
        return this.cedent;
    }

    public void setCedent(String cedent) {
        this.cedent = cedent;
    }

    public Date getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getReceivedDate() {
        return this.receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getReviewedBy() {
        return this.reviewedBy;
    }

    public void setReviewedBy(String reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public String getSoaText() {
        return this.soaText;
    }

    public void setSoaText(String soaText) {
        this.soaText = soaText;
    }

    public List<TRiSoaCurrency> getTRiSoaCurrencies() {
        return this.TRiSoaCurrencies;
    }

    public void setTRiSoaCurrencies(List<TRiSoaCurrency> TRiSoaCurrencies) {
        this.TRiSoaCurrencies = TRiSoaCurrencies;
    }

    public TRiSoaCurrency addTRiSoaCurrency(TRiSoaCurrency TRiSoaCurrency) {
        if (getTRiSoaCurrencies().isEmpty()) {
            setTRiSoaCurrencies(new ArrayList<TRiSoaCurrency>());
        }
        getTRiSoaCurrencies().add(TRiSoaCurrency);
        TRiSoaCurrency.setTRiSoa(this);
        return TRiSoaCurrency;
    }

    public TRiSoaCurrency removeTRiSoaCurrency(TRiSoaCurrency TRiSoaCurrency) {
        if (getTRiSoaCurrencies().isEmpty()) {
            setTRiSoaCurrencies(new ArrayList<TRiSoaCurrency>());
        }
        getTRiSoaCurrencies().remove(TRiSoaCurrency);
        TRiSoaCurrency.setTRiSoa(null);
        return TRiSoaCurrency;
    }

    public List<TRiSoaSection> getTRiSoaSections() {
        return this.TRiSoaSections;
    }

    public void setTRiSoaSections(List<TRiSoaSection> TRiSoaSections) {
        this.TRiSoaSections = TRiSoaSections;
    }

    public TRiSoaSection addTRiSoaSection(TRiSoaSection TRiSoaSection) {
        getTRiSoaSections().add(TRiSoaSection);
        TRiSoaSection.setTRiSoa(this);

        return TRiSoaSection;
    }

    public TRiSoaSection removeTRiSoaSection(TRiSoaSection TRiSoaSection) {
        getTRiSoaSections().remove(TRiSoaSection);
        TRiSoaSection.setTRiSoa(null);

        return TRiSoaSection;
    }

    public String getAccountLevel() {
        return accountLevel;
    }

    public void setAccountLevel(String accountLevel) {
        this.accountLevel = accountLevel;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public Integer getCedentQuarter() {
        return cedentQuarter;
    }

    public void setCedentQuarter(Integer cedentQuarter) {
        this.cedentQuarter = cedentQuarter;
    }

    public Integer getCedentYear() {
        return cedentYear;
    }

    public void setCedentYear(Integer cedentYear) {
        this.cedentYear = cedentYear;
    }

    public Integer getFinancialQuarter() {
        return financialQuarter;
    }

    public void setFinancialQuarter(Integer financialQuarter) {
        this.financialQuarter = financialQuarter;
    }

    public String getTaskreleaser() {
        return taskreleaser;
    }

    public void setTaskreleaser(String taskreleaser) {
        this.taskreleaser = taskreleaser;
    }

    public String getTaskcreator() {
        return taskcreator;
    }

    public void setTaskcreator(String taskcreator) {
        this.taskcreator = taskcreator;
    }

    public Integer getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(Integer financialYear) {
        this.financialYear = financialYear;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getEntryTask() {
        return entryTask;
    }

    public void setEntryTask(String entryTask) {
        this.entryTask = entryTask;
    }

    public String getReviewedFlag() {
        return reviewedFlag;
    }

    public void setReviewedFlag(String reviewedFlag) {
        this.reviewedFlag = reviewedFlag;
    }

    public String getSoaStatus() {
        return soaStatus;
    }

    public void setSoaStatus(String soaStatus) {
        this.soaStatus = soaStatus;
    }

    public boolean isWithdrawIgnoringCutoffDate() {
        return withdrawIgnoringCutoffDate;
    }

    public void setWithdrawIgnoringCutoffDate(boolean withdrawIgnoringCutoffDate) {
        this.withdrawIgnoringCutoffDate = withdrawIgnoringCutoffDate;
    }

    public String getSoaNumber() {
        return soaNumber;
    }

    public void setSoaNumber(String soaNumber) {
        this.soaNumber = soaNumber;
    }

    public String getTakswithdraw() {
        return takswithdraw;
    }

    public void setTakswithdraw(String takswithdraw) {
        this.takswithdraw = takswithdraw;
    }

    public String getTaskreleaserName() {
        return taskreleaserName;
    }

    public void setTaskreleaserName(String taskreleaserName) {
        this.taskreleaserName = taskreleaserName;
    }

    public String getTaskcreatorName() {
        return taskcreatorName;
    }

    public void setTaskcreatorName(String taskcreatorName) {
        this.taskcreatorName = taskcreatorName;
    }

    public String getTakswithdrawName() {
        return takswithdrawName;
    }

    public void setTakswithdrawName(String takswithdrawName) {
        this.takswithdrawName = takswithdrawName;
    }

    public Integer getUwYear() {
        return uwYear;
    }

    public void setUwYear(Integer uwYear) {
        this.uwYear = uwYear;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public boolean isReversal() {
        return isReversal;
    }

    public void setReversal(boolean isReversal) {
        this.isReversal = isReversal;
    }

    public String getStatementType() {
        return statementType;
    }

    public void setStatementType(String statementType) {
        this.statementType = statementType;
    }

    public Integer getCedentPeriod() {
        return cedentPeriod;
    }

    public void setCedentPeriod(Integer cedentPeriod) {
        this.cedentPeriod = cedentPeriod;
    }

    public String getSoaLatestText() {
        return soaLatestText;
    }

    public void setSoaLatestText(String soaLatestText) {
        this.soaLatestText = soaLatestText;
    }

    @Override
    public Long getPrimaryKey() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setPrimaryKey(Long paramLong) {
        // TODO Auto-generated method stub

    }

}
