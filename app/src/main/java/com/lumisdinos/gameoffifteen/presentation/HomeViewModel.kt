package com.lumisdinos.gameoffifteen.presentation

import androidx.lifecycle.ViewModel
import com.lumisdinos.gameoffifteen.common.AppConfig.cell15Size
import com.lumisdinos.gameoffifteen.data.Constants.FOUR_CELLS_IN_ROW
import com.lumisdinos.gameoffifteen.data.Constants.TWO_SIDES
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
                setCellSizInConfig(frWidth, gridMargin, cellMargin)
                //todo: generate cells
            }
        }
    }


    private fun setCellSizInConfig(frWidth: Int, gridMargin: Int, cellMargin: Int) {
        cell15Size =
            (frWidth - TWO_SIDES * gridMargin - TWO_SIDES * FOUR_CELLS_IN_ROW * cellMargin) / FOUR_CELLS_IN_ROW
    }


}