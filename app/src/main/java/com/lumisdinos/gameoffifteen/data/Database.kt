package com.lumisdinos.gameoffifteen.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lumisdinos.gameoffifteen.data.model.GameEntity
import com.lumisdinos.gameoffifteen.data.model.GameStateEntity

@Database(
    entities = [
        GameEntity::class,
        GameStateEntity::class
    ], version = 1, exportSchema = false
)
abstract class Database : RoomDatabase() {

    abstract fun daoDB(): DaoDB

}