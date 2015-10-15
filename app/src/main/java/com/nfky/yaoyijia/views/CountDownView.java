package com.nfky.yaoyijia.views;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by David on 8/24/15.
 * <p/>
 * CountDownView是一个显示倒计时的控件
 */
public class CountDownView extends LinearLayout {

	private Context mContext = null;
	private CountDownTimer mCountDown = null;
	private OnCountDownFinishListener finishListener = null;

	/**
	 * Instantiates a new Count down view.
	 *
	 * @param context the context
	 * @param attrs   the attrs
	 */
	public CountDownView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	/**
	 * Instantiates a new Count down view.
	 *
	 * @param context the context
	 */
	public CountDownView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		this.mContext = context;
	}

	/**
	 * 设置时间并开始倒计时
	 *
	 * @param totalMillis 总毫秒数量
	 * @param timeGap     执行onTick的时间间隙
	 */
	public void setCountDownAndStart(long totalMillis, long timeGap) {
		// 需要做的就是修改这个TextView的样式
		final TextView tvCountDown = new TextView(mContext);
		this.addView(tvCountDown);
		mCountDown = new CountDownTimer(totalMillis, timeGap) {
			@Override
			public void onTick(long millisUntilFinished) {
				long millis = millisUntilFinished / 10 % 100;
				long second = millisUntilFinished / 1000 % 60;
				long min = millisUntilFinished / 1000 / 60 % 60;
				long hour = millisUntilFinished / 1000 / 3600 % 24;

				tvCountDown.setText("" + (hour > 9 ? hour : "0" + hour) + " : " + (min > 9 ? min : "0" + min) + " : " + (second > 9 ? second : "0" + second) + " : " + (millis > 9 ? millis : "0" + millis));
			}

			@Override
			public void onFinish() {
				tvCountDown.setText("00 : 00 : 00 : 00");
				mCountDown = null;
				if (finishListener != null)
					finishListener.onFinish();
			}
		};

		mCountDown.start();
	}

	/**
	 * The interface On count down finish listener.
	 */
	public interface OnCountDownFinishListener {
		/**
		 * On finish.
		 */
		void onFinish();
	}

	/**
	 * Sets on count down finish listener.
	 *
	 * @param finishListener the finish listener
	 */
	public void setOnCountDownFinishListener(OnCountDownFinishListener finishListener) {
		this.finishListener = finishListener;
	}
}
