package com.part.jianzhiyi.mogu.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.part.jianzhiyi.R;
import com.part.jianzhiyi.corecommon.utils.FrescoUtil;
import com.part.jianzhiyi.model.entity.moku.MokuTaskListEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by jyx on 2020/5/15
 *
 * @author jyx
 * @describe
 */
public class TaskListSubAdapter extends RecyclerView.Adapter<TaskListSubAdapter.ViewHolder> {

    protected Context mContext;
    protected List<MokuTaskListEntity.DataBean> mDatas;
    protected LayoutInflater mInflater;
    private static OnRecyclerItemClickListener mOnItemClickListener;
    private static OnMonitorItemClickListener onMonitorItemClickListener;

    public TaskListSubAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setList(List<MokuTaskListEntity.DataBean> datas) {
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_tasksub_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        if (mDatas != null && mDatas.size() > 0) {
            FrescoUtil.setHttpPic(mDatas.get(position).getIcon(), viewHolder.mItemIvIcon);
            viewHolder.mItemTvName.setText(mDatas.get(position).getShow_name());
            viewHolder.mItemTvMoney.setText(mDatas.get(position).getShow_money());
            viewHolder.mItemTvSurplusNum.setText(mDatas.get(position).getStatusName());
            if (mDatas.get(position).getStatus().equals("0")) {
                viewHolder.mItemTvSurplusNum.setBackgroundResource(R.drawable.shape_moku_bg_yellow);
                viewHolder.mItemTvDesc.setText(mDatas.get(position).getDesc());
            } else if (mDatas.get(position).getStatus().equals("-1")) {
                viewHolder.mItemTvSurplusNum.setBackgroundResource(R.drawable.shape_moku_bg_red);
                viewHolder.mItemTvDesc.setText(TextUtils.isEmpty(mDatas.get(position).getReason()) ? mDatas.get(position).getDesc() : mDatas.get(position).getReason());
                viewHolder.mItemTvDesc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener == null) {
                            return;
                        }
                        mOnItemClickListener.onItemClick(position, mDatas.get(position).getReason());
                    }
                });
            } else if (mDatas.get(position).getStatus().equals("2")) {
                viewHolder.mItemTvSurplusNum.setBackgroundResource(R.drawable.shape_moku_bg_green);
                viewHolder.mItemTvDesc.setText(mDatas.get(position).getDesc());
            }
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onMonitorItemClickListener != null) {
                        onMonitorItemClickListener.onMonitorItemClick(position);
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

        private SimpleDraweeView mItemIvIcon;
        private TextView mItemTvName;
        private TextView mItemTvDesc;
        private TextView mItemTvMoney;
        private TextView mItemTvSurplusNum;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemIvIcon = (SimpleDraweeView) itemView.findViewById(R.id.item_iv_icon);
            mItemTvName = (TextView) itemView.findViewById(R.id.item_tv_name);
            mItemTvDesc = (TextView) itemView.findViewById(R.id.item_tv_desc);
            mItemTvMoney = (TextView) itemView.findViewById(R.id.item_tv_money);
            mItemTvSurplusNum = (TextView) itemView.findViewById(R.id.item_tv_surplus_num);
        }
    }

    public void setmOnItemClickListener(OnRecyclerItemClickListener mOnItemClickListener) {
        TaskListSubAdapter.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnMonitorItemClickListener(OnMonitorItemClickListener onMonitorItemClickListener) {
        TaskListSubAdapter.onMonitorItemClickListener = onMonitorItemClickListener;
    }

    /**
     * 点击事件监听
     */
    public interface OnRecyclerItemClickListener {
        void onItemClick(int position, String reason);
    }

    public interface OnMonitorItemClickListener {
        void onMonitorItemClick(int position);
    }
}
