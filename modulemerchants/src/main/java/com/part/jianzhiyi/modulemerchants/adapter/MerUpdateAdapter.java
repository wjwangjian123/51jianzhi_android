package com.part.jianzhiyi.modulemerchants.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.part.jianzhiyi.modulemerchants.R;
import com.part.jianzhiyi.modulemerchants.model.entity.MJobListEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by jyx on 2020/5/15
 *
 * @author jyx
 * @describe
 */
public class MerUpdateAdapter extends RecyclerView.Adapter<MerUpdateAdapter.ViewHolder> {

    protected Context mContext;
    protected List<MJobListEntity.DataBeanX.DataBean> mDatas;
    protected LayoutInflater mInflater;
    private static OnRecyclerItemClickListener mOnItemClickListener;

    public MerUpdateAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setList(List<MJobListEntity.DataBeanX.DataBean> datas) {
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_update_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        if (mDatas != null && mDatas.size() > 0) {
            viewHolder.mTvTitle.setText(mDatas.get(position).getTitle());
            viewHolder.mTvId.setText("ID：" + mDatas.get(position).getId());
            String reason_title = mDatas.get(position).getReason_title();
            String s = mDatas.get(position).getReason_title() + mDatas.get(position).getReason();
            SpannableString spannableString = new SpannableString(s);
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#FE954A"));
            spannableString.setSpan(foregroundColorSpan, 0, reason_title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            viewHolder.mTvStatus.setText(spannableString);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvTitle;
        private TextView mTvId;
        private TextView mTvStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mTvId = (TextView) itemView.findViewById(R.id.tv_id);
            mTvStatus = (TextView) itemView.findViewById(R.id.tv_status);
        }
    }

    public void setmOnItemClickListener(OnRecyclerItemClickListener mOnItemClickListener) {
        MerUpdateAdapter.mOnItemClickListener = mOnItemClickListener;
    }

    /**
     * 点击事件监听
     */

    public interface OnRecyclerItemClickListener {
        void onItemClick(int position, String id);
    }
}
