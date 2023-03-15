package com.smartdev.features.core.base.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<BINDING : ViewBinding> : Fragment(), View.OnClickListener {

    protected lateinit var binding: BINDING

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
    }

    /**
     * This function is used for collect Flow from `ViewModel`
     * Override this function to put your own collector
     */
    protected open fun initObserver() {}

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        initViewListener()
    }

    /**
     * This function MUST be implemented to return the inflated view binding for fragment
     */
    abstract fun getViewBinding(inflater: LayoutInflater): BINDING

    @CallSuper
    override fun onClick(view: View?) {
        //Maybe we will need track what view is clicked, which area is the most clicked...
        view ?: return
        onViewClicked(view)
    }

    /**
     * This function should be override if you want to trigger when view is clicked and keep tracking also
     */
    protected open fun onViewClicked(view: View) {}

    /**
     * This function should be override if you want initial view's listener such as: Click, TextChange, Checkbox..
     */
    protected open fun initViewListener() {}

    /**
     * This function should be override if you want initial view for first time after view is created
     */
    protected open fun initView() {}
}
