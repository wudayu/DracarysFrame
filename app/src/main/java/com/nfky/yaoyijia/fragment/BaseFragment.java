package com.nfky.yaoyijia.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nfky.yaoyijia.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.app.AppObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by David on 5/24/15.
 * <p/>
 * BaseFragment是所有Fragment的根类。它使用getActivity方法初始化了mContext作为上下文对象
 */
public abstract class BaseFragment extends Fragment {

    /**
     * The M context.
     */
    protected Context mContext = null;
    /**
     * The Subscriptions. 线程管理Subscription池
     */
	List<Subscription> subscriptions = new ArrayList<>();

    /**
     * 初始化界面容器  @param inflater the inflater
     *
     * @param container          the container
     * @param savedInstanceState the saved instance state
     * @return the view
     */
    protected abstract View initContainer(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * 初始化控件  @param fragView the frag view
     */
    protected abstract void initComponents(View fragView);

    /**
     * 初始化事件
     */
    protected abstract void initEvents();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 在一切初始化结束后的程序入口
     */
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
     * @param message        需要显示在加载框中的文字
     * @param cancelable     是否可以由用户来取消此加载框，例如点击后退键或者点击加载框以外的位置
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

    /**
     * 注销所有Subscription的任务
     */
    @Override
    public void onDestroy() {
        for (Subscription sub : subscriptions) {
            if (sub != null)
                sub.unsubscribe();
        }

        super.onDestroy();
    }

    /**
     * 创建能够自释放的响应式线程
     *
     * @param threadTag 此响应式线程的String名称，用于打印或标记
     * @param func      耗时任务的定义
     * @param observer  监听者，耗时任务完成后执行监听者的方法
     */
    protected void createRxThread(String threadTag, Func1<String, Object> func, Observer<Object> observer) {
        Subscription sub = AppObservable.bindSupportFragment(BaseFragment.this, _getObservable(threadTag, func))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

        subscriptions.add(sub);
    }

    /**
     * 初始化Observable耗时任务对象，执行外部Func1
     * just后接参数可多次当作Func1的第一个参数，多个just参数会按顺序执行Func1中的call
     * 但就速度来说第一个参数所执行的onNext顺序在第二个参数开始执行之后
     * 具体请参考执行结果
     *
     * @param threadTag 响应式线程String类型Tag
     * @param func 外部Func1
     * @return 自定义对象
     */
    private Observable<Object> _getObservable(String threadTag, Func1<String, Object> func) {
        return Observable.just(threadTag).map(func);
    }

}
