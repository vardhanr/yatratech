package com.yatra.tech.dto;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.StandardToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseDTO {

    @Override
    public String toString() {
	StandardToStringStyle style = new StandardToStringStyle();
	style.setFieldSeparator(", ");
	style.setUseClassName(false);
	style.setUseIdentityHashCode(false);
	return new ReflectionToStringBuilder(this, style).toString();
    }
}
