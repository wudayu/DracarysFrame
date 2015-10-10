package com.nfky.yaoyijia.handler;

import android.os.Environment;

/**
 *
 * Created by David on 8/24/15.
 *
 * SDCard相关操作接口
 *
 **/

public interface ISDCardHandler {

	String SD_CARD = Environment.getExternalStorageDirectory().getAbsolutePath();

	// SDCard中的应用主目录
	String SD_CARD_APP = SD_CARD + "/yyj";
    // 下载文件目录
	String SD_DOWNLOAD = SD_CARD_APP + "/download/";
    // 额外日志目录，并非LogHandler使用的目录
	String SD_LOG = SD_CARD_APP + "/log/";
    // 额外图片目录，并非ImageLoader使用的目录
	String SD_IMAGE_CACHE = SD_CARD_APP + "/image/cache/";

	boolean isSdcardAvaliable();

	boolean isAvaiableSpace(long size);

}
