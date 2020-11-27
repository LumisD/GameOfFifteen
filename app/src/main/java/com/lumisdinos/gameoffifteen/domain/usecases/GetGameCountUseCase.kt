package com.lumisdinos.gameoffifteen.domain.usecases

import com.lumisdinos.gameoffifteen.domain.repos.GameRepository
import javax.inject.Inject

class GetGameCountUseCase @Inject constructor(
    private val repository: GameRepository
) {
    suspend fun executeUseCase(): Int = repository.getGameCount()
}