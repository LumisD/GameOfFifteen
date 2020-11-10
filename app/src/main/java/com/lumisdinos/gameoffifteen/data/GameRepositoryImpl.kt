package com.lumisdinos.gameoffifteen.data

import com.lumisdinos.gameoffifteen.data.mapper.GameDataMapper
import com.lumisdinos.gameoffifteen.domain.model.GameModel
import com.lumisdinos.gameoffifteen.domain.repos.GameRepository
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val daoDB: DaoDB,
    private val gameDataMapper: GameDataMapper
) : GameRepository {

    override suspend fun getAllGames(): List<GameModel> =
        daoDB.getAllGames().map { with(gameDataMapper) { it.fromEntityToDomain() } }

    override suspend fun getGame(gameId: Int): GameModel? =
        daoDB.getGame(gameId)?.let { with(gameDataMapper) { it.fromEntityToDomain() } }

    override suspend fun getGame(gameName: String): GameModel? =
        daoDB.getGame(gameName)?.let { with(gameDataMapper) { it.fromEntityToDomain() } }

    override suspend fun insertAllGames(games: List<GameModel>) =
        daoDB.insertAllGames(games.map { with(gameDataMapper) { it.fromDomainToEntity() } })

    override suspend fun insertGame(game: GameModel) =
        daoDB.insertGame(with(gameDataMapper) { game.fromDomainToEntity() })

    override suspend fun deleteGame(gameId: Int) = daoDB.deleteGame(gameId)

    override suspend fun deleteAllGames() = daoDB.deleteAllGames()

    override suspend fun getMaxId(): Int = daoDB.getMaxIdGame()

    override suspend fun getGameCount(): Int = daoDB.getGameCount()
}