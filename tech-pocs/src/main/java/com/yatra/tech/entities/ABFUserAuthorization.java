package com.yatra.tech.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "abf_user_authorization")
public class ABFUserAuthorization extends BaseEntity {

	@Column(name = "user_id")
	private String userId;
	@Column(name = "authorization")
	private String authorization;

	public String getAuthorization() {
		return authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
