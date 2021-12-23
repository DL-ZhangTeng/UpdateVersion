package com.zhangteng.updateversionlibrary.dialog;

/**
 * Created by swing on 2017/5/3.
 */

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhangteng.updateversionlibrary.R;
import com.zhangteng.updateversionlibrary.UpdateVersion;

import java.text.NumberFormat;


public class CommonProgressDialog extends AlertDialog {
    private ProgressBar mProgress;
    private TextView mProgressNumber;
    private TextView mProgressPercent;
    private TextView mProgressMessage;
    private int mMax;
    private CharSequence mMessage;
    private boolean mHasStarted;
    private int mProgressVal;
    private String mProgressNumberFormat;
    private NumberFormat mProgressPercentFormat;

    public CommonProgressDialog(Context context) {
        super(context);
        initFormats();
    }

    public CommonProgressDialog(Context context, int theme) {
        super(context, theme);
        initFormats();
    }

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_progress_dialog);
        mProgress = findViewById(R.id.progress);
        if (UpdateVersion.getThemeColor() != R.color.version_theme_color) {
            mProgress.setProgressDrawable(getProgressLayerDrawable());
        }
        mProgressNumber = findViewById(R.id.progress_number);
        mProgressPercent = findViewById(R.id.progress_percent);
        mProgressMessage = findViewById(R.id.progress_message);
        if (UpdateVersion.getThemeColor() != R.color.version_theme_color) {
            mProgressMessage.setBackground(getTitleBackgroundDrawable());
        }
        onProgressChanged();
        if (mMessage != null) {
            setMessage(mMessage);
        }
        if (mMax > 0) {
            setMax(mMax);
        }
        if (mProgressVal > 0) {
            setProgress(mProgressVal);
        }
    }

    private void initFormats() {
        mProgressNumberFormat = "%1.2fM/%2.2fM";
        mProgressPercentFormat = NumberFormat.getPercentInstance();
        mProgressPercentFormat.setMaximumFractionDigits(0);
    }

    private void onProgressChanged() {
        int progress = mProgress.getProgress();
        int max = mProgress.getMax();
        double dProgress = (double) progress / (double) (1024 * 1024);
        double dMax = (double) max / (double) (1024 * 1024);
        if (mProgressNumberFormat != null) {
            String format = mProgressNumberFormat;
            mProgressNumber.setText(String.format(format, dProgress, dMax));
        } else {
            mProgressNumber.setText("");
        }
        if (mProgressPercentFormat != null) {
            double percent = 0;
            if (max >= 0) {
                percent = (double) progress / (double) max;
            }
            SpannableString tmp = new SpannableString(mProgressPercentFormat.format(percent));
            if (0 < tmp.length()) {
                tmp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),
                        0, tmp.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            mProgressPercent.setText(tmp);
        } else {
            mProgressPercent.setText("");
        }
    }

    public int getMax() {
        if (mProgress != null) {
            return mProgress.getMax();
        }
        return mMax;
    }

    public void setMax(int max) {
        if (mProgress != null) {
            mProgress.setMax(max);
            onProgressChanged();
        } else {
            mMax = max;
        }
    }

    public void setProgress(int value) {
        if (mHasStarted) {
            mProgress.setProgress(value);
            onProgressChanged();
        } else {
            mProgressVal = value;
        }
    }

    @Override
    public void setMessage(CharSequence message) {
        if (mProgressMessage != null) {
            mProgressMessage.setText(message);
        } else {
            mMessage = message;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mHasStarted = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHasStarted = false;
    }

    private LayerDrawable getProgressLayerDrawable() {

        int radius0 = getContext().getResources().getDimensionPixelSize(R.dimen.dialog_radius);
        float[] outerR = new float[]{radius0, radius0, radius0, radius0, radius0, radius0, radius0, radius0};
        RoundRectShape roundRectShape0 = new RoundRectShape(outerR, null, null);

        ShapeDrawable background = new ShapeDrawable();
        background.setPadding(0, 0, 0, 0);
        background.setShape(roundRectShape0);
        background.getPaint().setStyle(Paint.Style.FILL);
        background.getPaint().setColor(Color.parseColor("#f7f7f7"));

        ShapeDrawable secondaryProgress = new ShapeDrawable();
        background.setPadding(0, 0, 0, 0);
        secondaryProgress.setShape(roundRectShape0);
        secondaryProgress.getPaint().setStyle(Paint.Style.FILL);
        secondaryProgress.getPaint().setColor(Color.parseColor("#eeeeee"));

        ShapeDrawable progress = new ShapeDrawable();
        background.setPadding(0, 0, 0, 0);
        progress.setShape(roundRectShape0);
        progress.getPaint().setStyle(Paint.Style.FILL);
        progress.getPaint().setColor(getContext().getResources().getColor(UpdateVersion.getThemeColor()));

        Drawable[] layers = {background, secondaryProgress, progress};
        LayerDrawable layerDrawable = new LayerDrawable(layers);
        layerDrawable.setId(0, android.R.id.background);
        layerDrawable.setId(1, android.R.id.secondaryProgress);
        layerDrawable.setId(1, android.R.id.progress);
        return layerDrawable;
    }

    private ShapeDrawable getTitleBackgroundDrawable() {

        int radius0 = getContext().getResources().getDimensionPixelSize(R.dimen.dialog_radius);
        float[] outerR = new float[]{radius0, radius0, radius0, radius0, 0, 0, 0, 0};
        RoundRectShape roundRectShape0 = new RoundRectShape(outerR, null, null);

        ShapeDrawable background = new ShapeDrawable();
        background.setPadding(0, 0, 0, 0);
        background.setShape(roundRectShape0);
        background.getPaint().setStyle(Paint.Style.FILL);
        background.getPaint().setColor(getContext().getResources().getColor(UpdateVersion.getThemeColor()));
        return background;
    }
}
