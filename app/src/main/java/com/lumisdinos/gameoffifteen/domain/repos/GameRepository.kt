package com.lumisdinos.gameoffifteen.domain.repos

import com.lumisdinos.gameoffifteen.domain.model.GameModel

interface GameRepository {

    fun getAllGames(): List<GameModel>

    fun getSolvedGames(orderByShortestTime: Boolean): List<GameModel>

    fun getGame(gameId: Int): GameModel?

    fun getGameNotSolved(): GameModel?

    fun insertAllGames(games: List<GameModel>)

    fun insertGame(game: GameModel)

    fun deleteGame(gameId: Int)

    fun deleteAllGames()

    fun getMaxId(): Int

    fun getGameCount(): Int
}