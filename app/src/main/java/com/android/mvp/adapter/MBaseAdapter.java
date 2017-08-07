package com.android.mvp.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/7/6 0006.
 */

public abstract class MBaseAdapter<T> extends BaseAdapter {

    protected List<T> list;
    protected Context context;

    public MBaseAdapter(List<T> list, Context context) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return null == list ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
