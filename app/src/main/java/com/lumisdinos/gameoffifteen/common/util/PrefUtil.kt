package com.lumisdinos.gameoffifteen.common.util

import android.content.Context
import android.content.SharedPreferences
import com.lumisdinos.gameoffifteen.R
import timber.log.Timber

fun setFragmentWidthInPref(width: Int, preference: SharedPreferences, context: Context) {
    Timber.d("qwer setFragmentWidthInPref preference.edit .width: %s", width)
    preference.edit()
        .putInt(context.getString(R.string.pref_width_fragment), width)
        .apply()
}


fun getFragmentWidthFromPref(preference: SharedPreferences, context: Context): Int {
    val width = preference.getInt(context.getString(R.string.pref_width_fragment), 0)
    Timber.d("qwer getFragmentWidthFromPref width: %s", width)
    return width
}


fun setIsFirstLaunchFalseInPref(preference: SharedPreferences, context: Context) {
    preference.edit()
        .putBoolean(context.getString(R.string.pref_is_first_launch), false)
        .apply()
}


fun getIsFirstLaunchFromPref(preference: SharedPreferences, context: Context): Boolean {
    return preference.getBoolean(context.getString(R.string.pref_is_first_launch), true)
}