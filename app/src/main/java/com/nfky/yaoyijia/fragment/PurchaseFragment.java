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

import rx.Observer;
import rx.functions.Func1;


/**
 * Created by David on 8/25/15.
 * <p/>
 * Description: 第三个Fragment，用来测试响应式线程的使用
 */
public class PurchaseFragment extends BaseFragment {

    /**
     * The Ptl main.
     */
    PullToLoadView ptlMain;
    /**
     * The M recycler view.
     */
    RecyclerView mRecyclerView;

    /**
     * The Is loading.
     */
    boolean isLoading = false;
    /**
     * The Adapter.
     */
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
                createRxThread("testNow", new Func1<String, Object>() {
                    @Override
                    public Object call(String tag) {
                        try {
                            Utils.debug("testTag = " + tag);
                            Thread.sleep(3000);
                        } catch (InterruptedException ex) {
                            // balabala
                        }

                        return "success tag : " + tag;
                    }
                }, new Observer<Object>() {
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
                    public void onNext(Object s) {
                        Utils.debug("onNext Str = " + (String) s);
                    }
                });
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

    /**
     * The New data.
     */
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

    /**
     * The Load more handler.
     */
    Handler loadMoreHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            adapter.addData(newData);
            ptlMain.setComplete();
            isLoading = false;
        }
    };

    /**
     * Add data.
     */
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

}
