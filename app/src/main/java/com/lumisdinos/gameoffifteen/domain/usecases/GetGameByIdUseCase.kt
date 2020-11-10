package com.lumisdinos.gameoffifteen.domain.usecases

import com.lumisdinos.gameoffifteen.domain.model.GameModel
import com.lumisdinos.gameoffifteen.domain.repos.GameRepository
import javax.inject.Inject

class GetGameByIdUseCase @Inject constructor(
    private val repository: GameRepository
) {
    suspend fun executeUseCase(gameId: Int): GameModel? {
        return repository.getGame(gameId)
    }
}