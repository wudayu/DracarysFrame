package com.nfky.yaoyijia.views.imagezoom;

import java.io.InputStream;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

/**
 * Created by David on 8/27/15.
 * <p/>
 * imagezoom整个包都是用来支持可控制放大缩小的GestureImageView的控件，可以直接将ImageView用GestureImageView替换
 */
public class GestureImageView extends ImageView  {

	/**
	 * The constant GLOBAL_NS.
	 */
	public static final String GLOBAL_NS = "http://schemas.android.com/apk/res/android";
	/**
	 * The constant LOCAL_NS.
	 */
	public static final String LOCAL_NS = "http://schemas.polites.com/android";

	private final Semaphore drawLock = new Semaphore(0);
	private Animator animator;

	private Drawable drawable;

	private float x = 0, y = 0;

	private boolean layout = false;

	private float scaleAdjust = 1.0f;
	private float startingScale = -1.0f;

	private float scale = 1.0f;
	private float maxScale = 5.0f;
	private float minScale = 0.75f;
	private float fitScaleHorizontal = 1.0f;
	private float fitScaleVertical = 1.0f;
	private float rotation = 0.0f;

	private float centerX;
	private float centerY;
	
	private Float startX, startY;

	private int hWidth;
	private int hHeight;

	private int resId = -1;
	private boolean recycle = false;
	private boolean strict = false;

	private int displayHeight;
	private int displayWidth;

	private int alpha = 255;
	private ColorFilter colorFilter;

	private int deviceOrientation = -1;
	private int imageOrientation;

	private GestureImageViewListener gestureImageViewListener;
	private GestureImageViewTouchListener gestureImageViewTouchListener;
	
	private OnTouchListener customOnTouchListener;
	private OnClickListener onClickListener;

	/**
	 * Instantiates a new Gesture image view.
	 *
	 * @param context  the context
	 * @param attrs    the attrs
	 * @param defStyle the def style
	 */
	public GestureImageView(Context context, AttributeSet attrs, int defStyle) {
		this(context, attrs);
	}

	/**
	 * Instantiates a new Gesture image view.
	 *
	 * @param context the context
	 * @param attrs   the attrs
	 */
	public GestureImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		String scaleType = attrs.getAttributeValue(GLOBAL_NS, "scaleType");
		
		if(scaleType == null || scaleType.trim().length() == 0) {
			setScaleType(ScaleType.CENTER_INSIDE);
		}
		
		String strStartX = attrs.getAttributeValue(LOCAL_NS, "start-x");
		String strStartY = attrs.getAttributeValue(LOCAL_NS, "start-y");
		
		if(strStartX != null && strStartX.trim().length() > 0) {
			startX = Float.parseFloat(strStartX);
		}
		
		if(strStartY != null && strStartY.trim().length() > 0) {
			startY = Float.parseFloat(strStartY);
		}
		
		setStartingScale(attrs.getAttributeFloatValue(LOCAL_NS, "start-scale", startingScale));
		setMinScale(attrs.getAttributeFloatValue(LOCAL_NS, "min-scale", minScale));
		setMaxScale(attrs.getAttributeFloatValue(LOCAL_NS, "max-scale", maxScale));
		setStrict(attrs.getAttributeBooleanValue(LOCAL_NS, "strict", strict));
		setRecycle(attrs.getAttributeBooleanValue(LOCAL_NS, "recycle", recycle));
		
		initImage();
	}

	/**
	 * Instantiates a new Gesture image view.
	 *
	 * @param context the context
	 */
	public GestureImageView(Context context) {
		super(context);
		setScaleType(ScaleType.CENTER_INSIDE);
		initImage();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		if(drawable != null) {
			int orientation = getResources().getConfiguration().orientation;
			if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
				displayHeight = MeasureSpec.getSize(heightMeasureSpec);

				if(getLayoutParams().width == LayoutParams.WRAP_CONTENT) {
					float ratio = (float) getImageWidth() / (float) getImageHeight();
					displayWidth = Math.round( (float) displayHeight * ratio) ;
				}
				else {
					displayWidth = MeasureSpec.getSize(widthMeasureSpec);
				}
			}
			else {
				displayWidth = MeasureSpec.getSize(widthMeasureSpec);
				if(getLayoutParams().height == LayoutParams.WRAP_CONTENT) {
					float ratio = (float) getImageHeight() / (float) getImageWidth();
					displayHeight = Math.round( (float) displayWidth * ratio) ;
				}
				else {
					displayHeight = MeasureSpec.getSize(heightMeasureSpec);
				}				
			}
		}
		else {
			displayHeight = MeasureSpec.getSize(heightMeasureSpec);
			displayWidth = MeasureSpec.getSize(widthMeasureSpec);
		}

		setMeasuredDimension(displayWidth, displayHeight);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		if(changed || !layout) {
			setupCanvas(displayWidth, displayHeight, getResources().getConfiguration().orientation);
		}
	}

	/**
	 * Sets canvas.
	 *
	 * @param measuredWidth  the measured width
	 * @param measuredHeight the measured height
	 * @param orientation    the orientation
	 */
	protected void setupCanvas(int measuredWidth, int measuredHeight, int orientation) {

		if(deviceOrientation != orientation) {
			layout = false;
			deviceOrientation = orientation;
		}

		if(drawable != null && !layout) {
			int imageWidth = getImageWidth();
			int imageHeight = getImageHeight();

			hWidth = Math.round(((float)imageWidth / 2.0f));
			hHeight = Math.round(((float)imageHeight / 2.0f));
			
			measuredWidth -= (getPaddingLeft() + getPaddingRight());
			measuredHeight -= (getPaddingTop() + getPaddingBottom());
			
			computeCropScale(imageWidth, imageHeight, measuredWidth, measuredHeight);
			
			if(startingScale <= 0.0f) {
				computeStartingScale(imageWidth, imageHeight, measuredWidth, measuredHeight);
			}

			scaleAdjust = startingScale;

			this.centerX = (float) measuredWidth / 2.0f;
			this.centerY = (float) measuredHeight / 2.0f;
			
			if(startX == null) {
				x = centerX;
			}
			else {
				x = startX;
			}

			if(startY == null) {
				y = centerY;
			}
			else {
				y = startY;
			}	

			gestureImageViewTouchListener = new GestureImageViewTouchListener(this, measuredWidth, measuredHeight);
			
			if(isLandscape()) {
				gestureImageViewTouchListener.setMinScale(minScale * fitScaleHorizontal);
			}
			else {
				gestureImageViewTouchListener.setMinScale(minScale * fitScaleVertical);
			}
			
			
			gestureImageViewTouchListener.setMaxScale(maxScale * startingScale);
			
			gestureImageViewTouchListener.setFitScaleHorizontal(fitScaleHorizontal);
			gestureImageViewTouchListener.setFitScaleVertical(fitScaleVertical);
			gestureImageViewTouchListener.setCanvasWidth(measuredWidth);
			gestureImageViewTouchListener.setCanvasHeight(measuredHeight);
			gestureImageViewTouchListener.setOnClickListener(onClickListener);

			drawable.setBounds(-hWidth,-hHeight,hWidth,hHeight);

			super.setOnTouchListener(new OnTouchListener() {
				@SuppressLint("ClickableViewAccessibility")
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if(customOnTouchListener != null) {
						customOnTouchListener.onTouch(v, event);
					}
					return gestureImageViewTouchListener.onTouch(v, event);
				}
			});	

			layout = true;
		}
	}

	/**
	 * Compute crop scale.
	 *
	 * @param imageWidth     the image width
	 * @param imageHeight    the image height
	 * @param measuredWidth  the measured width
	 * @param measuredHeight the measured height
	 */
	protected void computeCropScale(int imageWidth, int imageHeight, int measuredWidth, int measuredHeight) {
		fitScaleHorizontal = (float) measuredWidth / (float) imageWidth;
		fitScaleVertical = (float) measuredHeight / (float) imageHeight;
	}

	/**
	 * Compute starting scale.
	 *
	 * @param imageWidth     the image width
	 * @param imageHeight    the image height
	 * @param measuredWidth  the measured width
	 * @param measuredHeight the measured height
	 */
	@SuppressWarnings("incomplete-switch")
	protected void computeStartingScale(int imageWidth, int imageHeight, int measuredWidth, int measuredHeight) {
		switch(getScaleType()) {
			case CENTER:
				// Center the image in the view, but perform no scaling.
				startingScale = 1.0f;
				break;
				
			case CENTER_CROP:
				// Scale the image uniformly (maintain the image's aspect ratio) so that both dimensions
				// (width and height) of the image will be equal to or larger than the corresponding dimension of the view (minus padding).
				startingScale = Math.max((float) measuredHeight / (float) imageHeight, (float) measuredWidth/ (float) imageWidth);
				break;
				
			case CENTER_INSIDE:

				// Scale the image uniformly (maintain the image's aspect ratio) so that both dimensions
				// (width and height) of the image will be equal to or less than the corresponding dimension of the view (minus padding).
				float wRatio = (float) imageWidth / (float) measuredWidth;
				float hRatio = (float) imageHeight / (float) measuredHeight;

				if(wRatio > hRatio) {
					startingScale = fitScaleHorizontal;
				}
				else {
					startingScale = fitScaleVertical;
				}

				break;
		}
	}

	/**
	 * Is recycled boolean.
	 *
	 * @return the boolean
	 */
	protected boolean isRecycled() {
		if(drawable != null && drawable instanceof BitmapDrawable) {
			Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
			if(bitmap != null) {
				return bitmap.isRecycled();
			}
		}
		return false;
	}

	/**
	 * Recycle.
	 */
	protected void recycle() {
		if(recycle && drawable != null && drawable instanceof BitmapDrawable) {
			Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
			if(bitmap != null) {
				bitmap.recycle();
			}
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		if(layout) {
			if(drawable != null && !isRecycled()) {
				canvas.save();
				
				float adjustedScale = scale * scaleAdjust;

				canvas.translate(x, y);

				if(rotation != 0.0f) {
					canvas.rotate(rotation);
				}

				if(adjustedScale != 1.0f) {
					canvas.scale(adjustedScale, adjustedScale);
				}

				drawable.draw(canvas);

				canvas.restore();
			}

			if(drawLock.availablePermits() <= 0) {
				drawLock.release();
			}
		}
	}

	/**
	 * Waits for a draw
	 *
	 * @param timeout max time to wait for draw (ms)
	 * @return the boolean
	 * @throws InterruptedException the interrupted exception
	 */
	public boolean waitForDraw(long timeout) throws InterruptedException {
		return drawLock.tryAcquire(timeout, TimeUnit.MILLISECONDS);
	}

	@Override
	protected void onAttachedToWindow() {
		animator = new Animator(this, "GestureImageViewAnimator");
		animator.start();

		if(resId >= 0 && drawable == null) {
			setImageResource(resId);
		}

		super.onAttachedToWindow();
	}

	/**
	 * Animation start.
	 *
	 * @param animation the animation
	 */
	public void animationStart(Animation animation) {
		if(animator != null) {
			animator.play(animation);
		}
	}

	/**
	 * Animation stop.
	 */
	public void animationStop() {
		if(animator != null) {
			animator.cancel();
		}
	}

	@Override
	protected void onDetachedFromWindow() {
		if(animator != null) {
			animator.finish();
		}
		if(recycle && drawable != null && !isRecycled()) {
			recycle();
			drawable = null;
		}
		super.onDetachedFromWindow();
	}

	/**
	 * Init image.
	 */
	protected void initImage() {
		if(this.drawable != null) {
			this.drawable.setAlpha(alpha);
			this.drawable.setFilterBitmap(true);
			if(colorFilter != null) {
				this.drawable.setColorFilter(colorFilter);
			}
		}
		
		if(!layout) {
			requestLayout();
			redraw();
		}
	}

	public void setImageBitmap(Bitmap image) {
		this.drawable = new BitmapDrawable(getResources(), image);
		initImage();
	}

	@Override
	public void setImageDrawable(Drawable drawable) {
		this.drawable = drawable;
		initImage();
	}

	public void setImageResource(int id) {
		if(this.drawable != null) {
			this.recycle();
		}
		if(id >= 0) {
			this.resId = id;
			setImageDrawable(getContext().getResources().getDrawable(id));
		}
	}

	/**
	 * Gets scaled width.
	 *
	 * @return the scaled width
	 */
	public int getScaledWidth() {
		return Math.round(getImageWidth() * getScale());
	}

	/**
	 * Gets scaled height.
	 *
	 * @return the scaled height
	 */
	public int getScaledHeight() {
		return Math.round(getImageHeight() * getScale());
	}

	/**
	 * Gets image width.
	 *
	 * @return the image width
	 */
	public int getImageWidth() {
		if(drawable != null) {
			return drawable.getIntrinsicWidth();
		}
		return 0;
	}

	/**
	 * Gets image height.
	 *
	 * @return the image height
	 */
	public int getImageHeight() {
		if(drawable != null) {
			return drawable.getIntrinsicHeight();
		}
		return 0;
	}

	/**
	 * Move by.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public void moveBy(float x, float y) {
		this.x += x;
		this.y += y;
	}

	/**
	 * Sets position.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Redraw.
	 */
	public void redraw() {
		postInvalidate();
	}

	/**
	 * Sets min scale.
	 *
	 * @param min the min
	 */
	public void setMinScale(float min) {
		this.minScale = min;
		if(gestureImageViewTouchListener != null) {
			gestureImageViewTouchListener.setMinScale(min * fitScaleHorizontal);
		}
	}

	/**
	 * Sets max scale.
	 *
	 * @param max the max
	 */
	public void setMaxScale(float max) {
		this.maxScale = max;
		if(gestureImageViewTouchListener != null) {
			gestureImageViewTouchListener.setMaxScale(max * startingScale);
		}
	}

	/**
	 * Sets scale.
	 *
	 * @param scale the scale
	 */
	public void setScale(float scale) {
		scaleAdjust = scale;
	}

	/**
	 * Gets scale.
	 *
	 * @return the scale
	 */
	public float getScale() {
		return scaleAdjust;
	}

	/**
	 * Gets image x.
	 *
	 * @return the image x
	 */
	public float getImageX() {
		return x;
	}

	/**
	 * Gets image y.
	 *
	 * @return the image y
	 */
	public float getImageY() {
		return y;
	}

	/**
	 * Is strict boolean.
	 *
	 * @return the boolean
	 */
	public boolean isStrict() {
		return strict;
	}

	/**
	 * Sets strict.
	 *
	 * @param strict the strict
	 */
	public void setStrict(boolean strict) {
		this.strict = strict;
	}

	/**
	 * Is recycle boolean.
	 *
	 * @return the boolean
	 */
	public boolean isRecycle() {
		return recycle;
	}

	/**
	 * Sets recycle.
	 *
	 * @param recycle the recycle
	 */
	public void setRecycle(boolean recycle) {
		this.recycle = recycle;
	}

	/**
	 * Reset.
	 */
	public void reset() {
		x = centerX;
		y = centerY;
		scaleAdjust = startingScale;
		if (gestureImageViewTouchListener != null) {
		    gestureImageViewTouchListener.reset();
		}
		redraw();
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	/**
	 * Sets gesture image view listener.
	 *
	 * @param pinchImageViewListener the pinch image view listener
	 */
	public void setGestureImageViewListener(GestureImageViewListener pinchImageViewListener) {
		this.gestureImageViewListener = pinchImageViewListener;
	}

	/**
	 * Gets gesture image view listener.
	 *
	 * @return the gesture image view listener
	 */
	public GestureImageViewListener getGestureImageViewListener() {
		return gestureImageViewListener;
	}

	@Override
	public Drawable getDrawable() {
		return drawable;
	}

	@Override
	public void setAlpha(int alpha) {
		this.alpha = alpha;
		if(drawable != null) {
			drawable.setAlpha(alpha);
		}
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		this.colorFilter = cf;
		if(drawable != null) {
			drawable.setColorFilter(cf);
		}
	}

	@Override
	public void setImageURI(Uri mUri) {
		if ("content".equals(mUri.getScheme())) {
			try {
				String[] orientationColumn = {MediaStore.Images.Media.ORIENTATION};
				
				Cursor cur = getContext().getContentResolver().query(mUri, orientationColumn, null, null, null);
				
				if (cur != null && cur.moveToFirst()) {
					imageOrientation = cur.getInt(cur.getColumnIndex(orientationColumn[0]));
				}  
				
				InputStream in = null;
				
				try {
					in = getContext().getContentResolver().openInputStream(mUri);
					Bitmap bmp = BitmapFactory.decodeStream(in);
					
					if(imageOrientation != 0) {
						Matrix m = new Matrix();
						m.postRotate(imageOrientation);
						Bitmap rotated = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), m, true);
						bmp.recycle();
						setImageDrawable(new BitmapDrawable(getResources(), rotated));
					}
					else {
						setImageDrawable(new BitmapDrawable(getResources(), bmp));
					}
				}
				finally {
					if(in != null) {
						in.close();
					}
					
					if(cur != null) {
						cur.close();
					}
				}
			}
			catch (Exception e) {
				Log.w("GestureImageView", "Unable to open content: " + mUri, e);
			}
		}
		else {
			setImageDrawable(Drawable.createFromPath(mUri.toString()));
		}

		if (drawable == null) {
			Log.e("GestureImageView", "resolveUri failed on bad bitmap uri: " + mUri);
			// Don't try again.
			mUri = null;
		}
	}

	@Override
	public Matrix getImageMatrix() {
		if(strict) {
			throw new UnsupportedOperationException("Not supported");
		}		
		return super.getImageMatrix();
	}

	@Override
	public void setScaleType(ScaleType scaleType) {
		if(scaleType == ScaleType.CENTER ||
			scaleType == ScaleType.CENTER_CROP ||
			scaleType == ScaleType.CENTER_INSIDE) {
			
			super.setScaleType(scaleType);
		}
		else if(strict) {
			throw new UnsupportedOperationException("Not supported");
		}
	}

	@Override
	public void invalidateDrawable(Drawable dr) {
		if(strict) {
			throw new UnsupportedOperationException("Not supported");
		}
		super.invalidateDrawable(dr);
	}

	@Override
	public int[] onCreateDrawableState(int extraSpace) {
		if(strict) {
			throw new UnsupportedOperationException("Not supported");
		}
		return super.onCreateDrawableState(extraSpace);
	}

	@Override
	public void setAdjustViewBounds(boolean adjustViewBounds) {
		if(strict) {
			throw new UnsupportedOperationException("Not supported");
		}
		super.setAdjustViewBounds(adjustViewBounds);
	}

	@Override
	public void setImageLevel(int level) {
		if(strict) {
			throw new UnsupportedOperationException("Not supported");
		}
		super.setImageLevel(level);
	}

	@Override
	public void setImageMatrix(Matrix matrix) {
		if(strict) {
			throw new UnsupportedOperationException("Not supported");
		}
	}

	@Override
	public void setImageState(int[] state, boolean merge) {
		if(strict) {
			throw new UnsupportedOperationException("Not supported");
		}
	}

	@Override
	public void setSelected(boolean selected) {
		if(strict) {
			throw new UnsupportedOperationException("Not supported");
		}
		super.setSelected(selected);
	}

	@Override
	public void setOnTouchListener(OnTouchListener l) {
		this.customOnTouchListener = l;
	}

	/**
	 * Gets center x.
	 *
	 * @return the center x
	 */
	public float getCenterX() {
		return centerX;
	}

	/**
	 * Gets center y.
	 *
	 * @return the center y
	 */
	public float getCenterY() {
		return centerY;
	}

	/**
	 * Is landscape boolean.
	 *
	 * @return the boolean
	 */
	public boolean isLandscape() {
		return getImageWidth() >= getImageHeight();
	}

	/**
	 * Is portrait boolean.
	 *
	 * @return the boolean
	 */
	public boolean isPortrait() {
		return getImageWidth() <= getImageHeight();
	}

	/**
	 * Sets starting scale.
	 *
	 * @param startingScale the starting scale
	 */
	public void setStartingScale(float startingScale) {
		this.startingScale = startingScale;
	}

	/**
	 * Sets starting position.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public void setStartingPosition(float x, float y) {
		this.startX = x;
		this.startY = y;
	}

	@Override
	public void setOnClickListener(OnClickListener l) {
		this.onClickListener = l;
		
		if(gestureImageViewTouchListener != null) {
			gestureImageViewTouchListener.setOnClickListener(l);
		}
	}

	/**
	 * Returns true if the image dimensions are aligned with the orientation of the device.
	 *
	 * @return boolean
	 */
	public boolean isOrientationAligned() {
		if(deviceOrientation == Configuration.ORIENTATION_LANDSCAPE) {
			return isLandscape();
		}
		else if(deviceOrientation == Configuration.ORIENTATION_PORTRAIT) {
			return isPortrait();
		}
		return true;
	}

	/**
	 * Gets device orientation.
	 *
	 * @return the device orientation
	 */
	public int getDeviceOrientation() {
		return deviceOrientation;
	}
}
