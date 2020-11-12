package com.lumisdinos.gameoffifteen.domain.model

import com.lumisdinos.gameoffifteen.common.Event

data class GameState(
    var cells: Event<List<Int>> = Event(emptyList()),
    var showAlertDialog: Event<String> = Event("")
)