package com.lumisdinos.gameoffifteen.domain.usecases

import com.lumisdinos.gameoffifteen.domain.repos.GameRepository
import javax.inject.Inject

class DeleteGameUseCase @Inject constructor(
    private val repository: GameRepository
) {
    suspend fun executeUseCase(gameId: Int) {
        return repository.deleteGame(gameId)
    }
}
