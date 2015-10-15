package com.nfky.yaoyijia.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by David on 8/24/15.
 * <p/>
 * NoSlideViewPager是一个不能用手指滑动的ViewPager控件
 */
public class NoSlideViewPager extends SwitchViewPager {

	/**
	 * Instantiates a new No slide view pager.
	 *
	 * @param context the context
	 */
	public NoSlideViewPager(Context context) {
		super(context);
	}

	/**
	 * Instantiates a new No slide view pager.
	 *
	 * @param context the context
	 * @param attrs   the attrs
	 */
	public NoSlideViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		return false;
	}
}
