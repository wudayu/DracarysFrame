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
 */
public class TestRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM_TYPE_NORMAL = 0;
    public static final int ITEM_TYPE_FOOTER = 1;

    private List<String> mDataset;

    public TestRecyclerViewAdapter(List<String> myDataset) {
        mDataset = myDataset;
    }

    class NormalViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public NormalViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.tv_test);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View v) {
            super(v);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 != getItemCount()) {
            return ITEM_TYPE_NORMAL;
        } else {
            return ITEM_TYPE_FOOTER;
        }
    }

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

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NormalViewHolder) {
            ((NormalViewHolder) holder).mTextView.setText(mDataset.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size() + 1;
    }

    public void addData(List<String> newDataSet) {
        mDataset.addAll(newDataSet);

        this.notifyDataSetChanged();
    }

}