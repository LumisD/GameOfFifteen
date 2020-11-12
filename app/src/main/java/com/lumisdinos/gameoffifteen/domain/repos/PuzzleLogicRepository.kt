package com.lumisdinos.gameoffifteen.domain.repos

import androidx.lifecycle.MutableLiveData
import com.lumisdinos.gameoffifteen.domain.model.GameState

interface PuzzleLogicRepository {

    var gameStateLive: MutableLiveData<GameState>

    fun initialLoadCells(frWidth: Int, gridMargin: Int, cellMargin: Int)

    fun reloadCells()

    fun swapCellWithEmpty(cellIndex: Int, zeroIndex: Int)

    companion object {
        const val ACTION_CONGRATULATIONS = "100"
        const val ACTION_UNSOLVABLE = "101"
    }

}