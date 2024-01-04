package com.smartdev.features.core.base.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun Context.showToast(content: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, content, duration).show()
}

fun Context.showToast(@StringRes stringRes: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, stringRes, duration).show()
}

fun Fragment.showToast(@StringRes stringRes: Int, duration: Int = Toast.LENGTH_SHORT) {
    requireContext().showToast(stringRes, duration)
}

fun Fragment.showToast(content: String, duration: Int = Toast.LENGTH_SHORT) {
    requireContext().showToast(content, duration)
}

class AutoCleanedValue<T : Any>(
    private val fragment: Fragment,
    private val initializer: (() -> T)?
) : ReadWriteProperty<Fragment, T> {

    private var _value: T? = null

    init {
        val viewLifecycleOwnerObserver = Observer<LifecycleOwner?> { viewLifecycleOwner ->
            viewLifecycleOwner?.lifecycle?.addObserver(object : DefaultLifecycleObserver {
                override fun onDestroy(owner: LifecycleOwner) {
                    _value = null
                    viewLifecycleOwner.lifecycle.removeObserver(this)
                }
            })
        }

        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observeForever(viewLifecycleOwnerObserver)
            }

            override fun onDestroy(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.removeObserver(viewLifecycleOwnerObserver)
                fragment.lifecycle.removeObserver(this)
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        val value = _value

        if (value != null) {
            return value
        }

        if (thisRef.viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            _value = initializer?.invoke()
            return _value ?: throw IllegalStateException("This field is not allowed nullable. initializer must be return non-null value")
        } else {
            throw IllegalStateException("Can not invoke this field when fragment already destroyed")
        }
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        _value = value
    }
}

fun <T : Any> Fragment.boundWithViewLifeCycle(initializer: (() -> T)? = null): AutoCleanedValue<T> {
    return AutoCleanedValue(this, initializer)
}
