package com.part.jianzhiyi.modulemerchants.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.part.jianzhiyi.modulemerchants.R;
import com.part.jianzhiyi.modulemerchants.model.entity.MLableEntity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * Created by jyx on 2020/11/30
 *
 * @author jyx
 * @describe
 */
public class MerTagAdapter extends TagAdapter<MLableEntity.DataBean.ListsBean> {

    private Context mContext;

    public MerTagAdapter(List<MLableEntity.DataBean.ListsBean> datas, Context context) {
        super(datas);
        mContext = context;
    }

    @Override
    public View getView(FlowLayout parent, int position, MLableEntity.DataBean.ListsBean listsBean) {
        TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_text_flow, null, false);
        tv.setText(listsBean.getTitle());
        return tv;
    }
}
