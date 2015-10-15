package com.nfky.yaoyijia;

import android.app.Application;

import com.nfky.yaoyijia.constant.Constant;
import com.nfky.yaoyijia.handler.CrashHandler;
import com.nostra13.universalimageloader.utils.L;
import com.nfky.yaoyijia.image.IImageHandler;
import com.nfky.yaoyijia.image.UILImageHandler;

/**
 * Created by David on 8/24/15.
 * <p/>
 * MainApp是整个项目使用的Application，其中初始化了一些操作，如果有需要也可以添加一些全局变量
 */
public class MainApp extends Application {

	/**
	 * The Image handler.
	 */
	IImageHandler imageHandler = null;

	@Override
	public void onCreate() {
		super.onCreate();

		initCrashHandler();
		initUIL();
	}

	/**
	 * 初始化全局异常捕捉器
	 */
	private void initCrashHandler() {
        // 若不在调试模式（如发布正式版本），则开启闪退捕捉
        if (!Constant.DEBUG)
		    CrashHandler.getInstance(this);
	}

	/**
	 * 初始化 Universal Image Loader
	 */
	private void initUIL() {
		imageHandler = UILImageHandler.getInstance(this);
		imageHandler.initImageLoader();

		L.writeLogs(false);
	}

	/**
	 * Gets haha.
	 *
	 * @return the haha
	 */
	public int getHaha() {
		return 1;
	}
}
