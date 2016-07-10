package com.ebao.ap99.accounting.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.ebao.ap99.parent.BaseEntityImpl;

/**
 * The persistent class for the T_RI_ACCOUNTING_IBNR_HISTORY database table.
 */
@Entity
@Table(name = "T_RI_ACCT_IBNR_INFO")
@NamedQuery(name = "TRiAcctIbnrInfo.findAll", query = "SELECT t FROM TRiAcctIbnrInfo t")
@NamedQueries({
	@NamedQuery(name = "TRiAcctIbnrInfo.findMaxDocumentIdBySegmentId", query = " select max(s.uploadId) FROM TRiAcctIbnrInfo s WHERE s.segmentId = :segmentId "),
	@NamedQuery(name = "TRiAcctIbnrInfo.findIBNRBySegmentIdAndUploadId", query = "  FROM TRiAcctIbnrInfo s WHERE s.segmentId = :segmentId and s.uploadId = :uploadId "),
	@NamedQuery(name = "TRiAcctIbnrInfo.findMaxDocumentIdBySegmentIdAndQuarter", query = " select max(s.uploadId) FROM TRiAcctIbnrInfo s WHERE s.segmentId = :segmentId and s.fnQuarter= :fnQuarter "),

})
public class TRiAcctIbnrInfo extends BaseEntityImpl implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
    private Long id;

    private BigDecimal amount;

    @Column(name = "FN_QUARTER")
    private String fnQuarter;
//
//    @Column(name = "FN_YEAR")
//    private String fnYear;

    @Column(name = "SEGMENT_ID")
    private Long segmentId;

    private String status;

    @Column(name = "UPLOAD_ID")
    private Long uploadId;

    @Column(name = "UW_YEAR")
    private String uwYear;

    public TRiAcctIbnrInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getFnQuarter() {
        return this.fnQuarter;
    }

    public void setFnQuarter(String fnQuarter) {
        this.fnQuarter = fnQuarter;
    }

//    public String getFnYear() {
//        return this.fnYear;
//    }
//
//    public void setFnYear(String fnYear) {
//        this.fnYear = fnYear;
//    }

    public Long getSegmentId() {
        return this.segmentId;
    }

    public void setSegmentId(Long segmentId) {
        this.segmentId = segmentId;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getUploadId() {
        return this.uploadId;
    }

    public void setUploadId(Long uploadId) {
        this.uploadId = uploadId;
    }

    public String getUwYear() {
        return this.uwYear;
    }

    public void setUwYear(String uwYear) {
        this.uwYear = uwYear;
    }

    @Override
    public Long getPrimaryKey() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setPrimaryKey(Long key) {
        // TODO Auto-generated method stub

    }

}
