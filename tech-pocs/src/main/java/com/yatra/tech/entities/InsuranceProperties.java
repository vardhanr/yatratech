package com.yatra.tech.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "insurance_properties")
public class InsuranceProperties {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long Id;

	@Column(name = "super_pnr")
	private String superPnr;

	@Column(name = "property_name")
	private String propertyName;

	@Column(name = "property_value")
	private String propertyValue;

	@Column(name = "created_on")
	private Timestamp createdOn;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getSuperPnr() {
		return superPnr;
	}

	public void setSuperPnr(String superPnr) {
		this.superPnr = superPnr;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

}