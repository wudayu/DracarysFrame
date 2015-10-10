package com.nfky.yaoyijia.image;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.Point;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nfky.yaoyijia.R;
import com.nfky.yaoyijia.constant.Constant;
import com.nfky.yaoyijia.constant.ImageLoaderHelper;
import com.nfky.yaoyijia.constant.ReqCode;
import com.nfky.yaoyijia.generic.DensityUtils;
import com.nfky.yaoyijia.handler.SdCardHandler;
import com.nfky.yaoyijia.generic.Utils;
import com.nfky.yaoyijia.handler.interfaces.ISdCardHandler;
import com.nfky.yaoyijia.handler.FileHandler;
import com.nfky.yaoyijia.handler.interfaces.IFileHandler;
import com.nfky.yaoyijia.handler.interfaces.IFileHandler.CacheDir;
import com.nfky.yaoyijia.image.component.ImageDownloaderWithAccessToken;
import com.nfky.yaoyijia.views.SelectPicPopupWindow;

/**
 *
 * Created by David on 8/25/15.
 *
 * UILImageHander是使用了Universal Image Loader来控制图片的单例类，它可以加载普通图片，加载圆角图片，加载圆形图片以及选择图片来源，对图片进行切割处理等功能
 *
 **/

public class UILImageHandler implements IImageHandler {

	private Context context = null;
	private ISdCardHandler sdCard = null;
	private IFileHandler fileHandler = null;

	ImageLoader imageLoader = null;
	ImageLoaderConfiguration defaultUilLoader = null;

	DisplayImageOptions defaultUilDisplay = null;
	DisplayImageOptions roundUilDisplay = null;
	DisplayImageOptions headerUilDisplay = null;

	String mImageCacheDir = null;

	/** Generate Singleton */
	private static volatile UILImageHandler instance;

	private UILImageHandler() {}

    public static IImageHandler getInstance(Context context) {
        if (instance == null) {
            synchronized (UILImageHandler.class) {
                if (instance == null) {
                    instance = new UILImageHandler();
                }
            }
        }

		instance.context = context;
		instance.sdCard = SdCardHandler.getInstance();
		instance.fileHandler = FileHandler.getInstance(context);

        return instance;
    }

	/**
	 * 初始化ImageLoader，在Application中执行
	 */
	@Override
	public void initImageLoader() {
		initUIL();

		if (imageLoader == null)
			imageLoader = ImageLoader.getInstance();
		if (!imageLoader.isInited())
			imageLoader.init(defaultUilLoader);

		mImageCacheDir = fileHandler.getCacheDirByType(CacheDir.IMAGE);
	}

	/**
	 * 清除所有图片缓存
	 */
	@Override
	public void cleanImageCache() {
		cleanImageCache(0, 0);
	}

	/**
	 * 清除图片缓存
	 *
	 * @param size 需要保留的大小
	 * @param millSecAgo 只保留millSecAgo毫秒内的图片
	 */
	@Override
	public void cleanImageCache(long size, long millSecAgo) {
		fileHandler.cleanCache(mImageCacheDir, size, millSecAgo);
	}

	/**
	 * 初始化ImageLoader的Display和Loader
	 */
	private void initUIL() {
		//普通图片的Display，用于没有特殊要求的图片加载
		defaultUilDisplay = new DisplayImageOptions.Builder()
//				.showImageOnLoading(R.drawable.default_houseimg) // resource or drawable
//		        .showImageForEmptyUri(R.drawable.default_houseimg) // resource or drawable
//		        .showImageOnFail(R.drawable.default_houseimg) // resource or drawable
		        // .resetViewBeforeLoading(false)  // default false
		        // .delayBeforeLoading(1000)
		        .cacheInMemory(false) // default false
		        .cacheOnDisk(true) // default false
		        // .preProcessor(...)
		        // .postProcessor(...)
		        // .extraForDownloader(...)
		        .considerExifParams(true) // default
		        .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default IN_SAMPLE_POWER_OF_2
		        .bitmapConfig(Bitmap.Config.RGB_565) // default 8888
		        // .decodingOptions(...)
		        .displayer(new FadeInBitmapDisplayer(500)) // default SimpleBitmapDisplayer
		        // .handler(new Handler()) // default
		        .build();

		// 圆角图片的Display，用于加载圆角的图片
		roundUilDisplay = new DisplayImageOptions.Builder()
//				.showImageOnLoading(R.drawable.default_houseimg)
//		        .showImageForEmptyUri(R.drawable.default_houseimg)
//		        .showImageOnFail(R.drawable.default_houseimg)
		        .cacheOnDisk(true)
		        .considerExifParams(true)
		        .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
		        .bitmapConfig(Bitmap.Config.RGB_565)
		        .displayer(new RoundedBitmapDisplayer(context.getResources().getDimensionPixelSize(R.dimen.round_corner_small)))
		        .build();

		// 头像图片的Display，用于加载圆形的图片
		headerUilDisplay = new DisplayImageOptions.Builder()
//				.showImageOnLoading(R.drawable.default_houseimg)
//		        .showImageForEmptyUri(R.drawable.default_houseimg)
//		        .showImageOnFail(R.drawable.default_houseimg)
		        .cacheOnDisk(true)
		        .considerExifParams(true)
		        .imageScaleType(ImageScaleType.EXACTLY)
		        .bitmapConfig(Bitmap.Config.ARGB_8888)
		        .displayer(new RoundedBitmapDisplayer(context.getResources().getDimensionPixelSize(R.dimen.round_corner_circle)))
		        .build();

		// 默认的Loader
		defaultUilLoader = new ImageLoaderConfiguration.Builder(context)
		.memoryCacheExtraOptions(DensityUtils.getScreenWidth(context), DensityUtils.getScreenHeight(context)) // default = device screen dimensions
        // .diskCacheExtraOptions(480, 800, null)
        // .taskExecutor(...)
        // .taskExecutorForCachedImages(...)
        // .threadPoolSize(3) // default
        .threadPriority(Thread.NORM_PRIORITY - 2) // default Thread.NORM_PRIORITY - 1
        .tasksProcessingOrder(QueueProcessingType.LIFO) // default FIFO
        .denyCacheImageMultipleSizesInMemory()
        .memoryCache(new WeakMemoryCache()) // default new LruMemoryCache(2 * 1024 * 1024)
        .memoryCacheSize(4 * 1024 * 1024) // default 2 * 1024 * 1024
        // .memoryCacheSizePercentage(13) // default
        .diskCache(new UnlimitedDiscCache(new File(ISdCardHandler.SD_IMAGE_CACHE))) // default
        // .diskCacheExtraOptions(maxImageWidthForDiskCache, maxImageHeightForDiskCache, processorForDiskCache)
        .diskCacheSize(128 * 1024 * 1024)
        // .diskCacheFileCount(100)
        .diskCacheFileNameGenerator(new Md5FileNameGenerator()) // default HashCodeFileNameGenerator
        .imageDownloader(new ImageDownloaderWithAccessToken(context)) // default
        // .imageDecoder(new BaseImageDecoder()) // default
        .defaultDisplayImageOptions(defaultUilDisplay) // default
        // .writeDebugLogs()
        .build();
	}

	/**
	 * 给ImageView加载圆形图片
	 *
	 * @param uri 图片资源所对应的URI
	 * @param imageView 需要加载到的ImageView容器
	 */
	@Override
	public void loadHeaderImage(String uri, ImageView imageView) {
		loadImage(uri, imageView, headerUilDisplay, null, null);
	}

	/**
	 * 给ImageView加载圆角图片
	 *
	 * @param uri 图片资源所对应的URI
	 * @param imageView 需要加载到的ImageView容器
	 */
	@Override
	public void loadRoundCornerImage(String uri, ImageView imageView) {
		loadRoundCornerImage(uri, imageView, null, null);
	}

	/**
	 * 给ImageView加载圆角图片
	 *
	 * @param uri 图片资源所对应的URI
	 * @param imageView 需要加载到的ImageView容器
	 * @param progressListener 进度监听器
	 */
	public void loadRoundCornerImage(String uri, ImageView imageView, ImageLoadingProgressListener progressListener) {
		loadRoundCornerImage(uri, imageView, null, progressListener);
	}

	/**
	 * 给ImageView加载圆角图片
	 *
	 * @param uri 图片资源所对应的URI
	 * @param imageView 需要加载到的ImageView容器
	 * @param loadingListener 加载事件监听器
	 * @param progressListener 进度监听器
	 */
	public void loadRoundCornerImage(String uri, ImageView imageView, ImageLoadingListener loadingListener, ImageLoadingProgressListener progressListener) {
		loadImage(uri, imageView, roundUilDisplay, loadingListener, progressListener);
	}

	/**
	 * 给ImageView加载普通图片
	 *
	 * @param uri 图片资源所对应的URI
	 * @param imageView 需要加载到的ImageView容器
	 */
	@Override
	public void loadImage(String uri, ImageView imageView) {
		loadImage(uri, imageView, null, null);
	}

	/**
	 * 给ImageView加载普通图片
	 *
	 * @param uri 图片资源所对应的URI
	 * @param imageView 需要加载到的ImageView容器
	 * @param progressListener 进度监听器
	 */
	@Override
	public void loadImage(String uri, ImageView imageView, ImageLoadingProgressListener progressListener) {
		loadImage(uri, imageView, defaultUilDisplay, null, progressListener);

		// the method that must be implemented in ImageLoadingProgressListener is like this:
		// void onProgressUpdate(String imageUri, View view, int current, int total);
	}

	/**
	 * 给ImageView加载普通图片
	 *
	 * @param uri 图片资源所对应的URI
	 * @param imageView 需要加载到的ImageView容器
	 * @param loadingListener 加载事件监听器
	 * @param progressListener 进度监听器
	 */
	@Override
	public void loadImage(String uri, ImageView imageView, ImageLoadingListener loadingListener, ImageLoadingProgressListener progressListener) {
		loadImage(uri, imageView, defaultUilDisplay, loadingListener, progressListener);
	}

	/**
	 * 给ImageView加载普通图片
	 *
	 * @param uri 图片资源所对应的URI
	 * @param imageView 需要加载到的ImageView容器
	 * @param options 自定义的Display
	 * @param outsideLoadingListener 加载事件监听器
	 * @param progressListener 进度监听器
	 */
	private void loadImage(String uri, ImageView imageView, DisplayImageOptions options, ImageLoadingListener outsideLoadingListener, ImageLoadingProgressListener progressListener) {
		// use image cache
		String fileName = fileHandler.getFileNameInUrl(uri);
		String fullPath = mImageCacheDir + fileName;
		if (fileHandler.isFileExists(fullPath)) {
			imageLoader.displayImage(ImageLoaderHelper.URI_PREFIX_FILE + fullPath, imageView, options, outsideLoadingListener, progressListener);
		} else {
			imageLoader.displayImage(uri, imageView, options, new SaveCacheImageLoadingListener(fullPath, outsideLoadingListener), progressListener);
		}
	}

	/**
	 * 自定义的图片缓存加载监听器
	 */
	public class SaveCacheImageLoadingListener implements ImageLoadingListener {

		String fullPath = null;
		ImageLoadingListener outsideLoadingListener = null;

		public SaveCacheImageLoadingListener(String fullPath, ImageLoadingListener outsideLoadingListener) {
			this.fullPath = fullPath;
			this.outsideLoadingListener = outsideLoadingListener;
		}

		/**
		 * Is called when image loading task was started
		 *
		 * @param imageUri Loading image URI
		 * @param view     View for image
		 */
		@Override
		public void onLoadingStarted(String imageUri, View view) {
			if (outsideLoadingListener != null)
				outsideLoadingListener.onLoadingStarted(imageUri, view);
		}
		/**
		 * Is called when an error was occurred during image loading
		 *
		 * @param imageUri   Loading image URI
		 * @param view       View for image. Can be null
		 * @param failReason {@linkplain com.nostra13.universalimageloader.core.assist.FailReason The reason} why image
		 *                   loading was failed
		 */
		@Override
		public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
			if (outsideLoadingListener != null)
				outsideLoadingListener.onLoadingFailed(imageUri, view, failReason);
		}

		/**
		 * Is called when image is loaded successfully (and displayed in View if one was specified)
		 *
		 * @param imageUri    Loaded image URI
		 * @param view        View for image. Can be null
		 * @param loadedImage Bitmap of loaded and decoded image
		 */
		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			fileHandler.saveBitmap(fullPath, loadedImage, CompressFormat.PNG);

			if (outsideLoadingListener != null)
				outsideLoadingListener.onLoadingComplete(imageUri, view, loadedImage);
		}

		/**
		 * Is called when image loading task was cancelled because View for image was reused in newer task
		 *
		 * @param imageUri Loading image URI
		 * @param view     View for image. Can be null
		 */
		@Override
		public void onLoadingCancelled(String imageUri, View view) {
			if (outsideLoadingListener != null)
				outsideLoadingListener.onLoadingCancelled(imageUri, view);
		}
	}

	/**
	 * 按照指定宽高缩放图片
	 * 
	 * @param bitmap 源Bitmap
	 * @param toW 想要的宽度
	 * @param toH 想要的高度
	 * @param scaleType 0：按照宽高缩放；1:按照宽度缩放；2:按照高度缩放
	 * @return 缩放后的Bitmap
	 */
	@Override
	public Bitmap resizeBitmap(Bitmap bitmap, float toW, float toH,
			int scaleType) {
		int bitmapW = bitmap.getWidth();
		int bitmapH = bitmap.getHeight();
		// 判断是否需要缩放
		if (toW == bitmapW && toH == bitmapH) {
			return bitmap;
		}

		Matrix matrix = new Matrix();
		float scaleW, scaleH;
		scaleW = toW / bitmapW;
		scaleH = toH / bitmapH;
		if (scaleType == 0) {
			matrix.postScale(scaleW, scaleH);
		} else if (scaleType == 1) {
			matrix.postScale(scaleW, scaleW);
		} else {
			matrix.postScale(scaleH, scaleH);
		}

		return Bitmap.createBitmap(bitmap, 0, 0, bitmapW, bitmapH, matrix, true);
	}

	/**
	 * 获取重新计算大小的Bitmap 最大边不超过max
	 * 
	 * @param path 图片路径
	 * @param max 最大边长度
	 * @return 新Bitmap
	 */
	@Override
	public Bitmap resizeBitmap(String path, int max) {
		int sample = 1;
		BitmapFactory.Options opts = new Options();
		opts.inJustDecodeBounds = true;
		opts.inSampleSize = sample;
		BitmapFactory.decodeFile(path, opts);
		int w = opts.outWidth;
		int h = opts.outHeight;
		if (Math.max(w, h) > max * 4) {
			sample = 8;
		} else if (Math.max(w, h) > max * 2) {
			sample = 4;
		} else if (Math.max(w, h) > max) {
			sample = 2;
		}
		opts.inPreferredConfig = Config.ARGB_8888;
		opts.inJustDecodeBounds = false;
		opts.inSampleSize = sample;
		try {
			return (sample == 1) ? BitmapFactory.decodeFile(path, opts)
					: getScaledBitmap(BitmapFactory.decodeFile(path, opts), max);
		} catch (OutOfMemoryError e) {
			Utils.debug(e.toString());
			opts.inSampleSize = sample * 2;
			try {
				return getScaledBitmap(BitmapFactory.decodeFile(path, opts),
						max);
			} catch (OutOfMemoryError e1) {
				try {
					Utils.debug(e1.toString());
					opts.inSampleSize = sample * 2;
					return getScaledBitmap(
							BitmapFactory.decodeFile(path, opts), max);
				} catch (Exception e2) {
					Utils.debug(e2.toString());
					return null;
				}
			}
		}
	}

	/**
	 * 获取缩放后的大小
	 *
	 * @param path 图片路径
	 * @param max 最大边长度
	 * @return 缩放后的大小
	 */
	@Override
	public Point getScaledSize(String path, int max) {
		BitmapFactory.Options opts = new Options();
		opts.inJustDecodeBounds = true;
		opts.inSampleSize = 1;
		BitmapFactory.decodeFile(path, opts);
		int w = opts.outWidth;
		int h = opts.outHeight;
		float scale;
		int destW;
		int destH;
		if (w > h) {
			scale = (max * 1.0f) / w;
			destW = max;
			destH = (int) (scale * h);
		} else {
			scale = (max * 1.0f) / h;
			destH = max;
			destW = (int) (scale * w);
		}
		return new Point(destW, destH);
	}

	/**
	 * 获取缩放之后的Bitmap
	 * 
	 * @param bitmap 源Bitmap
	 * @param max 最大边长度
	 * @return 缩放后的Bitmap
	 */
	private Bitmap getScaledBitmap(Bitmap bitmap, int max) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		float scale;
		int destW;
		int destH;
		if (w > h) {
			scale = (max * 1.0f) / w;
			destW = max;
			destH = (int) (scale * h);
		} else {
			scale = (max * 1.0f) / h;
			destH = max;
			destW = (int) (scale * w);
		}
		return Bitmap.createScaledBitmap(bitmap, destW, destH, true);
	}

	/**
	 * 从缓存文件中获取Bitmap
	 *
	 * @param url 源图片在网络上的路径
	 * @param defRes 默认图片资源
	 * @return 缓存文件的Bitmap
	 */
	@Override
	public Bitmap getBitmapFromCachedFile(String url, int defRes) {
		String path = fileHandler.getCacheDirByType(IFileHandler.CacheDir.IMAGE) + fileHandler.getFileNameInUrl(url);
		if (!fileHandler.isFileExists(path)) {
			return BitmapFactory.decodeResource(context.getResources(), defRes);
		}
		return BitmapFactory.decodeFile(path);
	}

	/**
	 * 判断原始素材的方向 （适用于相机拍摄的照片）
	 * 
	 * @param path 图片的地址
	 * @return angle->角度
	 */
	@Override
	public int isRotatedImage(String path) {
		int angle = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientationPhoto = exifInterface.getAttributeInt("Orientation",
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientationPhoto) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				angle = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				angle = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				angle = 270;
				break;
			default:
				angle = 0;
				break;
			}
		} catch (IOException e) {
			Utils.debug(e.toString());
		}
		return angle;
	}

	/**
	 * 旋转Bitmap
	 * 
	 * @param bitmap 需要旋转的Bitmap
	 * @param angle 旋转的角度，正数将顺时针旋转图像，负数将逆时针旋转图像
	 * @return 旋转后的Bitmap
	 */
	@Override
	public Bitmap rotateBitmap(Bitmap bitmap, int angle) {
		Matrix m = new Matrix();
		m.setRotate(angle);
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		try {
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, m, true);
		} catch (OutOfMemoryError oom) {
			try {
				m.postScale(1.0f, 1.0f);
				bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, m,
						true);
			} catch (Exception e) {
				Utils.debug(e.toString());
			}
		}
		return bitmap;
	}

	/**
	 * 获取Bitmap数据
	 * 
	 * @param source Bitmap数据源
	 * @return Bitmap的字节数据
	 */
	@Override
	public byte[] getBitmapData(Bitmap source) {
		if (source == null) {
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		source.compress(CompressFormat.JPEG, 80, baos);
		byte[] result = baos.toByteArray();
		if (null != baos) {
			try {
				baos.close();
			} catch (IOException e) {
				Utils.debug(e.toString());
			}
		}
		return result;
	}

	/**
	 * 压缩图片
	 *
	 * @param pathOri 源图片地址
	 * @param quality 图片压缩质量，使用Constant中的IMAGE_QUALITY
	 * @return 压缩完的图片的地址
	 */
	@Override
	public String compress(String pathOri, int quality) {
		String suffix;

		if (pathOri.endsWith(".png")) {
			suffix = ".png";
		} else {
			suffix = ".jpg";
		}

		int angle = isRotatedImage(pathOri);

		String tmpPath = mImageCacheDir + COMPRESSED_IMAGE_INITIAL
				+ System.currentTimeMillis() + suffix;

		Bitmap bitmap;

		CompressFormat format;

		if (pathOri.endsWith(".png")) {
			bitmap = resizeBitmap(pathOri, 1024);

			format = CompressFormat.PNG;
		} else {
			bitmap = resizeBitmap(pathOri, 1024);

			if (angle != 0) {
				bitmap = rotateBitmap(bitmap, angle);
			}

			format = CompressFormat.JPEG;
		}

		if (bitmap == null) {
			return "";
		}

		saveBitmap(new File(tmpPath), bitmap, quality, format);

		return tmpPath;
	}

	/**
	 * 保存Bitmap到文件
	 *
	 * @param file 需要保存到的文件File
	 * @param bitmap 需要保存的源Bitmap
	 * @param quality 图片压缩质量，使用Constant中的IMAGE_QUALITY
	 * @param format 压缩格式
	 * @return 是否压缩成功
	 */
	private boolean saveBitmap(File file, Bitmap bitmap, int quality,
			CompressFormat format) {
		if (sdCard.isSdcardAvailable()) {
			try {
				FileOutputStream out = new FileOutputStream(file);

				if (bitmap.compress(format, quality, out)) {
					out.flush();
					out.close();
					return true;
				}
			} catch (IOException e) {
				Utils.debug(e.toString());
			}
		} else {
			Utils.debug(context.getString(R.string.error_can_not_find_sdcard));
		}

		return false;
	}

	// 以下函数为图片获取到上传操作所可能涉及到的函数

	/**
	 * 获取一个新的图片路径
	 *
	 * @return 新的图片路径
	 */
	@Override
	public String getNewTmpImagePath() {
		return getNewTmpImagePath(SUFFIX_JPEG);
	}

	/**
	 * 获取一个新的图片路径
	 *
	 * @param imageSuffix 图片的扩展名，使用IImageHandler中的扩张名
	 * @return 新的图片路径
	 */
	@Override
	public String getNewTmpImagePath(String imageSuffix) {
		return mImageCacheDir + TMP_IMAGE_INITIAL + System.currentTimeMillis() + imageSuffix;
	}

	/**
	 * 选择获取图片方式
	 *
	 * @param activity 所在Activity
	 * @param hangView 挂载的View
	 * @param takePicturePath 将图片保存到的位置
	 */
	@Override
	public void selectGetImageWay(final Activity activity, View hangView, final String takePicturePath) {
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive()) {
			imm.hideSoftInputFromWindow(hangView.getWindowToken(), 0);
		}

		final SelectPicPopupWindow menuWindow = new SelectPicPopupWindow(activity);
		menuWindow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				menuWindow.dismiss();
				switch (v.getId()) {
				case R.id.btn_take_photo:
					// 跳转相机拍照
					String sdStatus = Environment.getExternalStorageState();
					if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
						Utils.toastMessage(activity, context.getString(R.string.error_can_not_find_sdcard));
						return;
					}
					Intent intentTakenPic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					// 指定调用相机拍照后的照片存储的路径
					intentTakenPic.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(takePicturePath)));
					activity.startActivityForResult(intentTakenPic, ReqCode.CAMERA);
					break;
				case R.id.btn_pick_photo:
					Intent intentPickPic = new Intent(Intent.ACTION_PICK, null);
					intentPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_CONTENT_TYPE);
					activity.startActivityForResult(intentPickPic, ReqCode.ALBUM);
					break;
				default:
					break;
				}
			}
		});
		// show the choose window
		menuWindow.showAtLocation(hangView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
	}

	/**
	 * 剪裁图片
	 *
	 * @param activity 操作所在的Activity
	 * @param uri 需要剪裁的图片所在的资源Uri
	 * @param cuttedImagePath 剪裁完成的图片的地址
	 * @param aspectX 宽（比例），若无比例设定为-1
	 * @param aspectY 高（比例），若无比例设定为-1
	 * @param outputX 宽的长度，若无要求设定为-1
	 * @param outputY 高的长度，若无要求设定为-1
	 * @param outputFormat 输出格式
	 */
	@Override
	public void cutTheImage(Activity activity, Uri uri, String cuttedImagePath, int aspectX, int aspectY, int outputX, int outputY, String outputFormat) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, IMAGE_CONTENT_TYPE);
		intent.putExtra("crop", "true");

		// aspectX aspectY 宽高的比例
		if (aspectX > 0)
			intent.putExtra("aspectX", aspectX);
		if (aspectY > 0)
			intent.putExtra("aspectY", aspectY);

		// outputX outputY 裁剪图片宽高
		if (outputX > 0)
			intent.putExtra("outputX", outputX);
		if (outputY > 0)
			intent.putExtra("outputY", outputY);

		intent.putExtra("outputFormat", FORMAT_IN_STRING_JPEG);
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(cuttedImagePath)));

		activity.startActivityForResult(intent, ReqCode.CUTTED);
	}

	/**
	 * 剪裁普通图片
	 *
	 * @param activity 操作所在的Activity
	 * @param uri 需要剪裁的图片所在的资源Uri
	 * @param cuttedImagePath 剪裁完成的图片的地址
	 */
	@Override
	public void cutTheImageNormal(Activity activity, Uri uri, String cuttedImagePath) {
		cutTheImage(activity, uri, cuttedImagePath, -1, -1, -1, -1, FORMAT_IN_STRING_JPEG);
	}

	/**
	 * 剪裁头像图片，宽高比为1:1
	 *
	 * @param activity 操作所在的Activity
	 * @param uri 需要剪裁的图片所在的资源Uri
	 * @param cuttedImagePath 剪裁完成的图片的地址
	 */
	@Override
	public void cutTheImageHead(Activity activity, Uri uri, String cuttedImagePath) {
		cutTheImage(activity, uri, cuttedImagePath, 1, 1, -1, -1, FORMAT_IN_STRING_JPEG);
	}

	/**
	 * 压缩图片
	 *
	 * @param imagePath 需要压缩的图片
	 * @return 压缩后的图片路径
	 */
	@Override
	public String compressImage(String imagePath) {
		return compressImage(imagePath, Constant.IMAGE_QUALITY);
	}

	/**
	 * 压缩图片
	 *
	 * @param imagePath 需要压缩的图片
	 * @param quality 图片压缩质量，使用Constant中的IMAGE_QUALITY
	 * @return 压缩后的图片路径
	 */
	@Override
	public String compressImage(String imagePath, int quality) {
		return compress(imagePath, quality);
	}

}
