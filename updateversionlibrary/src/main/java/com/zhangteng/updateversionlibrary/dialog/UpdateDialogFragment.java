package com.zhangteng.updateversionlibrary.dialog;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhangteng.updateversionlibrary.R;


public class UpdateDialogFragment extends DialogFragment implements OnClickListener {

    private TextView mCancel;
    private TextView mOk;
    private TextView mTitle;
    private String mTitleText;
    private String mNegativeText;
    private String mPositiveText;
    private OnClickListener mNegativeClickListener;
    private OnClickListener mPositiveClickListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return inflater.inflate(R.layout.fragment_common_dialog_common, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCancel = (TextView) view.findViewById(R.id.dialog_cancel);
        mCancel.setOnClickListener(this);
        mOk = (TextView) view.findViewById(R.id.dialog_ok);
        mOk.setOnClickListener(this);
        mTitle = (TextView) view.findViewById(R.id.dialog_title);
        if (mTitleText != null) {
            mTitle.setText(mTitleText);
        }
        if (mNegativeText != null) {
            mCancel.setText(mNegativeText);
        }
        if (mPositiveText != null) {
            mOk.setText(mPositiveText);
        }
    }

    public void setTitle(String title) {
        mTitleText = title;
    }

    public void setNegativeBtnText(String text) {
        mNegativeText = text;
    }

    public void setPositiveBtnText(String text) {
        mPositiveText = text;
    }

    public void setNegativeBtn(String text, final OnClickListener Listener) {
        mNegativeText = text;
        mNegativeClickListener = Listener;
    }

    public void setPositiveBtn(String text, final OnClickListener Listener) {
        mPositiveText = text;
        mPositiveClickListener = Listener;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.dialog_cancel) {
            mNegativeClickListener.onClick();

        } else if (i == R.id.dialog_ok) {
            mPositiveClickListener.onClick();

        } else {
        }
        this.dismiss();
    }

    public interface OnClickListener {
        void onClick();
    }
}
