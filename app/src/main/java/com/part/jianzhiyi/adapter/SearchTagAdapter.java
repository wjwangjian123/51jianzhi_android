package com.part.jianzhiyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.model.entity.LableEntity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

/**
 * Created by jyx on 2020/11/30
 *
 * @author jyx
 * @describe
 */
public class SearchTagAdapter extends TagAdapter<LableEntity.DataBean> {

    private Context mContext;

    public SearchTagAdapter(List<LableEntity.DataBean> datas, Context context) {
        super(datas);
        mContext = context;
    }

    @Override
    public View getView(FlowLayout parent, int position, LableEntity.DataBean dataBean) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_search_flow, null, false);
        ImageView ivHot = inflate.findViewById(R.id.iv_hot);
        TextView tvTitle = inflate.findViewById(R.id.tv_flow_title);
        if (dataBean.getStatus().equals("1")) {
            ivHot.setVisibility(View.VISIBLE);
        } else {
            ivHot.setVisibility(View.GONE);
        }
        tvTitle.setText(dataBean.getTitle());
        return inflate;
    }
}
