package com.part.jianzhiyi.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.part.jianzhiyi.R;
import com.part.jianzhiyi.corecommon.utils.FrescoUtil;
import com.part.jianzhiyi.loader.GlideRoundTransformUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by jyx on 2020/11/17
 *
 * @author jyx
 * @describe
 */
public class IntegralBannerAdapter extends RecyclerView.Adapter<IntegralBannerAdapter.ViewHolder> {

    protected Context mContext;
    protected List<String> mDatas;
    protected LayoutInflater mInflater;
    private static OnRecyclerItemClickListener mOnItemClickListener;

    public IntegralBannerAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setList(List<String> datas) {
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_integral_banner, parent, false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        if (mDatas != null && mDatas.size() > 0) {
            FrescoUtil.setHttpPic(mDatas.get(position), viewHolder.mIvImg);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView mIvImg;

        public ViewHolder(View itemView) {
            super(itemView);
            mIvImg = (SimpleDraweeView) itemView.findViewById(R.id.iv_img);
        }
    }

    public void setmOnItemClickListener(OnRecyclerItemClickListener mOnItemClickListener) {
        IntegralBannerAdapter.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnRecyclerItemClickListener {
        void onItemClick(int position);
    }
}
