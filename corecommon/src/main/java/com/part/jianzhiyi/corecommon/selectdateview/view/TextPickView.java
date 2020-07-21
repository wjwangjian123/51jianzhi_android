package com.part.jianzhiyi.corecommon.selectdateview.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

public class TextPickView extends PickerViewGroup {

    private List<TextModel> list = new ArrayList<>();
    private final TextAdapter cityAdapter = new TextAdapter();

    private Context context;
    private PickerView pickerView;
    private TextModel selectTextModel;

    public TextPickView(Context context) {
        super(context);
        init(context);
    }

    public TextPickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TextPickView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        pickerView = new PickerView(context);
        settlePickerView(pickerView);

    }


    public void setSelectedText(String text) {
        for (int i = 0; i < list.size(); i++) {
            if (TextUtils.equals(text, list.get(i).getText())) {
                pickerView.setSelectedItemPosition(i);
                return;
            }
        }

    }

    /**
     * 设置省市区数据，省市区数据通过父子关系构成树形结构，此处需传入第一级（省）列表
     */
    public void setTextModels(List<TextModel> textModels) {
        this.list = textModels;
        cityAdapter.setTexts(textModels);
        pickerView.setAdapter(cityAdapter);

        PickerView.OnSelectedItemChangedListener listener = new PickerView.OnSelectedItemChangedListener() {
            @Override
            public void onSelectedItemChanged(PickerView pickerView, int previousPosition, int selectedItemPosition) {
                if (onSelectedDivisionChangedListener != null) {
                    onSelectedDivisionChangedListener.onSelectedDivisionChanged(pickerView.getSelectedItem(TextModel.class));

                }
            }
        };

        pickerView.setOnSelectedItemChangedListener(listener);
    }

    public interface OnSelectedDivisionChangedListener {
        void onSelectedDivisionChanged(TextModel division);
    }

    private OnSelectedDivisionChangedListener onSelectedDivisionChangedListener;

    public void setOnSelectedDateChangedListener(OnSelectedDivisionChangedListener onSelectedDivisionChangedListener) {
        this.onSelectedDivisionChangedListener = onSelectedDivisionChangedListener;
    }

    public TextModel getSelectTextModel() {
        return selectTextModel;
    }
}
