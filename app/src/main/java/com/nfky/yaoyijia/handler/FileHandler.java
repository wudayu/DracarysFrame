package com.nfky.yaoyijia.handler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import org.apache.http.util.EncodingUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.nfky.yaoyijia.constant.Constant;
import com.nfky.yaoyijia.generic.Utils;
import com.nfky.yaoyijia.handler.interfaces.IFileHandler;

/**
 * Created by David on 8/26/15.
 *
 * Description: FileHandler用来处理文件操作
 *
 **/

public class FileHandler implements IFileHandler {

	public static final String DEFAULT_ENCODING = "UTF-8";
	private Context context = null;

	/** Generate Singleton */
	private static volatile FileHandler instance;

	private FileHandler() {}

    public static IFileHandler getInstance(Context context) {
        if (instance == null) {
            synchronized (FileHandler.class) {
                if (instance == null) {
                    instance = new FileHandler();
                }
            }
        }

		instance.context = context;

        return instance;
    }

	/**
	 * 使用IFileHandler中定义的枚举类型获取缓存目录
	 *
	 * @param dir 缓存类型
	 * @return 对应的缓存目录
	 */
	public String getCacheDirByType(CacheDir dir) {
		String path = this.getCacheDir() + dir;

		File file = new File(path);

		if (!file.exists()) {
			file.mkdirs();
		}

		return path;
	}

	/**
	 * 使用IFileHandler中定义的枚举类型获取数据目录
	 *
	 * @param dir 数据类型
	 * @return 对应的数据目录
	 */
	public String getFileDirByType(DataDir dir) {
		String path = this.getFileDir() + dir;

		File file = new File(path);

		if (!file.exists()) {
			file.mkdirs();
		}

		return path;
	}

	/**
	 * 获取可用的文件目录 优先/sdcard/Android/data/mypacketname/file
	 * 不可用时返回/data/data/mypacketname/file
	 * 
	 * @return path
	 */
	public String getFileDir() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File file = context.getExternalFilesDir(null);

			if (file != null) {
				return file.getAbsolutePath();
			}
		}

		return context.getFilesDir().getAbsolutePath();
	}

	/**
	 * 获取可用的缓存目录 优先/sdcard/Android/data/mypacketname/cache
	 * 不可用时返回/data/data/mypacketname/file
	 * 
	 * @return path
	 */
	public String getCacheDir() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File file = context.getExternalCacheDir();

			if (file != null) {
				return file.getAbsolutePath();
			}
		}

		return context.getCacheDir().getAbsolutePath();
	}

	/**
	 * 判断文件或文件夹是否存在
	 * 
	 * @param filePath 需要判断的文件路径
	 * @return true->存在
	 */
	@Override
	public boolean isFileExists(String filePath) {
		File file = new File(filePath);
		return file.exists();
	}

	/**
	 * 创建一个新文件
	 *
	 * @param filePath 要创建的文件路径
	 * @return true->已经创建
	 */
	@Override
	public boolean createFile(String filePath) throws IOException {
		File path = new File(filePath);
		return path.exists() || path.createNewFile();
	}

	/**
	 * 创建文件夹，可递归创建
	 * 
	 * @param folderPath 文件夹路径
	 * @return true->已经创建
	 */
	@Override
	public boolean createFolder(String folderPath) {
		File path = new File(folderPath);
		return path.isDirectory() || path.mkdirs();
	}

	/**
	 * 删除文件
	 * 
	 * @param filePathAndName 文件路径
	 * @return true->删除成功
	 */
	@Override
	public boolean deleteFile(String filePathAndName) {
		File file = new File(filePathAndName);
		if (file.isFile()) { // Don't need file.exists()
			file.delete();
			return true;
		}
		return false;
	}

	/**
	 * 删除目录，支持递归
	 * 
	 * @param path 需要删除的目录
	 * @param recursive 是否递归删除
	 * @return false->非法目录
	 */
	@Override
	public boolean deleteFolder(String path, boolean recursive) {
		File thisFolder = new File(path);
		if (thisFolder.isDirectory()) {
			File[] childFiles = thisFolder.listFiles();
			if (childFiles != null && childFiles.length > 0) {
				for (File file : childFiles) {
					if (file.isDirectory()) {
						if (recursive) {
							String p = file.getAbsolutePath();
							deleteFolder(p, recursive);
						}
					} else {
						file.delete();
					}
				}
			}
			thisFolder.delete();
			return true;
		}
		return false;
	}

	/**
	 * 删除目录下所有文件，支持递归
	 * 
	 * @param path 需要删除的文件夹
	 * @param recursive 是否递归删除
	 * @return false->非法目录
	 */
	@Override
	public boolean deleteFilesInFloder(String path, boolean recursive) {
		File thisFolder = new File(path);
		if (thisFolder.isDirectory()) {
			File[] childFiles = thisFolder.listFiles();
			if (childFiles != null && childFiles.length > 0) {
				for (File file : childFiles) {
					if (file.isDirectory()) {
						if (recursive) {
							String p = file.getAbsolutePath();
							deleteFolder(p, recursive);
						}
					} else {
						file.delete();
					}
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * 获取文件名，不包含扩展名
	 * 
	 * @param fileName 文件名
	 * @return 不含扩展名的文件名
	 */
	@Override
	public String getFileBaseName(final String fileName) {
		if (fileName == null || !fileName.contains(".")) {
			return null;
		}
		return fileName.substring(0, fileName.lastIndexOf("."));
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @param fileName 文件名
	 * @return 此文件的扩展名
	 */
	@Override
	public String getFileExtension(final String fileName) {
		if (fileName == null || fileName.lastIndexOf(".") == -1
				|| fileName.lastIndexOf(".") == fileName.length() - 1) {
			return "";
		}
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}

	/**
	 * 复制文件
	 * 
	 * @param sourcePath 原文件路径
	 * @param targetPath 目标文件路径
	 * @return true->复制成功
	 */
	@Override
	public boolean copyFile(String sourcePath, String targetPath) {
		File sourcefile = new File(sourcePath);
		if (sourcefile.isFile()) {
			String dirPath = getDirPath(targetPath);
			if (dirPath != null) {
				boolean successful = createFolder(dirPath);
				if (successful) {
					InputStream is = null;
					FileOutputStream fos = null;
					try {
						is = new FileInputStream(sourcePath);
						fos = new FileOutputStream(targetPath);
						byte[] buffer = new byte[1024];
						int length;
						while ((length = is.read(buffer)) != -1) {
							fos.write(buffer, 0, length);
						}
						return true;
					} catch (IOException e) {
						Utils.debug(e.toString());
					} finally {
						try {
							if (fos != null) {
								fos.close();
								fos = null;
							}
							if (is != null) {
								is.close();
								is = null;
							}
						} catch (IOException e) {
							Utils.debug(e.toString());
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * 复制文件
	 * 
	 * @param src 源File文件
	 * @param dst 不表File文件
	 * @throws IOException
	 */
	@Override
	@SuppressWarnings("resource")
	public void copyFile(File src, File dst) throws IOException {
		FileChannel inChannel = new FileInputStream(src).getChannel();
		FileChannel outChannel = new FileOutputStream(dst).getChannel();
		try {
			inChannel.transferTo(0, inChannel.size(), outChannel);
		} finally {
			if (inChannel != null) {
				inChannel.close();
			}
			if (outChannel != null) {
				outChannel.close();
			}
		}
	}

	/**
	 * 从文件路径中提取目录路径
	 * 
	 * @param filePath 文件路径
	 * @return 文件所在的目录的路径
	 */
	@Override
	public String getDirPath(String filePath) {
		int separatePos = filePath.lastIndexOf('/');
		return separatePos == -1 ? null : filePath.substring(0, separatePos);
	}

	/**
	 * 获取文件名称
     *
     * @param filePath 文件路径
     * @return 文件名
	 */
	@Override
	public String getFileName(String filePath) {
		int separatePos = filePath.lastIndexOf('/');
		return separatePos == -1 ? null : filePath.substring(separatePos + 1);
	}

	/**
	 * 获取文件名称 不包含扩展名
	 * 
	 * @param path 文件路径
	 * @return 无扩展名的文件名
	 */
	@Override
	public String getFileNameWithoutSuffix(String path) {
		String name = getFileName(path);
		if (name.contains(".")) {
			return name.substring(0, name.lastIndexOf("."));
		} else {
			return name;
		}
	}

	/**
	 * 读取文本文件到字符串中
	 * 
	 * @param path 文件路径
	 * @return 文本内容的字符串
	 */
	@Override
	public String readTextFile(String path) {
		File file = new File(path);
		if (file.isFile()) {
			InputStream inputStream = null;
			BufferedReader buffReader = null;
			try {
				StringBuffer content = new StringBuffer();
				inputStream = new FileInputStream(file);
				if (inputStream != null) {
					buffReader = new BufferedReader(new InputStreamReader(inputStream));
					String line;
					while ((line = buffReader.readLine()) != null) {
                        // 此处在换行处加了换行符
                        if (content.length() > 0) {
                            content.append("\n");
                        }
						content.append(line);
					}
				}

				return content.toString();
			} catch (IOException e) {
				Utils.debug(e.toString());
			} finally {
				try {
					if (buffReader != null) {
						buffReader.close();
						buffReader = null;
					}
					if (inputStream != null) {
						inputStream.close();
						inputStream = null;
					}
				} catch (IOException e) {
					Utils.debug(e.toString());
				}
			}
		}
		return null;
	}

    /**
     * 向指定文件写入制定内容
     *
     * @param path 制定文件路径
     * @param content 指定内容
     */
    public void writeTextFile(String path, String content) {
        File file = new File(path);
        FileWriter fileWriter = null;
        BufferedWriter bufWriter = null;
        try {
            fileWriter = new FileWriter(file, true);
            bufWriter = new BufferedWriter(fileWriter);
            bufWriter.write(content);
            bufWriter.newLine();
        } catch (IOException e) {
            Utils.debug(e.toString());
        } finally {
            try {
                if (bufWriter != null) {
                    bufWriter.close();
                    bufWriter = null;
                }
                if (fileWriter != null) {
                    fileWriter.close();
                    fileWriter = null;
                }
            } catch (IOException e) {
                Utils.debug(e.toString());
            }
        }
    }

	/**
	 * 列出指定目录下的文件，支持过滤器和递归。 因为担心开发者在写FileFilter时不经意把当前目录下的子目录过滤掉，所以代码中做了特殊处理，
	 * 要求FileFilter中返回的筛选结果只能是文件。
	 * 
	 * @param path 指定要搜索的目录
	 * @param filter 文件过滤器
	 * @param recursive 是否递归过滤
	 * @return 符合要求的文件路径集合
	 */
	@Override
	public ArrayList<String> listFiles(String path, FileFilter filter,
			boolean recursive) {
		ArrayList<String> resultList = new ArrayList<String>();
		File thisPath = new File(path);
		if (thisPath.isDirectory()) {
			ArrayList<File> fileList = new ArrayList<File>();
			File[] files = thisPath.listFiles(filter);
			// 过滤掉结果中的目录
			if (files != null) {
				for (File f : files) {
					if (f.isFile()) {
						fileList.add(f);
					}
				}
			}
			// 过滤掉当前目录中的文件，仅获取目录
			files = thisPath.listFiles();
			if (files != null) {
				for (File f : files) {
					if (f.isDirectory()) {
						fileList.add(f);
					}
				}
			}
			files = null;
			for (File file : fileList) {
				if (file.isDirectory()) {
					if (recursive) {
						ArrayList<String> list = listFiles(
								file.getAbsolutePath(), filter, recursive);
						resultList.addAll(list);
					}
				} else {
					resultList.add(file.getAbsolutePath());
				}
			}
		}
		thisPath = null;
		return resultList;
	}

	/**
	 * 重命名文件
     *
     * @param pathOri 原文件路径
     * @param pathNew 新文件路径
	 */
	@Override
	public boolean renameFile(String pathOri, String pathNew) {
		File fOri = new File(pathOri);
		File fNew = new File(pathNew);
		if (fOri.exists() && !fNew.exists()) {
			return fOri.renameTo(fNew);
		} else {
			Utils.debug("Rename file failed!");
			return false;
		}
	}

	/**
	 * 获取URL中的文件名
     *
     * @param url 需要获取的URL
     * @return url中包含的文件名
	 */
	@Override
	public String getFileNameInUrl(String url) {
		if (Utils.isNull(url) || url.length() < 1) {
			return "";
		}
		String str = "?name=";
		if (url.contains(str)) {
			return url.substring(url.lastIndexOf(str) + str.length(),
					url.length());
		}
		if (url.contains("/")) {
			return url.substring(url.lastIndexOf("/") + 1, url.length());
		}
		return "";
	}

	/**
	 * 获取文件大小描述
     *
     * @param size 文件的Byte大小
     * @return 文件的大小以携带单位的可读式
	 */
	@Override
	public String getDataSizeTxt(Long size) {
		double sss;
		String unit;
		if (size < 1024) {
			sss = (double) size;
			unit = "B";
		} else if (size < 1024 * 1024) {
			sss = (double) (size * 1.0f / 1024);
			unit = "KB";
		} else if (size < 1024 * 1024 * 1024) {
			sss = (double) (size * 1.0f / 1024 / 1024);
			unit = "MB";
		} else {
			return "ERROR";
		}
		/** 保留两位小数点，四舍五入 */
		BigDecimal bigDecimal = new BigDecimal(sss);
		sss = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return sss + unit;
	}

	/**
	 * 保存Bitmap为图片文件，需要目标图片格式
     *
     * @param fullPath 图片文件的全路径
     * @param bitmap 源Bitmap
     * @param format 压缩格式
	 */
	@Override
	public void saveBitmap(String fullPath, Bitmap bitmap, CompressFormat format) {
		File file = new File(fullPath);
		saveBitmap(file, bitmap, Constant.IMAGE_QUALITY, format);
	}

	/**
	 * 保存Bitmap为图片，需要目标图片格式，图片质量
     *
     * @param fullPath 图片文件的全路径
     * @param bitmap 源Bitmap
     * @param quality 图片质量，在Constant中有定义
     * @param format 压缩格式
	 */
	@Override
	public void saveBitmap(String fullPath, Bitmap bitmap, int quality, CompressFormat format) {
		File file = new File(fullPath);
		saveBitmap(file, bitmap, quality, format);
	}

	/**
	 * 保存Bitmap为图片
     *
     * @param file 图片文件
     * @param bitmap 源Bitmap
     * @param quality 图片质量，在Constant中有定义
     * @param format 压缩格式
	 */
	@Override
	public void saveBitmap(File file, Bitmap bitmap, int quality, CompressFormat format) {
		if (null == bitmap) {
			return;
		}

		FileOutputStream out;

		try {
			out = new FileOutputStream(file);

			if (bitmap.compress(format, quality, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {
			Utils.debug("saveBitmap 1 : " + e.toString());
		} catch (IOException e) {
			Utils.debug("saveBitmap 2 : " + e.toString());
		}
	}

	/**
	 * 解码文件到Bitmap中
     *
     * @param f 需要获取Bitmap的文件
     * @return 文件的Bitmap形式
	 */
	@Override
	public Bitmap decodeFile(File f) {
		try {
			return BitmapFactory.decodeStream(new FileInputStream(f));
		} catch (FileNotFoundException e) {
			Utils.debug("decodeFile : " + e.toString());
			return null;
		}
	}

	/**
	 * 拷贝文件
	 * 
	 * @param source 原文件的InputStream
	 * @param dst 目标文件
	 */
	@Override
	public void copyFileFromAsset(InputStream source, File dst) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(dst);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = source.read(buffer)) != -1) {
				fos.write(buffer, 0, length);
			}
			fos.flush();
		} catch (Exception e) {
			Utils.debug("copyFileFromAsset : " + e.toString());
			dst.delete();
		} finally {
			if (null != fos) {
				try {
					fos.close();
				} catch (IOException e) {
					Utils.debug("copyFileFromAsset 1 : " + e.toString());
				}
			}
			if (null != source) {
				try {
					source.close();
				} catch (IOException e) {
					Utils.debug("copyFileFromAsset 2 : " + e.toString());
				}
			}

		}
	}

	/**
	 * 获取文件或者文件夹大小
	 * 
	 * @param path 文件路径
	 * @return size long类型 单位bytes
	 */
	@Override
	public long getFolderOrFileSize(String path) {
		File file = new File(path);
		long size = 0;
		File[] fileList = file.listFiles();
		for (int i = 0; i < fileList.length; i++) {
			if (fileList[i].isDirectory()) {
				size += getFolderOrFileSize(fileList[i].getPath());
			} else {
				size += fileList[i].length();
			}
		}
		return size;
	}

	/**
	 * 当缓存文件夹达到阈值，清理工作
	 * 
	 * @param millSec 删除距离millSec毫秒外的文件
	 */
	private void doCleanCache(long millSec, String path) {
		long curtime = System.currentTimeMillis();
		File file = new File(path);
		File[] fileList = file.listFiles();
		for (int i = 0; i < fileList.length; i++) {
			if (fileList[i].isDirectory()) {
				doCleanCache(millSec, fileList[i].getAbsolutePath());
			} else {
				if (fileList[i].lastModified() < curtime - millSec) {
					fileList[i].delete();
				}
			}
		}
	}

	/**
	 * 根据具体路径的大小和时间清理缓存文件
     *
	 * @param path 需要清理的路径
	 * @param size 单位bytes
	 * @param millSecAgo 删除距离millSecAgo毫秒外的文件
	 */
	@Override
	public void cleanCache(String path, long size, long millSecAgo) {
		if (getFolderOrFileSize(path) > size) {
			doCleanCache(millSecAgo, path);
		}
	}

	/**
	 * 创建屏蔽媒体扫描文件
     *
	 * @param path 创建的路径
	 */
	@Override
	public void createNoMediaFile(String path) {
		File file = new File(path);
		if (!file.exists() || !file.isDirectory()) {
			return;
		}
		File nomedia = new File(path + "/.nomedia");
		if (!nomedia.exists()) {
			try {
				nomedia.createNewFile();
			} catch (IOException e) {
				Utils.debug("createNoMedia : " + e.toString());
			}
		}
	}

	/**
	 * 获得Assets中文本文件的内容
	 *
	 * @param fileName 资源名
	 * @return 文本文件的内容
	 */
	public String getStringFromAssets(String fileName) {
		String result = "";
		try {
			InputStream in = context.getAssets().open(fileName);
			// 获取文件的字节数
			int length = in.available();
			// 创建byte数组
			byte[] buffer = new byte[length];
			// 将文件中的数据读到byte数组中
			in.read(buffer);
			result = EncodingUtils.getString(buffer, DEFAULT_ENCODING);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
