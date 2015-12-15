package com.alipay.api.domain;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;

/**
 * 通过该接口可以查询商户录入的所有商品编号
 *
 * @author auto create
 * @since 1.0, 2015-10-30 10:11:28
 */
public class AlipayOfflineMarketProductBatchqueryModel extends AlipayObject {

	private static final long serialVersionUID = 7559345396825265566L;

	/**
	 * 页码，留空标示第一页，默认300个结果为一页
	 */
	@ApiField("page_no")
	private String pageNo;

	public String getPageNo() {
		return this.pageNo;
	}
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

}
