package com.alipay.api.domain;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;

/**
 * 系统商通过该接口可以查询单个门店的详细信息，包括门店基础信息，门店状态等信息
 *
 * @author auto create
 * @since 1.0, 2015-11-27 14:41:20
 */
public class AlipayOfflineMarketShopQuerydetailModel extends AlipayObject {

	private static final long serialVersionUID = 8445467739935246355L;

	/**
	 * 店铺id
	 */
	@ApiField("shop_id")
	private String shopId;

	public String getShopId() {
		return this.shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

}
