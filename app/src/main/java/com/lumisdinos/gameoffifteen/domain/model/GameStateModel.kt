package com.lumisdinos.gameoffifteen.domain.model

import com.lumisdinos.gameoffifteen.common.Event

data class GameStateModel(
    var cells: Event<List<Int>> = Event(emptyList()),
    var showAlertDialog: Event<String> = Event("")
)