package com.nfky.yaoyijia.handler.interfaces;

import com.nfky.yaoyijia.model.VcUser;


/**
 * Created by David on 8/26/15.
 * <p/>
 * IDataHandler是DataHandler的接口，还包含了数据操作回调的定义
 */
public interface IDataHandler {

	/**
	 * Gets for user info.
	 *
	 * @param userId the user id
	 * @param dcb    the dcb
	 */
	void getForUserInfo(String userId, DataCallback<VcUser> dcb);

	/**
	 * 数据加载完成回调
	 *
	 * @param <T> 数据的对应的model类型
	 */
	interface DataCallback<T> {
		/**
		 * On success.
		 *
		 * @param object the object
		 */
		void onSuccess(T object);

		/**
		 * On failure.
		 *
		 * @param errInfo the err info
		 * @param excep   the excep
		 */
		void onFailure(String errInfo, Exception excep);
	}

}
