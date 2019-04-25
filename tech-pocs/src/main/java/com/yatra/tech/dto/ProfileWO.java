package com.yatra.tech.dto;

import java.util.List;

public class ProfileWO {

	private Long userId;
	private PersonWO personName;
	private Boolean offers;
	private String additionalProperties;
	private List<Languages> languages;
	private List<Hobbies> hobbies;
	private String createdOn;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public PersonWO getPersonName() {
		return personName;
	}

	public void setPersonName(PersonWO personName) {
		this.personName = personName;
	}

	public Boolean getOffers() {
		return offers;
	}

	public void setOffers(Boolean offers) {
		this.offers = offers;
	}

	public String getAdditionalProperties() {
		return additionalProperties;
	}

	public void setAdditionalProperties(String additionalProperties) {
		this.additionalProperties = additionalProperties;
	}

	public List<Languages> getLanguages() {
		return languages;
	}

	public void setLanguages(List<Languages> languages) {
		this.languages = languages;
	}

	public List<Hobbies> getHobbies() {
		return hobbies;
	}

	public void setHobbies(List<Hobbies> hobbies) {
		this.hobbies = hobbies;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public class Languages {
		private String language;

		public String getLanguage() {
			return language;
		}

		public void setLanguage(String language) {
			this.language = language;
		}

	}

	public class Hobbies {
		private String hobby;

		public String getHobby() {
			return hobby;
		}

		public void setHobby(String hobby) {
			this.hobby = hobby;
		}

	}
}
