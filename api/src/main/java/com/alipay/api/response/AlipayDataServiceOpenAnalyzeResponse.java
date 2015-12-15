package com.alipay.api.response;

import com.alipay.api.internal.mapping.ApiField;
import com.alipay.api.domain.AlipayDataServiceResult;

import com.alipay.api.AlipayResponse;

/**
 * ALIPAY API: alipay.data.service.open.analyze response.
 * 
 * @author auto create
 * @since 1.0, 2015-11-09 11:44:04
 */
public class AlipayDataServiceOpenAnalyzeResponse extends AlipayResponse {

	private static final long serialVersionUID = 1622791739133634159L;

	/** 
	 * 调用返回
	 */
	@ApiField("result")
	private AlipayDataServiceResult result;

	public void setResult(AlipayDataServiceResult result) {
		this.result = result;
	}
	public AlipayDataServiceResult getResult( ) {
		return this.result;
	}

}
