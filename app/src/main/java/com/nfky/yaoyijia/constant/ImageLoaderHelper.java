package com.nfky.yaoyijia.constant;

/**
 *
 * Created by David on 5/26/15.
 * 
 * ImageLoaderHelper是ImageLoader的帮助类，它通过拼接Schema来帮助ImageLoader来定位图片资源
 *
 **/

public class ImageLoaderHelper {

	/*
	 * 使用方式举例
	 * String imageUri = "http://site.com/image.png"; // from Web
	 * String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
	 * String imageUri = "content://media/external/audio/albumart/13"; // from content provider
	 * String imageUri = "assets://image.png"; // from assets
	 * String imageUri = "drawable://" + R.drawable.image; // from drawables (only images, non-9patch)
	 */

	// 文件头
	public static final String URI_PREFIX_FILE = "file://";

	// 资源头
	public static final String URI_PREFIX_ASSETS = "assets://";

	// 图片头
	public static final String URI_PREFIX_DRAWABLE = "drawable://";

}
