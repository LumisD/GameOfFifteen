package com.lumisdinos.gameoffifteen.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lumisdinos.gameoffifteen.common.AppConfig.cell15Size
import com.lumisdinos.gameoffifteen.common.Event
import com.lumisdinos.gameoffifteen.data.Constants.FOUR_CELLS_IN_ROW
import com.lumisdinos.gameoffifteen.data.Constants.TWO_SIDES
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeViewModel @Inject constructor(
) : ViewModel() {

    private val _setCells = MutableLiveData<Event<List<Int>>>()
    val setCells: LiveData<Event<List<Int>>> = _setCells
    private val digits = mutableListOf<Int>()


    fun initialLoadCells(frWidth: Int, gridMargin: Int, cellMargin: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                setCellSizInConfig(frWidth, gridMargin, cellMargin)
                generateCells()
                _setCells.postValue(Event(digits))
            }
        }
    }


    private fun setCellSizInConfig(frWidth: Int, gridMargin: Int, cellMargin: Int) {
        cell15Size =
            (frWidth - TWO_SIDES * gridMargin - TWO_SIDES * FOUR_CELLS_IN_ROW * cellMargin) / FOUR_CELLS_IN_ROW
    }


    private fun generateCells() {
        digits.clear()
        while (digits.size < 16) {
            val digit = (0..15).random()
            if (digits.contains(digit)) {
                var isAdded = false
                var nextDigit = digit
                while (!isAdded) {
                    nextDigit += 1
                    if (nextDigit > 15) {
                        nextDigit = 0
                    }
                    if (!digits.contains(nextDigit)) {
                        digits.add(nextDigit)
                        isAdded = true
                    }
                }
            } else {
                digits.add(digit)
            }
        }
    }


    fun swapCellWithEmpty(cellIndex: Int, zeroIndex: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                digits[cellIndex] = digits[zeroIndex].also { digits[zeroIndex] = digits[cellIndex] }
                var isSolved = true
                var isFirst13Solved = false
                for (i in 0 .. 14) {
                    if (i + 1 != digits[i]) {
                        isSolved = false
                        if (i > 12) {
                            isFirst13Solved = true
                        }
                        break
                    }
                }
                //todo: if isSolved - show dialog with congratulations
            }
        }
    }




}