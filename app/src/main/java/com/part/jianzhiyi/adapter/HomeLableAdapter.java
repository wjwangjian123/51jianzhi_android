package com.part.jianzhiyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.model.entity.LableEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by jyx on 2020/5/15
 *
 * @author jyx
 * @describe
 */
public class HomeLableAdapter extends RecyclerView.Adapter<HomeLableAdapter.ViewHolder> {

    protected Context mContext;
    protected List<LableEntity.DataBean> mDatas;
    protected LayoutInflater mInflater;
    private static OnRecyclerSetClickListener mOnSetClickListener;
    private static OnRecyclerItemClickListener mOnItemClickListener;

    public HomeLableAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setList(List<LableEntity.DataBean> datas) {
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_lable_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        if (mDatas != null && mDatas.size() > 0) {
            viewHolder.mTvLable.setText(mDatas.get(position).getTitle());
            if (mDatas.get(position).getSelect() == 0) {
                viewHolder.mIvLableBg.setImageResource(R.color.color_ffffff);
            }else if (mDatas.get(position).getSelect() == 1){
                viewHolder.mIvLableBg.setImageResource(R.drawable.icon_home_lable_bg);
            }
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(position, mDatas.get(position).getId());
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

        private TextView mTvLable;
        private ImageView mIvLableBg;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvLable = (TextView) itemView.findViewById(R.id.tv_lable);
            mIvLableBg = (ImageView) itemView.findViewById(R.id.iv_lable_bg);
        }
    }

    public void setmOnItemClickListener(OnRecyclerItemClickListener mOnItemClickListener) {
        HomeLableAdapter.mOnItemClickListener = mOnItemClickListener;
    }

    public void setmOnSetClickListener(OnRecyclerSetClickListener mOnSetClickListener) {
        HomeLableAdapter.mOnSetClickListener = mOnSetClickListener;
    }

    /**
     * 点击事件监听
     */
    public interface OnRecyclerSetClickListener {
        void onSetClick(int position, String nickName, int id);
    }

    public interface OnRecyclerItemClickListener {
        void onItemClick(int position, String id);
    }
}
