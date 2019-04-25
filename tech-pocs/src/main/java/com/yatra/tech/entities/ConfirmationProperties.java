package com.yatra.tech.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "confirmation_properties")
public class ConfirmationProperties {
    
    @Id
    @GeneratedValue
    @Column(name = "confirmation_property_id")
    private long paymentPropertyId;
    
    @Column(name = "booking_id")
    private String bookingId;

    @Column(name = "super_pnr")
    private String superPnr;
    
    @Column(name = "property_name")
    private String propertyName;

    @Column(name = "property_value")
    private String propertyValue;

    @Column(name = "created_on")
    private Date createdOn;

    public long getPaymentPropertyId() {
        return paymentPropertyId;
    }

    public void setPaymentPropertyId(long paymentPropertyId) {
        this.paymentPropertyId = paymentPropertyId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getSuperPnr() {
        return superPnr;
    }

    public void setSuperPnr(String superPnr) {
        this.superPnr = superPnr;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
    
    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
    
}
