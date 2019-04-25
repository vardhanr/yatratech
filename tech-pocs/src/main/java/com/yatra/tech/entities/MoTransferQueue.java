package com.yatra.tech.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mo_transfer_queue_06mar2018")
public class MoTransferQueue {

	@Id
	@Column(name = "super_pnr")
	private String superPnr;

	@Column(name = "payment_id")
	private Short paymentId;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "tenant_id")
	private Long tenantId;

	@Column(name = "ttid")
	private String ttid;

	@Column(name = "created_on")
	private Timestamp createdOn;

	@Column(name = "booking_xml")
	private String bookingXml;

	@Column(name = "mo_xml")
	private String moXml;

	@Column(name = "mo_transfer")
	private String moTransfer;

	@Column(name = "ylp_status")
	private String ylpStatus;

	@Column(name = "crosssell")
	private String crosssell;

	@Column(name = "repayment_pnr")
	private String repaymentPnr;

	public String getSuperPnr() {
		return superPnr;
	}

	public void setSuperPnr(String superPnr) {
		this.superPnr = superPnr;
	}

	public Short getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Short paymentId) {
		this.paymentId = paymentId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public String getTtid() {
		return ttid;
	}

	public void setTtid(String ttid) {
		this.ttid = ttid;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public String getBookingXml() {
		return bookingXml;
	}

	public void setBookingXml(String bookingXml) {
		this.bookingXml = bookingXml;
	}

	public String getMoXml() {
		return moXml;
	}

	public void setMoXml(String moXml) {
		this.moXml = moXml;
	}

	public String getMoTransfer() {
		return moTransfer;
	}

	public void setMoTransfer(String moTransfer) {
		this.moTransfer = moTransfer;
	}

	public String getYlpStatus() {
		return ylpStatus;
	}

	public void setYlpStatus(String ylpStatus) {
		this.ylpStatus = ylpStatus;
	}

	public String getCrosssell() {
		return crosssell;
	}

	public void setCrosssell(String crosssell) {
		this.crosssell = crosssell;
	}

	public String getRepaymentPnr() {
		return repaymentPnr;
	}

	public void setRepaymentPnr(String repaymentPnr) {
		this.repaymentPnr = repaymentPnr;
	}
}