package com.lumisdinos.gameoffifteen.data

import androidx.room.*
import com.lumisdinos.gameoffifteen.data.model.GameEntity

@Dao
interface DaoDB {

    @Query("SELECT * FROM game")
    fun getAllGames(): List<GameEntity>

    @Query("SELECT * FROM game WHERE id = :gameId LIMIT 1")
    fun getGame(gameId: Int): GameEntity?

    @Query("SELECT * FROM game WHERE name = :name LIMIT 1")
    fun getGame(name: String): GameEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllGames(games: List<GameEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGame(game: GameEntity)

    @Query("DELETE FROM game WHERE id = :gameId")
    fun deleteGame(gameId: Int)

    @Query("DELETE FROM game")
    fun deleteAllGames()

    @Query("SELECT MAX(id) FROM game")
    fun getMaxIdGame(): Int

    @Query("SELECT COUNT(*) FROM game")
    fun getGameCount(): Int

}