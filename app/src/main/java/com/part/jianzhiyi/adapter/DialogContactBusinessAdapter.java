package com.part.jianzhiyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.model.entity.JoinJobEntity;
import com.part.jianzhiyi.preference.PreferenceUUID;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by jyx on 2020/5/15
 *
 * @author jyx
 * @describe
 */
public class DialogContactBusinessAdapter extends RecyclerView.Adapter<DialogContactBusinessAdapter.ViewHolder> {

    protected Context mContext;
    protected List<JoinJobEntity.DataBean> mDatas;
    protected LayoutInflater mInflater;
    private static OnRecyclerSetClickListener mOnSetClickListener;

    public DialogContactBusinessAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setList(List<JoinJobEntity.DataBean> datas) {
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_contact_business, parent, false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        if (mDatas != null && mDatas.size() > 0) {
            viewHolder.mConTvTitle.setText(mDatas.get(position).getTitle());
            if (mDatas.get(position).getPlace()==null||mDatas.get(position).getPlace()==""){
                if (PreferenceUUID.getInstence().getCity()==""||PreferenceUUID.getInstence().getCity()==null){
                    viewHolder.mConTvPlace.setText("不限工作地点");
                }else {
                    viewHolder.mConTvPlace.setText(PreferenceUUID.getInstence().getCity());
                }
            }else {
                viewHolder.mConTvPlace.setText(mDatas.get(position).getPlace());
            }
            if (PreferenceUUID.getInstence().getShowWx()==1){
                //1是微信，2是QQ，3是公众号，4是手机号，5是网址
                if (mDatas.get(position).getContact_type()==1){
                    viewHolder.mConTvAdd.setText("复制微信号");
                }else if (mDatas.get(position).getContact_type()==2){
                    viewHolder.mConTvAdd.setText("复制QQ号");
                }else if (mDatas.get(position).getContact_type()==3){
                    viewHolder.mConTvAdd.setText("复制公众号");
                }else if (mDatas.get(position).getContact_type()==4){
                    viewHolder.mConTvAdd.setText("复制手机号");
                }else if (mDatas.get(position).getContact_type()==5){
                    viewHolder.mConTvAdd.setText("复制网址");
                }
            }else {
                viewHolder.mConTvAdd.setText("复制联系方式");
            }
            viewHolder.mConTvAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnSetClickListener != null) {
                        mOnSetClickListener.onSetClick(position, mDatas.get(position).getId());
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

        private TextView mConTvTitle;
        private TextView mConTvPlace;
        private TextView mConTvAdd;

        public ViewHolder(View itemView) {
            super(itemView);
            mConTvTitle = (TextView) itemView.findViewById(R.id.con_tv_title);
            mConTvPlace = (TextView) itemView.findViewById(R.id.con_tv_place);
            mConTvAdd = (TextView) itemView.findViewById(R.id.con_tv_add);
        }
    }

    public void setmOnSetClickListener(OnRecyclerSetClickListener mOnSetClickListener) {
        DialogContactBusinessAdapter.mOnSetClickListener = mOnSetClickListener;
    }

    /**
     * 点击事件监听
     */
    public interface OnRecyclerSetClickListener {
        void onSetClick(int position, String id);
    }
}
