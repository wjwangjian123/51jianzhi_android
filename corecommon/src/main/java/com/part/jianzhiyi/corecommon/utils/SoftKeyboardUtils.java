package com.part.jianzhiyi.corecommon.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by jyx on 2019/5/23
 *
 * @author jyx
 * @describe
 */
public class SoftKeyboardUtils {

    private View rootView;//activity的根视图
    int rootViewVisibleHeight;//纪录根视图的显示高度
    private OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener;

    public SoftKeyboardUtils(Activity activity) {
        //获取activity的根视图
        rootView = activity.getWindow().getDecorView();

        //监听视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //获取当前根视图在屏幕上显示的大小
                Rect r = new Rect();
                rootView.getWindowVisibleDisplayFrame(r);
                int visibleHeight = r.height();
                if (rootViewVisibleHeight == 0) {
                    rootViewVisibleHeight = visibleHeight;
                    return;
                }

                //根视图显示高度没有变化，可以看作软键盘显示／隐藏状态没有改变
                if (rootViewVisibleHeight == visibleHeight) {
                    return;
                }

                //根视图显示高度变小超过200，可以看作软键盘显示了
                if (rootViewVisibleHeight - visibleHeight > 200) {
                    if (onSoftKeyBoardChangeListener != null) {
                        onSoftKeyBoardChangeListener.keyBoardShow(rootViewVisibleHeight - visibleHeight);
                    }
                    rootViewVisibleHeight = visibleHeight;
                    return;
                }

                //根视图显示高度变大超过200，可以看作软键盘隐藏了
                if (visibleHeight - rootViewVisibleHeight > 200) {
                    if (onSoftKeyBoardChangeListener != null) {
                        onSoftKeyBoardChangeListener.keyBoardHide(visibleHeight - rootViewVisibleHeight);
                    }
                    rootViewVisibleHeight = visibleHeight;
                    return;
                }

            }
        });
    }

    private void setOnSoftKeyBoardChangeListener(OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener) {
        this.onSoftKeyBoardChangeListener = onSoftKeyBoardChangeListener;
    }

    public interface OnSoftKeyBoardChangeListener {
        void keyBoardShow(int height);

        void keyBoardHide(int height);
    }

    public void setListener(OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener) {
        setOnSoftKeyBoardChangeListener(onSoftKeyBoardChangeListener);
    }

    /**
     * 隐藏或显示软键盘
     * 如果现在是显示调用后则隐藏 反之则显示
     *
     * @param activity
     */
    public static void showORhideSoftKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 强制显示软键盘
     *
     * @param activity
     */
    public static void showSoftKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(activity.getWindow().getDecorView(), InputMethodManager.SHOW_FORCED);
    }

    /**
     * sgf
     * 强制隐藏软键盘
     *
     * @param activity
     */
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        //强制隐藏键盘
        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    /**
     * 调用系统方法 强制隐藏软键盘
     *
     * @param activity
     */
    public static void hideSystemSoftKeyboard(Activity activity) {
        ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 判断软键盘是否显示方法
     *
     * @param activity
     * @return
     */

    public static boolean isSoftShowing(Activity activity) {
        //获取当屏幕内容的高度
        int screenHeight = activity.getWindow().getDecorView().getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        //DecorView即为activity的顶级view
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        //考虑到虚拟导航栏的情况（虚拟导航栏情况下：screenHeight = rect.bottom + 虚拟导航栏高度）
        //选取screenHeight*2/3进行判断
        return screenHeight * 2 / 3 > rect.bottom + getSoftButtonsBarHeight(activity);
    }

    /**
     * 底部虚拟按键栏的高度
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static int getSoftButtonsBarHeight(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        //这个方法获取可能不是真实屏幕的高度
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        //获取当前屏幕的真实高度
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        } else {
            return 0;
        }
    }

    public static void setEditText(final EditText editText, AppCompatActivity appCompatActivity) {
        editText.setFocusable(false);
        editText.setCursorVisible(false);
        editText.setFocusableInTouchMode(false);
        SoftKeyboardUtils mSoftKeyboardUtils = new SoftKeyboardUtils(appCompatActivity);

        mSoftKeyboardUtils.setListener(new OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                editText.setFocusable(true);
                editText.setCursorVisible(true);
                editText.requestFocus();
                editText.setFocusableInTouchMode(true);
            }

            @Override
            public void keyBoardHide(int height) {
                editText.setFocusable(false);
                editText.setCursorVisible(false);
                editText.setFocusableInTouchMode(false);
            }
        });
    }

    public static void setEditTextListener(final EditText editText, final AppCompatActivity appCompatActivity) {
        //设置点击事件,显示软键盘
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSoftKeyboard(appCompatActivity);
            }
        });
        //防止EditText点击两次才获取到焦点
        editText.setOnTouchListener(new View.OnTouchListener() {
            //按住和松开的标识
            int flag = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                flag++;
                if (flag == 2) {
                    flag = 0;
                    //处理逻辑
                    editText.setFocusable(true);
                    editText.setFocusableInTouchMode(true);
                    editText.setCursorVisible(true);
                }
                return false;
            }
        });
    }
}
