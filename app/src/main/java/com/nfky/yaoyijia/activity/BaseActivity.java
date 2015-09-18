package com.nfky.yaoyijia.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.nfky.yaoyijia.constant.BroadcastActions;
import com.nfky.yaoyijia.generic.Utils;
import com.nfky.yaoyijia.views.ProcessingDialog;

/**
 *
 * Created by David on 8/24/15.
 *
 * BaseActivity是一切Activity的父类，它包括一个用来关闭所有Activity的本地广播。除此之外，如果我们希望让所有Activity都做某件事情的话，我们需要将其放在BaseActivity中去做
 *
 **/

public abstract class BaseActivity extends FragmentActivity {

	// 通用上下文
	protected Context mContext;
    // 通用加载框，可以自定义加载框中显示的文字内容
	protected ProcessingDialog processingDialog = null;
    // 已保存实例状态
	protected Bundle savedInstanceState = null;

	/** 初始化界面容器 */
	protected abstract void initContainer();
	/** 初始化控件 */
	protected abstract void initComponents();
	/** 初始化事件 */
	protected abstract void initEvents();
	/** 初始化数据 */
	protected abstract void initData();
	/** 在一切初始化结束后的程序入口 */
	protected abstract void afterAllSet();

    /**
     * onCreate 调用了一些模版方法，以此来规范Activity的注册与使用
     *
     * @param arg0 savedInstanceState
     */
	@Override
	protected void onCreate(Bundle arg0) {
		savedInstanceState = arg0;
		super.onCreate(savedInstanceState);
		mContext = BaseActivity.this;

		initContainer();
		initComponents();
		initEvents();
		initData();
		afterAllSet();

		// 注册 关闭所有Activity 广播
		LocalBroadcastManager.getInstance(mContext).registerReceiver(finishReceiver, new IntentFilter(BroadcastActions.FINISH_ACTIVITY));
	}

    // 关闭Activity的本地广播接收者
	private BroadcastReceiver finishReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			finish();
		}
	};

    /**
     * 注销 关闭所有Activity 广播
     */
	@Override
	protected void onDestroy() {
		try {
			LocalBroadcastManager.getInstance(mContext).unregisterReceiver(finishReceiver);
		} catch (Exception e) {
			Utils.debug("BaseActivity : " + e.toString());
		}

		super.onDestroy();
	}

    /**
     * 发送关闭所有Activity广播的方法
     */
	protected void closeAllActivity() {
		LocalBroadcastManager.getInstance(BaseActivity.this).sendBroadcast(new Intent(BroadcastActions.FINISH_ACTIVITY));
	}

    /**
     * 显示加载框
     *
     * @param message 需要显示在加载框中的文字
     * @param cancelable 是否可以由用户来取消此加载框，例如点击后退键或者点击加载框以外的位置
     * @param cancelListener 取消加载框之后的事件调用
     */
	public void showProcessingDialog(String message, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        if (message == null) {
            processingDialog = new ProcessingDialog(this, cancelable, cancelListener);
        } else {
            processingDialog = new ProcessingDialog(this, message, cancelable, cancelListener);
        }

		processingDialog.show();
	}

    /**
     * 取消加载框
     */
	public void dismissProcessingDialog() {
		if (processingDialog != null) {
			processingDialog.dismiss();
		}
	}

    /**
     * 关闭此界面，默认被通用的Activity头部使用，如果后退键有其他的功能，则需要重新实现这个方法
     *
     * @param view 被点击的后退按钮
     */
	public void finishThis(View view) {
		this.finish();
	}

}
