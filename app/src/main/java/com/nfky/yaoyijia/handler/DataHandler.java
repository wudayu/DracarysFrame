package com.nfky.yaoyijia.handler;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;

import com.nfky.yaoyijia.db.IDbHandler;
import com.nfky.yaoyijia.db.OrmliteDbHandler;
import com.nfky.yaoyijia.model.VcUser;
import com.nfky.yaoyijia.net.INetHandler;
import com.nfky.yaoyijia.net.RetrofitNetHandler;
import com.nfky.yaoyijia.net.protocol.VcUserResult;

/**
 *
 * Created by David on 8/26/15.
 *
 * 数据控制器，用来统一管理数据库或网络访问方式
 *
 **/
public class DataHandler implements IDataHandler {

	private static Activity sContext = null;
	private static INetHandler sNetHandler = null;
	private static IDbHandler sDbHandler = null;

	/** Generate the Singleton */
	private static volatile IDataHandler instance;

	private DataHandler() {}

    public static IDataHandler getInstance(Activity context) {
        sContext = context;
        sNetHandler = RetrofitNetHandler.getInstance();
        sDbHandler = OrmliteDbHandler.getInstance(context);

        if (instance == null) {
            synchronized (DataHandler.class) {
                if (instance == null) {
                    instance = new DataHandler();
                }
            }
        }
        return instance;
    }

	/**
	 * 获取用户信息
	 *
	 * @param userId 用户ID
	 * @param dcb 数据获取回调
	 */
	@Override
	public void getForUserInfo(final String userId, final DataCallback<VcUser> dcb) {
		Callback<VcUserResult> cbRetrofit = new Callback<VcUserResult>() {
			@Override
			public void failure(RetrofitError error) {
				RetrofitNetHandler.toastNetworkError(sContext, error);
				// 网络无法连接，连接本地数据库
				sDbHandler.getForUserInfo(userId, dcb);
			}
			@Override
			public void success(VcUserResult result, Response response) {
				// 获取到数据，首先写入数据库
				VcUser user = result.getObjValue();
				sDbHandler.setForUserInfo(user);
				dcb.onSuccess(user);
			}
		};
        // TODO edit this function
//		sNetHandler.getForUserInfo(userId, cbRetrofit);
	}

}
