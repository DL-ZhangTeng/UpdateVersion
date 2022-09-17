package com.zhangteng.updateversion.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.widget.ProgressBar
import android.widget.TextView
import com.zhangteng.updateversion.R
import com.zhangteng.updateversion.UpdateVersion
import java.text.NumberFormat

/**
 * Created by swing on 2017/5/3.
 */
class CommonProgressDialog : AlertDialog {
    private var mProgress: ProgressBar? = null
    private var mProgressNumber: TextView? = null
    private var mProgressPercent: TextView? = null
    private var mProgressMessage: TextView? = null
    private var mMax = 0
    private var mMessage: CharSequence? = null
    private var mHasStarted = false
    private var mProgressVal = 0
    private var mProgressNumberFormat: String? = null
    private var mProgressPercentFormat: NumberFormat? = null

    constructor(context: Context?) : super(context) {
        initFormats()
    }

    constructor(context: Context?, theme: Int) : super(context, theme) {
        initFormats()
    }

    @SuppressLint("HandlerLeak", "UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.common_progress_dialog)
        mProgress = findViewById(R.id.progress)
        if (UpdateVersion.progressDrawable != R.drawable.progressbar) {
            mProgress?.progressDrawable = context.getDrawable(UpdateVersion.progressDrawable)
        }
        mProgressNumber = findViewById(R.id.progress_number)
        mProgressPercent = findViewById(R.id.progress_percent)
        mProgressMessage = findViewById(R.id.progress_message)
        if (UpdateVersion.themeColor != R.color.version_theme_color) {
            mProgressMessage?.background = titleBackgroundDrawable
        }
        onProgressChanged()
        if (mMessage != null) {
            setMessage(mMessage!!)
        }
        if (mMax > 0) {
            max = mMax
        }
        if (mProgressVal > 0) {
            setProgress(mProgressVal)
        }
    }

    private fun initFormats() {
        mProgressNumberFormat = "%1.2fM/%2.2fM"
        mProgressPercentFormat = NumberFormat.getPercentInstance()
        mProgressPercentFormat?.maximumFractionDigits = 0
    }

    private fun onProgressChanged() {
        val progress = mProgress!!.progress
        val max = mProgress!!.max
        val dProgress = progress.toDouble() / (1024 * 1024).toDouble()
        val dMax = max.toDouble() / (1024 * 1024).toDouble()
        if (mProgressNumberFormat != null) {
            val format: String = mProgressNumberFormat!!
            mProgressNumber!!.text = String.format(format, dProgress, dMax)
        } else {
            mProgressNumber!!.text = ""
        }
        if (mProgressPercentFormat != null) {
            var percent = 0.0
            if (max >= 0) {
                percent = progress.toDouble() / max.toDouble()
            }
            val tmp = SpannableString(mProgressPercentFormat!!.format(percent))
            if (0 < tmp.length) {
                tmp.setSpan(
                    StyleSpan(Typeface.BOLD),
                    0, tmp.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            mProgressPercent!!.text = tmp
        } else {
            mProgressPercent!!.text = ""
        }
    }

    var max: Int
        get() = if (mProgress != null) {
            mProgress!!.max
        } else mMax
        set(max) {
            if (mProgress != null) {
                mProgress!!.max = max
                onProgressChanged()
            } else {
                mMax = max
            }
        }

    fun setProgress(value: Int) {
        if (mHasStarted) {
            mProgress!!.progress = value
            onProgressChanged()
        } else {
            mProgressVal = value
        }
    }

    override fun setMessage(message: CharSequence) {
        if (mProgressMessage != null) {
            mProgressMessage!!.text = message
        } else {
            mMessage = message
        }
    }

    override fun onStart() {
        super.onStart()
        mHasStarted = true
    }

    override fun onStop() {
        super.onStop()
        mHasStarted = false
    }

    private val titleBackgroundDrawable: ShapeDrawable
        get() {
            val radius0 = context.resources.getDimensionPixelSize(R.dimen.dialog_radius)
            val outerR = floatArrayOf(
                radius0.toFloat(),
                radius0.toFloat(),
                radius0.toFloat(),
                radius0.toFloat(),
                0f,
                0f,
                0f,
                0f
            )
            val roundRectShape0 = RoundRectShape(outerR, null, null)
            val background = ShapeDrawable()
            background.setPadding(0, 0, 0, 0)
            background.shape = roundRectShape0
            background.paint.style = Paint.Style.FILL
            background.paint.color =
                context.resources.getColor(UpdateVersion.themeColor)
            return background
        }
}