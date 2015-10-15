package com.nfky.yaoyijia.constant;

/**
 * Created by David on 5/24/15.
 * <p/>
 * BroadcastActions包含了所有本地广播的广播标识，所有的本地广播标识都需要在这里注册
 */
public class BroadcastActions {

	/** 所有BroadcastActions的前缀 */
	private static final String PKG = "cn.wudayu.daf_";

	/**
	 * 关闭所有Activity
	 */
	public static final String FINISH_ACTIVITY = PKG + "finish_activity";

	/**
	 * JPush获取消息的Action
	 */
	public static final String JPUSH_MESSAGE_RECEIVED = PKG + "jpush_message_received";

}
