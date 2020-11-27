package com.lumisdinos.gameoffifteen.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game")
data class GameEntity @JvmOverloads constructor(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "start_time") val startTime: Long = 0L,
    @ColumnInfo(name = "end_time") val endTime: Long = 0L,
    @ColumnInfo(name = "time") val time: Long = 0L,
    @ColumnInfo(name = "digits") val digits: String = "",
    @ColumnInfo(name = "is_solved") val isSolved: Boolean = false
)