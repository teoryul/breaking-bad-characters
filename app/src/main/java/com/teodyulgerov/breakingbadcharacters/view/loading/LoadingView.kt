package com.teodyulgerov.breakingbadcharacters.view.loading

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.teodyulgerov.breakingbadcharacters.R
import kotlinx.android.synthetic.main.loading_view.view.*

class LoadingView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr) {

    private val loadingView: View = LayoutInflater.from(context)
        .inflate(R.layout.loading_view, this)

    fun setProgressBarVisibility(isVisible: Boolean) {
        loadingView.progressBarLoadingView.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    fun setLoadingText(resId: Int) {
        loadingView.txtLoadingView.text = context.getString(resId)
    }

    fun setLoadingTextVisibility(isVisible: Boolean) {
        loadingView.txtLoadingView.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}