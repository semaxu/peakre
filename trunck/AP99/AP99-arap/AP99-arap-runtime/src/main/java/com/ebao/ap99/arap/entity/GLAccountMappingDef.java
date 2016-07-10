package com.ebao.ap99.arap.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.jsoup.helper.StringUtil;

/**
 * The persistent class for the T_RI_GL_ACCOUNT_MAPPING_DEF database table.
 */
@Entity
@Table(name = "T_RI_GL_ACCOUNT_MAPPING_DEF")
@NamedQuery(name = "GLAccountMappingDef.findAll", query = "SELECT g FROM GLAccountMappingDef g")
@NamedQueries({
        @NamedQuery(name = "GLAccountMappingDef.findByEntryCodeAndCashBalanceType", query = " FROM GLAccountMappingDef c WHERE c.entryCode = :entryCode and c.cashBalanceType = :cashBalanceType"),
        @NamedQuery(name = "GLAccountMappingDef.findByAccountNoAndTransType", query = " FROM GLAccountMappingDef c WHERE c.glAccountNo = :glAccountNo and c.glAccountType = :glAccountType and c.mainTransaction = :mainTransaction and c.subTransaction = :subTransaction") })
public class GLAccountMappingDef implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
    @Column(name = "MAPPING_ID")
    private Long mappingId;

    @Column(name = "CONTRACT_CATE")
    private Integer contractCate;

    @Column(name = "CURRENCY_CODE")
    private String currencyCode;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ENTRY_CODE")
    private String entryCode;

    @Column(name = "GL_ACCOUNT_NO")
    private String glAccountNo;

    @Column(name = "GL_ACCOUNT_TYPE")
    private String glAccountType;

    @Column(name = "MAIN_TRANSACTION")
    private String mainTransaction;

    @Column(name = "SUB_TRANSACTION")
    private String subTransaction;

    @Column(name = "CASH_BALANCE_TYPE")
    private Integer cashBalanceType;

    @Column(name = "NEED_POST")
    private Integer needPost;
    
    @Column(name = "OTHER_FACTORS")
    private String otherFactors;

    public GLAccountMappingDef() {
    }

    public Long getMappingId() {
        return mappingId;
    }

    public void setMappingId(Long mappingId) {
        this.mappingId = mappingId;
    }

    public Integer getContractCate() {
        return contractCate;
    }

    public void setContractCate(Integer contractCate) {
        this.contractCate = contractCate;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEntryCode() {
        return this.entryCode;
    }

    public void setEntryCode(String entryCode) {
        this.entryCode = entryCode;
    }

    public String getGlAccountNo() {
        return this.glAccountNo;
    }

    public void setGlAccountNo(String glAccountNo) {
        this.glAccountNo = glAccountNo;
    }

    public String getGlAccountType() {
        return this.glAccountType;
    }

    public void setGlAccountType(String glAccountType) {
        this.glAccountType = glAccountType;
    }

    public String getMainTransaction() {
        return this.mainTransaction;
    }

    public void setMainTransaction(String mainTransaction) {
        this.mainTransaction = mainTransaction;
    }
	
    public void setSubTransaction(String subTransaction) {
        this.subTransaction = subTransaction;
    }
    
    public Integer getCashBalanceType() {
		return cashBalanceType;
	}

	public void setCashBalanceType(Integer cashBalanceType) {
		this.cashBalanceType = cashBalanceType;
	}

	public Integer getNeedPost() {
        return needPost;
    }

    public void setNeedPost(Integer needPost) {
        this.needPost = needPost;
    }

	public String getSubTransaction() {
		return subTransaction;
	}

	public String getOtherFactors() {
		return otherFactors;
	}

	public void setOtherFactors(String otherFactors) {
		this.otherFactors = otherFactors;
	}
	
	public Map<String, String> getOtherFactorMap(){
		if(!StringUtil.isBlank(this.getOtherFactors())){
			Map<String, String> factorMap = new HashMap<String, String>();
			String rex = "\\s*\\w+\\s*:\\s*\\w+\\s*";
			Pattern pattern = Pattern.compile(rex);
			
			Matcher matcher = pattern.matcher(getOtherFactors());
			while(matcher.find()){
				String[] kv = matcher.group().split(":");
				factorMap.put(kv[0].trim(), kv[1].trim());
			}
			return factorMap;
		}
		return null;
	}
}
