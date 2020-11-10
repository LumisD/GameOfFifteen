package com.lumisdinos.gameoffifteen.domain.usecases

import com.lumisdinos.gameoffifteen.domain.model.GameModel
import com.lumisdinos.gameoffifteen.domain.repos.GameRepository
import javax.inject.Inject

class GetGameByNameUseCase @Inject constructor(
    private val repository: GameRepository
) {
    suspend fun executeUseCase(gameName: String): GameModel? {
        return repository.getGame(gameName)
    }
}