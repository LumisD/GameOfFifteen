package com.lumisdinos.gameoffifteen.domain.repos

import com.lumisdinos.gameoffifteen.domain.model.GameModel

interface GameRepository {

    fun getAllGames(): List<GameModel>

    fun getGame(gameId: Int): GameModel?

    fun getGame(gameName: String): GameModel?

    fun insertAllGames(games: List<GameModel>)

    fun insertGame(game: GameModel)

    fun deleteGame(gameId: Int)

    fun deleteAllGames()

    fun getMaxId(): Int

    fun getGameCount(): Int
}