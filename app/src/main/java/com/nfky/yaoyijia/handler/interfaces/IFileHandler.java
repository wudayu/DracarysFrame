package com.nfky.yaoyijia.handler.interfaces;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

/**
 * Created by David on 8/26/15.
 * <p/>
 * Description: IFileHandler是文件处理者的接口类
 */
public interface IFileHandler {

	/**
	 * The enum Cache dir. 缓存目录枚举
	 */
	enum CacheDir {
		/**
		 * Image cache dir.
		 */
		IMAGE("/.image/"),
		/**
		 * Log cache dir.
		 */
		LOG("/.log/"), /**
		 * Audio cache dir.
		 */
		AUDIO("/.audio/"),
		/**
		 * Video cache dir.
		 */
		VIDEO("/.video/"),
		/**
		 * Avatar cache dir.
		 */
		AVATAR("/.avatar/");

		private String dir;

		private CacheDir(String dir) {
			this.dir = dir;
		}

		@Override
		public String toString() {
			return String.valueOf(this.dir);
		}

		/**
		 * Gets dir.
		 *
		 * @return the dir
		 */
		public String getDir() {
			return dir;
		}
	}

	/**
	 * The enum Data dir. 数据目录
	 */
	enum DataDir {
		/**
		 * Backup data dir.
		 */
		BACKUP("/.backup/"),
		/**
		 * Gif data dir.
		 */
		GIF("/.gif/"),
		/**
		 * Flags data dir.
		 */
		FLAGS("/.flags/"),
		/**
		 * Database data dir.
		 */
		DATABASE("/.db/"),
		/**
		 * Emoji data dir.
		 */
		EMOJI("/.emoji/");

		private String dir;

		/**
		 * Instantiates a new Data dir.
		 *
		 * @param dir the dir
		 */
		DataDir(String dir) {
			this.dir = dir;
		}

		@Override
		public String toString() {
			return String.valueOf(this.dir);
		}

		/**
		 * Gets dir.
		 *
		 * @return the dir
		 */
		public String getDir() {
			return dir;
		}
	}

	/**
	 * Gets cache dir by type.
	 *
	 * @param dir the dir
	 * @return the cache dir by type
	 */
	String getCacheDirByType(CacheDir dir);

	/**
	 * Gets file dir by type.
	 *
	 * @param dir the dir
	 * @return the file dir by type
	 */
	String getFileDirByType(DataDir dir);

	/**
	 * Gets file dir.
	 *
	 * @return the file dir
	 */
	String getFileDir();

	/**
	 * Gets cache dir.
	 *
	 * @return the cache dir
	 */
	String getCacheDir();

	/**
	 * Is file exists boolean.
	 *
	 * @param filePath the file path
	 * @return the boolean
	 */
	boolean isFileExists(String filePath);

	/**
	 * Create file boolean.
	 *
	 * @param filePath the file path
	 * @return the boolean
	 * @throws IOException the io exception
	 */
	boolean createFile(String filePath) throws IOException;

	/**
	 * Create folder boolean.
	 *
	 * @param folderPath the folder path
	 * @return the boolean
	 */
	boolean createFolder(String folderPath);

	/**
	 * Delete file boolean.
	 *
	 * @param filePathAndName the file path and name
	 * @return the boolean
	 */
	boolean deleteFile(String filePathAndName);

	/**
	 * Delete folder boolean.
	 *
	 * @param path      the path
	 * @param recursive the recursive
	 * @return the boolean
	 */
	boolean deleteFolder(String path, boolean recursive);

	/**
	 * Delete files in floder boolean.
	 *
	 * @param path      the path
	 * @param recursive the recursive
	 * @return the boolean
	 */
	boolean deleteFilesInFloder(String path, boolean recursive);

	/**
	 * Gets file base name.
	 *
	 * @param fileName the file name
	 * @return the file base name
	 */
	String getFileBaseName(final String fileName);

	/**
	 * Gets file extension.
	 *
	 * @param fileName the file name
	 * @return the file extension
	 */
	String getFileExtension(final String fileName);

	/**
	 * Copy file boolean.
	 *
	 * @param sourcePath the source path
	 * @param targetPath the target path
	 * @return the boolean
	 */
	boolean copyFile(String sourcePath, String targetPath);

	/**
	 * Copy file.
	 *
	 * @param src the src
	 * @param dst the dst
	 * @throws IOException the io exception
	 */
	void copyFile(File src, File dst) throws IOException;

	/**
	 * Gets dir path.
	 *
	 * @param filePath the file path
	 * @return the dir path
	 */
	String getDirPath(String filePath);

	/**
	 * Gets file name.
	 *
	 * @param filePath the file path
	 * @return the file name
	 */
	String getFileName(String filePath);

	/**
	 * Gets file name without suffix.
	 *
	 * @param path the path
	 * @return the file name without suffix
	 */
	String getFileNameWithoutSuffix(String path);

	/**
	 * Read text file string.
	 *
	 * @param path the path
	 * @return the string
	 */
	String readTextFile(String path);

	/**
	 * Write text file.
	 *
	 * @param path    the path
	 * @param content the content
	 */
	void writeTextFile(String path, String content);

	/**
	 * List files array list.
	 *
	 * @param path      the path
	 * @param filter    the filter
	 * @param recursive the recursive
	 * @return the array list
	 */
	ArrayList<String> listFiles(String path, FileFilter filter, boolean recursive);

	/**
	 * Rename file boolean.
	 *
	 * @param pathOri the path ori
	 * @param pathNew the path new
	 * @return the boolean
	 */
	boolean renameFile(String pathOri, String pathNew);

	/**
	 * Gets file name in url.
	 *
	 * @param url the url
	 * @return the file name in url
	 */
	String getFileNameInUrl(String url);

	/**
	 * Gets data size txt.
	 *
	 * @param size the size
	 * @return the data size txt
	 */
	String getDataSizeTxt(Long size);

	/**
	 * Save bitmap.
	 *
	 * @param fullPath the full path
	 * @param bitmap   the bitmap
	 * @param format   the format
	 */
	void saveBitmap(String fullPath, Bitmap bitmap, CompressFormat format);

	/**
	 * Save bitmap.
	 *
	 * @param fullPath the full path
	 * @param bitmap   the bitmap
	 * @param quality  the quality
	 * @param format   the format
	 */
	void saveBitmap(String fullPath, Bitmap bitmap, int quality, CompressFormat format);

	/**
	 * Save bitmap.
	 *
	 * @param file    the file
	 * @param bitmap  the bitmap
	 * @param quality the quality
	 * @param format  the format
	 */
	void saveBitmap(File file, Bitmap bitmap, int quality, CompressFormat format);

	/**
	 * Decode file bitmap.
	 *
	 * @param f the f
	 * @return the bitmap
	 */
	Bitmap decodeFile(File f);

	/**
	 * Copy file from asset.
	 *
	 * @param source the source
	 * @param dst    the dst
	 */
	void copyFileFromAsset(InputStream source, File dst);

	/**
	 * Gets folder or file size.
	 *
	 * @param path the path
	 * @return the folder or file size
	 */
	long getFolderOrFileSize(String path);

	/**
	 * Clean cache.
	 *
	 * @param path       the path
	 * @param size       the size
	 * @param millSecAgo the mill sec ago
	 */
	void cleanCache(String path, long size, long millSecAgo);

	/**
	 * Create no media file.
	 *
	 * @param path the path
	 */
	void createNoMediaFile(String path);

	/**
	 * Gets string from assets.
	 *
	 * @param fileName the file name
	 * @return the string from assets
	 */
	String getStringFromAssets(String fileName);

}
