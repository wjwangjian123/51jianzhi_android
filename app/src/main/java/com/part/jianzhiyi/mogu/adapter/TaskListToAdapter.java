package com.part.jianzhiyi.mogu.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fendasz.moku.planet.source.bean.ClientSampleTaskData;
import com.fendasz.moku.planet.source.bean.TaskDataApplyRecord;
import com.part.jianzhiyi.R;
import com.part.jianzhiyi.corecommon.utils.FrescoUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by jyx on 2020/5/15
 *
 * @author jyx
 * @describe
 */
public class TaskListToAdapter extends RecyclerView.Adapter<TaskListToAdapter.ViewHolder> {

    protected Context mContext;
    protected List<ClientSampleTaskData> mDatas;
    protected LayoutInflater mInflater;
    private static OnRecyclerItemClickListener mOnItemClickListener;

    public TaskListToAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setList(List<ClientSampleTaskData> datas) {
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_taskdata_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        if (mDatas != null && mDatas.size() > 0) {
            FrescoUtil.setHttpPic(mDatas.get(position).getIcon(), viewHolder.mListIvIcon);
            viewHolder.mListTvName.setText(mDatas.get(position).getShowName());
            viewHolder.mListTvMoney.setText(mDatas.get(position).getShowMoney() + "");
            viewHolder.mListTvYuan.setText(mDatas.get(position).getCybermoneyName());
            if (mDatas.get(position).getTaskDataApplyRecord() != null && mDatas.get(position).getTaskDataApplyRecord().getStatus().equals(TaskDataApplyRecord.STATUS_OF_APPLYING)) {
                //该任务在申请中
                viewHolder.mListTvSurplusNum.setText("进行中");
                viewHolder.mListTvSurplusNum.setTextColor(Color.WHITE);
                viewHolder.mListTvSurplusNum.setBackgroundResource(R.drawable.shape_moku_bg_green);
                viewHolder.mListTvDesc.setText("结束时间：" + mDatas.get(position).getTaskDataApplyRecord().getExpirationTime());
            } else {
                viewHolder.mListTvSurplusNum.setText("剩余" + mDatas.get(position).getSurplusNum() + "份");
                viewHolder.mListTvSurplusNum.setTextColor(Color.parseColor("#9A9A9A"));
                viewHolder.mListTvSurplusNum.setBackgroundColor(Color.WHITE);
                viewHolder.mListTvDesc.setText(mDatas.get(position).getDesc());
            }
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(position, mDatas.get(position).getTaskDataId());
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

        private SimpleDraweeView mListIvIcon;
        private TextView mListTvName;
        private TextView mListTvDesc;
        private TextView mListTvMoney;
        private TextView mListTvYuan;
        private TextView mListTvSurplusNum;

        public ViewHolder(View itemView) {
            super(itemView);
            mListIvIcon = (SimpleDraweeView) itemView.findViewById(R.id.list_iv_icon);
            mListTvName = (TextView) itemView.findViewById(R.id.list_tv_name);
            mListTvDesc = (TextView) itemView.findViewById(R.id.list_tv_desc);
            mListTvMoney = (TextView) itemView.findViewById(R.id.list_tv_money);
            mListTvYuan = (TextView) itemView.findViewById(R.id.list_tv_yuan);
            mListTvSurplusNum = (TextView) itemView.findViewById(R.id.list_tv_surplus_num);
        }
    }

    public void setmOnItemClickListener(OnRecyclerItemClickListener mOnItemClickListener) {
        TaskListToAdapter.mOnItemClickListener = mOnItemClickListener;
    }

    /**
     * 点击事件监听
     */
    public interface OnRecyclerItemClickListener {
        void onItemClick(int position, Integer id);
    }
}
