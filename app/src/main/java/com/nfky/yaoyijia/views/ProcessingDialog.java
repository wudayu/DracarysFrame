package com.nfky.yaoyijia.views;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.nfky.yaoyijia.R;

/**
 * Created by David on 8/24/15.
 * <p/>
 * ProcessingDialog是一个AlertDialog，当应用后台有任务的时候用来显示加载信息
 */
public class ProcessingDialog extends AlertDialog {

	private String mMessage = null;

	/**
	 * Instantiates a new Processing dialog.
	 *
	 * @param context        the context
	 * @param message        the message
	 * @param cancelable     the cancelable
	 * @param cancelListener the cancel listener
	 */
	public ProcessingDialog(Context context, String message, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);

		this.mMessage = message;
	}

	/**
	 * Instantiates a new Processing dialog.
	 *
	 * @param context        the context
	 * @param cancelable     the cancelable
	 * @param cancelListener the cancel listener
	 */
	public ProcessingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	/**
	 * Instantiates a new Processing dialog.
	 *
	 * @param context the context
	 * @param theme   the theme
	 */
	public ProcessingDialog(Context context, int theme) {
		super(context, theme);
	}

	/**
	 * Instantiates a new Processing dialog.
	 *
	 * @param context the context
	 */
	public ProcessingDialog(Context context) {
		super(context);
	}

	/**
	 * 绘制并显示整个加载框
	 */
	@Override
	public void show() {
		super.show();

		setContentView(R.layout.layout_davidwu_processing_dialog);

		TextView tvMessage = (TextView) findViewById(R.id.iv_message);
		if (mMessage != null)
			tvMessage.setText(mMessage);

	}

}
