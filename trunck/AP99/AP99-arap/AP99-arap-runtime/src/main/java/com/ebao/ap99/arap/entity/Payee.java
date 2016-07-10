package com.ebao.ap99.arap.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.ebao.unicorn.platform.data.domain.BaseEntityImpl;


/**
 * The persistent class for the T_RI_BCP_PAYEE database table.
 * 
 */
@Entity
@Table(name="T_RI_BCP_PAYEE")
@NamedQuery(name="Payee.findAll", query="SELECT p FROM Payee p")
@NamedQueries({
	@NamedQuery(name = "Payee.findByPaymentId", query = " FROM Payee c WHERE c.paymentId = :paymentId")
})
public class Payee extends BaseEntityImpl {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
	@Column(name="PAYEE_ID")
	private Long payeeId;

	@Column(name="AMOUNT")
	private BigDecimal amount;

	@Column(name="CURRENCY_CODE")
	private String currencyCode;

	@Column(name="PARTNER_CODE")
	private String partnerCode;

	@Column(name="PAYMENT_ID")
	private Long paymentId;

	public Payee() {
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrencyCode() {
		return this.currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Long getPayeeId() {
		return payeeId;
	}
	
	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	public void setPayeeId(Long payeeId) {
		this.payeeId = payeeId;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	@Override
	public Long getPrimaryKey() {
		return this.getPayeeId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.setPayeeId(key);
	}
	
}