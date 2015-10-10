package com.nfky.yaoyijia.handler;

import android.os.Environment;
import android.os.StatFs;

import com.nfky.yaoyijia.generic.Utils;

/**
 *
 * Created by David on 8/25/15.
 *
 * SDCard相关操作实现类
 *
 **/

public class SDCardHandler implements ISDCardHandler {

	/** Generate Singleton */
	private static volatile SDCardHandler instance;

	private SDCardHandler() {}

    public static ISDCardHandler getInstance() {
        if (instance == null) {
            synchronized (SDCardHandler.class) {
                if (instance == null) {
                    instance = new SDCardHandler();
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
	public boolean isSdcardAvaliable() {
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
	public boolean isAvaiableSpace(long size) {
		boolean ishasSpace = false;
		if (isSdcardAvaliable()) {
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
