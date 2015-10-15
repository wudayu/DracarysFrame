package com.nfky.yaoyijia.db;

import com.nfky.yaoyijia.handler.interfaces.IDataHandler;
import com.nfky.yaoyijia.model.VcUser;

/**
 * Created by David on 8/26/15.
 * <p/>
 * 数据库操作接口
 */
public interface IDbHandler extends IDataHandler {

	void getForUserInfo(String userId, DataCallback<VcUser> dcb);

	/**
	 * Sets for user info.
	 *
	 * @param user the user
	 */
	void setForUserInfo(VcUser user);

}
