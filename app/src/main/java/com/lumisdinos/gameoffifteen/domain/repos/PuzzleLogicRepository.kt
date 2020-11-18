package com.lumisdinos.gameoffifteen.domain.repos

import com.lumisdinos.gameoffifteen.domain.model.GameStateModel
import kotlinx.coroutines.flow.Flow

interface PuzzleLogicRepository {

    fun getGameState(): Flow<GameStateModel>

    fun initialLoadCells(frWidth: Int, gridMargin: Int, cellMargin: Int)

    fun reloadCells()

    fun swapCellWithEmpty(cellIndex: Int, zeroIndex: Int)

    companion object {
        const val ACTION_CONGRATULATIONS = "100"
        const val ACTION_UNSOLVABLE = "101"
    }

}