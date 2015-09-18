package com.nfky.yaoyijia.db;

import com.nfky.yaoyijia.handler.IDataHandler;
import com.nfky.yaoyijia.model.VcUser;

/**
 *
 * Created by David on 8/26/15.
 *
 * 数据库操作接口
 *
 **/
public interface IDbHandler extends IDataHandler {

	void getForUserInfo(String userId, DataCallback<VcUser> dcb);
	void setForUserInfo(VcUser user);

}
