package com.yatra.tech.dto;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ticketing_queue")
public class TicketingQueue extends BaseDTO {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "super_pnr")
	private String superPnr;

	@Column(name = "attempts")
	private Integer attempts;

	@Column(name = "supplier_codes")
	private String supplierCodes;

	@Column(name = "status")
	private String status;

	@Column(name = "created_on", updatable = false, insertable = false)
	private Timestamp createdOn;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSuperPnr() {
		return superPnr;
	}

	public void setSuperPnr(String superPnr) {
		this.superPnr = superPnr;
	}

	public Integer getAttempts() {
		return attempts;
	}

	public void setAttempts(Integer attempts) {
		this.attempts = attempts;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public String getSupplierCodes() {
		return supplierCodes;
	}

	public void setSupplierCodes(String supplierCodes) {
		this.supplierCodes = supplierCodes;
	}
}
