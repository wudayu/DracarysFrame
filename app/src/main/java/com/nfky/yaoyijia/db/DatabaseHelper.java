package com.nfky.yaoyijia.db;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.nfky.yaoyijia.R;
import com.nfky.yaoyijia.model.VcUser;

/**
 * Created by David on 8/26/15.
 * <p/>
 * OrmLite的DatabaseHelper 主要包含了建表工作和版的控制工作
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	private static final String DATABASE_NAME = "wudayu_yyj.db";
	private static final int DATABASE_VERSION = 1;
	private Context mContext;

	/**
	 * 初始化DatabaseHelper，将建表内容指向事先生成的R.raw.db_config
	 *
	 * @param context 上下文
	 */
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.db_config);
		mContext = context;
	}

    /**
     * 建表语句编写，按照格式将对应的model写入即可
     *
     * @param db 数据库对象，一般用不到
     * @param connectionSource 已经建立的数据库链接
     */
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			Log.i(DatabaseHelper.class.getName(), "--------begin creating database tables--------");
			TableUtils.createTable(connectionSource, VcUser.class);
			Log.i(DatabaseHelper.class.getName(), "--------end creating database tables--------");
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}
	}

    /**
     * 数据库升级操作
     * 若数据库比较复杂，可以采用删除原来的数据库，重新建立一个数据库的方式
     */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int oldVersion, int newVersion) {
		// upgrade db to version 2
		switch (oldVersion) {
		case 1:
            // upgrade_1_2();
			break;
		default:
			break;
		}

	}

	/**
	 * 数据库版本从1升级到2操作
	 
	private void upgrade_1_2() {
		try {
			Dao<DuduContact, Integer> dao = getDao(VcWeather.class);
			dao.executeRaw("ALTER TABLE `DuduContact` ADD COLUMN remark VARCHAR(200);");
			dao.executeRaw("UPDATE `DuduContact` SET  remark = dudu_nickname;");
			// 触发联系人操作
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}*/

}
