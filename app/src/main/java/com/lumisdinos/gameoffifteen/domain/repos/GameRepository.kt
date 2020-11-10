package com.lumisdinos.gameoffifteen.domain.repos

import com.lumisdinos.gameoffifteen.domain.model.GameModel

interface GameRepository {

    suspend fun getAllGames(): List<GameModel>

    suspend fun getGame(gameId: Int): GameModel?

    suspend fun getGame(gameName: String): GameModel?

    suspend fun insertAllGames(games: List<GameModel>)

    suspend fun insertGame(game: GameModel)

    suspend fun deleteGame(gameId: Int)

    suspend fun deleteAllGames()

    suspend fun getMaxId(): Int

    suspend fun getGameCount(): Int
}