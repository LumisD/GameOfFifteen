package com.lumisdinos.gameoffifteen.data

import android.os.CountDownTimer
import com.lumisdinos.gameoffifteen.common.AppConfig.cell15Size
import com.lumisdinos.gameoffifteen.common.AppConfig.currentGameDimension
import com.lumisdinos.gameoffifteen.common.AppConfig.fragWidth
import com.lumisdinos.gameoffifteen.common.util.convertIntList2String
import com.lumisdinos.gameoffifteen.common.util.convertString2IntList
import com.lumisdinos.gameoffifteen.data.Constants.GAME_15
import com.lumisdinos.gameoffifteen.domain.model.GameModel
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
    private var game: GameModel? = null
    private var timer: CountDownTimer? = null
    private var isTimerWorking = false


    override fun getGameState(): Flow<GameStateModel> {
        val state = gameStateRepository.getGameState()
        return state
    }


    override fun initialLoadCells(frWidth: Int, gridMargin: Int, cellMargin: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            startTimer()
            withContext(Dispatchers.IO) {
                currentGameDimension = GAME_15
                setCellAndFragWidthInConfig(frWidth, gridMargin, cellMargin)
                createGame()
            }
        }
    }


    override fun reloadCells() {
        CoroutineScope(Dispatchers.Main).launch {
            setStateTime(0)
            startTimer()
            withContext(Dispatchers.IO) {
                game?.let {
                    if (it.isSolved) {
                        createGame()
                    } else {
                        generateCells()
                    }
                }
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
                    game?.let {
                        game = it.copy(isSolved = true, endTime = System.currentTimeMillis())
                    }
                    saveGame()
                } else if (firstUnordered == currentGameDimension - 2) {
                    if (digits[currentGameDimension - 2] == currentGameDimension) {
                        setStateDialog(ACTION_UNSOLVABLE)
                        game?.let {
                            game = it.copy(isSolved = true, endTime = System.currentTimeMillis())
                        }
                        saveGame()
                    }
                }
            }
        }
    }


    override fun createGame() {
        if (cell15Size == 0) return
        CoroutineScope(Dispatchers.Main).launch {
            if (!isTimerWorking) {
                startTimer()
            }
            withContext(Dispatchers.IO) {
                game = gameRepository.getGameNotSolved()
                if (game == null) {
                    game =
                        GameModel(
                            id = gameRepository.getMaxId() + 1,
                            startTime = System.currentTimeMillis()
                        )
                    gameRepository.insertGame(game!!)
                }
                game?.let {
                    generateCells(convertString2IntList(it.digits))
                }
            }
        }
    }


    override fun saveGame() {
        CoroutineScope(Dispatchers.Main).launch {
            stopTimer()
            withContext(Dispatchers.IO) {
                game?.let {
                    val gameUpdate = it.copy(digits = convertIntList2String(digits))
                    gameRepository.insertGame(gameUpdate)
                }
            }
        }
    }


    override fun cellsAreRendered() {
        gameState = gameState.copy(isCellsUpdated = false)
    }

    override fun dialogIsRendered() {
        gameState = gameState.copy(isDialogUpdated = false)
    }


    private fun generateCells(savedCells: List<Int> = emptyList()) {
        digits.clear()
        val cells: List<Int>
        if (savedCells.isEmpty()) {
            //cells = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 0, 15, 14)
            cells = (0..currentGameDimension).shuffled().toList()
        } else {
            cells = savedCells
        }
        digits.addAll(cells)
        CoroutineScope(Dispatchers.Main).launch { setStateCells(digits) }
    }


    private fun setCellAndFragWidthInConfig(frWidth: Int, gridMargin: Int, cellMargin: Int) {
        fragWidth = frWidth
        cell15Size =
            (frWidth - Constants.TWO_SIDES * gridMargin - Constants.TWO_SIDES * Constants.FOUR_CELLS_IN_ROW * cellMargin) / Constants.FOUR_CELLS_IN_ROW
    }


    private fun startTimer() {
        stopTimer()
        isTimerWorking = true
        var isFirstTick = true
        timer = object : CountDownTimer(1_000_000_000L, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                game?.let {
                    if (isFirstTick) {
                        isFirstTick = false
                        CoroutineScope(Dispatchers.Main).launch { setStateTime(it.time) }
                    } else {
                        CoroutineScope(Dispatchers.Main).launch { setStateTime(it.time + 1000) }
                    }
                }
            }

            override fun onFinish() {
            }
        }
        timer!!.start()
    }


    private fun stopTimer() {
        isTimerWorking = false
        timer?.let { it.cancel() }
        timer = null
    }


    private suspend fun setStateCells(cells: List<Int>) {
        withContext(Dispatchers.IO) {
            gameState = gameState.copy(cells = cells, isCellsUpdated = true)
            gameStateRepository.insertGameState(gameState)
        }
    }


    private suspend fun setStateTime(newTime: Long) {
        withContext(Dispatchers.IO) {
            game?.let {
                game = it.copy(time = newTime)
            }
            gameState = gameState.copy(time = newTime)
            gameStateRepository.insertGameState(gameState)
        }
    }


    private suspend fun setStateDialog(action: String) {
        withContext(Dispatchers.IO) {
            gameState = gameState.copy(showAlertDialog = action, isDialogUpdated = true)
            gameStateRepository.insertGameState(gameState)
        }
    }
}