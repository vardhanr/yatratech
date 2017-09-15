package com.yatra.tech.dto;

import java.util.List;

public class AddPaxResponse extends BaseUserProfileDTO {

	private List<Long> result;

	public List<Long> getResult() {
		return result;
	}

	public void setResult(List<Long> result) {
		this.result = result;
	}

}
