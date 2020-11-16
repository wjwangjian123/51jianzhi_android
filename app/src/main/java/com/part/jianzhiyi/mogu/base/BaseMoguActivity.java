package com.part.jianzhiyi.mogu.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.mogu.utils.PhoneScreenUtils;
import com.part.jianzhiyi.utils.statusbar.StatusBarUtil;

import androidx.fragment.app.FragmentActivity;


public abstract class BaseMoguActivity extends FragmentActivity {

    /**
     * 下拉状态
     */
    public static final int STATUS_PULL_TO_REFRESH = 0;

    /**
     * 释放立即刷新状态
     */
    public static final int STATUS_RELEASE_TO_REFRESH = 1;

    /**
     * 正在刷新状态
     */
    public static final int STATUS_REFRESHING = 2;

    /**
     * 刷新完成或未刷新状态
     */
    public static final int STATUS_REFRESH_FINISHED = 3;

    /**
     * 下拉头部回滚的速度
     */
    public static final int SCROLL_SPEED = -30;


    /**
     * 下拉的长度
     */

    private int pullLength;


    /**
     * 下拉刷新的回调接口
     */
    private PullToRefreshListener mListener;

    /**
     * 判断是否可以下拉接口
     */
    private IsAbleToPull isAbleToPullListener;

    /**
     * 下拉头的View
     */
    private View header;

    /**
     * 需要去下拉刷新的ListView
     */
    private View listenedView;

    /**
     * 刷新时显示的进度条
     */
    // private ProgressBar progressBar;


    //三角形
    private ImageView iv_triangle;

    /**
     * 指示下拉和释放的箭头
     */
    // private ImageView arrow;

    /**
     * 指示下拉和释放的文字描述
     */
    //private TextView description;


    /**
     * 下拉头的布局参数
     */
    private ViewGroup.MarginLayoutParams headerLayoutParams;


    /**
     * 为了防止不同界面的下拉刷新在上次更新时间上互相有冲突，使用id来做区分
     */
    private int mId = -1;

    /**
     * 下拉头的高度
     */
    private int hideHeaderHeight;

    /**
     * 当前处理什么状态，可选值有STATUS_PULL_TO_REFRESH, STATUS_RELEASE_TO_REFRESH,
     * STATUS_REFRESHING 和 STATUS_REFRESH_FINISHED
     */
    private int currentStatus = STATUS_REFRESH_FINISHED;
    ;

    /**
     * 记录上一次的状态是什么，避免进行重复操作
     */
    private int lastStatus = currentStatus;

    /**
     * 手指按下时的屏幕纵坐标
     */
    private float yDown;

    /**
     * 在被判定为滚动之前用户手指可以移动的最大值。
     */
    private int touchSlop;

    /**
     * 是否已加载过一次layout，这里onLayout中的初始化只需加载一次
     */
    private boolean loadOnce;

    /**
     * 当前是否可以下拉，只有ListView滚动到头的时候才允许下拉
     */
    private boolean ableToPull;

    /**
     * 刷新控件是否已测量过宽高
     */
    private boolean hasMeasured = false;

    /**
     * 是否显示返回按钮
     */
    private boolean isBackable = false;

    private String strTitle = "";
    private int titleHeight = 120;
    private int titleColor;
    private Context mContext;
    private LinearLayout mLlRoot;
    private RelativeLayout mRlTitle;
    private PhoneScreenUtils mPhoneScreenUtils;
    private RelativeLayout mRlRoot;
    private RelativeLayout mRlTitleVisiable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mPhoneScreenUtils = PhoneScreenUtils.getInstance(mContext);

        //设置状态栏
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            StatusBarUtil.setStatusBarColor(this, 0x55000000);
        }
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);

        initRootView();
    }

    protected void initRootView() {
        mRlRoot = new RelativeLayout(mContext);
        ViewGroup.LayoutParams rlLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mRlRoot.setLayoutParams(rlLayoutParams);

        mLlRoot = new LinearLayout(mContext);
        RelativeLayout.LayoutParams llLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mLlRoot.setOrientation(LinearLayout.VERTICAL);
        llLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP | RelativeLayout.ALIGN_PARENT_LEFT);
        mLlRoot.setLayoutParams(llLayoutParams);

        mRlRoot.addView(mLlRoot);

        ((Activity) mContext).setContentView(mRlRoot);

        View contentView = getContentView();
        mLlRoot.addView(contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        onLoad();
    }

    protected abstract View getContentView();

    protected abstract void onLoad();

    public void initTitle(boolean isBackable) {
        this.isBackable = isBackable;
        mRlTitle = new RelativeLayout(mContext);
        //在根布局添加标题布局
        mRlTitle.setBackgroundResource(R.drawable.icon_task_bg_top);
        ViewGroup.LayoutParams rlLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mPhoneScreenUtils.getScale(titleHeight));
        mRlTitle.setLayoutParams(rlLayoutParams);
        mLlRoot.addView(mRlTitle, 0);
        setTitle(mRlTitle);
    }

    private void setTitle(RelativeLayout rlTitle) {
        //标题栏添加文字
        TextView tvTitle = new TextView(mContext);
        tvTitle.setText(strTitle);
        tvTitle.setTextSize(mPhoneScreenUtils.getTitleTextSize());
        tvTitle.setTextColor(Color.WHITE);
        RelativeLayout.LayoutParams tvLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        tvTitle.setLayoutParams(tvLayoutParams);
        rlTitle.addView(tvTitle);

        //是否有返回按钮
        if (isBackable) {
            ImageView ivBack = new ImageView(mContext);
            ivBack.setImageResource(R.drawable.ic_white_back);
            RelativeLayout.LayoutParams ivLayoutParams = new RelativeLayout.LayoutParams(mPhoneScreenUtils.getScale(60), mPhoneScreenUtils.getScale(60));
            ivLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT | RelativeLayout.CENTER_VERTICAL);
            ivLayoutParams.leftMargin = mPhoneScreenUtils.getScale(20);
            ivBack.setLayoutParams(ivLayoutParams);
            rlTitle.addView(ivBack);
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity) mContext).finish();
                }
            });
        }
    }

    public void setTitleText(String strTitle) {
        this.strTitle = strTitle;
        if (mRlTitle != null) {
            ((TextView) (mRlTitle.getChildAt(0))).setText(strTitle);
        }
        if (mRlTitleVisiable != null) {
            ((TextView) (mRlTitleVisiable.getChildAt(0))).setText(strTitle);
        }
    }

    protected void setTitleColor(Integer color) {
        this.titleColor = color;
        if (mRlTitle != null) {
            mRlTitle.setBackgroundColor(color);
        }
        if (mRlTitleVisiable != null) {
            mRlTitleVisiable.setBackgroundColor(color);
        }
    }

    protected void setTitleHeight(int height) {
        this.titleHeight = height;
        if (mRlTitle != null) {
            ViewGroup.LayoutParams layoutParams = mRlTitle.getLayoutParams();
            layoutParams.height = mPhoneScreenUtils.getScale(height);
            mRlTitle.setLayoutParams(layoutParams);
        }
        if (mRlTitleVisiable != null) {
            ViewGroup.LayoutParams layoutParams = mRlTitleVisiable.getLayoutParams();
            layoutParams.height = mPhoneScreenUtils.getScale(height);
            mRlTitleVisiable.setLayoutParams(layoutParams);
        }
    }

    int preDistance = 0;

    /**
     * 下拉刷新控件的初始化函数，会在运行时动态添加一个下拉头的布局。
     */
    public void initFreshableView(final View listenedView, IsAbleToPull listener) {
        header = LayoutInflater.from(mContext).inflate(R.layout.pull_down_refresh_head, null, true);
        iv_triangle = (ImageView) header.findViewById(R.id.iv_triangle);
        touchSlop = ViewConfiguration.get(mContext).getScaledTouchSlop();
        if (mRlTitle != null) {
            mLlRoot.addView(header, 1);
        } else {
            mLlRoot.addView(header, 0);
        }

        ViewTreeObserver viewTreeObserver = header.getViewTreeObserver();
        // 获取控件宽度
        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (!hasMeasured) {
                    hideHeaderHeight = -header.getHeight();
                    pullLength = hideHeaderHeight / 4 * 3;
                    headerLayoutParams = (ViewGroup.MarginLayoutParams) header.getLayoutParams();
                    headerLayoutParams.topMargin = hideHeaderHeight;
                    header.setLayoutParams(headerLayoutParams);
                    hasMeasured = true;
                    if (mRlTitle != null) {
                        mRlTitleVisiable = new RelativeLayout(mContext);
                        //在根布局添加标题布局
                        mRlTitleVisiable.setBackgroundResource(R.color.colorPrimary);
                        RelativeLayout.LayoutParams rlLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mPhoneScreenUtils.getScale(titleHeight));
                        rlLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP | RelativeLayout.ALIGN_PARENT_LEFT);
                        mRlTitleVisiable.setLayoutParams(rlLayoutParams);
                        mRlRoot.addView(mRlTitleVisiable);
                        setTitle(mRlTitleVisiable);
                    }
                }
                return true;
            }
        });

        this.isAbleToPullListener = listener;
        this.listenedView = listenedView;
        this.listenedView.setOnTouchListener(new View.OnTouchListener() {
            /**
             * 当ListView被触摸时调用，其中处理了各种下拉刷新的具体逻辑。
             */
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setIsAbleToPull(event);
                if (ableToPull) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            yDown = event.getRawY();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            float yMove = event.getRawY();
                            int distance = (int) (yMove - yDown);
                            // 如果手指是下滑状态，并且下拉头是完全隐藏的，就屏蔽下拉事件
                            if (distance <= pullLength && headerLayoutParams.topMargin <= hideHeaderHeight) {
                                return false;
                            }
                            if (distance < touchSlop) {
                                return false;
                            }
                            if (currentStatus != STATUS_REFRESHING) {
                                if (headerLayoutParams.topMargin > pullLength) {
                                    currentStatus = STATUS_RELEASE_TO_REFRESH;
                                } else {
                                    currentStatus = STATUS_PULL_TO_REFRESH;
                                }
                                // 通过偏移下拉头的topMargin值，来实现下拉效果，修改分母可以有不同的拉力效果
                                headerLayoutParams.topMargin = (int) ((distance / 2.8) + hideHeaderHeight);
                                header.setLayoutParams(headerLayoutParams);
                                //添加动画（这里是手指控制滑动的动画）、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、
                                rotateTriangle((distance - preDistance) / 2);
                                preDistance = distance;
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                        default:
                            if (currentStatus == STATUS_RELEASE_TO_REFRESH) {
                                // 松手时如果是释放立即刷新状态，就去调用正在刷新的任务
                                new RefreshingTask().execute();
                            } else if (currentStatus == STATUS_PULL_TO_REFRESH) {
                                // 松手时如果是下拉状态，就去调用隐藏下拉头的任务
                                new HideHeaderTask().execute();
                            }
                            break;
                    }
                    // 时刻记得更新下拉头中的信息
                    if (currentStatus == STATUS_PULL_TO_REFRESH
                            || currentStatus == STATUS_RELEASE_TO_REFRESH) {
                        updateHeaderView();
                        // 当前正处于下拉或释放状态，要让ListView失去焦点，否则被点击的那一项会一直处于选中状态
                        listenedView.setPressed(false);
                        listenedView.setFocusable(false);
                        listenedView.setFocusableInTouchMode(false);
                        lastStatus = currentStatus;
                        // 当前正处于下拉或释放状态，通过返回true屏蔽掉ListView的滚动事件
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void setIsAbleToPull(MotionEvent event) {
        ableToPull = isAbleToPullListener.setIsAbleToPull(event);
        if (ableToPull) {
            if (!ableToPull) {
                yDown = event.getRawY();
            }
        } else {
            if (headerLayoutParams.topMargin != hideHeaderHeight) {
                headerLayoutParams.topMargin = hideHeaderHeight;
                header.setLayoutParams(headerLayoutParams);
            }
        }
    }

    /**
     * 给下拉刷新控件注册一个监听器。
     *
     * @param listener 监听器的实现。
     * @param id       为了防止不同界面的下拉刷新在上次更新时间上互相有冲突， 请不同界面在注册下拉刷新监听器时一定要传入不同的id。
     */
    public void setOnRefreshListener(PullToRefreshListener listener, int id) {
        mListener = listener;
        mId = id;
    }

    /**
     * 当所有的刷新逻辑完成后，调用一下，否则你的刷新控件将一直处于正在刷新状态。
     */
    public void finishRefreshing() {
        currentStatus = STATUS_REFRESH_FINISHED;
        new HideHeaderTask().execute();
    }

    public interface IsAbleToPull {
        boolean setIsAbleToPull(MotionEvent event);
    }

    /**
     * 更新下拉头中的信息。
     */
    private void updateHeaderView() {
        if (lastStatus != currentStatus) {
            if (currentStatus == STATUS_PULL_TO_REFRESH) {
                //下拉状态

            } else if (currentStatus == STATUS_RELEASE_TO_REFRESH) {
                //释放状态

            } else if (currentStatus == STATUS_REFRESHING) {
                //刷新中

                iv_triangle.clearAnimation();
                TriangelRotate();
            }
        }
    }

    float preDegres = 0;

    //手指下拉的时候的动画
    private void rotateTriangle(float angle) {
        float pivotX = iv_triangle.getWidth() / 2;
        float pivotY = (float) (iv_triangle.getHeight() / 2);
        float fromDegrees = preDegres;
        float toDegrees = angle;

        RotateAnimation animation = new RotateAnimation(fromDegrees, toDegrees, pivotX, pivotY);
        animation.setDuration(10);
        animation.setFillAfter(true);
        iv_triangle.startAnimation(animation);
        preDegres = preDegres + angle;
    }

    //刷新的时候一直转的动画
    private void TriangelRotate() {
        float pivotX = iv_triangle.getWidth() / 2;
        float pivotY = (float) (iv_triangle.getHeight() / 2);
        RotateAnimation animation = new RotateAnimation(0f, 90f, pivotX, pivotY);
        animation.setDuration(50);
        animation.setRepeatMode(Animation.RESTART);
        animation.setRepeatCount(Animation.INFINITE);
        preDegres = 0;
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        animation.setInterpolator(linearInterpolator);
        iv_triangle.startAnimation(animation);
    }

    /**
     * 正在刷新的任务，在此任务中会去回调注册进来的下拉刷新监听器。
     * 下拉超过了，要返回到刷新的位置
     *
     * @author guolin
     */
    class RefreshingTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            int topMargin = headerLayoutParams.topMargin;
            while (true) {
                topMargin = topMargin + SCROLL_SPEED;
                if (topMargin <= pullLength) {
                    topMargin = pullLength;
                    break;
                }
                publishProgress(topMargin);
                sleep(10);
            }
            currentStatus = STATUS_REFRESHING;
            publishProgress(pullLength);
            if (mListener != null) {
                mListener.onRefresh();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... topMargin) {
            updateHeaderView();
            headerLayoutParams.topMargin = topMargin[0];
            header.setLayoutParams(headerLayoutParams);
            //添加动画（这里是手指松开后返回到刷新位置的动画）、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、
        }

    }

    /**
     * 隐藏下拉头的任务，当未进行下拉刷新或下拉刷新完成后，此任务将会使下拉头重新隐藏。
     *
     * @author guolin
     */
    class HideHeaderTask extends AsyncTask<Void, Integer, Integer> {

        @SuppressLint("WrongThread")
        @Override
        protected Integer doInBackground(Void... params) {
            int topMargin = headerLayoutParams.topMargin;
            while (true) {
                topMargin = topMargin + SCROLL_SPEED;
                if (topMargin <= hideHeaderHeight) {
                    topMargin = hideHeaderHeight;
                    break;
                }
                publishProgress(topMargin);
                sleep(10);
            }
            return topMargin;
        }

        @Override
        protected void onProgressUpdate(Integer... topMargin) {
            headerLayoutParams.topMargin = topMargin[0];
            header.setLayoutParams(headerLayoutParams);
            //添加动画（这里是手指松开后返回到初始位置的动画）、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、

        }

        @Override
        protected void onPostExecute(Integer topMargin) {
            headerLayoutParams.topMargin = topMargin;
            header.setLayoutParams(headerLayoutParams);
            currentStatus = STATUS_REFRESH_FINISHED;
            //完成刷新、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、
            iv_triangle.clearAnimation();
        }
    }

    /**
     * 使当前线程睡眠指定的毫秒数。
     *
     * @param time 指定当前线程睡眠多久，以毫秒为单位
     */
    private void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下拉刷新的监听器，使用下拉刷新的地方应该注册此监听器来获取刷新回调。
     *
     * @author guolin
     */
    public interface PullToRefreshListener {

        /**
         * 刷新时会去回调此方法，在方法内编写具体的刷新逻辑。注意此方法是在子线程中调用的， 你可以不必另开线程来进行耗时操作。
         */
        void onRefresh();

    }

}
