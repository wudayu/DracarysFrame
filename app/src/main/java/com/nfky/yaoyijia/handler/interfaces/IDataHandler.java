package com.nfky.yaoyijia.handler.interfaces;

import com.nfky.yaoyijia.model.VcUser;


/**
 *
 * Created by David on 8/26/15.
 *
 * IDataHandler是DataHandler的接口，还包含了数据操作回调的定义
 *
 **/

public interface IDataHandler {

	void getForUserInfo(String userId, DataCallback<VcUser> dcb);

	/**
	 * 数据加载完成回调
	 * @param <T> 数据的对应的model类型
	 */
	interface DataCallback<T> {
		void onSuccess(T object);
		void onFailure(String errInfo, Exception excep);
	}

}
