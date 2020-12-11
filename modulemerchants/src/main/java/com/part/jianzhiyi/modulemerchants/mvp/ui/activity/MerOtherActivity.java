package com.part.jianzhiyi.modulemerchants.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.part.jianzhiyi.corecommon.selectdateview.dialog.ActionListener;
import com.part.jianzhiyi.corecommon.selectdateview.dialog.BaseDialogFragment;
import com.part.jianzhiyi.corecommon.selectdateview.dialog.TextPickerDialog;
import com.part.jianzhiyi.corecommon.selectdateview.view.TextModel;
import com.part.jianzhiyi.corecommon.ui.InputFilteEditText;
import com.part.jianzhiyi.modulemerchants.R;
import com.part.jianzhiyi.modulemerchants.base.BaseActivity;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MJobInfoEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MLableContactEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MLableEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MLableSalaryEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MUserInfoEntity;
import com.part.jianzhiyi.modulemerchants.mvp.contract.MPublishContract;
import com.part.jianzhiyi.modulemerchants.mvp.presenter.MPublishPresenter;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

public class MerOtherActivity extends BaseActivity<MPublishPresenter> implements MPublishContract.IMPublishView {

    private InputFilteEditText mEtPlace;
    private InputFilteEditText mEtCycle;
    private InputFilteEditText mEtTime;
    private TextView mTvContact;
    private TextView mTvAgeMin;
    private TextView mTvAgeMax;
    private TextView mTvSex;
    private InputFilteEditText mEtContact1;
    private LinearLayout mLlContact1;
    private LinearLayout mLlContactType;
    private TextView mTvNext;
    private int type = 0;
    private MJobInfoEntity mJobInfoEntity;

    private List<MLableContactEntity.DataBean> mListContact;
    private List<TextModel> mList;
    private List<TextModel> mSexList;
    private InputFilteEditText editText2;
    private InputFilteEditText editText3;
    private InputFilteEditText editText4;
    private InputFilteEditText editText5;
    private InputFilteEditText editText6;
    private InputFilteEditText editText7;
    private InputFilteEditText editText8;
    private InputFilteEditText editText9;
    private InputFilteEditText editText10;
    private InputFilteEditText editText11;
    private InputFilteEditText editText12;
    private InputFilteEditText editText13;
    private InputFilteEditText editText14;
    private InputFilteEditText editText15;
    private InputFilteEditText editText16;
    private InputFilteEditText editText17;
    private InputFilteEditText editText18;
    private InputFilteEditText editText19;
    private InputFilteEditText editText20;
    private LinearLayout linearLayout2;
    private LinearLayout linearLayout3;
    private LinearLayout linearLayout4;
    private LinearLayout linearLayout5;
    private LinearLayout linearLayout6;
    private LinearLayout linearLayout7;
    private LinearLayout linearLayout8;
    private LinearLayout linearLayout9;
    private LinearLayout linearLayout10;
    private LinearLayout linearLayout11;
    private LinearLayout linearLayout12;
    private LinearLayout linearLayout13;
    private LinearLayout linearLayout14;
    private LinearLayout linearLayout15;
    private LinearLayout linearLayout16;
    private LinearLayout linearLayout17;
    private LinearLayout linearLayout18;
    private LinearLayout linearLayout19;
    private LinearLayout linearLayout20;
    private String label_id;
    private String job_id;
    private String title;
    private String content;
    private String num;
    private String method;
    private String price1;
    private String price2;
    private String contact;
    private int contact_type;
    private String id;
    private InputFilteEditText[] mViews;
    private LinearLayout[] mViewlls;
    private List<TextModel> list_age;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mPresenter.getMLabelContact("4");
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mer_other;
    }

    @Override
    protected void initView() {
        mEtPlace = (InputFilteEditText) findViewById(R.id.et_place);
        mEtCycle = (InputFilteEditText) findViewById(R.id.et_cycle);
        mEtTime = (InputFilteEditText) findViewById(R.id.et_time);
        mTvContact = (TextView) findViewById(R.id.tv_contact);
        mTvAgeMin = (TextView) findViewById(R.id.tv_age_min);
        mTvAgeMax = (TextView) findViewById(R.id.tv_age_max);
        mTvSex = (TextView) findViewById(R.id.tv_sex);
        mEtContact1 = (InputFilteEditText) findViewById(R.id.et_contact1);
        mLlContact1 = (LinearLayout) findViewById(R.id.ll_contact1);
        mLlContactType = (LinearLayout) findViewById(R.id.ll_contact_type);
        mTvNext = (TextView) findViewById(R.id.tv_next);
        initToolbar("");
        linearLayout2 = (LinearLayout) LayoutInflater.from(MerOtherActivity.this).inflate(R.layout.item_contact, null);
        editText2 = linearLayout2.findViewById(R.id.et_contact);
        TextView textView2 = linearLayout2.findViewById(R.id.tv_contact);
        textView2.setText("联系方式2");
        linearLayout3 = (LinearLayout) LayoutInflater.from(MerOtherActivity.this).inflate(R.layout.item_contact, null);
        editText3 = linearLayout3.findViewById(R.id.et_contact);
        TextView textView3 = linearLayout3.findViewById(R.id.tv_contact);
        textView3.setText("联系方式3");
        linearLayout4 = (LinearLayout) LayoutInflater.from(MerOtherActivity.this).inflate(R.layout.item_contact, null);
        TextView textView4 = linearLayout4.findViewById(R.id.tv_contact);
        textView4.setText("联系方式4");
        editText4 = linearLayout4.findViewById(R.id.et_contact);
        linearLayout5 = (LinearLayout) LayoutInflater.from(MerOtherActivity.this).inflate(R.layout.item_contact, null);
        TextView textView5 = linearLayout5.findViewById(R.id.tv_contact);
        textView5.setText("联系方式5");
        editText5 = linearLayout5.findViewById(R.id.et_contact);
        linearLayout6 = (LinearLayout) LayoutInflater.from(MerOtherActivity.this).inflate(R.layout.item_contact, null);
        TextView textView6 = linearLayout6.findViewById(R.id.tv_contact);
        textView6.setText("联系方式6");
        editText6 = linearLayout6.findViewById(R.id.et_contact);
        linearLayout7 = (LinearLayout) LayoutInflater.from(MerOtherActivity.this).inflate(R.layout.item_contact, null);
        TextView textView7 = linearLayout7.findViewById(R.id.tv_contact);
        textView7.setText("联系方式7");
        editText7 = linearLayout7.findViewById(R.id.et_contact);
        linearLayout8 = (LinearLayout) LayoutInflater.from(MerOtherActivity.this).inflate(R.layout.item_contact, null);
        TextView textView8 = linearLayout8.findViewById(R.id.tv_contact);
        textView8.setText("联系方式8");
        editText8 = linearLayout8.findViewById(R.id.et_contact);
        linearLayout9 = (LinearLayout) LayoutInflater.from(MerOtherActivity.this).inflate(R.layout.item_contact, null);
        TextView textView9 = linearLayout9.findViewById(R.id.tv_contact);
        textView9.setText("联系方式9");
        editText9 = linearLayout9.findViewById(R.id.et_contact);
        linearLayout10 = (LinearLayout) LayoutInflater.from(MerOtherActivity.this).inflate(R.layout.item_contact, null);
        TextView textView10 = linearLayout10.findViewById(R.id.tv_contact);
        textView10.setText("联系方式10");
        editText10 = linearLayout10.findViewById(R.id.et_contact);
        linearLayout11 = (LinearLayout) LayoutInflater.from(MerOtherActivity.this).inflate(R.layout.item_contact, null);
        TextView textView11 = linearLayout11.findViewById(R.id.tv_contact);
        textView11.setText("联系方式11");
        editText11 = linearLayout11.findViewById(R.id.et_contact);
        linearLayout12 = (LinearLayout) LayoutInflater.from(MerOtherActivity.this).inflate(R.layout.item_contact, null);
        TextView textView12 = linearLayout12.findViewById(R.id.tv_contact);
        textView12.setText("联系方式12");
        editText12 = linearLayout12.findViewById(R.id.et_contact);
        linearLayout13 = (LinearLayout) LayoutInflater.from(MerOtherActivity.this).inflate(R.layout.item_contact, null);
        TextView textView13 = linearLayout13.findViewById(R.id.tv_contact);
        textView13.setText("联系方式13");
        editText13 = linearLayout13.findViewById(R.id.et_contact);
        linearLayout14 = (LinearLayout) LayoutInflater.from(MerOtherActivity.this).inflate(R.layout.item_contact, null);
        TextView textView14 = linearLayout14.findViewById(R.id.tv_contact);
        textView14.setText("联系方式14");
        editText14 = linearLayout14.findViewById(R.id.et_contact);
        linearLayout15 = (LinearLayout) LayoutInflater.from(MerOtherActivity.this).inflate(R.layout.item_contact, null);
        TextView textView15 = linearLayout15.findViewById(R.id.tv_contact);
        textView15.setText("联系方式15");
        editText15 = linearLayout15.findViewById(R.id.et_contact);
        linearLayout16 = (LinearLayout) LayoutInflater.from(MerOtherActivity.this).inflate(R.layout.item_contact, null);
        TextView textView16 = linearLayout16.findViewById(R.id.tv_contact);
        textView16.setText("联系方式16");
        editText16 = linearLayout16.findViewById(R.id.et_contact);
        linearLayout17 = (LinearLayout) LayoutInflater.from(MerOtherActivity.this).inflate(R.layout.item_contact, null);
        TextView textView17 = linearLayout17.findViewById(R.id.tv_contact);
        textView17.setText("联系方式17");
        editText17 = linearLayout17.findViewById(R.id.et_contact);
        linearLayout18 = (LinearLayout) LayoutInflater.from(MerOtherActivity.this).inflate(R.layout.item_contact, null);
        TextView textView18 = linearLayout18.findViewById(R.id.tv_contact);
        textView18.setText("联系方式18");
        editText18 = linearLayout18.findViewById(R.id.et_contact);
        linearLayout19 = (LinearLayout) LayoutInflater.from(MerOtherActivity.this).inflate(R.layout.item_contact, null);
        TextView textView19 = linearLayout19.findViewById(R.id.tv_contact);
        textView19.setText("联系方式19");
        editText19 = linearLayout19.findViewById(R.id.et_contact);
        linearLayout20 = (LinearLayout) LayoutInflater.from(MerOtherActivity.this).inflate(R.layout.item_contact, null);
        TextView textView20 = linearLayout20.findViewById(R.id.tv_contact);
        textView20.setText("联系方式20");
        editText20 = linearLayout20.findViewById(R.id.et_contact);
        mViews = new InputFilteEditText[]{mEtContact1, editText2, editText3, editText4, editText5, editText6, editText7, editText8, editText9, editText10, editText11, editText12, editText13, editText14, editText15, editText16, editText17, editText18, editText19, editText20};
        mViewlls = new LinearLayout[]{linearLayout2, linearLayout3, linearLayout4, linearLayout5, linearLayout6, linearLayout7, linearLayout8, linearLayout9, linearLayout10, linearLayout11, linearLayout12, linearLayout13, linearLayout14, linearLayout15, linearLayout16, linearLayout17, linearLayout18, linearLayout19, linearLayout20};
    }

    @Override
    protected void initData() {
        mListContact = new ArrayList<>();
        mList = new ArrayList<>();
        list_age = new ArrayList<>();
        mSexList = new ArrayList<>();
        mSexList.add(new TextModel("男"));
        mSexList.add(new TextModel("女"));
        mSexList.add(new TextModel("不限"));
        for (int i = 12; i < 61; i++) {
            list_age.add(new TextModel(i + ""));
        }
        label_id = getIntent().getStringExtra("label_id");
        job_id = getIntent().getStringExtra("job_id");
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        num = getIntent().getStringExtra("num");
        method = getIntent().getStringExtra("method");
        price1 = getIntent().getStringExtra("price1");
        price2 = getIntent().getStringExtra("price2");
        type = getIntent().getIntExtra("type", 0);
        if (type == 1) {
            mJobInfoEntity = (MJobInfoEntity) getIntent().getSerializableExtra("mJobInfoEntity");
        }
        if (mJobInfoEntity != null && mJobInfoEntity.getData() != null) {
            mEtPlace.setText(mJobInfoEntity.getData().getPlace());
            mEtCycle.setText(mJobInfoEntity.getData().getDuration());
            mEtTime.setText(mJobInfoEntity.getData().getTime());
            if (mJobInfoEntity.getData().getContact_type() == 1) {
                mTvContact.setText("微信");
            } else if (mJobInfoEntity.getData().getContact_type() == 2) {
                mTvContact.setText("QQ");
            } else if (mJobInfoEntity.getData().getContact_type() == 3) {
                mTvContact.setText("公众号");
            } else if (mJobInfoEntity.getData().getContact_type() == 4) {
                mTvContact.setText("手机号");
            } else if (mJobInfoEntity.getData().getContact_type() == 5) {
                mTvContact.setText("网址");
            }
            for (int i = 0; i < mJobInfoEntity.getData().getContact().size(); i++) {
                if (i < mViewlls.length) {
                    mLlContactType.addView(mViewlls[i]);
                }
                mViews[i].setText(mJobInfoEntity.getData().getContact().get(i));
            }
            mTvAgeMin.setText(mJobInfoEntity.getData().getAge1());
            mTvAgeMax.setText(mJobInfoEntity.getData().getAge2());
            mTvSex.setText(mJobInfoEntity.getData().getSex());
            id = mJobInfoEntity.getData().getId();
        }
        //监听联系电话1
        mEtContact1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    //创建联系电话2
                    if (mLlContactType.getChildCount() == 0) {
                        mLlContactType.addView(linearLayout2);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //监听联系电话2
        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    //创建联系电话3
                    if (mLlContactType.getChildCount() == 1) {
                        mLlContactType.addView(linearLayout3);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //监听联系电话3
        editText3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    //创建联系电话4
                    if (mLlContactType.getChildCount() == 2) {
                        mLlContactType.addView(linearLayout4);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //监听联系电话4
        editText4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    //创建联系电话5
                    if (mLlContactType.getChildCount() == 3) {
                        mLlContactType.addView(linearLayout5);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //监听联系电话5
        editText5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    //创建联系电话6
                    if (mLlContactType.getChildCount() == 4) {
                        mLlContactType.addView(linearLayout6);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //监听联系电话6
        editText6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    //创建联系电话7
                    if (mLlContactType.getChildCount() == 5) {
                        mLlContactType.addView(linearLayout7);
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //监听联系电话7
        editText7.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    //创建联系电话8
                    if (mLlContactType.getChildCount() == 6) {
                        mLlContactType.addView(linearLayout8);
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //监听联系电话8
        editText8.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    //创建联系电话9
                    if (mLlContactType.getChildCount() == 7) {
                        mLlContactType.addView(linearLayout9);
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //监听联系电话9
        editText9.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    //创建联系电话10
                    if (mLlContactType.getChildCount() == 8) {
                        mLlContactType.addView(linearLayout10);
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //监听联系电话10
        editText10.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    //创建联系电话11
                    if (mLlContactType.getChildCount() == 9) {
                        mLlContactType.addView(linearLayout11);
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //监听联系电话11
        editText11.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    //创建联系电话12
                    if (mLlContactType.getChildCount() == 10) {
                        mLlContactType.addView(linearLayout12);
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //监听联系电话12
        editText12.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    //创建联系电话13
                    if (mLlContactType.getChildCount() == 11) {
                        mLlContactType.addView(linearLayout13);
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //监听联系电话13
        editText13.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    //创建联系电话14
                    if (mLlContactType.getChildCount() == 12) {
                        mLlContactType.addView(linearLayout14);
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //监听联系电话14
        editText14.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    //创建联系电话15
                    if (mLlContactType.getChildCount() == 13) {
                        mLlContactType.addView(linearLayout15);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //监听联系电话15
        editText15.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    //创建联系电话16
                    if (mLlContactType.getChildCount() == 14) {
                        mLlContactType.addView(linearLayout16);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //监听联系电话16
        editText16.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    //创建联系电话17
                    if (mLlContactType.getChildCount() == 15) {
                        mLlContactType.addView(linearLayout17);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //监听联系电话17
        editText17.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    //创建联系电话18
                    if (mLlContactType.getChildCount() == 16) {
                        mLlContactType.addView(linearLayout18);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //监听联系电话18
        editText18.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    //创建联系电话19
                    if (mLlContactType.getChildCount() == 17) {
                        mLlContactType.addView(linearLayout19);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //监听联系电话19
        editText19.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    //创建联系电话20
                    if (mLlContactType.getChildCount() == 18) {
                        mLlContactType.addView(linearLayout20);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private long clickTime = 0;
    @Override
    protected void setListener() {
        super.setListener();
        mTvContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextPickerDialog pickDialog = TextPickerDialog.newInstance(BaseDialogFragment.TYPE_DIALOG, new MyAction(0));
                pickDialog.setList(mList);
                pickDialog.show(getFragmentManager(), "dialog");
            }
        });
        mTvSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextPickerDialog pickDialog = TextPickerDialog.newInstance(BaseDialogFragment.TYPE_DIALOG, new MyAction(1));
                pickDialog.setList(mSexList);
                pickDialog.show(getFragmentManager(), "dialog");
            }
        });
        mTvAgeMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextPickerDialog pickDialog = TextPickerDialog.newInstance(BaseDialogFragment.TYPE_DIALOG, new MyAction(2));
                pickDialog.setList(list_age);
                pickDialog.show(getFragmentManager(), "dialog");
            }
        });
        mTvAgeMax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextPickerDialog pickDialog = TextPickerDialog.newInstance(BaseDialogFragment.TYPE_DIALOG, new MyAction(3));
                pickDialog.setList(list_age);
                pickDialog.show(getFragmentManager(), "dialog");
            }
        });
        mTvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(MerOtherActivity.this, "mer_other");
                mPresenter.getmdAdd("72");
                if (TextUtils.isEmpty(mEtPlace.getText().toString().trim())) {
                    showToast("请输入工作地点");
                    return;
                }
                if (TextUtils.isEmpty(mEtCycle.getText().toString().trim())) {
                    showToast("请输入工作周期");
                    return;
                }
                if (TextUtils.isEmpty(mEtTime.getText().toString().trim())) {
                    showToast("请输入工作时间");
                    return;
                }
                if (TextUtils.isEmpty(mTvSex.getText().toString().trim())) {
                    showToast("请选择性别");
                    return;
                }
                if (TextUtils.isEmpty(mTvAgeMin.getText().toString().trim())) {
                    showToast("请选择最小年龄");
                    return;
                }
                if (TextUtils.isEmpty(mTvAgeMax.getText().toString().trim())) {
                    showToast("请选择最大年龄");
                    return;
                }
                if (TextUtils.isEmpty(mTvContact.getText().toString().trim())) {
                    showToast("请选择联系方式");
                    return;
                }
                if (TextUtils.isEmpty(mEtContact1.getText().toString().trim())) {
                    showToast("请输入联系方式");
                    return;
                }
                String trim = mTvAgeMin.getText().toString().trim();
                String trim1 = mTvAgeMax.getText().toString().trim();
                if (trim.compareTo(trim1) > 0) {
                    showToast("最小年龄要小于最大年龄");
                    return;
                }
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0; i < mViews.length; i++) {
                    stringBuffer = stringBuffer.append(mViews[i].getText().toString().trim() + ",");
                }
                contact = String.valueOf(stringBuffer);
                for (int i = 0; i < mListContact.size(); i++) {
                    if (mTvContact.getText().toString().trim().equals(mListContact.get(i).getName())) {
                        contact_type = mListContact.get(i).getId();
                    }
                }
                if (System.currentTimeMillis() - clickTime > 3000) {
                    clickTime = System.currentTimeMillis();
                    Intent intent = new Intent(MerOtherActivity.this, MerPreviewActivity.class);
                    intent.putExtra("label_id", label_id);
                    intent.putExtra("job_id", job_id);
                    intent.putExtra("title", title);
                    intent.putExtra("content", content);
                    intent.putExtra("num", num);
                    intent.putExtra("price1", price1);
                    intent.putExtra("price2", price2);
                    intent.putExtra("method", method);
                    intent.putExtra("place", mEtPlace.getText().toString().trim());
                    intent.putExtra("time", mEtCycle.getText().toString().trim());
                    intent.putExtra("duration", mEtTime.getText().toString().trim());
                    intent.putExtra("contact_type", contact_type);
                    intent.putExtra("contact1", mEtContact1.getText().toString().trim());
                    intent.putExtra("contact", contact);
                    intent.putExtra("age1", mTvAgeMin.getText().toString().trim());
                    intent.putExtra("age2", mTvAgeMax.getText().toString().trim());
                    intent.putExtra("sex", mTvSex.getText().toString().trim());
                    intent.putExtra("type", type);
                    intent.putExtra("id", id);
                    startActivity(intent);
                }else {
                    showToast("点击过于频繁请稍后再试");
                }
            }
        });
    }

    private class MyAction implements ActionListener {

        private int position;

        public MyAction(int i) {
            this.position = i;
        }

        @Override
        public void onCancelClick(BaseDialogFragment dialog) {

        }

        @Override
        public void onDoneClick(BaseDialogFragment dialog) {
            String content = "";
            if (position == 0) {
                TextPickerDialog selectedTitle = (TextPickerDialog) dialog;
                content = selectedTitle.getSelectedTitle();
                mTvContact.setText(content);
            }
            if (position == 1) {
                TextPickerDialog selectedTitle = (TextPickerDialog) dialog;
                content = selectedTitle.getSelectedTitle();
                mTvSex.setText(content);
            }
            if (position == 2) {
                TextPickerDialog selectedTitle = (TextPickerDialog) dialog;
                content = selectedTitle.getSelectedTitle();
                mTvAgeMin.setText(content);
            }
            if (position == 3) {
                TextPickerDialog selectedTitle = (TextPickerDialog) dialog;
                content = selectedTitle.getSelectedTitle();
                mTvAgeMax.setText(content);
            }
        }

    }

    @Override
    protected MPublishPresenter createPresenter() {
        return new MPublishPresenter(this);
    }

    @Override
    public void startIntent() {

    }

    @Override
    public void updategetMLabel(MLableEntity mLableEntity) {

    }

    @Override
    public void updategetMLabelMethod(MLableSalaryEntity mLableSalaryEntity) {

    }

    @Override
    public void updategetMLabelSalary(MLableSalaryEntity mLableSalaryEntity) {

    }

    @Override
    public void updategetMLabelContact(MLableContactEntity mLableContactEntity) {
        mListContact.clear();
        mList.clear();
        if (mLableContactEntity != null) {
            if (mLableContactEntity.getData().size() > 0) {
                mListContact.addAll(mLableContactEntity.getData());
                for (int i = 0; i < mListContact.size(); i++) {
                    mList.add(new TextModel(mListContact.get(i).getName()));
                }
            }
        }
    }

    @Override
    public void updategetIsSing(ResponseData responseData) {

    }

    @Override
    public void updategetCheckJob(ResponseData responseData) {

    }

    @Override
    public void updategetAddJob(ResponseData responseData) {

    }

    @Override
    public void updategetMerUserinfo(MUserInfoEntity mUserInfoEntity) {

    }

    @Override
    public void updategetmdAdd(ResponseData responseData) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("商户端职位其他要求");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("商户端职位其他要求");
        MobclickAgent.onPause(this);
    }
}
