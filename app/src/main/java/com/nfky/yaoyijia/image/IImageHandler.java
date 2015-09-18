package com.nfky.yaoyijia.image;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

/**
 *
 * Created by David on 8/25/15.
 *
 * IImageHandler是图片处理者的接口类
 *
 **/

public interface IImageHandler {

	String TMP_IMAGE_INITIAL = "TMP_IMG_";
	String COMPRESSED_IMAGE_INITIAL = "COMPRESSED_IMG_";

	String IMAGE_CONTENT_TYPE = "image/*";

	String FORMAT_IN_STRING_JPEG = "JPEG";
	String FORMAT_IN_STRING_PNG = "PNG";
	String SUFFIX_JPEG = ".jpg";
	String SUFFIX_PNG = ".png";

	int IMAGE_LOAD_NORMAL = 0x101;
	int IMAGE_LOAD_ROUND_CORNER = 0x102;
	int IMAGE_LOAD_HEAD = 0x103;


	Bitmap resizeBitmap(Bitmap bitmap, float toW, float toH,
			int scaleType);

	Bitmap resizeBitmap(String path, int max);

	Point getScaledSize(String path, int max);

	Bitmap getBitmapFromCachedFile(String url, int defRes);

	int isRotatedImage(String path);

	Bitmap rotateBitmap(Bitmap bitmap, int angle);

	byte[] getBitmapData(Bitmap source);

	String compress(String pathOri, int quality);

	void cleanImageCache();

	void cleanImageCache(long size, long millSecAgo);

	void initImageLoader();

	void loadImage(String uri, ImageView imageView);

	void loadImage(String uri, ImageView imageView, ImageLoadingProgressListener progresslistener);

	void loadImage(String uri, ImageView imageView, ImageLoadingListener loadingListener, ImageLoadingProgressListener progresslistener);

	void loadRoundCornerImage(String uri, ImageView imageView);

	void loadRoundCornerImage(String uri, ImageView imageView, ImageLoadingProgressListener progresslistener);

	void loadRoundCornerImage(String uri, ImageView imageView, ImageLoadingListener loadingListener, ImageLoadingProgressListener progresslistener);

	void loadHeaderImage(String uri, ImageView imageView);

	String getNewTmpImagePath();

	String getNewTmpImagePath(String imageSuffix);

	void selectGetImageWay(final Activity activity, View hangView, final String takePicturePath);

	void cutTheImage(Activity activity, Uri uri, String cuttedImagePath, int aspectX, int aspectY, int outputX, int outputY, String outputFormat);

	void cutTheImageNormal(Activity activity, Uri uri, String cuttedImagePath);

	void cutTheImageHead(Activity activity, Uri uri, String cuttedImagePath);

	String compressImage(String imagePath);

	String compressImage(String imagePath, int quality);

}
