package com.lumisdinos.gameoffifteen.domain.repos

import com.lumisdinos.gameoffifteen.domain.model.GameStateModel
import kotlinx.coroutines.flow.Flow

interface GameStateRepository {

    fun getGameState(): Flow<GameStateModel>

    fun insertGameState(gameState: GameStateModel)

    fun deleteAllGameStates()
}