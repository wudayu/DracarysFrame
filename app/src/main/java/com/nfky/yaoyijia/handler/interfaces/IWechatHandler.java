package com.nfky.yaoyijia.handler.interfaces;

/**
 * Created by David on 8/28/15.
 * <p/>
 * IWechatHandler是微信操作的接口类
 */
public interface IWechatHandler {

	/**
	 * 链接并启动微信支付 耗时操作  @param appId the app id
	 *
	 * @param partnerId the partner id
	 * @param prepayId  the prepay id
	 * @param noncestr  the noncestr
	 * @param timeStamp the time stamp
	 * @param sign      the sign
	 * @return the boolean
	 */
	boolean connectWechatPay(String appId, String partnerId, String prepayId, String noncestr, String timeStamp, String sign);

}
