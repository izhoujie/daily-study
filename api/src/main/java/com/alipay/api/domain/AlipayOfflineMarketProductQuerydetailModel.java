package com.alipay.api.domain;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;

/**
 * 通过该接口可以查询商户录入的指定商品详细信息
 *
 * @author auto create
 * @since 1.0, 2015-11-27 14:41:11
 */
public class AlipayOfflineMarketProductQuerydetailModel extends AlipayObject {

	private static final long serialVersionUID = 3426275888129761863L;

	/**
	 * 商品ID
	 */
	@ApiField("item_id")
	private String itemId;

	public String getItemId() {
		return this.itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

}
