package com.teodyulgerov.breakingbadcharacters.ui.fragment.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teodyulgerov.breakingbadcharacters.R
import com.teodyulgerov.breakingbadcharacters.utils.Event

abstract class BaseVM : ViewModel(), LifecycleObserver {

    val newDestinationEvent = MutableLiveData<Event<Int>>()
    val loadingViewProgressBarVisibility = MutableLiveData<Boolean>().apply { value = false }
    val loadingViewText = MutableLiveData<Int>().apply { value = R.string.empty }
    val loadingViewTextVisibility = MutableLiveData<Boolean>().apply { value = false }

    fun showProgressBar(isVisible: Boolean) {
        if (isVisible) {
            loadingViewTextVisibility.value  = false;
        }
        if (loadingViewProgressBarVisibility.value != isVisible) {
            loadingViewProgressBarVisibility.value = isVisible
        }
    }

    fun showLoadingText(isVisible: Boolean) {
        if(isVisible) {
            loadingViewProgressBarVisibility.value = false
        }
        if (loadingViewTextVisibility.value != isVisible) {
            loadingViewTextVisibility.value = isVisible
        }
    }

    fun setLoadingViewText(resId: Int) {
        loadingViewText.value = resId
        showLoadingText(true)
    }
}