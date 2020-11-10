package com.lumisdinos.gameoffifteen.common.extension

import java.lang.NumberFormatException

@JvmName("postSuccessResult")
fun String.stringToInt(default: Int = 0): Int {
    return try {
        this.toInt()
    } catch (nfe: NumberFormatException) {
        default
    }
}