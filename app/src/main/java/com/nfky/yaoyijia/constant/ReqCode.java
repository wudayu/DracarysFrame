package com.nfky.yaoyijia.constant;

/**
 * Created by David on 5/24/15.
 * <p/>
 * ReqCode包含了应用中所有的RequestCode和ResultCode，这些用在Activity间通信的Code需要在此定义
 */
public class ReqCode {

	/**
	 * 以下三个ReqCode由UILImageHandler专用. 从照片库中获取照片
	 */
	public static final int ALBUM = 501;
	/**
	 * The constant CAMERA.
	 */
	public static final int CAMERA = 502;
	/**
	 * The constant CUTTED. 切割照片
	 */
	public static final int CUTTED = 503;


	/**
	 * The constant SIGN_IN. 登陆
	 */
	public static final int SIGN_IN = 5201;

	/**
	 * The constant SIGN_OUT. 登出
	 */
	public static final int SIGN_OUT = 5202;

}
