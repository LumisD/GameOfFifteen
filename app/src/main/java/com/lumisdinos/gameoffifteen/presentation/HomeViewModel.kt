package com.lumisdinos.gameoffifteen.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lumisdinos.gameoffifteen.common.AppConfig.cell15Size
import com.lumisdinos.gameoffifteen.common.AppConfig.currentGameDimention
import com.lumisdinos.gameoffifteen.common.Event
import com.lumisdinos.gameoffifteen.data.Constants.FOUR_CELLS_IN_ROW
import com.lumisdinos.gameoffifteen.data.Constants.GAME_15
import com.lumisdinos.gameoffifteen.data.Constants.TWO_SIDES
import com.lumisdinos.gameoffifteen.ui.fragment.HomeFragment.Companion.ACTION_CONGRATULATIONS
import com.lumisdinos.gameoffifteen.ui.fragment.HomeFragment.Companion.ACTION_UNSOLVABLE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeViewModel @Inject constructor(
) : ViewModel() {

    private val _showAlertDialog = MutableLiveData<Event<String>>()
    val showAlertDialog: LiveData<Event<String>> = _showAlertDialog

    private val _setCells = MutableLiveData<Event<List<Int>>>()
    val setCells: LiveData<Event<List<Int>>> = _setCells
    private val digits = mutableListOf<Int>()


    fun initialLoadCells(frWidth: Int, gridMargin: Int, cellMargin: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                currentGameDimention = GAME_15
                setCellSizInConfig(frWidth, gridMargin, cellMargin)
                generateCells(currentGameDimention)
                _setCells.postValue(Event(digits))
            }
        }
    }


    private fun setCellSizInConfig(frWidth: Int, gridMargin: Int, cellMargin: Int) {
        cell15Size =
            (frWidth - TWO_SIDES * gridMargin - TWO_SIDES * FOUR_CELLS_IN_ROW * cellMargin) / FOUR_CELLS_IN_ROW
    }


    private fun generateCells(dimention: Int) {
        digits.clear()
        val cells = (0..dimention).shuffled().toList()
        digits.addAll(cells)
    }


    fun swapCellWithEmpty(cellIndex: Int, zeroIndex: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                digits[cellIndex] = digits[zeroIndex].also { digits[zeroIndex] = digits[cellIndex] }

                var firstUnordered = 0
                run lit@{
                    digits.forEachIndexed { index, number ->
                        if (index + 1 != number) {
                            firstUnordered = index
                            return@lit
                        }
                    }
                }

                if (firstUnordered == currentGameDimention) {
                    _showAlertDialog.postValue(Event(ACTION_CONGRATULATIONS))
                } else if (firstUnordered == currentGameDimention - 2) {
                    if (digits[currentGameDimention - 2] == currentGameDimention) {
                        _showAlertDialog.postValue(Event(ACTION_UNSOLVABLE))
                    }
                }
            }
        }
    }

}