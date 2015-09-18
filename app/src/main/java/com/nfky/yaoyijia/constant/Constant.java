package com.nfky.yaoyijia.constant;


/**
 *
 * Created by David on 5/24/15.
 *
 * Constant包含了应用程序中的一些重要的Flags
 *
 **/

public class Constant {

	/** 调试模式, TODO 发布前改为false */
	public static final boolean DEBUG = true;

	/** 应用Log的TAG */
	public static final String TAG = "com.nkfy.yaoyijia";

	/** 图片压缩质量百分比 */
	public static final int IMAGE_QUALITY = 60;

	/** 忽略多余的Json属性, TODO 发布前改为true */
	public static final boolean jsonIgnoreUnknown = true;

	/** 总是忽略多余的Json属性, 用于返回属性确实太多的情况 */
	public static final boolean jsonIgnoreTooMuch = true;

	/** 推送Service开关 */
	public static final boolean needPush = false;

	/** 微信支付 */
	public static final String WX_APP_ID = "";

	/** 友盟分享 */
	public static final String UM_DESCRIPTOR = "com.umeng.share";

}
