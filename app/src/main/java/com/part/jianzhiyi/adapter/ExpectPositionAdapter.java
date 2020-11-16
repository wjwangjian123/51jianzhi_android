package com.part.jianzhiyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.model.entity.MyitemEntity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by jyx on 2020/5/15
 *
 * @author jyx
 * @describe
 */
public class ExpectPositionAdapter extends RecyclerView.Adapter<ExpectPositionAdapter.ViewHolder> {

    protected Context mContext;
    protected List<MyitemEntity.DataBean> mDatas;
    protected List<MyitemEntity.DataBean.ChildrenBean> mlist;
    protected LayoutInflater mInflater;
    private static OnRecyclerSetClickListener mOnSetClickListener;
    private static OnRecyclerItemClickListener mOnItemClickListener;

    public ExpectPositionAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setList(List<MyitemEntity.DataBean> datas) {
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_expect_recycle, parent, false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        if (mDatas != null && mDatas.size() > 0) {
            mlist = mDatas.get(position).getChildren();
            viewHolder.mExTvTitle.setText(mDatas.get(position).getItem());
            viewHolder.mExTfl.setAdapter(new TagAdapter<MyitemEntity.DataBean.ChildrenBean>(mlist) {
                @Override
                public View getView(FlowLayout parent, int position, MyitemEntity.DataBean.ChildrenBean dataBean) {
                    TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_about_flow, ((TagFlowLayout) viewHolder.mExTfl), false);
                    tv.setText(dataBean.getItem());
                    if (mlist.get(position).getIvType()==0){
                        tv.setBackgroundResource(R.drawable.shape_about_unselect);
                        tv.setTextColor(mContext.getResources().getColor(R.color.color_666666));
                    }else if (mlist.get(position).getIvType()==1){
                        tv.setBackgroundResource(R.drawable.shape_about_select);
                        tv.setTextColor(mContext.getResources().getColor(R.color.color_ffffff));
                    }
                    return tv;
                }
            });
            viewHolder.mExTfl.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int mposition, FlowLayout parent) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(position,mposition,mDatas.get(position).getChildren().get(mposition).getId());
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mExTvTitle;
        private TagFlowLayout mExTfl;

        public ViewHolder(View itemView) {
            super(itemView);
            mExTvTitle = (TextView) itemView.findViewById(R.id.ex_tv_title);
            mExTfl = (TagFlowLayout) itemView.findViewById(R.id.ex_tfl);
        }
    }

    public void setmOnItemClickListener(OnRecyclerItemClickListener mOnItemClickListener) {
        ExpectPositionAdapter.mOnItemClickListener = mOnItemClickListener;
    }

    public void setmOnSetClickListener(OnRecyclerSetClickListener mOnSetClickListener) {
        ExpectPositionAdapter.mOnSetClickListener = mOnSetClickListener;
    }

    /**
     * 点击事件监听
     */
    public interface OnRecyclerSetClickListener {
        void onSetClick(int position, String nickName, int id);
    }

    public interface OnRecyclerItemClickListener {
        void onItemClick(int position,int mposition,String id);
    }
}
