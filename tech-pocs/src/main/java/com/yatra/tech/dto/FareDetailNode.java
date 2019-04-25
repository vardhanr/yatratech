package com.yatra.tech.dto;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;

public class FareDetailNode extends BaseDTO {

    @JsonProperty(value = "O")
    private Map<String, Map<String, PaxFareNode>> o;
    private Integer markupId;

    public Integer getMarkupId() {
	return markupId;
    }

    public void setMarkupId(Integer markupId) {
	this.markupId = markupId;
    }

    public Map<String, Map<String, PaxFareNode>> getO() {
	return o;
    }

    public void setO(Map<String, Map<String, PaxFareNode>> o) {
	this.o = o;
    }
}
