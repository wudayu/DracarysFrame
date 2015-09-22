package com.nfky.yaoyijia.handler;

import android.content.Context;

import com.nfky.yaoyijia.constant.Constant;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 *
 * Created by David on 8/28/15.
 *
 * WechatHandler是包含了对微信操作的一系列工具，目前只包含微信支付部分.
 *
 **/

public class WechatHandler implements IWechatHandler {

	/** Generate Singleton */
	private static volatile WechatHandler instance;
	private static IWXAPI api = null;

	private WechatHandler() {}

    public static IWechatHandler getInstance(Context context) {
        api = WXAPIFactory.createWXAPI(context, Constant.WX_APP_ID);
        api.registerApp(Constant.WX_APP_ID);

        if (instance == null) {
            synchronized (WechatHandler.class) {
                if (instance == null) {
                    instance = new WechatHandler();
                }
            }
        }
        return instance;
    }

	/**
	 * 链接并启动微信支付 耗时操作
	 *
	 * @param appId appId
	 * @param partnerId partnerId
	 * @param prepayId prepayId
	 * @param noncestr noncester
	 * @param timeStamp timeStamp
	 * @param sign sign
	 * @return true->成功调用微信
	 */
	@Override
	public boolean connectWechatPay(String appId, String partnerId, String prepayId, String noncestr, String timeStamp, String sign) {
		PayReq req = new PayReq();
		req.appId = appId;
		req.partnerId = partnerId;
		req.prepayId = prepayId;
		req.nonceStr = noncestr;
		req.timeStamp = timeStamp;
		req.packageValue = "Sign=WXPay";
		req.sign = sign;

		//启动微信支付接口
		return api.sendReq(req);
	}

}