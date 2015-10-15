package com.nfky.yaoyijia.net;

import retrofit.Callback;

import com.nfky.yaoyijia.model.VcTestUser;
import com.nfky.yaoyijia.model.VcUser;
import com.nfky.yaoyijia.net.protocol.VcListResult;
import com.nfky.yaoyijia.net.protocol.VcObjectResult;
import com.nfky.yaoyijia.net.protocol.VcUserResult;
import com.nfky.yaoyijia.net.protocol.WeatherResult;

/**
 * Created by David on 8/25/15.
 * <p/>
 * INetHandler是网络访问接口，其中还包括了网络地址等信息
 */
public interface INetHandler {

	/** 两种Schema */
	/**
	 * The constant PREFIX_HTTP.
	 */
	String PREFIX_HTTP = "http://";
	/**
	 * The constant PREFIX_HTTPS.
	 */
	String PREFIX_HTTPS = "https://";
	/**
	 * 测试地址和发布地址（客户环境）
	 */
	String SERVER_URL_TEST = "172.18.50.206:8080/server-core";
	/**
	 * The constant SERVER_URL_OFFICAL_PRE.
	 */
	String SERVER_URL_OFFICAL_PRE = "serveu.xwzf.gov.cn/server-core";
	/**
	 * The constant SERVER_URL_OFFICAL.
	 */
	String SERVER_URL_OFFICAL = "";
	/**
	 * 默认的Schema前缀
	 */
	String PREFIX_DEFAULT = PREFIX_HTTP;
	/**
	 * Server URL Names TODO Switch to the last one when publish
	 */
//	String SERVER_URL_NAME = SERVER_URL_TEST;
	String SERVER_URL_NAME = SERVER_URL_OFFICAL_PRE;
//	String SERVER_URL_NAME = SERVER_URL_OFFICAL;

	/**
	 * 被继承此类的类所使用的参数
	 */
	String SERVER_URL_FOR_RETROFIT = PREFIX_DEFAULT + SERVER_URL_NAME;
	/**
	 * The constant SERVER_URL_WEATHER.
	 */
	String SERVER_URL_WEATHER = "http://www.weather.com.cn/data/cityinfo";
	/**
	 * The constant CONTANT_CODE.
	 */
	String CONTANT_CODE = "UTF-8";
	/**
	 * The constant UPLOAD_PIC_FILE_KEY.
	 */
	String UPLOAD_PIC_FILE_KEY = "attachFile";

	/** Http Headers
	String HEADER_CLIENT_SESSION = "client-session";
	String HEADER_CLIENT_VERSION = "client-version";
	String HEADER_API_VERSION = "api-version";
	String HEADER_CONTENT_TYPE = "Content-Type";
	String HEADER_CONTENT_DISPOSITION = "Content-Disposition";
	String HEADER_CONTENT_ENCODING = "Content-Encoding";
	String HEADER_CONNECTION = "Connection";
	String HEADER_REGION_CODE = "Region-Code";
	String HEADER_HOST = "Host";
	*/


	/*****************
	 * Methods Below *
	 *****************/

	/**
	 * 获取天气信息  @param code the code
	 *
	 * @param cb the cb
	 */
	void getForWeather(String code, Callback<WeatherResult> cb);

	/**
	 * 获取用户信息  @param userId the user id
	 *
	 * @param cb the cb
	 */
	void getForUserInfo(String userId, Callback<VcObjectResult<VcUser>> cb);

	/**
	 * Gets for get test user.
	 *
	 * @param userId the user id
	 * @param cb     the cb
	 */
	void getForGetTestUser(String userId, Callback<VcObjectResult<VcTestUser>> cb);

	/**
	 * 上传图片  @param imagePath the image path
	 *
	 * @param cb the cb
	 */
	void postForUploadSinglePic(String imagePath, Callback<VcListResult<String>> cb);

	/**
	 * Post for upload multiple pic.
	 *
	 * @param imagePaths the image paths
	 * @param cb         the cb
	 */
	void postForUploadMultiplePic(String[] imagePaths, Callback<VcListResult<String>> cb);

}
