package com.yatra.tech.entities;

//import com.mysql.jdbc.Blob;
import java.sql.Blob;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ticket_pdf")
public class TicketingPdf extends BaseEntity {

	    @Id
	    @GeneratedValue
	    @Column(name = "id")
	    private Long id;

	    @Column(name = "super_pnr")
	    private String superPnr;

	    @Column(name = "ttid")
	    private String ttid;

	    @Column(name = "email_id")
	    private String emailId;

	    @Column(name = "created_on")
	    private Timestamp createdOn;

	    @Column(name = "ticket")
	    private Blob ticket;

	    @Column(name="paxId")
	    private int paxId;

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

		public String getTtid() {
			return ttid;
		}

		public void setTtid(String ttid) {
			this.ttid = ttid;
		}

		public String getEmailId() {
			return emailId;
		}

		public void setEmailId(String emailId) {
			this.emailId = emailId;
		}

		public Timestamp getCreatedOn() {
			return createdOn;
		}

		public void setCreatedOn(Timestamp createdOn) {
			this.createdOn = createdOn;
		}

		public Blob getTicket() {
			return ticket;
		}

		public void setTicket(Blob ticket) {
			this.ticket = ticket;
		}

		public int getPaxId() {
			return paxId;
		}
		
		public void setPaxId(int paxId) {
			this.paxId = paxId;
		}


}
