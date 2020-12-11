package com.part.jianzhiyi.modulemerchants.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.PagerAdapter;

/**
 * Created by jyx on 2020/5/26
 *
 * @author jyx
 * @describe
 */
public class GuideAdapter extends PagerAdapter {

    private List<ImageView> mList;
    private OnItemClickListener mOnItemClickListener;

    public GuideAdapter() {
        mList = new ArrayList<ImageView>();
    }

    public void setData(List<ImageView> list) {
        if (list != null) {
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mList.get(position), 0);
        ImageView imageView = mList.get(position);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position);
                } else {
                    return;
                }
            }
        });
        return mList.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    /**
     * 点击事件监听
     */
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}