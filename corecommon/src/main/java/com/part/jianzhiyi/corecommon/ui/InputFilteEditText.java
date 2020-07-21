package com.part.jianzhiyi.corecommon.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.part.jianzhiyi.corecommon.R;
import com.part.jianzhiyi.corecommon.constants.ConstantsDimens;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.core.content.ContextCompat;

/*
 * 自定义Editext,增加(字符过滤，光标移动到末尾，清除icon)
 */
@SuppressLint("AppCompatCustomView")
public class InputFilteEditText extends EditText implements TextWatcher {

    private final static int defaultHeight = ConstantsDimens.EDITE_HEIGHT;
    private int delIconId = R.drawable.icon_login_clear;
    private Drawable delDrawalbe;
    private boolean isIconShow;
    private boolean isClearIconVisible = true;
    private OnTextChangeListener onTextChangeListener;
    private boolean isEnableDefaultHeight = true;

    public void setOnTextChangeListener(OnTextChangeListener onTextChangeListener) {
        this.onTextChangeListener = onTextChangeListener;
    }

    public InputFilteEditText(Context context) {
        super(context);
        init(context);
    }

    public InputFilteEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public InputFilteEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    private void init(Context context) {
        delDrawalbe = ContextCompat.getDrawable(context, delIconId);
        addTextChangedListener(this);
        setClearVisibleOrGone(getText());
        if (isEnableDefaultHeight) {
            setMinHeight(defaultHeight);
        }

    }

    public void setEnableDefaultHeight(boolean enableDefaultHeight) {
        isEnableDefaultHeight = enableDefaultHeight;
    }

    public void setPhoneType() {
        setInputFilterLength(11);
        setInputType(InputType.TYPE_CLASS_PHONE);
    }

    public void setNomalType(int maxLength) {
        setInputFilterLength(maxLength);
    }

    public void setNumberType(int maxLength) {
        setInputFilterLength(maxLength);
        setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    public void setInputFilterLength(int textMaxLength) {
        //表情过滤器
        InputFilter emojiFilter = new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest,
                                       int dstart, int dend) {
                Pattern emoji = Pattern.compile(
                        "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                        Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
                Matcher emojiMatcher = emoji.matcher(source);
                if (emojiMatcher.find()) {
                    return "";
                }
                return null;
            }
        };
        //特殊字符过滤器
        InputFilter specialCharFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String regexStr = "[`~@#$%^&*()+=|{}''\\[\\]<><>/~@#￥%……&*（）——+|{}【】”“’]";
                Pattern pattern = Pattern.compile(regexStr);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.matches()) {
                    return "";
                } else {
                    return null;
                }

            }
        };
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(" ") || source.toString().contentEquals("\n")) {
                    return "";
                } else {
                    return null;
                }
            }

        };
        if (textMaxLength > 0) {
            setFilters(new InputFilter[]{emojiFilter, specialCharFilter, new InputFilter.LengthFilter(textMaxLength), filter});
        } else {
            setFilters(new InputFilter[]{emojiFilter, specialCharFilter, filter});
        }

    }

    public void setInputFilterNoLength() {
        //表情过滤器
        InputFilter emojiFilter = new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest,
                                       int dstart, int dend) {
                Pattern emoji = Pattern.compile(
                        "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                        Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
                Matcher emojiMatcher = emoji.matcher(source);
                if (emojiMatcher.find()) {
                    return "";
                }
                return null;
            }
        };
        //特殊字符过滤器
        InputFilter specialCharFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String regexStr = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
                Pattern pattern = Pattern.compile(regexStr);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.matches()) {
                    return "";
                } else {
                    return null;
                }

            }
        };
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(" ") || source.toString().contentEquals("\n")) {
                    return "";
                } else {
                    return null;
                }
            }

        };
        setFilters(new InputFilter[]{emojiFilter, specialCharFilter, filter});

    }


    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
                || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        moveCursorEnd();
        if (!isClearIconVisible) {
            return;
        }
        setClearVisibleOrGone(s);
        if (onTextChangeListener != null) {
            onTextChangeListener.onTextChange(s.toString());
        }
    }

    private void setClearVisibleOrGone(CharSequence s) {
        if (s.length() > 0) {
            isIconShow = true;
            setCompoundDrawablesWithIntrinsicBounds(null, null, delDrawalbe, null);
        } else {
            isIconShow = false;
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused) {
            moveCursorEnd();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isClearIconVisible && isIconShow) {
            Rect bounds = getCompoundDrawables()[2].getBounds();
            int x = (int) event.getX();
            int rectX = getWidth() - bounds.width() - getPaddingRight();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (x > rectX) {
                        clearText();
                        return true;
                    }
                    break;
            }

        }
        return super.onTouchEvent(event);
    }


    public void setClearIconGone() {
        isClearIconVisible = false;
        setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
    }

    private void clearText() {
        setText("");
        isIconShow = false;
        //获取输入焦点
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
        findFocus();
    }

    private void moveCursorEnd() {
        setSelection(getText().length());
    }

    public interface OnTextChangeListener {
        void onTextChange(String content);
    }
}
