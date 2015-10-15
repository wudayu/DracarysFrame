package com.nfky.yaoyijia.handler;

import android.os.Environment;
import android.os.StatFs;

import com.nfky.yaoyijia.generic.Utils;
import com.nfky.yaoyijia.handler.interfaces.ISdCardHandler;

/**
 * Created by David on 8/25/15.
 * <p/>
 * SDCard相关操作实现类
 */
public class SdCardHandler implements ISdCardHandler {

    /** Generate Singleton */
    private static volatile SdCardHandler instance;

    private SdCardHandler() {}

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ISdCardHandler getInstance() {
        if (instance == null) {
            synchronized (SdCardHandler.class) {
                if (instance == null) {
                    instance = new SdCardHandler();
                }
            }
        }

        return instance;
    }

    /**
     * SD卡是否可用
     *
     * @return true->SD卡可用
     */
    @Override
    public boolean isSdcardAvailable() {
        return Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * SD卡空间是否足够 单位是b
     *
     * @param size 文件大小
     * @return true->空间充足
     */
    @Override
    @SuppressWarnings("deprecation")
    public boolean isAvailableSpace(long size) {
        boolean ishasSpace = false;
        if (isSdcardAvailable()) {
            String sdcard = Environment.getExternalStorageDirectory().getPath();
            StatFs statFs = new StatFs(sdcard);
            long blockSize = statFs.getBlockSize();
            long blocks = statFs.getAvailableBlocks();
            long availableSpare = blocks * blockSize;
            if (availableSpare > size) {
                ishasSpace = true;
            }
        } else {
            Utils.debug("SD Card Not Found !");
            return false;
        }

        return ishasSpace;
    }

}