package com.alipay.api.domain;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;

/**
 * 商户可以通过此接口对商品进行库存等信息的修改（库存修改值只能大于当前值）
 *
 * @author auto create
 * @since 1.0, 2015-11-06 19:20:11
 */
public class AlipayOfflineMarketItemModifyModel extends AlipayObject {

	private static final long serialVersionUID = 6396287121828494224L;

	/**
	 * 库存
	 */
	@ApiField("inventory")
	private Long inventory;

	/**
	 * 商品id
	 */
	@ApiField("item_id")
	private String itemId;

	/**
	 * 备注
	 */
	@ApiField("memo")
	private String memo;

	/**
	 * 商户接口异步通知的地址
	 */
	@ApiField("operate_notify_url")
	private String operateNotifyUrl;

	/**
	 * 请求id，必须保证单商户下唯一
	 */
	@ApiField("request_id")
	private String requestId;

	/**
	 * 商品展示权重
	 */
	@ApiField("weight")
	private Long weight;

	public Long getInventory() {
		return this.inventory;
	}
	public void setInventory(Long inventory) {
		this.inventory = inventory;
	}

	public String getItemId() {
		return this.itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getMemo() {
		return this.memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getOperateNotifyUrl() {
		return this.operateNotifyUrl;
	}
	public void setOperateNotifyUrl(String operateNotifyUrl) {
		this.operateNotifyUrl = operateNotifyUrl;
	}

	public String getRequestId() {
		return this.requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public Long getWeight() {
		return this.weight;
	}
	public void setWeight(Long weight) {
		this.weight = weight;
	}

}
