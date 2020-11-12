package com.lumisdinos.gameoffifteen.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lumisdinos.gameoffifteen.domain.model.GameState
import com.lumisdinos.gameoffifteen.domain.repos.PuzzleLogicRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val logicRepo: PuzzleLogicRepository
) : ViewModel() {

    val gameState: LiveData<GameState> = logicRepo.gameStateLive

    fun initialLoadCells(frWidth: Int, gridMargin: Int, cellMargin: Int) {
        logicRepo.initialLoadCells(frWidth, gridMargin, cellMargin)
    }

    fun reloadCells() {
        logicRepo.reloadCells()
    }

    fun swapCellWithEmpty(cellIndex: Int, zeroIndex: Int) {
        logicRepo.swapCellWithEmpty(cellIndex, zeroIndex)
    }

}