package com.yatra.tech.entities;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * An entity for table pax_details.
 *
 * @author Sadiya Kazi
 *
 */
@Entity
@Table(name = "pax_detail")
public class PaxDetail {

	@Id
	@GeneratedValue
	@Column(name = "pd_id")
	private long pdId;

	@Column(name = "tenant_id")
	private Short tenantId;

	@Column(name = "payment_id")
	private String paymentId;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "pd_first_name")
	private String paxFirstName;

	@Column(name = "pd_middle_name")
	private String paxMiddleName;

	@Column(name = "pd_last_name")
	private String paxLastName;

	@Column(name = "pd_dob")
	private Date paxDOB;

	@Column(name = "pd_title")
	private String paxTitle;

	@Column(name = "pd_type")
	private String paxType;

	@Column(name = "pd_ppt_number")
	private String paxPptNumber;

	@Column(name = "pd_ticket_number")
	private String paxTicketNumber;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_on")
	private Date createdOn;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "updated_on")
	private Timestamp updatedOn;

	@Column(name = "pd_insured")
	private String paxInsured;

	@Column(name = "ip_policy_id")
	private String policyId;

	@Column(name = "super_pnr")
	private String superPnr;

	@Column(name = "ttid")
	private String ttid;

	@Column(name = "ip_policy_link")
	private String policyLink;

	@Column(name = "pax_insurance_id")
	private String paxInsuranceId;


	@Column(name = "meal_pref")
	private String mealPref;
	
	@Column(name = "seat_pref")
	private String seatPref;

	@Column(name = "meal_pref_onward")
	private String onwardMealOption;

	@Column(name = "meal_pref_return")
	private String returnMealOption;

	@Column(name = "baggage_pref_onward")
	private String onwardBaggageOption;

	@Column(name = "baggage_pref_return")
	private String returnBaggageOption;

	@Column(name = "other_option_onward")
	private String onwardOtherOption;
	
	@Column(name = "other_option_return")
	private String returnOtherOption;
	
	@Column(name = "pax_nationality")
	private String nationality;

	@Column(name = "issuing_country")
	private String issuingCountry;
	
	@Column(name = "nominee_name")
	private String nomineeName;
	
	@Column(name = "address")
	private String address;

	@Column(name = "passport_expiry_date")
	private Date passportExpiryDate;
	
	@Column(name = "is_leading_passenger")
	private int isLeadPassenger;
		
	@Column(name = "freq_flyer_airline")
	private String freqFlyerAirline;
	
	@Column(name = "freq_flyer_number")
	private String freqFlyerNumber;
	
	@Column(name="reporting_params")
	private String reportingParams;
	
	@Column(name="corp_client_id")
	private String corpClientId;
	
	@Column(name="seat_pref_onward")
	private String onwardSeatOption;
	
	@Column(name="seat_pref_return")
	private String returnSeatOption;
	
		
	public String getFreqFlyerNumber() {
		return freqFlyerNumber;
	}

	public void setFreqFlyerNumber(String freqFlyerNumber) {
		this.freqFlyerNumber = freqFlyerNumber;
	}

	public String getFreqFlyerAirline() {
		return freqFlyerAirline;
	}

	public void setFreqFlyerAirline(String freqFlyerAirline) {
		this.freqFlyerAirline = freqFlyerAirline;
	}


	public Date getPassportExpiryDate() {
		return passportExpiryDate;
	}

	public void setPassportExpiryDate(Date passportExpiryDate) {
		this.passportExpiryDate = passportExpiryDate;
	}

	public String getOnwardMealOption() {
		return onwardMealOption;
	}

	public void setOnwardMealOption(String onwardMealOption) {
		this.onwardMealOption = onwardMealOption;
	}

	public String getReturnMealOption() {
		return returnMealOption;
	}

	public void setReturnMealOption(String returnMealOption) {
		this.returnMealOption = returnMealOption;
	}

	public String getOnwardBaggageOption() {
		return onwardBaggageOption;
	}

	public void setOnwardBaggageOption(String onwardBaggageOption) {
		this.onwardBaggageOption = onwardBaggageOption;
	}

	public String getReturnBaggageOption() {
		return returnBaggageOption;
	}

	public void setReturnBaggageOption(String returnBaggageOption) {
		this.returnBaggageOption = returnBaggageOption;
	}


	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getIssuingCountry() {
		return issuingCountry;
	}

	public void setIssuingCountry(String issuingCountry) {
		this.issuingCountry = issuingCountry;
	}


	public String getMealPref() {
		return mealPref;
	}

	public void setMealPref(String mealPref) {
		this.mealPref = mealPref;
	}

	public long getPdId() {
		return pdId;
	}

	public void setPdId(long pdId) {
		this.pdId = pdId;
	}

	public Short getTenantId() {
		return tenantId;
	}

	public void setTenantId(Short tenantId) {
		this.tenantId = tenantId;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPaxTitle() {
		return paxTitle;
	}

	public void setPaxTitle(String paxTitle) {
		this.paxTitle = paxTitle;
	}

	public String getPaxType() {
		return paxType;
	}

	public void setPaxType(String paxType) {
		this.paxType = paxType;
	}

	public Date getPaxDOB() {
		return paxDOB;
	}

	public void setPaxDOB(Date paxDOB) {
		final Date newPaxDOB = new Date(paxDOB.getTime());
		this.paxDOB = newPaxDOB;
	}

	public String getPaxFirstName() {
		return paxFirstName;
	}

	public void setPaxFirstName(String paxFirstName) {
		this.paxFirstName = paxFirstName;
	}

	public String getPaxMiddleName() {
		return paxMiddleName;
	}

	public void setPaxMiddleName(String paxMiddleName) {
		this.paxMiddleName = paxMiddleName;
	}

	public String getPaxLastName() {
		return paxLastName;
	}

	public void setPaxLastName(String paxLastName) {
		this.paxLastName = paxLastName;
	}

	public String getPaxPptNumber() {
		return paxPptNumber;
	}

	public void setPaxPptNumber(String paxPptNumber) {
		this.paxPptNumber = paxPptNumber;
	}

	public String getPaxTicketNumber() {
		return paxTicketNumber;
	}

	public void setPaxTicketNumber(String paxTicketNumber) {
		this.paxTicketNumber = paxTicketNumber;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return new Date(createdOn.getTime());

	}

	public void setCreatedOn(Date createdOn) {
		final Date newCreatedOn = new Date(createdOn.getTime());
		this.createdOn = newCreatedOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	/**
	 * @return the paxInsured
	 */
	public String getPaxInsured() {
		return paxInsured;
	}

	/**
	 * @param paxInsured
	 *            the paxInsured to set
	 */
	public void setPaxInsured(String paxInsured) {
		this.paxInsured = paxInsured;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	/**
	 * @return the policyId
	 */
	public String getPolicyId() {
		return policyId;
	}

	/**
	 * @param policyId
	 *            the policyId to set
	 */
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	/**
	 * @return the superPnr
	 */
	public String getSuperPnr() {
		return superPnr;
	}

	/**
	 * @param superPnr
	 *            the superPnr to set
	 */
	public void setSuperPnr(String superPnr) {
		this.superPnr = superPnr;
	}

	/**
	 * @return the ttid
	 */
	public String getTtid() {
		return ttid;
	}

	/**
	 * @param ttid
	 *            the ttid to set
	 */
	public void setTtid(String ttid) {
		this.ttid = ttid;
	}

	public String getPolicyLink() {
		return policyLink;
	}

	public void setPolicyLink(String policyLink) {
		this.policyLink = policyLink;
	}

	public String getPaxInsuranceId() {
		return paxInsuranceId;
	}

	public void setPaxInsuranceId(String paxInsuranceId) {
		this.paxInsuranceId = paxInsuranceId;
	}

	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
			this.address = address;
	}
	
	public String getNomineeName() {
		return nomineeName;
	}
	
	public void setNomineeName(String nomineeName) {
		this.nomineeName = nomineeName;
	}
	
	public int isLeadPassenger() {
		return isLeadPassenger;
	}

	public void setLeadPassenger(int isLeadPassenger) {
		this.isLeadPassenger = isLeadPassenger;
	}
	
	public String getOnwardOtherOption() {
		return onwardOtherOption;
	}

	public void setOnwardOtherOption(String onwardOtherOption) {
		this.onwardOtherOption = onwardOtherOption;
	}

	public String getReturnOtherOption() {
		return returnOtherOption;
	}

	public void setReturnOtherOption(String returnOtherOption) {
		this.returnOtherOption = returnOtherOption;
	}

	public String getReportingParams() {
		return reportingParams;
	}

	public void setReportingParams(String reportingParams) {
		this.reportingParams = reportingParams;
	}
	public String getSeatPref() {
		return seatPref;
	}

	public void setSeatPref(String seatPref) {
		this.seatPref = seatPref;
	}

	public String getCorpClientId() {
		return corpClientId;
	}

	public void setCorpClientId(String corpClientId) {
		this.corpClientId = corpClientId;
	}

	public String getOnwardSeatOption() {
		return onwardSeatOption;
	}

	public void setOnwardSeatOption(String onwardSeatOption) {
		this.onwardSeatOption = onwardSeatOption;
	}

	public String getReturnSeatOption() {
		return returnSeatOption;
	}

	public void setReturnSeatOption(String returnSeatOption) {
		this.returnSeatOption = returnSeatOption;
	}
	
	
	
}
