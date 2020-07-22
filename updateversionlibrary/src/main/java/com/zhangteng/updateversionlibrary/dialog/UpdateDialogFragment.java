package com.zhangteng.updateversionlibrary.dialog;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhangteng.updateversionlibrary.R;
import com.zhangteng.updateversionlibrary.UpdateVersion;


public class UpdateDialogFragment extends DialogFragment implements OnClickListener {

    private ImageView mUpdateImageView, mNoNetImageView;
    private TextView mCancel;
    private TextView mOk;
    private TextView mTitle;
    private TextView mContentTitle;
    private TextView mContent;
    private String mTitleText;
    private String mContentTitleText;
    private String mContentText;
    private String mNegativeText;
    private String mPositiveText;
    private boolean netHit = false;
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
        mUpdateImageView = view.findViewById(R.id.dialog_iv1);
        if (UpdateVersion.getUploadImage() != R.mipmap.upload_version_gengxin) {
            mUpdateImageView.setImageResource(UpdateVersion.getUploadImage());
        }
        mNoNetImageView = view.findViewById(R.id.dialog_iv2);
        if (UpdateVersion.getNoNetImage() != R.mipmap.upload_version_nonet) {
            mNoNetImageView.setImageResource(UpdateVersion.getNoNetImage());
        }
        mCancel = view.findViewById(R.id.dialog_cancel);
        mCancel.setOnClickListener(this);
        mOk = view.findViewById(R.id.dialog_ok);
        if (UpdateVersion.getThemeColor() != R.color.base_theme_color) {
            mOk.setTextColor(getResources().getColor(UpdateVersion.getThemeColor()));
        }
        mOk.setOnClickListener(this);
        mTitle = view.findViewById(R.id.dialog_title);
        mContentTitle = view.findViewById(R.id.dialog_content_title);
        mContent = view.findViewById(R.id.dialog_content);
        if (mTitleText != null) {
            mTitle.setText(mTitleText);
        }
        if (mContentTitleText != null) {
            mContentTitle.setText(mContentTitleText);
        } else {
            mContentTitle.setVisibility(View.GONE);
        }
        if (mContentText != null) {
            mContent.setText(mContentText);
        } else {
            mContent.setVisibility(View.GONE);
        }
        if (mNegativeText != null) {
            mCancel.setText(mNegativeText);
        }
        if (mPositiveText != null) {
            mOk.setText(mPositiveText);
        }
        if (netHit) {
            mTitle.setVisibility(View.GONE);
            mUpdateImageView.setVisibility(View.GONE);
            mNoNetImageView.setVisibility(View.VISIBLE);
        }
    }

    public void setTitle(String title) {
        mTitleText = title;
    }

    public void setNetHint(boolean netHint) {
        this.netHit = netHint;
    }

    public void setContentTitleText(String mContentTitleText) {
        this.mContentTitleText = mContentTitleText;
    }

    public void setContentText(String mContentText) {
        this.mContentText = mContentText;
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
            if (mNegativeClickListener != null)
                mNegativeClickListener.onClick();

        } else if (i == R.id.dialog_ok) {
            if (mPositiveClickListener != null)
                mPositiveClickListener.onClick();
        }
        this.dismissAllowingStateLoss();
    }

    public interface OnClickListener {
        void onClick();
    }
}
