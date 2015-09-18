package com.nfky.yaoyijia.handler;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

/**
 *
 * Created by David on 8/26/15.
 *
 * Description: IFileHandler是文件处理者的接口类
 *
 **/

public interface IFileHandler {

	// 缓存目录枚举
	enum CacheDir {
		IMAGE("/.image/"), LOG("/.log/"), AUDIO("/.audio/"), VIDEO("/.video/"), AVATAR(
				"/.avatar/");

		private String dir;

		private CacheDir(String dir) {
			this.dir = dir;
		}

		@Override
		public String toString() {
			return String.valueOf(this.dir);
		}

		public String getDir() {
			return dir;
		}
	}

	// 数据目录
	enum DataDir {
		BACKUP("/.backup/"), GIF("/.gif/"), FLAGS("/.flags/"), DATABASE("/.db/"), EMOJI(
				"/.emoji/");

		private String dir;

		DataDir(String dir) {
			this.dir = dir;
		}

		@Override
		public String toString() {
			return String.valueOf(this.dir);
		}

		public String getDir() {
			return dir;
		}
	}

	String getCacheDirByType(CacheDir dir);

	String getFileDirByType(DataDir dir);

	String getFileDir();

	String getCacheDir();

	boolean isFileExists(String filePath);

	boolean createFolder(String folderPath);

	boolean deleteFile(String filePathAndName);

	boolean deleteFolder(String path, boolean recursive);

	boolean deleteFilesInFloder(String path, boolean recursive);

	String getFileBaseName(final String fileName);

	String getFileExtension(final String fileName);

	boolean copyFile(String sourcePath, String targetPath);

	void copyFile(File src, File dst) throws IOException;

	String getDirPath(String filePath);

	String getFileName(String filePath);

	String getFileNameWithoutSuffix(String path);

	String readTextFile(String path);

	ArrayList<String> listFiles(String path, FileFilter filter, boolean recursive);

	boolean renameFile(String pathOri, String pathNew);

	String getFileNameInUrl(String url);

	String getDataSizeTxt(Long size);

	void saveBitmap(String fullPath, Bitmap bitmap, CompressFormat format);

	void saveBitmap(String fullPath, Bitmap bitmap, int quality, CompressFormat format);

	void saveBitmap(File file, Bitmap bitmap, int quality, CompressFormat format);

	Bitmap decodeFile(File f);

	void copyFileFromAsset(InputStream source, File dst);

	long getFolderOrFileSize(String path);

	void cleanCache(String path, long size, long millSecAgo);

	void createNoMediaFile(String path);

	String getStringFromAssets(String fileName);

}
