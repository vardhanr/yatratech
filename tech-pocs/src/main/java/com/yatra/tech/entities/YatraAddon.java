package com.yatra.tech.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This is model class for optional add ons.
 * 
 * @author 236628
 * 
 */
@Entity
@Table(name = "user_addon")
public class YatraAddon {
	
	@Id
	@GeneratedValue
	@Column(name = "ua_id")
	private Long uaId;
	
	@Column(name = "ttid")
	private String ttId;

	@Column(name = "tenant_id")
	private Short tenantId;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "addon_id")
	private Long addOnId;
	
	@Column(name = "addon_title")
	private String addonTitle;

	@Column(name = "created_on")
	private Timestamp createdOn;
	
	@Column(name = "amount")
	private Double amt;

	@Column(name = "addon_type")
	private String addOnType;
	
	@Column(name = "addon_label")
	private String addOnLabel;
	/**
	 * @return the uaId
	 */
	public Long getUaId() {
		return uaId;
	}

	/**
	 * @param uaId the uaId to set
	 */
	public void setUaId(Long uaId) {
		this.uaId = uaId;
	}	

	/**
	 * @return the tenantId
	 */
	public Short getTenantId() {
		return tenantId;
	}

	/**
	 * @param tenantId the tenantId to set
	 */
	public void setTenantId(Short tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the addOnId
	 */
	public Long getAddOnId() {
		return addOnId;
	}

	/**
	 * @param addOnId the addOnId to set
	 */
	public void setAddOnId(Long addOnId) {
		this.addOnId = addOnId;
	}

	/**
	 * @return the addonTitle
	 */
	public String getAddonTitle() {
		return addonTitle;
	}

	/**
	 * @param addonTitle the addonTitle to set
	 */
	public void setAddonTitle(String addonTitle) {
		this.addonTitle = addonTitle;
	}

	/**
	 * @return the createdOn
	 */
	public Timestamp getCreatedOn() {
		return createdOn;
	}

	/**
	 * @param createdOn the createdOn to set
	 */
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public String getTtId() {
		return ttId;
	}

	public void setTtId(String ttId) {
		this.ttId = ttId;
	}

	public Double getAmt() {
		return amt;
	}

	public void setAmt(double amt) {
		this.amt = amt;
	}

	public String getAddOnType() {
		return addOnType;
	}

	public void setAddOnType(String addOnType) {
		this.addOnType = addOnType;
	}

	public String getAddOnLabel() {
		return addOnLabel;
	}

	public void setAddOnLabel(String addOnLabel) {
		this.addOnLabel = addOnLabel;
	}

	
}
