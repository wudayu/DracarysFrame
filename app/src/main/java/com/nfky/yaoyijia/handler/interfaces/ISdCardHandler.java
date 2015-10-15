package com.nfky.yaoyijia.handler.interfaces;

import android.os.Environment;

/**
 * Created by David on 8/24/15.
 * <p/>
 * SDCard相关操作接口
 */
public interface ISdCardHandler {

    /**
     * The constant SD_CARD.
     */
    String SD_CARD = Environment.getExternalStorageDirectory().getAbsolutePath();

    /**
     * The constant SD_CARD_APP. SDCard中的应用主目录
     */
    String SD_CARD_APP = SD_CARD + "/yyj";
    /**
     * The constant SD_DOWNLOAD. 下载文件目录
     */
    String SD_DOWNLOAD = SD_CARD_APP + "/download/";
    /**
     * The constant SD_LOG. 额外日志目录，并非LogHandler使用的目录
     */
    String SD_LOG = SD_CARD_APP + "/log/";
    /**
     * The constant SD_IMAGE_CACHE. 额外图片目录，并非ImageLoader使用的目录
     */
    String SD_IMAGE_CACHE = SD_CARD_APP + "/image/cache/";

    /**
     * Is sdcard available boolean.
     *
     * @return the boolean
     */
    boolean isSdcardAvailable();

    /**
     * Is available space boolean.
     *
     * @param size the size
     * @return the boolean
     */
    boolean isAvailableSpace(long size);

}
