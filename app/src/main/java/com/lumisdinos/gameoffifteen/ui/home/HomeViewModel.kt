package com.lumisdinos.gameoffifteen.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.lumisdinos.gameoffifteen.domain.model.GameStateModel
import com.lumisdinos.gameoffifteen.domain.repos.PuzzleLogicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val logicRepo: PuzzleLogicRepository
) : ViewModel() {

    @ExperimentalCoroutinesApi
    val gameState: LiveData<GameStateModel> = logicRepo
        .getGameState()
        .catch {
            Timber.d("qwer getGameState catch: %s", it.message)
        }
        .asLiveData()

    fun initialLoadCells(frWidth: Int, gridMargin: Int, cellMargin: Int) {
        logicRepo.initialLoadCells(frWidth, gridMargin, cellMargin)
    }

    fun reloadCells() {
        logicRepo.reloadCells()
    }

    fun swapCellWithEmpty(cellIndex: Int, zeroIndex: Int) {
        logicRepo.swapCellWithEmpty(cellIndex, zeroIndex)
    }

    fun getGame() {
        logicRepo.createGame()
    }


    fun saveGame() {
        logicRepo.saveGame()
    }


    fun cellsAreRendered() {
        logicRepo.cellsAreRendered()
    }


    fun dialogIsRendered() {
        logicRepo.dialogIsRendered()
    }

}