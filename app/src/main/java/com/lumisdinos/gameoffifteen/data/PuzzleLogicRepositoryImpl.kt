package com.lumisdinos.gameoffifteen.data

import com.lumisdinos.gameoffifteen.common.AppConfig
import com.lumisdinos.gameoffifteen.common.AppConfig.cell15Size
import com.lumisdinos.gameoffifteen.common.AppConfig.currentGameDimension
import com.lumisdinos.gameoffifteen.common.Event
import com.lumisdinos.gameoffifteen.data.Constants.GAME_15
import com.lumisdinos.gameoffifteen.domain.model.GameStateModel
import com.lumisdinos.gameoffifteen.domain.repos.GameRepository
import com.lumisdinos.gameoffifteen.domain.repos.GameStateRepository
import com.lumisdinos.gameoffifteen.domain.repos.PuzzleLogicRepository
import com.lumisdinos.gameoffifteen.domain.repos.PuzzleLogicRepository.Companion.ACTION_CONGRATULATIONS
import com.lumisdinos.gameoffifteen.domain.repos.PuzzleLogicRepository.Companion.ACTION_UNSOLVABLE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PuzzleLogicRepositoryImpl @Inject constructor(
    private val gameRepository: GameRepository,
    private val gameStateRepository: GameStateRepository
) : PuzzleLogicRepository {

    var gameState = GameStateModel()
    private val digits = mutableListOf<Int>()


    override fun getGameState(): Flow<GameStateModel> {
        val state = gameStateRepository.getGameState()
        return state
    }


    override fun initialLoadCells(frWidth: Int, gridMargin: Int, cellMargin: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                currentGameDimension = GAME_15
                setCellAndFragWidthInConfig(frWidth, gridMargin, cellMargin)
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

                if (firstUnordered == currentGameDimension) {
                    setStateDialog(ACTION_CONGRATULATIONS)
                } else if (firstUnordered == currentGameDimension - 2) {
                    if (digits[currentGameDimension - 2] == currentGameDimension) {
                        setStateDialog(ACTION_UNSOLVABLE)
                    }
                }
            }
        }
    }


    private fun generateCells() {
        digits.clear()
        val cells = (0..currentGameDimension).shuffled().toList()
        //val cells = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 0, 14, 15)
        digits.addAll(cells)
        setStateCells(digits)
    }


    private fun setCellAndFragWidthInConfig(frWidth: Int, gridMargin: Int, cellMargin: Int) {
        AppConfig.fragWidth = frWidth
        cell15Size =
            (frWidth - Constants.TWO_SIDES * gridMargin - Constants.TWO_SIDES * Constants.FOUR_CELLS_IN_ROW * cellMargin) / Constants.FOUR_CELLS_IN_ROW
    }


    private fun setStateCells(cells: List<Int>) {
        gameState = gameState.copy(cells = Event(cells))
        gameStateRepository.insertGameState(gameState)
    }


    private fun setStateDialog(action: String) {
        gameState = gameState.copy(showAlertDialog = Event(action))
        gameStateRepository.insertGameState(gameState)
    }
}