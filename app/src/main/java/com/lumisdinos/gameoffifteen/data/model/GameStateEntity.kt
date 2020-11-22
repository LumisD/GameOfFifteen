package com.lumisdinos.gameoffifteen.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_state")
data class GameStateEntity @JvmOverloads constructor(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "cells") val cells: String = "",
    @ColumnInfo(name = "show_alert_dialog") val showAlertDialog: String = ""
)