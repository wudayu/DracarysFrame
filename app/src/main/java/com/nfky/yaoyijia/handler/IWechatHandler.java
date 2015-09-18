package com.nfky.yaoyijia.handler;

/**
 *
 * Created by David on 8/28/15.
 *
 * IWechatHandler是微信操作的接口类
 *
 **/

public interface IWechatHandler {

	/** 链接并启动微信支付 耗时操作 */
	boolean connectWechatPay(String appId, String partnerId, String prepayId, String noncestr, String timeStamp, String sign);

}
