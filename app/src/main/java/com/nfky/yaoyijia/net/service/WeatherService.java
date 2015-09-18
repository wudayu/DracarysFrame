package com.nfky.yaoyijia.net.service;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

import com.nfky.yaoyijia.net.protocol.WeatherResult;

/**
 *
 * Created by David on 8/28/15.
 *
 * WeatherService是用来向气象台请求天气信息的Retrofit的Service
 *
 **/

public interface WeatherService {

	/**
	 * 获取天气
	 *
	 * @param code 城市编码
	 * @param cb 网络回调
	 */
	@GET("/{code}.html")
	void getWeather(@Path("code") String code, Callback<WeatherResult> cb);

}
