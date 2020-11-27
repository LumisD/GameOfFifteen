package com.lumisdinos.gameoffifteen.common.extension

import android.view.View
import com.lumisdinos.gameoffifteen.common.listener.SingleClickListener

fun View.setSingleOnClickListener(onSafeClick: (View) -> Unit) {
    val safeClickListener = SingleClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}