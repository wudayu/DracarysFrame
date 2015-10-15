package com.nfky.yaoyijia.model;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

/**
 * Created by David on 8/26/15.
 * <p/>
 * When created a new database model(entity) we need run this method for best performance
 * and in case of an Android project, you have to remove Android Lib from the launch configuration for that specific class.
 * <p/>
 * 以上内容摘自官方文档。每当创建了一个新的本地数据库model后，需要运行此文件的main函数
 */
public class DatabaseConfigUtil extends OrmLiteConfigUtil {
    /*
    private static final Class<?>[] classes = new Class[] {
            VcUser.class
    };
    */

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception {
		writeConfigFile("db_config.bin");
	}
}
