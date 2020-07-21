package com.part.jianzhiyi.corecommon.selectdateview.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

public abstract class BaseDialogFragment extends DialogFragment {

    public static final int TYPE_DIALOG = 0;
    public static final int TYPE_VIEW = 1;
    protected int type = TYPE_DIALOG;

    protected ActionListener actionListener;

    public static <T extends BaseDialogFragment> T newInstance(Class<T> cls, int type, ActionListener actionListener) {
        T dialogFragment;
        try {
            dialogFragment = cls.newInstance();
        } catch (java.lang.InstantiationException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        }
        dialogFragment.actionListener = actionListener;

        Bundle args = new Bundle();
        args.putInt("type", type);
        dialogFragment.setArguments(args);

        return dialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt("type");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (type != TYPE_DIALOG) {
            return super.onCreateDialog(savedInstanceState);
        }

        return createDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (type != TYPE_VIEW) {
            return super.onCreateView(inflater, container, savedInstanceState);
        }

        return createView(inflater, container, savedInstanceState);
    }

    protected abstract Dialog createDialog(Bundle savedInstanceState);

    protected abstract View createView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState);

    protected void attachActions(View done, View cancel) {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actionListener != null) {
                    actionListener.onCancelClick(BaseDialogFragment.this);
                }
                dismiss();
            }
        } );
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actionListener != null) {
                    actionListener.onDoneClick(BaseDialogFragment.this);
                }
                dismiss();
            }
        } );
    }
}
