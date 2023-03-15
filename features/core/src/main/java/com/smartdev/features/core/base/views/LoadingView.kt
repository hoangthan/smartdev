package com.smartdev.features.core.base.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.smartdev.features.core.databinding.LayoutLoadingViewBinding

/**
 * Use this loading view make the loading view in whole app to be consistent and easy to update
 */
class LoadingView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : RelativeLayout(context, attributeSet, defStyleAttr, defStyleRes) {

    private val binding: LayoutLoadingViewBinding

    init {
        val layoutInflater = LayoutInflater.from(context)
        binding = LayoutLoadingViewBinding.inflate(layoutInflater, this, true)
    }
}
