package com.part.jianzhiyi.corecommon.selectdateview.view;

public class TextModel implements PickerView.PickerItem {
    private String title;

    public TextModel(String title) {
        this.title = title;
    }

    @Override
    public String getText() {
        return title;
    }
}
