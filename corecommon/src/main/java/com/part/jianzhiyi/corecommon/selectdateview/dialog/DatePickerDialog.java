package com.part.jianzhiyi.corecommon.selectdateview.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.part.jianzhiyi.corecommon.R;
import com.part.jianzhiyi.corecommon.selectdateview.view.DateTimePickerView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import androidx.annotation.Nullable;


public class DatePickerDialog extends BaseDialogFragment {

    private DateTimePickerView datePicker;
    private Calendar selectedCalendar;
    private View done;
    private View cancel;

    public static DatePickerDialog newInstance(int type, ActionListener actionListener) {

        return BaseDialogFragment.newInstance(DatePickerDialog.class, type, actionListener);
    }

    @Override
    protected Dialog createDialog(Bundle savedInstanceState) {
        PickerViewDialog dialog = new PickerViewDialog(getActivity());
        dialog.setContentView(R.layout.dialog_date_picker);
        datePicker = dialog.findViewById(R.id.datePicker);
        done = dialog.findViewById(R.id.done);
        cancel = dialog.findViewById(R.id.cancel);
        if (selectedCalendar != null) {
            datePicker.setSelectedDate(selectedCalendar);
        }
        datePicker.setOnSelectedDateChangedListener(new DateTimePickerView.OnSelectedDateChangedListener() {
            @Override
            public void onSelectedDateChanged(Calendar date) {
                Date startTime = new GregorianCalendar(1970, 0, 1, 21, 30).getTime();
                long daysBetweenStart = (date.getTime().getTime() - startTime.getTime() + 1000000) / (60 * 60 * 24 * 1000);
                if (daysBetweenStart < 0) {
                    done.setEnabled(false);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            datePicker.setSelectedDate(new GregorianCalendar(2000, 0, 1, 21, 30));
                            done.setEnabled(true);
                        }
                    }, 1000);
                }

                Date time = new Date();
                long daysBetween = (date.getTime().getTime() - time.getTime() + 1000000) / (60 * 60 * 24 * 1000);
                if (daysBetween > 0) {
                    done.setEnabled(false);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            datePicker.setSelectedDate(Calendar.getInstance());
                            done.setEnabled(true);
                        }
                    }, 1000);
                }
            }
        });
        attachActions(dialog.findViewById(R.id.done), dialog.findViewById(R.id.cancel));
        return dialog;
    }

    public void setSelectedCalendar(Calendar selectedCalendar) {
        this.selectedCalendar = selectedCalendar;
    }

    @Override
    protected View createView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_date_picker, container, false);
        datePicker = view.findViewById(R.id.datePicker);
        done = view.findViewById(R.id.done);
        cancel = view.findViewById(R.id.cancel);
        attachActions(done, cancel);
        return view;
    }

    public Calendar getSelectedDate() {
        return datePicker.getSelectedDate();
    }
}
