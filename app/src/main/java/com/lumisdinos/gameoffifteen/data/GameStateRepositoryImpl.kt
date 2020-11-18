package com.lumisdinos.gameoffifteen.data

import com.lumisdinos.gameoffifteen.data.mapper.GameStateDataMapper
import com.lumisdinos.gameoffifteen.domain.model.GameStateModel
import com.lumisdinos.gameoffifteen.domain.repos.GameStateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GameStateRepositoryImpl @Inject constructor(
    private val daoDB: DaoDB,
    private val gameStateDataMapper: GameStateDataMapper
) : GameStateRepository {

    override fun getGameState(): Flow<GameStateModel> {
        return daoDB.getGameState().map {
            if (it == null) {
                GameStateModel()
            } else {
                with(gameStateDataMapper) { it.fromEntityToDomain() }
            }
        }
    }


    override fun insertGameState(gameState: GameStateModel) =
        daoDB.insertGameState(with(gameStateDataMapper) { gameState.fromDomainToEntity() })

    override fun deleteAllGameStates() = daoDB.deleteAllGameStates()
}