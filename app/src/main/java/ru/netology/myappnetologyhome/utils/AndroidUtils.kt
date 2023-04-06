@file:Suppress("DEPRECATION")

package ru.netology.myappnetologyhome.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object AndroidUtils {
    fun hideKeyboard(view: View) {
        val inputMethodManager =
            view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showKeyboard(view: View) {
        val inputMethodManager =
            view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)// hideSoftInputFromWindow(view.windowToken, 0)
    }

}