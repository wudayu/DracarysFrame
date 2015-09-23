package com.nfky.yaoyijia.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nfky.yaoyijia.R;

import java.util.List;

/**
 * Created by David on 9/21/15.
 *
 * TestRecyclerViewAdapter展示了一个RecyclerView的Adapter的基本结构，其中还携带了两种不同的ITEM类型
 */
public class TestRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // 两种不同的列表项类型
    public static final int ITEM_TYPE_NORMAL = 0;
    public static final int ITEM_TYPE_FOOTER = 1;

    // 数据集
    private List<String> mDataset;

    /**
     * 初始化Adapter的数据
     *
     * @param myDataset 需要绑定的数据
     */
    public TestRecyclerViewAdapter(List<String> myDataset) {
        mDataset = myDataset;
    }

    /**
     * 定义普通的ViewHolder
     */
    class NormalViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public NormalViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.tv_test);
        }
    }

    /**
     * 定义底部的ViewHolder
     */
    class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View v) {
            super(v);
        }
    }

    /**
     * 获取列表项的类型
     *
     * @param position 在数据集中的位置
     * @return 类型 ITEM_TYPE_XXXXXX
     */
    @Override
    public int getItemViewType(int position) {
        if (position + 1 != getItemCount()) {
            return ITEM_TYPE_NORMAL;
        } else {
            return ITEM_TYPE_FOOTER;
        }
    }

    /**
     * onCreateViewHolder
     *      绘制各个列表项的UI ViewHolder
     * @param parent 父ViewGroup
     * @param viewType 列表项的类型
     * @return 生成的UI ViewHolder
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_NORMAL) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test_recyclerview, parent, false);
            return new NormalViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_footer_view, parent, false);
            return new FooterViewHolder(v);
        }
    }

    /**
     * onBindViewHolder
     *      绑定数据到已经实例化的列表项中
     * @param holder 列表项
     * @param position 对应的数据在数据集中的位置
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NormalViewHolder) {
            ((NormalViewHolder) holder).mTextView.setText(mDataset.get(position));
        }
    }

    /**
     * 获取列表项的总数，由于有一个Footer，所以总数是数据数量+1
     *
     * @return 列表项总数
     */
    @Override
    public int getItemCount() {
        return mDataset.size() + 1;
    }

    /**
     * 向数据集中添加数据的操作
     *
     * @param extraDataSet 需要添加的数据
     */
    public void addData(List<String> extraDataSet) {
        mDataset.addAll(extraDataSet);

        this.notifyDataSetChanged();
    }

    /**
     * 删除数据集中指定位置的数据
     *
     * @param position 指定位置
     */
    public void removeData(int position) {
        mDataset.remove(position);

        this.notifyDataSetChanged();
    }

    /**
     * 清除数据集中的数据
     */
    public void clearData() {
        mDataset.clear();

        this.notifyDataSetChanged();
    }
}