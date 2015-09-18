package com.nfky.yaoyijia.views;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 *
 * Created by David on 8/25/15.
 *
 * SwitchViewPager是可以自动滚动的ViewPager
 *
 **/

public class SwitchViewPager extends BaseViewPager {

	private static final int ROLL = 0x10;
	private static final int TIME_GAP = 5000;

	private Timer rollTimer = null;
	private boolean isTouching = false;
	private int timeGap = -1;

	public SwitchViewPager(Context context) {
		super(context);

		init();
	}

	public SwitchViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);

		init();
	}

	private void init() {}

	/**
	 * 设置是否要旋转
	 *
	 * @param rollable 是否旋转
	 */
	public void setRollable(boolean rollable) {
		if (rollTimer != null) {
			rollTimer.cancel();
			rollTimer = null;
		}

		if (this.getAdapter() == null || !rollable || this.getAdapter().getCount() < 2) {
			isTouching = false;
			
			return;
		}

		rollTimer = new Timer();
		rollTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				Message msg = new Message();
				msg.what = SwitchViewPager.ROLL;
				mHandler.sendMessage(msg);
			}
		}, getRightTimeGap(), getRightTimeGap());
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_MOVE:
				isTouching = true;
				break;
			case MotionEvent.ACTION_UP:
				isTouching = false;
				break;
			default:
				break;
		}
		return super.onTouchEvent(event);
	}

	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == SwitchViewPager.ROLL && !isTouching) {
				SwitchViewPager.this.setCurrentItem((SwitchViewPager.this.getCurrentItem() + 1) % getAdapter().getCount());
			}
		}
	};

	/**
	 * 当SwitchViewPager被销毁时需要关闭Timer
	 */
	@Override
	protected void onDetachedFromWindow() {
		if (rollTimer != null)
			rollTimer.cancel();

		super.onDetachedFromWindow();
	}

	public void setTimeGap(int timeGap) {
		this.timeGap = timeGap;
	}

	private int getRightTimeGap() {
		return this.timeGap > 0 ? this.timeGap : TIME_GAP;
	}

}
