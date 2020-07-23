package com.teodyulgerov.breakingbadcharacters.utils

import android.os.Bundle

class Event<out T>(private val content: T, val bundle: Bundle? = null) {

    private var hasBeenHandled = false

    /**
     * @return The content if the Event hasn't bee handled yet, null - otherwise.
     */
    fun getContent(): T? =
        if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
}

enum class NavigationAction(val value: Int) {
    POP_BACK_STACK(-1)
}