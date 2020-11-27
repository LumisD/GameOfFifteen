package com.lumisdinos.gameoffifteen.domain.model

data class GameModel(
    val id: Int = 0,
    val name: String = "",
    val startTime: Long = 0L,
    val endTime: Long = 0L,
    val time: Long = 0L,
    val digits: String = "",
    val isSolved: Boolean = false
)