package com.part.jianzhiyi.modulemerchants.mvp.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.part.jianzhiyi.corecommon.ui.InputFilteEditText;
import com.part.jianzhiyi.modulemerchants.R;
import com.part.jianzhiyi.modulemerchants.base.BaseActivity;
import com.part.jianzhiyi.modulemerchants.customview.ColorPickerView;
import com.part.jianzhiyi.modulemerchants.customview.RichEditor;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MCityEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MJobInfoEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MLableContactEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MLableEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MLableSalaryEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MUserInfoEntity;
import com.part.jianzhiyi.modulemerchants.mvp.contract.MPublishContract;
import com.part.jianzhiyi.modulemerchants.mvp.presenter.MPublishPresenter;
import com.umeng.analytics.MobclickAgent;

public class MerPositionInfoActivity extends BaseActivity<MPublishPresenter> implements MPublishContract.IMPublishView, View.OnClickListener {

    private InputFilteEditText mEtTitle;
    private TextView mTvClear;
    private InputFilteEditText mEtNum;
    private TextView mTvNext;
    private String label_id;
    private String job_id;
    private MJobInfoEntity mJobInfoEntity;
    private int type = 0;
    private int mpositionType = 0;
    /********************View**********************/
    //文本编辑器
    private RichEditor mEditor;
    //加粗按钮
    private ImageView mBold;
    //颜色编辑器
    private TextView mTextColor;
    //显示显示View
    private LinearLayout llColorView;
    //预览按钮
    private TextView mPreView;
    //按序号排列（ol）
    private ImageView mListOL;
    //按序号排列（ul）
    private ImageView mListUL;
    //字体下划线
    private ImageView mLean;
    //字体倾斜
    private ImageView mItalic;
    //字体左对齐
    private ImageView mAlignLeft;
    //字体右对齐
    private ImageView mAlignRight;
    //字体居中对齐
    private ImageView mAlignCenter;
    /********************boolean开关**********************/
    //是否加粗
    boolean isClickBold = false;
    //是否正在执行动画
    boolean isAnimating = false;
    //是否按ol排序
    boolean isListOl = false;
    //是否按ul排序
    boolean isListUL = false;
    //是否下划线字体
    boolean isTextLean = false;
    //是否下倾斜字体
    boolean isItalic = false;
    //是否左对齐
    boolean isAlignLeft = false;
    //是否右对齐
    boolean isAlignRight = false;
    //是否中对齐
    boolean isAlignCenter = false;
    //是否缩进
    boolean isIndent = false;
    //是否较少缩进
    boolean isOutdent = false;
    //是否索引
    boolean isBlockquote = false;
    //字体中划线
    boolean isStrikethrough = false;
    //字体上标
    boolean isSuperscript = false;
    //字体下标
    boolean isSubscript = false;
    /********************变量**********************/
    //折叠视图的宽高
    private int mFoldedViewMeasureHeight;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mer_position_info;
    }

    @Override
    protected void initView() {
        mEtTitle = (InputFilteEditText) findViewById(R.id.et_title);
        mTvClear = (TextView) findViewById(R.id.tv_clear);
//        mEtContent = (InputFilteEditText) findViewById(R.id.et_content);
        mEtNum = (InputFilteEditText) findViewById(R.id.et_num);
        mTvNext = (TextView) findViewById(R.id.tv_next);
        mBold = findViewById(R.id.button_bold);
        mTextColor = findViewById(R.id.button_text_color);
        llColorView = findViewById(R.id.ll_main_color);
        mPreView = findViewById(R.id.tv_main_preview);
        mListOL = findViewById(R.id.button_list_ol);
        mListUL = findViewById(R.id.button_list_ul);
        mLean = findViewById(R.id.button_underline);
        mItalic = findViewById(R.id.button_italic);
        mAlignLeft = findViewById(R.id.button_align_left);
        mAlignRight = findViewById(R.id.button_align_right);
        mAlignCenter = findViewById(R.id.button_align_center);

        initToolbar("");
        //富文本编辑器
        initEditor();
        initColorPicker();
        getViewMeasureHeight();
    }

    /**
     * 获取控件的高度
     */
    private void getViewMeasureHeight() {
        //获取像素密度
        float mDensity = getResources().getDisplayMetrics().density;
        //获取布局的高度
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        llColorView.measure(w, h);
        int height = llColorView.getMeasuredHeight();
        mFoldedViewMeasureHeight = (int) (mDensity * height + 0.5);
    }

    /**
     * 初始化文本编辑器
     */
    private void initEditor() {
        mEditor = findViewById(R.id.re_main_editor);
        //mEditor.setEditorHeight(400);
        //输入框显示字体的大小
        mEditor.setEditorFontSize(14);
        //输入框显示字体的颜色
        mEditor.setEditorFontColor(Color.parseColor("#292E3B"));
        //输入框背景设置
        mEditor.setEditorBackgroundColor(Color.WHITE);
        //mEditor.setBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundResource(R.drawable.bg);
        //mEditor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        //输入框文本padding
        mEditor.setPadding(10, 10, 10, 10);
        //输入提示文本
        mEditor.setPlaceholder("请输入编辑内容");
        //是否允许输入
        //mEditor.setInputEnabled(false);
        //文本输入框监听事件
        mEditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {
                Log.d("mEditor", "html文本：" + text);
            }
        });
    }

    /**
     * 初始化颜色选择器
     */
    private void initColorPicker() {
        ColorPickerView right = findViewById(R.id.cpv_main_color);
        right.setOnColorPickerChangeListener(new ColorPickerView.OnColorPickerChangeListener() {
            @Override
            public void onColorChanged(ColorPickerView picker, int color) {
                mTextColor.setBackgroundColor(color);
                mEditor.setTextColor(color);
            }

            @Override
            public void onStartTrackingTouch(ColorPickerView picker) {

            }

            @Override
            public void onStopTrackingTouch(ColorPickerView picker) {

            }
        });
    }

    @Override
    protected void initData() {
        label_id = getIntent().getStringExtra("label_id");
        job_id = getIntent().getStringExtra("job_id");
        type = getIntent().getIntExtra("type", 0);
        mpositionType = getIntent().getIntExtra("mpositionType", 0);
        if (type == 1) {
            mJobInfoEntity = (MJobInfoEntity) getIntent().getSerializableExtra("mJobInfoEntity");
        }
        if (mJobInfoEntity != null && mJobInfoEntity.getData() != null) {
            mEtTitle.setText(mJobInfoEntity.getData().getTitle());
            mEditor.setHtml(mJobInfoEntity.getData().getContent());
            mEtNum.setText(mJobInfoEntity.getData().getNumber());
        }
    }

    private long clickTime = 0;

    @Override
    protected void setListener() {
        super.setListener();
        mBold.setOnClickListener(this);
        mTextColor.setOnClickListener(this);
        mPreView.setOnClickListener(this);
        mListOL.setOnClickListener(this);
        mListUL.setOnClickListener(this);
        mLean.setOnClickListener(this);
        mItalic.setOnClickListener(this);
        mAlignLeft.setOnClickListener(this);
        mAlignRight.setOnClickListener(this);
        mAlignCenter.setOnClickListener(this);
        mTvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setHtml("");
            }
        });
        mTvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mEtTitle.getText().toString().trim())) {
                    showToast("请输入标题");
                    return;
                }
                if (TextUtils.isEmpty(mEditor.getHtml())) {
                    showToast("请输入工作内容");
                    return;
                }
                if (TextUtils.isEmpty(mEtNum.getText().toString().trim())) {
                    showToast("请输入招聘人数");
                    return;
                }
                if (System.currentTimeMillis() - clickTime > 3000) {
                    clickTime = System.currentTimeMillis();
                    mPresenter.getTextFilter(mEtTitle.getText().toString().trim(), mEditor.getHtml(), "", "", "");
                } else {
                    showToast("点击过于频繁请稍后再试");
                }
            }
        });
    }

    @Override
    protected MPublishPresenter createPresenter() {
        return new MPublishPresenter(this);
    }

    @Override
    public void startIntent() {

    }

    private long clickTime1 = 0;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button_bold) {//字体加粗
//            if (isClickBold) {
//                mBold.setImageResource(R.mipmap.bold);
//            } else {  //加粗
//                mBold.setImageResource(R.mipmap.bold_);
//            }
            isClickBold = !isClickBold;
            mEditor.setBold();
        } else if (id == R.id.button_text_color) {//设置字体颜色
            //如果动画正在执行,直接return,相当于点击无效了,不会出现当快速点击时,
            // 动画的执行和ImageButton的图标不一致的情况
            if (isAnimating) {
                return;
            }
            //如果动画没在执行,走到这一步就将isAnimating制为true , 防止这次动画还没有执行完毕的
            //情况下,又要执行一次动画,当动画执行完毕后会将isAnimating制为false,这样下次动画又能执行
            isAnimating = true;

            if (llColorView.getVisibility() == View.GONE) {
                //打开动画
                animateOpen(llColorView);
            } else {
                //关闭动画
                animateClose(llColorView);
            }
        } else if (id == R.id.button_list_ol) {
//            if (isListOl) {
//                mListOL.setImageResource(R.mipmap.list_ol);
//            } else {
//                mListOL.setImageResource(R.mipmap.list_ol_);
//            }
            isListOl = !isListOl;
            mEditor.setNumbers();
        } else if (id == R.id.button_list_ul) {
//            if (isListUL) {
//                mListUL.setImageResource(R.mipmap.list_ul);
//            } else {
//                mListUL.setImageResource(R.mipmap.list_ul_);
//            }
            isListUL = !isListUL;
            mEditor.setBullets();
        } else if (id == R.id.button_underline) {
//            if (isTextLean) {
//                mLean.setImageResource(R.mipmap.underline);
//            } else {
//                mLean.setImageResource(R.mipmap.underline_);
//            }
            isTextLean = !isTextLean;
            mEditor.setUnderline();
        } else if (id == R.id.button_italic) {
//            if (isItalic) {
//                mItalic.setImageResource(R.mipmap.lean);
//            } else {
//                mItalic.setImageResource(R.mipmap.lean_);
//            }
            isItalic = !isItalic;
            mEditor.setItalic();
        } else if (id == R.id.button_align_left) {
//            if (isAlignLeft) {
//                mAlignLeft.setImageResource(R.mipmap.align_left);
//            } else {
//                mAlignLeft.setImageResource(R.mipmap.align_left_);
//            }
            isAlignLeft = !isAlignLeft;
            mEditor.setAlignLeft();
        } else if (id == R.id.button_align_right) {
//            if (isAlignRight) {
//                mAlignRight.setImageResource(R.mipmap.align_right);
//            } else {
//                mAlignRight.setImageResource(R.mipmap.align_right_);
//            }
            isAlignRight = !isAlignRight;
            mEditor.setAlignRight();
        } else if (id == R.id.button_align_center) {
//            if (isAlignCenter) {
//                mAlignCenter.setImageResource(R.mipmap.align_center);
//            } else {
//                mAlignCenter.setImageResource(R.mipmap.align_center_);
//            }
            isAlignCenter = !isAlignCenter;
            mEditor.setAlignCenter();
        }
        //H1--H6省略，需要的自己添加
        else if (id == R.id.tv_main_preview) {
            if (System.currentTimeMillis() - clickTime1 > 3000) {
                clickTime1 = System.currentTimeMillis();
                //预览
                Intent intent = new Intent(MerPositionInfoActivity.this, MerWebDataActivity.class);
                intent.putExtra("diarys", mEditor.getHtml());
                startActivity(intent);
            } else {
                showToast("点击过于频繁请稍后再试");
            }
        }
    }

    /**
     * 开启动画
     *
     * @param view 开启动画的view
     */
    private void animateOpen(LinearLayout view) {
        view.setVisibility(View.VISIBLE);
        ValueAnimator animator = createDropAnimator(view, 0, mFoldedViewMeasureHeight);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimating = false;
            }
        });
        animator.start();
    }

    /**
     * 关闭动画
     *
     * @param view 关闭动画的view
     */
    private void animateClose(final LinearLayout view) {
        int origHeight = view.getHeight();
        ValueAnimator animator = createDropAnimator(view, origHeight, 0);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
                isAnimating = false;
            }
        });
        animator.start();
    }


    /**
     * 创建动画
     *
     * @param view  开启和关闭动画的view
     * @param start view的高度
     * @param end   view的高度
     * @return ValueAnimator对象
     */
    private ValueAnimator createDropAnimator(final View view, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = value;
                view.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("商户端完善职位信息页面");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("商户端完善职位信息页面");
        MobclickAgent.onPause(this);
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
    public void updategetTextFilter(ResponseData responseData) {
        if (responseData != null) {
            if (responseData.getCode().equals("200")) {
                Intent intent = new Intent(MerPositionInfoActivity.this, MerSalaryActivity.class);
                intent.putExtra("label_id", label_id);
                intent.putExtra("job_id", job_id);
                intent.putExtra("title", mEtTitle.getText().toString().trim());
                intent.putExtra("content", mEditor.getHtml());
                intent.putExtra("num", mEtNum.getText().toString().trim());
                intent.putExtra("type", type);
                intent.putExtra("mpositionType", mpositionType);
                intent.putExtra("mJobInfoEntity", mJobInfoEntity);
                startActivity(intent);
            } else {
                showToast(responseData.getMsg());
            }
        }
    }

    @Override
    public void updategetMerCity(MCityEntity mCityEntity) {

    }
}
