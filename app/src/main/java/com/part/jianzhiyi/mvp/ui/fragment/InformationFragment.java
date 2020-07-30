package com.part.jianzhiyi.mvp.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.greendao.gen.MessageResponseEntityDao;
import com.part.jianzhiyi.R;
import com.part.jianzhiyi.adapter.MessageListAdapter;
import com.part.jianzhiyi.base.BaseFragment;
import com.part.jianzhiyi.corecommon.ui.ListViewInScrollView;
import com.part.jianzhiyi.dbmodel.GreenDaoManager;
import com.part.jianzhiyi.model.entity.ChatJobInfoEntity;
import com.part.jianzhiyi.model.entity.MessageResponseEntity;
import com.part.jianzhiyi.model.entity.MsgResponseEntity;
import com.part.jianzhiyi.model.entity.ViewedEntity;
import com.part.jianzhiyi.mvp.contract.InformationContract;
import com.part.jianzhiyi.mvp.presenter.InformationPresenter;
import com.part.jianzhiyi.mvp.ui.activity.ChatActivity;
import com.part.jianzhiyi.mvp.ui.activity.SeeMineActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jyx on 2020/7/14
 *
 * @author jyx
 * @describe
 */
@Route(path = "/app/fragment/information")
public class InformationFragment extends BaseFragment<InformationPresenter> implements InformationContract.IInformationView, View.OnClickListener {

    private TextView mInfoTvTitle;
    private ImageView mInfoIv;
    private ImageView mViewIvRed;
    private RelativeLayout mInfoRl;
    private TextView mInfoTvSee;
    private TextView mInfoTvNum;
    private TextView mInfoTvTime;
    private RelativeLayout mInfoCon;
    private ListViewInScrollView mLvMessage;
    private ImageView mIvInfoNoData;
    private MessageListAdapter adapter;
    private List<MessageResponseEntity> list;
    private int number = 1;

    @Override
    protected InformationPresenter createPresenter() {
        return new InformationPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_information;
    }

    @Override
    protected void afterCreate() {
        //调用被查看接口
        mPresenter.viewedJob();
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mInfoTvTitle=view.findViewById(R.id.info_tv_title);
        mInfoIv=view.findViewById(R.id.info_iv);
        mViewIvRed=view.findViewById(R.id.view_iv_red);
        mInfoRl=view.findViewById(R.id.info_rl);
        mInfoTvSee=view.findViewById(R.id.info_tv_see);
        mInfoTvNum=view.findViewById(R.id.info_tv_num);
        mInfoTvTime=view.findViewById(R.id.info_tv_time);
        mInfoCon=view.findViewById(R.id.info_con);
        mLvMessage=view.findViewById(R.id.lv_message);
        mIvInfoNoData=view.findViewById(R.id.iv_info_no_data);
        setToolbarVisible(false);
        mActivity.setImmerseLayout(mInfoTvTitle);
        list=new ArrayList<>();
        adapter = new MessageListAdapter(getActivity(), list);
        mLvMessage.setAdapter(adapter);
        if (list != null) {
            list.clear();
        }
        MessageResponseEntityDao messageResponseEntityDao = GreenDaoManager.getInstance().getDaoSession().getMessageResponseEntityDao();
        if (messageResponseEntityDao != null) {
            List<MessageResponseEntity> list1 = messageResponseEntityDao.queryBuilder().list();
            if (list1.size() > 0) {
                mLvMessage.setVisibility(View.VISIBLE);
                mIvInfoNoData.setVisibility(View.GONE);
                this.list.addAll(list1);
                adapter.notifyDataSetChanged();
            } else {
                mIvInfoNoData.setVisibility( View.VISIBLE);
                mLvMessage.setVisibility(View.GONE);
            }
        }
        mLvMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MessageResponseEntityDao messageResponseEntityDao = GreenDaoManager.getInstance().getDaoSession().getMessageResponseEntityDao();
                if (messageResponseEntityDao != null) {
                    MessageResponseEntity mlist = messageResponseEntityDao.queryBuilder().where(MessageResponseEntityDao.Properties.CompanyId.eq(list.get(position).getCompanyId())).unique();
                    if (mlist != null) {
                        MessageResponseEntity messageResponseEntity = mlist;
                        messageResponseEntity.setIsRed(1);
                        messageResponseEntityDao.update(messageResponseEntity);
                    }
                }
                Intent intent = new Intent(mActivity, ChatActivity.class);
                intent.putExtra("sortId", "" + position);
                intent.putExtra("type", 1);
                intent.putExtra("id", list.get(position).getCompanyId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void setListener() {
        super.setListener();
        mInfoCon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.info_con){
            mViewIvRed.setVisibility(View.GONE);
            Intent intent = new Intent(getActivity(), SeeMineActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void updateList(List<MsgResponseEntity> list) {

    }

    @Override
    public void updategetChatJobinfo(ChatJobInfoEntity chatJobInfoEntity) {

    }

    @Override
    public void updateviewedJob(ViewedEntity list) {
        if (list.getData().size() > 0) {
            mViewIvRed.setVisibility(View.VISIBLE);
            number = list.getData().size();
            mInfoTvNum.setText("有" + number + "位招聘者对你感兴趣并向你发出邀请");
            mInfoTvTime.setText(list.getData().get(0).getData());
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (list != null) {
                list.clear();
            }
            MessageResponseEntityDao messageResponseEntityDao = GreenDaoManager.getInstance().getDaoSession().getMessageResponseEntityDao();
            if (messageResponseEntityDao != null) {
                List<MessageResponseEntity> list1 = messageResponseEntityDao.queryBuilder().list();
                if (list1.size() > 0) {
                    mLvMessage.setVisibility(View.VISIBLE);
                    mIvInfoNoData.setVisibility(View.GONE);
                    this.list.addAll(list1);
                    adapter.notifyDataSetChanged();
                } else {
                    mIvInfoNoData.setVisibility(View.VISIBLE);
                    mLvMessage.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (list != null) {
            list.clear();
        }
        MessageResponseEntityDao messageResponseEntityDao = GreenDaoManager.getInstance().getDaoSession().getMessageResponseEntityDao();
        if (messageResponseEntityDao != null) {
            List<MessageResponseEntity> list1 = messageResponseEntityDao.queryBuilder().list();
            if (list1.size() > 0) {
                mLvMessage.setVisibility(View.VISIBLE);
                mIvInfoNoData.setVisibility(View.GONE);
                this.list.addAll(list1);
                adapter.notifyDataSetChanged();
            } else {
                mIvInfoNoData.setVisibility(View.VISIBLE);
                mLvMessage.setVisibility(View.GONE);
            }
        }
    }
}
