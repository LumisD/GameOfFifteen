package com.lumisdinos.gameoffifteen.common.util

import android.annotation.SuppressLint
import com.lumisdinos.gameoffifteen.data.Constants
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit


fun formatToDigitalClock(milliSeconds: Long): String {
    val hours = TimeUnit.MILLISECONDS.toHours(milliSeconds).toInt() % 24
    val minutes = TimeUnit.MILLISECONDS.toMinutes(milliSeconds).toInt() % 60
    val seconds = TimeUnit.MILLISECONDS.toSeconds(milliSeconds).toInt() % 60
    return when {
        hours > 0 -> String.format("%d:%02d:%02d", hours, minutes, seconds)
        minutes > 0 -> String.format("%02d:%02d", minutes, seconds)
        seconds > 0 -> String.format("00:%02d", seconds)
        else -> ""
    }
}


@SuppressLint("SimpleDateFormat")
fun convertLongToDateString(systemTime: Long): String {
    return try {
        SimpleDateFormat(Constants.DAY_MONTH_YEAR).format(systemTime)
    } catch (e: java.lang.Exception) {
        ""
    }
}