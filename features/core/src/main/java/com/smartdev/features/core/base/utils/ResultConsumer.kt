package com.smartdev.features.core.base.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

//region Fragment
fun <T> Fragment.observeFlow(
    flow: Flow<T>,
    observer: suspend (T) -> Unit,
) {
    observeFlow(Lifecycle.State.CREATED, flow, observer)
}

fun <T> Fragment.observeFlow(
    state: Lifecycle.State, flow: Flow<T>, observer: suspend (T) -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(state) {
            flow.collectLatest { observer(it) }
        }
    }
}
//endregion
