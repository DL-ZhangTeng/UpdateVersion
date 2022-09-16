package com.zhangteng.updateversion.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.zhangteng.updateversion.R
import com.zhangteng.updateversion.UpdateVersion

class UpdateDialogFragment : DialogFragment(), View.OnClickListener {
    private var mUpdateImageView: ImageView? = null
    private var mNoNetImageView: ImageView? = null
    private var mCancel: TextView? = null
    private var mOk: TextView? = null
    private var mTitle: TextView? = null
    private var mContentTitle: TextView? = null
    private var mContent: TextView? = null
    private var mTitleText: String? = null
    private var mContentTitleText: String? = null
    private var mContentText: String? = null
    private var mNegativeText: String? = null
    private var mPositiveText: String? = null
    private var netHit = false
    private var mNegativeClickListener: OnClickListener? = null
    private var mPositiveClickListener: OnClickListener? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return inflater.inflate(R.layout.fragment_common_dialog_common, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mUpdateImageView = view.findViewById(R.id.dialog_iv1)
        if (UpdateVersion.uploadImage != R.mipmap.upload_version_gengxin) {
            mUpdateImageView?.setImageResource(UpdateVersion.uploadImage)
        }
        mNoNetImageView = view.findViewById(R.id.dialog_iv2)
        if (UpdateVersion.noNetImage != R.mipmap.upload_version_nonet) {
            mNoNetImageView?.setImageResource(UpdateVersion.noNetImage)
        }
        mCancel = view.findViewById(R.id.dialog_cancel)
        mCancel?.setOnClickListener(this)
        mOk = view.findViewById(R.id.dialog_ok)
        if (UpdateVersion.themeColor != R.color.version_theme_color) {
            mOk?.setTextColor(resources.getColor(UpdateVersion.themeColor))
        }
        mOk?.setOnClickListener(this)
        mTitle = view.findViewById(R.id.dialog_title)
        mContentTitle = view.findViewById(R.id.dialog_content_title)
        mContent = view.findViewById(R.id.dialog_content)
        if (mTitleText != null) {
            mTitle?.text = mTitleText
        }
        if (mContentTitleText != null) {
            mContentTitle?.text = mContentTitleText
        } else {
            mContentTitle?.visibility = View.GONE
        }
        if (mContentText != null) {
            mContent?.text = mContentText
        } else {
            mContent?.visibility = View.GONE
        }
        if (mNegativeText != null) {
            mCancel?.text = mNegativeText
        }
        if (mPositiveText != null) {
            mOk?.text = mPositiveText
        }
        if (netHit) {
            mTitle?.visibility = View.GONE
            mUpdateImageView?.visibility = View.GONE
            mNoNetImageView?.visibility = View.VISIBLE
        }
    }

    fun setTitle(title: String?) {
        mTitleText = title
    }

    fun setNetHint(netHint: Boolean) {
        netHit = netHint
    }

    fun setContentTitleText(mContentTitleText: String?) {
        this.mContentTitleText = mContentTitleText
    }

    fun setContentText(mContentText: String?) {
        this.mContentText = mContentText
    }

    fun setNegativeBtnText(text: String?) {
        mNegativeText = text
    }

    fun setPositiveBtnText(text: String?) {
        mPositiveText = text
    }

    fun setNegativeBtn(text: String?, Listener: OnClickListener?) {
        mNegativeText = text
        mNegativeClickListener = Listener
    }

    fun setPositiveBtn(text: String?, Listener: OnClickListener?) {
        mPositiveText = text
        mPositiveClickListener = Listener
    }

    override fun onClick(v: View) {
        val i = v.id
        if (i == R.id.dialog_cancel) {
            if (mNegativeClickListener != null) mNegativeClickListener!!.onClick()
        } else if (i == R.id.dialog_ok) {
            if (mPositiveClickListener != null) mPositiveClickListener!!.onClick()
        }
        dismissAllowingStateLoss()
    }

    interface OnClickListener {
        fun onClick()
    }
}