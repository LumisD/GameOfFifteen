package com.lumisdinos.gameoffifteen.presentation

import androidx.lifecycle.ViewModel
import com.lumisdinos.gameoffifteen.common.extension.LiveResult
import com.lumisdinos.gameoffifteen.common.extension.postEmpty
import com.lumisdinos.gameoffifteen.common.extension.postLoading
import com.lumisdinos.gameoffifteen.common.extension.postSuccess
import com.lumisdinos.gameoffifteen.domain.model.GameModel
import com.lumisdinos.gameoffifteen.domain.repos.GameRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ListViewModel @Inject constructor(
    private val gameRepository: GameRepository
) : ViewModel() {

    val games = LiveResult<List<GameModel>>()
    var isOrderedByShortestTime = true


    fun loadSolvedGames(orderByShortestTime: Boolean) {
        CoroutineScope(Dispatchers.Main).launch {
            games.postLoading()
            withContext(Dispatchers.IO) {
                isOrderedByShortestTime = orderByShortestTime
                getGamesAndPost()
            }
        }
    }


    fun deleteGame(gameId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            games.postLoading()
            withContext(Dispatchers.IO) {
                gameRepository.deleteGame(gameId)
                getGamesAndPost()
            }
        }
    }


    private fun getGamesAndPost() {
        val list = gameRepository.getSolvedGames(isOrderedByShortestTime)
        if (list.isEmpty()) {
            games.postEmpty(list)
        } else {
            games.postSuccess(list)
        }
    }


}