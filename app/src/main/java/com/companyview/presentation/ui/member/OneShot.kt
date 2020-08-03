/*
 * Copyright (C) GM Global Technology Operations LLC 2020
 * All Rights Reserved.
 * GM Confidential Restricted.
 */
package com.mvvmretrofitroomdagger2coroutinekotlin.presentation.ui.member

import androidx.lifecycle.Observer

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
class OneShot<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}

/**
 * An [Observer] for [Event]s, simplifying the pattern of checking if the [Event]'s content has
 * already been handled.
 *
 * [onEventUnhandledContent] is *only* called if the [Event]'s contents has not been handled.
 */
class OneShotObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<OneShot<T>> {
    override fun onChanged(oneShotEvent: OneShot<T>?) {
        oneShotEvent?.getContentIfNotHandled()?.let {
            onEventUnhandledContent(it)
        }
    }
}
