package com.nfky.yaoyijia.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nfky.yaoyijia.activity.BaseActivity;
import com.nfky.yaoyijia.views.ProcessingDialog;

/**
 *
 * Created by David on 5/24/15.
 *
 * BaseFragment是所有Fragment的根类。它使用getActivity方法初始化了mContext作为上下文对象
 *
 **/

public abstract class BaseFragment extends Fragment {

	protected Context mContext = null;

    /** 初始化界面容器 */
	protected abstract View initContainer(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
    /** 初始化控件 */
	protected abstract void initComponents(View fragView);
    /** 初始化事件 */
	protected abstract void initEvents();
    /** 初始化数据 */
	protected abstract void initData();
    /** 在一切初始化结束后的程序入口 */
	protected abstract void afterAllSet();

    /**
     * 初始化上下文
     */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		mContext = getActivity();

		super.onCreate(savedInstanceState);
	}

    /**
     * 初始化模版，注意，在编写每个Fragment的initContainer()时，只需要按照以下格式定义此Fragment的布局文件
     *      return inflater.inflate(R.layout.fragment_XXX, null);
     *
     * @param inflater 布局渲染器
     * @param container 容器
     * @return 返回渲染好的Fragment布局
     */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View fragView = initContainer(inflater, container, savedInstanceState);

		initComponents(fragView);
		initEvents();
		initData();
		afterAllSet();

		return fragView;
	}

    /**
     * 抽象Activity中的加载框方法到Fragment中
     *
     * @param message 需要显示在加载框中的文字
     * @param cancelable 是否可以由用户来取消此加载框，例如点击后退键或者点击加载框以外的位置
     * @param cancelListener 取消加载框之后的事件调用
     */
	protected void showProcessingDialog(String message, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
		((BaseActivity) this.getActivity()).showProcessingDialog(message, cancelable, cancelListener);
	}

    /**
     * 取消加载框
     */
	protected void dismissProcessingDialog() {
		((BaseActivity) this.getActivity()).dismissProcessingDialog();
	}

}
