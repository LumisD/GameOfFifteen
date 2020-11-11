package com.lumisdinos.gameoffifteen.presentation

import androidx.lifecycle.ViewModel
import com.lumisdinos.gameoffifteen.common.AppConfig
import com.lumisdinos.gameoffifteen.common.AppConfig.cell15Size
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeViewModel @Inject constructor(
) : ViewModel() {


    fun initialLoadCells(frWidth: Int, gridMargin: Int, cellMargin: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                setFragWidthAndCellSizInConfig(frWidth, gridMargin, cellMargin)
                //todo: generate cells
            }
        }
    }


    private fun setFragWidthAndCellSizInConfig(frWidth: Int, gridMargin: Int, cellMargin: Int) {
        AppConfig.fragmentWidth = frWidth
        cell15Size = (frWidth - 2 * gridMargin - 8 * cellMargin) / 4
    }


}