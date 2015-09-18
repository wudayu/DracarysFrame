package com.nfky.yaoyijia.net.service;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

import com.nfky.yaoyijia.model.VcTestUser;
import com.nfky.yaoyijia.model.VcUser;
import com.nfky.yaoyijia.net.protocol.VcObjectResult;
import com.nfky.yaoyijia.net.protocol.VcUserResult;

/**
 *
 * Created by David on 8/25/15.
 *
 * UserService是用来完成用户相关的工作的Retrofit的Service
 *
 **/

public interface UserService {

	/**
	 * 获取用户信息
	 *
	 * @param userId 用户ID
	 * @param fromApp 是否来自App，"1"是，"0"否
	 * @param cb 网络回调
	 */
	@GET("/rest/broker/getBrokerInfo")
	void getUser(@Query("id") String userId, @Query("fromApp") String fromApp, Callback<VcObjectResult<VcUser>> cb);

    // TODO remove this
    @GET("/api/v1/user/{userId}")
    void getTestUser(@Path("userId") String userId, Callback<VcObjectResult<VcTestUser>> cb);

}
