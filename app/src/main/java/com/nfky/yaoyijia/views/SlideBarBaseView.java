package com.nfky.yaoyijia.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.nfky.yaoyijia.R;

/**
 * Created by David on 8/28/15.
 * <p/>
 * SlideBarBaseView是用来在数据中有一系列字母开头的情况下通过滑动选择响应字母数据的控件，一般位于屏幕右侧
 * 具体样式需要根据具体UI进行干煸
 */
public class SlideBarBaseView extends View {

	/**
	 * The constant DEFAULT_INDEX.
	 */
	public static final char[] DEFAULT_INDEX = { 'A', 'B', 'C', 'D', 'E', 'F',
			'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
			'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	/**
	 * The constant SPECIAL_CHAR_INDEX.
	 */
	public static final char[] SPECIAL_CHAR_INDEX = { 'A', 'B', 'C', 'D', 'E', 'F',
		'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
		'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '#' };

	private char[] indexs = SPECIAL_CHAR_INDEX;

	/**
	 * The M paint.
	 */
	Paint mPaint = null;

	/**
	 * The interface On slide bar base view flip listener.
	 */
	public interface OnSlideBarBaseViewFlipListener {

		/**
		 * On flip.
		 *
		 * @param index the index
		 * @param mChar the m char
		 */
		void onFlip(int index, String mChar);

		/**
		 * On flip up.
		 */
		void onFlipUp();
	}

	private OnSlideBarBaseViewFlipListener flipListener = null;

	/**
	 * Set SlideBar OnFlip Listener
	 *
	 * @param mListener the m listener
	 */
	public void setFlipListener(OnSlideBarBaseViewFlipListener mListener) {
		this.flipListener = mListener;
	}

	/**
	 * Instantiates a new Slide bar base view.
	 *
	 * @param context the context
	 */
	public SlideBarBaseView(Context context) {
		super(context);
		init();
	}

	/**
	 * Instantiates a new Slide bar base view.
	 *
	 * @param context      the context
	 * @param attrs        the attrs
	 * @param defStyleAttr the def style attr
	 */
	public SlideBarBaseView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	/**
	 * Instantiates a new Slide bar base view.
	 *
	 * @param context the context
	 * @param attrs   the attrs
	 */
	public SlideBarBaseView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		mPaint = new Paint();
		mPaint.setTextSize(getResources().getDimensionPixelOffset(
				R.dimen.px24));
		mPaint.setTextAlign(Paint.Align.CENTER);
		mPaint.setColor(Color.parseColor("#FF136AFF"));
		mPaint.setTypeface(Typeface.DEFAULT_BOLD);
		mPaint.setAntiAlias(true);
	}

	/**
	 * Sets indexs.
	 *
	 * @param wantedIndexs the wanted indexs
	 */
	public void setIndexs(char[] wantedIndexs) {
		this.indexs = wantedIndexs;
		invalidate();
	}

	/**
	 * Sets text color.
	 *
	 * @param color the color
	 */
	public void setTextColor(int color) {
		mPaint.setColor(color);
	}

	protected void onDraw(Canvas canvas) {
		for (int i = 0; i < indexs.length; i++) {
			float xPos = getWidth() / 2;
			float yPos = (getHeight() / indexs.length) * i
					+ (getHeight() / indexs.length);
			canvas.drawText(String.valueOf(indexs[i]), xPos, yPos, mPaint);
		}
		super.onDraw(canvas);
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(@NonNull MotionEvent event) {
		float touchY = event.getY();

		float everyHeight = ((float) getHeight() / indexs.length);

		int touchId = (int) (touchY / everyHeight);

		if (touchId < 0) {
			touchId = 0;
		}

		if (touchId > indexs.length - 1) {
			touchId = indexs.length - 1;
		}

		if (event.getAction() == MotionEvent.ACTION_DOWN
				|| event.getAction() == MotionEvent.ACTION_MOVE) {
			if (this.flipListener != null) {
				this.flipListener.onFlip(touchId,
						String.valueOf(indexs[touchId]));
			}
		} else {
			if (this.flipListener != null) {
				this.flipListener.onFlipUp();
			}
		}
		return true;
	}

}
