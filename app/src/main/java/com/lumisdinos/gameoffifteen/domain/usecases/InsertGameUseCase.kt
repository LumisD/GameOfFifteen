package com.lumisdinos.gameoffifteen.domain.usecases

import com.lumisdinos.gameoffifteen.domain.model.GameModel
import com.lumisdinos.gameoffifteen.domain.repos.GameRepository
import javax.inject.Inject

class InsertGameUseCase @Inject constructor(
    private val repository: GameRepository
) {
    suspend fun executeUseCase(game: GameModel) {
        repository.insertGame(game)
    }
}