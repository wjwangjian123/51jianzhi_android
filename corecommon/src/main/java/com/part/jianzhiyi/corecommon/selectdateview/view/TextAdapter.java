package com.part.jianzhiyi.corecommon.selectdateview.view;

import java.util.List;

public class TextAdapter extends PickerView.Adapter<TextModel> {

    private List<? extends TextModel> textModels;

    public void setTexts(List<? extends TextModel> textModels) {
        this.textModels = textModels;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return textModels == null ? 0 : textModels.size();
    }

    @Override
    public TextModel getItem(int index) {
        return textModels.get(index);
    }
}
