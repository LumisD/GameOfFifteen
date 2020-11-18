package com.lumisdinos.gameoffifteen.data.mapper

import com.lumisdinos.gameoffifteen.common.Event
import com.lumisdinos.gameoffifteen.common.util.convertIntList2String
import com.lumisdinos.gameoffifteen.common.util.convertString2IntList
import com.lumisdinos.gameoffifteen.data.model.GameStateEntity
import com.lumisdinos.gameoffifteen.domain.model.GameStateModel
import javax.inject.Inject

class GameStateDataMapper @Inject constructor() {

    fun GameStateEntity.fromEntityToDomain() = GameStateModel(
        cells = Event(convertString2IntList(cells)),
        showAlertDialog = Event(showAlertDialog)
    )

    fun GameStateModel.fromDomainToEntity() = GameStateEntity(
        cells = convertIntList2String(cells.peekContent()),
        showAlertDialog = showAlertDialog.peekContent()
    )

}