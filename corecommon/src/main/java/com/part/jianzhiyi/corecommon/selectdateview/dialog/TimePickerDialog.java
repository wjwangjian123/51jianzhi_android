package com.part.jianzhiyi.corecommon.selectdateview.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.part.jianzhiyi.corecommon.R;
import com.part.jianzhiyi.corecommon.selectdateview.view.TimePickerView;

import java.util.Calendar;

import androidx.annotation.Nullable;


public class TimePickerDialog extends BaseDialogFragment {

    private TimePickerView timePickerView;
    private String title;
    private Calendar selectedCalendar;

    public static TimePickerDialog newInstance(int type, ActionListener actionListener) {

        return BaseDialogFragment.newInstance(TimePickerDialog.class, type, actionListener);
    }


    @Override
    protected Dialog createDialog(Bundle savedInstanceState) {
        PickerViewDialog dialog = new PickerViewDialog(getActivity());
        dialog.setContentView(R.layout.dialog_time_picker);
        timePickerView = dialog.findViewById(R.id.timePicker);
        timePickerView.setType(4);
        TextView tvContent = dialog.findViewById(R.id.tv_content);
        tvContent.setText(title);
        if (selectedCalendar != null) {
            timePickerView.setSelectedDate(selectedCalendar);
        }

        attachActions(dialog.findViewById(R.id.done), dialog.findViewById(R.id.cancel));
        return dialog;
    }


    public void setSelectedCalendar(Calendar selectedCalendar) {
        this.selectedCalendar = selectedCalendar;
    }

    @Override
    protected View createView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_time_picker, container, false);
        timePickerView = view.findViewById(R.id.timePicker);
        attachActions(view.findViewById(R.id.done), view.findViewById(R.id.cancel));
        return view;
    }

    public Calendar getSelectedDate() {
        return timePickerView.getSelectedDate();
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
