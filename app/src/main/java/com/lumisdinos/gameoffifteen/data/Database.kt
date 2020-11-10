package com.lumisdinos.gameoffifteen.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lumisdinos.gameoffifteen.data.model.GameEntity

@Database(
    entities = [
        GameEntity::class
    ], version = 1, exportSchema = false
)
abstract class Database : RoomDatabase() {

    abstract fun daoDB(): DaoDB

}