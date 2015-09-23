package com.nfky.yaoyijia.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nfky.yaoyijia.R;
import com.nfky.yaoyijia.adapter.TestRecyclerViewAdapter;
import com.nfky.yaoyijia.generic.Utils;
import com.nfky.yaoyijia.views.refreshrecycler.PullCallBack;
import com.nfky.yaoyijia.views.refreshrecycler.PullToLoadView;

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
 *
 * Created by David on 8/25/15.
 *
 * Description: 第三个Fragment
 * 准备抽象整个RxJava的访问方式，将所有的subscription放在Base的集合中，在Destroy里依次注销。
 * 开启的线程方法放入Base中
 **/

public class PurchaseFragment extends BaseFragment {

    PullToLoadView ptlMain;
    RecyclerView mRecyclerView;

    boolean isLoading = false;
    TestRecyclerViewAdapter adapter;

    @Override
    protected View initContainer(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_purchase, null);
    }

    @Override
    protected void initComponents(View fragView) {
        ptlMain = (PullToLoadView) fragView.findViewById(R.id.ptl_pull_load_view);
        mRecyclerView = ptlMain.getRecyclerView();
        // 设置是否加载更多功能
        ptlMain.isLoadMoreEnabled(true);
        // 设定ll布局
        LinearLayoutManager manager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        ptlMain.setColorSchemeResources(android.R.color.holo_purple, android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }

    @Override
    protected void initEvents() {
        //添加监听
        ptlMain.setPullCallBack(new PullCallBack() {
            @Override
            public void onLoadMore() {
                isLoading = true;
                //加载更多处理
                addData();
            }

            @Override
            public void onRefresh() {
                //刷新处理
                isLoading = true;
                loadingData();
            }

            @Override
            public boolean isLoading() {
                //返回当前是否加载中
                return isLoading;
            }

            @Override
            public boolean hasLoadedAllItems() {
                //返回当前是否还有更多数据
                return false;
            }
        });
    }

    List<String> newData = new ArrayList<>();
    @Override
    protected void initData() {
        adapter = new TestRecyclerViewAdapter(new ArrayList<String>());
        mRecyclerView.setAdapter(adapter);
        newData.clear();
        for (int i = 0; i < 20; ++i) {
            newData.add("David " + i);
        }
        adapter.addData(newData);
    }

    @Override
    protected void afterAllSet() {
        ptlMain.initLoad();
    }

    Handler loadMoreHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            adapter.addData(newData);
            ptlMain.setComplete();
            isLoading = false;
        }
    };
    public void addData() {
        newData.clear();
        for (int i = 0; i < 20; ++i) {
            newData.add("David " + i);
        }

        new Thread() {
            @Override
            public void run() {
                super.run();

                try {
                    Thread.sleep(3000);
                    loadMoreHandler.sendEmptyMessage(0);
                } catch (InterruptedException ex) {
                    // balabala
                }
            }
        }.start();
    }

    /**
     * 以下代码用来测试RxJava
     */
    private Subscription _subscription; // 订阅对象

    /**
     * 当Activity销毁时，注销订阅对象
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (_subscription != null) {
            _subscription.unsubscribe();
        }
    }

    /**
     * 加载代码，给订阅对象设置耗时任务以及监听器
     */
    private void loadingData() {
        _subscription = AppObservable.bindSupportFragment(PurchaseFragment.this, _getObservable(1, 2, 3))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(_getObserver());
    }

    /**
     * 耗时任务（Observable）定义
     * just后接参数可多次当作Func1的第一个参数，多个just参数会按顺序执行Func1中的call
     * 但就速度来说第一个参数所执行的onNext顺序在第二个参数开始执行之后
     * 具体请参考执行结果
     *
     * @return 耗时任务 Observable
     */
    private Observable<String> _getObservable(int firstArg, int secondArg, int thirdArg) {
        return Observable.just(firstArg, secondArg, thirdArg).map(new Func1<Integer, String>() {
            @Override
            public String call(Integer d) {
                try {

                    Utils.debug("Integer d = " + d);
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    // balabala
                }

                return "success " + d;
            }
        });

    }

    /**
     * 设置监听器
     *
     * @return 监听器
     */
    private Observer<String> _getObserver() {
        return new Observer<String>() {
            @Override
            public void onCompleted() {
                Utils.debug("onCompleted");
                isLoading = false;
                ptlMain.setComplete();
            }

            @Override
            public void onError(Throwable e) {
                Utils.debug("onError");
                isLoading = false;
                ptlMain.setComplete();
            }

            @Override
            public void onNext(String s) {
                Utils.debug("onNext Str = " + s);
            }
        };
    }

}
