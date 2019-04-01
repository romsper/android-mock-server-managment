package com.romsper.mock_server.helper

import android.app.Activity
import android.support.v4.app.FragmentActivity
import android.view.inputmethod.InputMethodManager

fun FragmentActivity.hideKeyboard() = getInputManager(this).hideSoftInputFromWindow(currentFocus?.windowToken, 0)

private fun getInputManager(activity: FragmentActivity) =
    activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager