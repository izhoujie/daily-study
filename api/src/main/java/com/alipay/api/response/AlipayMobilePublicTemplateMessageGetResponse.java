package com.alipay.api.response;

import com.alipay.api.internal.mapping.ApiField;

import com.alipay.api.AlipayResponse;

/**
 * ALIPAY API: alipay.mobile.public.template.message.get response.
 * 
 * @author auto create
 * @since 1.0, 2015-12-01 20:45:34
 */
public class AlipayMobilePublicTemplateMessageGetResponse extends AlipayResponse {

	private static final long serialVersionUID = 4689666749846617768L;

	/** 
	 * 模板内容
	 */
	@ApiField("template")
	private String template;

	public void setTemplate(String template) {
		this.template = template;
	}
	public String getTemplate( ) {
		return this.template;
	}

}
