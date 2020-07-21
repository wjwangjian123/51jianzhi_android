package com.part.jianzhiyi.corecommon.selectdateview.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.part.jianzhiyi.corecommon.R;
import com.part.jianzhiyi.corecommon.selectdateview.view.TextModel;
import com.part.jianzhiyi.corecommon.selectdateview.view.TextPickView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;


public class TextPickerDialog extends BaseDialogFragment {

    private TextPickView textPickView;
    private String title;
    private List<TextModel> list = new ArrayList<>();
    private String selectText;

    public static TextPickerDialog newInstance(int type, ActionListener actionListener) {

        return BaseDialogFragment.newInstance(TextPickerDialog.class, type, actionListener);
    }

    @Override
    protected Dialog createDialog(Bundle savedInstanceState) {
        PickerViewDialog dialog = new PickerViewDialog(getActivity());
        dialog.setContentView(R.layout.dialog_text_picker);

        textPickView = dialog.findViewById(R.id.datePicker);
        textPickView.setTextModels(list);
        if (list.size() > 0 && !TextUtils.isEmpty(title)) {
            textPickView.setSelectedText(title);
        }
        textPickView.setOnSelectedDateChangedListener(division -> {
            textPickView.setSelectedText(division.getText());
            selectText = division.getText();
        });
        attachActions(dialog.findViewById(R.id.done), dialog.findViewById(R.id.cancel));
        return dialog;
    }

    public void setTitle(String title) {
        this.title = title;
        selectText = title;
    }

    public void setList(List<TextModel> list) {
        this.list = list;
    }

    @Override
    protected View createView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_date_picker, container, false);
        textPickView = view.findViewById(R.id.datePicker);

        attachActions(view.findViewById(R.id.done), view.findViewById(R.id.cancel));
        return view;
    }

    public String getSelectedTitle() {
        return TextUtils.isEmpty(selectText) ? list.get(0).getText() : selectText;
    }
}
