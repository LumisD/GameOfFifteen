package com.lumisdinos.gameoffifteen.data

import com.lumisdinos.gameoffifteen.data.mapper.GameDataMapper
import com.lumisdinos.gameoffifteen.domain.model.GameModel
import com.lumisdinos.gameoffifteen.domain.repos.GameRepository
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val daoDB: DaoDB,
    private val gameDataMapper: GameDataMapper
) : GameRepository {

    override fun getAllGames(): List<GameModel> =
        daoDB.getAllGames().map { with(gameDataMapper) { it.fromEntityToDomain() } }

    override fun getSolvedGames(orderByShortestTime: Boolean): List<GameModel> {
        return if (orderByShortestTime) {
            daoDB.getSolvedGames().map { with(gameDataMapper) { it.fromEntityToDomain() } }
        } else {
            daoDB.getSolvedGamesOrderByShortestTime()
                .map { with(gameDataMapper) { it.fromEntityToDomain() } }
        }
    }

    override fun getGame(gameId: Int): GameModel? =
        daoDB.getGame(gameId)?.let { with(gameDataMapper) { it.fromEntityToDomain() } }

    override fun getGameNotSolved(): GameModel? =
        daoDB.getGameNotSolved()?.let { with(gameDataMapper) { it.fromEntityToDomain() } }

    override fun insertAllGames(games: List<GameModel>) =
        daoDB.insertAllGames(games.map { with(gameDataMapper) { it.fromDomainToEntity() } })

    override fun insertGame(game: GameModel) =
        daoDB.insertGame(with(gameDataMapper) { game.fromDomainToEntity() })

    override fun deleteGame(gameId: Int) = daoDB.deleteGame(gameId)

    override fun deleteAllGames() = daoDB.deleteAllGames()

    override fun getMaxId(): Int = daoDB.getMaxIdGame()

    override fun getGameCount(): Int = daoDB.getGameCount()
}