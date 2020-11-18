package com.lumisdinos.gameoffifteen.common.util

import com.lumisdinos.gameoffifteen.common.extension.stringToInt
import com.lumisdinos.gameoffifteen.common.AppConfig.previousClickTimeMillis
import com.lumisdinos.gameoffifteen.data.Constants.LONG_DELAY
import com.lumisdinos.gameoffifteen.data.Constants.SHORT_DELAY


fun strToInt(string: String, default: Int = 0): Int {
    return try {
        string.toInt()
    } catch (nfe: NumberFormatException) {
        default
    }
}


fun intToStr(int: Int?): String {
    return try {
        int.toString()
    } catch (e: Exception) {
        "0"
    }
}


fun convertString2IntList(listAsString: String): MutableList<Int> {
    val list = listAsString.split(",").map {
        it.trim().stringToInt(-1)
    }.filter { it != -1 }.toMutableList()
    return list
}


fun convertIntList2String(list: List<Int>): String {
    return list.joinToString(",").trim()
}


fun isClickedSingle(): Boolean {
    return isAlreadyClick(LONG_DELAY)
}


fun isClickedShort(): Boolean {
    return isAlreadyClick(SHORT_DELAY)
}


fun isAlreadyClick(time: Long): Boolean {
    val currentTimeMillis = System.currentTimeMillis()
    if (currentTimeMillis >= previousClickTimeMillis + time) {
        previousClickTimeMillis = currentTimeMillis
        return false
    } else {
        return true
    }
}