package com.lumisdinos.gameoffifteen.domain.model

import com.lumisdinos.gameoffifteen.common.Event

data class GameStateModel(
    val time: Long = 0L,
    val cells: List<Int> = emptyList(),
    val isCellsUpdated: Boolean = false,
    val showAlertDialog: String = "",
    val isDialogUpdated: Boolean = false
)