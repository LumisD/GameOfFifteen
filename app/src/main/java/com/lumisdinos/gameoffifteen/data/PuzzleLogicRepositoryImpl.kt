package com.lumisdinos.gameoffifteen.data

import androidx.lifecycle.MutableLiveData
import com.lumisdinos.gameoffifteen.common.AppConfig.cell15Size
import com.lumisdinos.gameoffifteen.common.AppConfig.currentGameDimention
import com.lumisdinos.gameoffifteen.common.Event
import com.lumisdinos.gameoffifteen.data.Constants.GAME_15
import com.lumisdinos.gameoffifteen.domain.model.GameState
import com.lumisdinos.gameoffifteen.domain.repos.GameRepository
import com.lumisdinos.gameoffifteen.domain.repos.PuzzleLogicRepository
import com.lumisdinos.gameoffifteen.domain.repos.PuzzleLogicRepository.Companion.ACTION_CONGRATULATIONS
import com.lumisdinos.gameoffifteen.domain.repos.PuzzleLogicRepository.Companion.ACTION_UNSOLVABLE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PuzzleLogicRepositoryImpl @Inject constructor(
    private val gameRepository: GameRepository
) : PuzzleLogicRepository {

    override var gameStateLive = MutableLiveData<GameState>()
    var gameState: GameState? = null
    private val digits = mutableListOf<Int>()


    private fun currentGameState(): GameState {
        if (gameState == null) gameState = GameState()
        return gameState!!
    }


    override fun initialLoadCells(frWidth: Int, gridMargin: Int, cellMargin: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                currentGameDimention = GAME_15
                setCellSizInConfig(frWidth, gridMargin, cellMargin)
                generateCells()
            }
        }
    }

    override fun reloadCells() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                generateCells()
            }
        }
    }

    override fun swapCellWithEmpty(cellIndex: Int, zeroIndex: Int) {
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
                    setStateDialog(ACTION_CONGRATULATIONS)
                } else if (firstUnordered == currentGameDimention - 2) {
                    if (digits[currentGameDimention - 2] == currentGameDimention) {
                        setStateDialog(ACTION_UNSOLVABLE)
                    }
                }
            }
        }
    }


    private fun generateCells() {
        digits.clear()
        val cells = (0..currentGameDimention).shuffled().toList()
        digits.addAll(cells)
        setStateCells(digits)
    }


    private fun setCellSizInConfig(frWidth: Int, gridMargin: Int, cellMargin: Int) {
        cell15Size =
            (frWidth - Constants.TWO_SIDES * gridMargin - Constants.TWO_SIDES * Constants.FOUR_CELLS_IN_ROW * cellMargin) / Constants.FOUR_CELLS_IN_ROW
    }


    private fun setStateCells(cells: List<Int>) {
        gameState = currentGameState().copy(cells = Event(cells))
        gameStateLive.postValue(gameState)
    }


    private fun setStateDialog(action: String) {
        gameState = currentGameState().copy(showAlertDialog = Event(action))
        gameStateLive.postValue(gameState)
    }
}