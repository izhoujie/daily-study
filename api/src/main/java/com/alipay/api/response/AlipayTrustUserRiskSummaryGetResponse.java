package com.alipay.api.response;

import com.alipay.api.internal.mapping.ApiField;

import com.alipay.api.AlipayResponse;

/**
 * ALIPAY API: alipay.trust.user.risk.summary.get response.
 * 
 * @author auto create
 * @since 1.0, 2015-07-02 17:18:11
 */
public class AlipayTrustUserRiskSummaryGetResponse extends AlipayResponse {

	private static final long serialVersionUID = 3517882185258314386L;

	/** 
	 * 是否在风险名单
	 */
	@ApiField("risk_code")
	private String riskCode;

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}
	public String getRiskCode( ) {
		return this.riskCode;
	}

}
