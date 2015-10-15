package com.nfky.yaoyijia.db;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.nfky.yaoyijia.R;
import com.nfky.yaoyijia.generic.Utils;
import com.nfky.yaoyijia.model.VcUser;

/**
 * Created by David on 8/26/15.
 * <p/>
 * 数据库操作实现类
 */
public class OrmliteDbHandler implements IDbHandler {

	private Context context = null;

	/** Generate the Singleton */
	private static volatile OrmliteDbHandler instance;

	private OrmliteDbHandler() {}

	/**
	 * Gets instance.
	 *
	 * @param context the context
	 * @return the instance
	 */
	public static IDbHandler getInstance(Context context) {
        if (instance == null) {
            synchronized (OrmliteDbHandler.class) {
                if (instance == null) {
                    instance = new OrmliteDbHandler();
                }
            }
        }

		instance.context = context;

        return instance;
    }

	/**
	 * The M database helper.
	 */
	DatabaseHelper mDatabaseHelper = null;

	/**
	 * 初始化DatabaseHelper
	 *
	 * @return DatabaseHelper
	 */
	private DatabaseHelper getHelper() {
	    if (mDatabaseHelper == null) {
	    	mDatabaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
	    }

	    return mDatabaseHelper;
	}

	/**
	 * 获取用户详情
	 *
	 * @param userId 用户的UUID
	 * @param dcb 回调方法
	 */
	@Override
	public void getForUserInfo(String userId, DataCallback<VcUser> dcb) {
		DatabaseHelper dbHelper = getHelper();
		try {
			RuntimeExceptionDao<VcUser, Integer> userDao = RuntimeExceptionDao.createDao(dbHelper.getConnectionSource(), VcUser.class);
			List<VcUser> users = userDao.queryForEq(VcUser.USER_ID, userId);
			if (users != null && users.size() > 0) {
				dcb.onSuccess(users.get(0));
			} else {
				dcb.onFailure(context.getString(R.string.error_local_database_has_no_data), null);
			}
		} catch (SQLException e) {
			dcb.onFailure(context.getString(R.string.error_local_database_connot_be_connected), null);
			e.printStackTrace();
		}
	}

	/**
	 * 存储用户信息到本地
	 *
	 * @param user 用户对象
	 */
	@Override
	public void setForUserInfo(VcUser user) {
		DatabaseHelper dbHelper = getHelper();
		try {
			RuntimeExceptionDao<VcUser, Integer> userDao = RuntimeExceptionDao.createDao(dbHelper.getConnectionSource(), VcUser.class);
			if (user != null) {
				// 有数据则添加或更新
				userDao.createOrUpdate(user);
			} else {
				// 没数据则清空表，保持与服务器统一
				userDao.deleteBuilder().delete();
			}
		} catch (SQLException e) {
			Utils.debug("--:(------can not connect database---");
			e.printStackTrace();
		}
	}

}
