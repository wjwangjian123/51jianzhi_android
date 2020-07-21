package com.part.jianzhiyi.corecommon.base.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/*
 * ListView adatper的基类
 * @author shixinxin
 * @param <P>
 */
public abstract class CustomBaseAdapter<T> extends BaseAdapter {
    List<T> list;
    Context context;
    int resId;

    public CustomBaseAdapter() {
    }

    public CustomBaseAdapter(Context context, int resId, List<T> list) {
        this.list = list;
        this.resId=resId;
        this.context = context;
    }



    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.get(context, convertView, parent, resId, position);
        this.convert(viewHolder, (T) getItem(position), position);
        return viewHolder.getConvertView();
    }

    protected abstract void convert(ViewHolder viewHolder, T item, int position);
}
