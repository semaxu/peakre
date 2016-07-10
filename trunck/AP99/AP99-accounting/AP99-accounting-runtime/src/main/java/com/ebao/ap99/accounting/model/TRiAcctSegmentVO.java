/**
 * 
 */
package com.ebao.ap99.accounting.model;

/**
 * @author xiaoyu.yang
 */
public class TRiAcctSegmentVO {

    private String segmentId;

    private String cob;

    private String segmentCode;

    private String contractNature;

    private String segmentName;

    private Integer status;

    private String cedentCountry;

    private String uwYear;

    private int pageIndex;

    private int countPerPage;

    public String getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(String segmentId) {
        this.segmentId = segmentId;
    }

    public String getCob() {
        return cob;
    }

    public void setCob(String cob) {
        this.cob = cob;
    }

    public String getSegmentCode() {
        return segmentCode;
    }

    public void setSegmentCode(String segmentCode) {
        this.segmentCode = segmentCode;
    }

    public String getContractNature() {
        return contractNature;
    }

    public void setContractNature(String contractNature) {
        this.contractNature = contractNature;
    }

    public String getSegmentName() {
        return segmentName;
    }

    public void setSegmentName(String segmentName) {
        this.segmentName = segmentName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCedentCountry() {
        return cedentCountry;
    }

    public void setCedentCountry(String cedentCountry) {
        this.cedentCountry = cedentCountry;
    }

    public String getUwYear() {
        return uwYear;
    }

    public void setUwYear(String uwYear) {
        this.uwYear = uwYear;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getCountPerPage() {
        return countPerPage;
    }

    public void setCountPerPage(int countPerPage) {
        this.countPerPage = countPerPage;
    }

}
