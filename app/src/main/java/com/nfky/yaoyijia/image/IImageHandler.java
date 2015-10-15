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
 * Created by David on 8/25/15.
 * <p/>
 * IImageHandler是图片处理者的接口类
 */
public interface IImageHandler {

	/**
	 * The constant TMP_IMAGE_INITIAL.
	 */
	String TMP_IMAGE_INITIAL = "TMP_IMG_";
	/**
	 * The constant COMPRESSED_IMAGE_INITIAL.
	 */
	String COMPRESSED_IMAGE_INITIAL = "COMPRESSED_IMG_";

	/**
	 * The constant IMAGE_CONTENT_TYPE.
	 */
	String IMAGE_CONTENT_TYPE = "image/*";

	/**
	 * The constant FORMAT_IN_STRING_JPEG.
	 */
	String FORMAT_IN_STRING_JPEG = "JPEG";
	/**
	 * The constant FORMAT_IN_STRING_PNG.
	 */
	String FORMAT_IN_STRING_PNG = "PNG";
	/**
	 * The constant SUFFIX_JPEG.
	 */
	String SUFFIX_JPEG = ".jpg";
	/**
	 * The constant SUFFIX_PNG.
	 */
	String SUFFIX_PNG = ".png";

	/**
	 * The constant IMAGE_LOAD_NORMAL.
	 */
	int IMAGE_LOAD_NORMAL = 0x101;
	/**
	 * The constant IMAGE_LOAD_ROUND_CORNER.
	 */
	int IMAGE_LOAD_ROUND_CORNER = 0x102;
	/**
	 * The constant IMAGE_LOAD_HEAD.
	 */
	int IMAGE_LOAD_HEAD = 0x103;


	/**
	 * Resize bitmap bitmap.
	 *
	 * @param bitmap    the bitmap
	 * @param toW       the to w
	 * @param toH       the to h
	 * @param scaleType the scale type
	 * @return the bitmap
	 */
	Bitmap resizeBitmap(Bitmap bitmap, float toW, float toH,
			int scaleType);

	/**
	 * Resize bitmap bitmap.
	 *
	 * @param path the path
	 * @param max  the max
	 * @return the bitmap
	 */
	Bitmap resizeBitmap(String path, int max);

	/**
	 * Gets scaled size.
	 *
	 * @param path the path
	 * @param max  the max
	 * @return the scaled size
	 */
	Point getScaledSize(String path, int max);

	/**
	 * Gets bitmap from cached file.
	 *
	 * @param url    the url
	 * @param defRes the def res
	 * @return the bitmap from cached file
	 */
	Bitmap getBitmapFromCachedFile(String url, int defRes);

	/**
	 * Is rotated image int.
	 *
	 * @param path the path
	 * @return the int
	 */
	int isRotatedImage(String path);

	/**
	 * Rotate bitmap bitmap.
	 *
	 * @param bitmap the bitmap
	 * @param angle  the angle
	 * @return the bitmap
	 */
	Bitmap rotateBitmap(Bitmap bitmap, int angle);

	/**
	 * Get bitmap data byte [ ].
	 *
	 * @param source the source
	 * @return the byte [ ]
	 */
	byte[] getBitmapData(Bitmap source);

	/**
	 * Compress string.
	 *
	 * @param pathOri the path ori
	 * @param quality the quality
	 * @return the string
	 */
	String compress(String pathOri, int quality);

	/**
	 * Clean image cache.
	 */
	void cleanImageCache();

	/**
	 * Clean image cache.
	 *
	 * @param size       the size
	 * @param millSecAgo the mill sec ago
	 */
	void cleanImageCache(long size, long millSecAgo);

	/**
	 * Init image loader.
	 */
	void initImageLoader();

	/**
	 * Load image.
	 *
	 * @param uri       the uri
	 * @param imageView the image view
	 */
	void loadImage(String uri, ImageView imageView);

	/**
	 * Load image.
	 *
	 * @param uri              the uri
	 * @param imageView        the image view
	 * @param progresslistener the progresslistener
	 */
	void loadImage(String uri, ImageView imageView, ImageLoadingProgressListener progresslistener);

	/**
	 * Load image.
	 *
	 * @param uri              the uri
	 * @param imageView        the image view
	 * @param loadingListener  the loading listener
	 * @param progresslistener the progresslistener
	 */
	void loadImage(String uri, ImageView imageView, ImageLoadingListener loadingListener, ImageLoadingProgressListener progresslistener);

	/**
	 * Load round corner image.
	 *
	 * @param uri       the uri
	 * @param imageView the image view
	 */
	void loadRoundCornerImage(String uri, ImageView imageView);

	/**
	 * Load round corner image.
	 *
	 * @param uri              the uri
	 * @param imageView        the image view
	 * @param progresslistener the progresslistener
	 */
	void loadRoundCornerImage(String uri, ImageView imageView, ImageLoadingProgressListener progresslistener);

	/**
	 * Load round corner image.
	 *
	 * @param uri              the uri
	 * @param imageView        the image view
	 * @param loadingListener  the loading listener
	 * @param progresslistener the progresslistener
	 */
	void loadRoundCornerImage(String uri, ImageView imageView, ImageLoadingListener loadingListener, ImageLoadingProgressListener progresslistener);

	/**
	 * Load header image.
	 *
	 * @param uri       the uri
	 * @param imageView the image view
	 */
	void loadHeaderImage(String uri, ImageView imageView);

	/**
	 * Gets new tmp image path.
	 *
	 * @return the new tmp image path
	 */
	String getNewTmpImagePath();

	/**
	 * Gets new tmp image path.
	 *
	 * @param imageSuffix the image suffix
	 * @return the new tmp image path
	 */
	String getNewTmpImagePath(String imageSuffix);

	/**
	 * Select get image way.
	 *
	 * @param activity        the activity
	 * @param hangView        the hang view
	 * @param takePicturePath the take picture path
	 */
	void selectGetImageWay(final Activity activity, View hangView, final String takePicturePath);

	/**
	 * Cut the image.
	 *
	 * @param activity        the activity
	 * @param uri             the uri
	 * @param cuttedImagePath the cutted image path
	 * @param aspectX         the aspect x
	 * @param aspectY         the aspect y
	 * @param outputX         the output x
	 * @param outputY         the output y
	 * @param outputFormat    the output format
	 */
	void cutTheImage(Activity activity, Uri uri, String cuttedImagePath, int aspectX, int aspectY, int outputX, int outputY, String outputFormat);

	/**
	 * Cut the image normal.
	 *
	 * @param activity        the activity
	 * @param uri             the uri
	 * @param cuttedImagePath the cutted image path
	 */
	void cutTheImageNormal(Activity activity, Uri uri, String cuttedImagePath);

	/**
	 * Cut the image head.
	 *
	 * @param activity        the activity
	 * @param uri             the uri
	 * @param cuttedImagePath the cutted image path
	 */
	void cutTheImageHead(Activity activity, Uri uri, String cuttedImagePath);

	/**
	 * Compress image string.
	 *
	 * @param imagePath the image path
	 * @return the string
	 */
	String compressImage(String imagePath);

	/**
	 * Compress image string.
	 *
	 * @param imagePath the image path
	 * @param quality   the quality
	 * @return the string
	 */
	String compressImage(String imagePath, int quality);

}
