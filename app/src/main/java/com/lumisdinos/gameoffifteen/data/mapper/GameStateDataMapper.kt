package com.lumisdinos.gameoffifteen.data.mapper

import com.lumisdinos.gameoffifteen.common.util.convertIntList2String
import com.lumisdinos.gameoffifteen.common.util.convertString2IntList
import com.lumisdinos.gameoffifteen.data.model.GameStateEntity
import com.lumisdinos.gameoffifteen.domain.model.GameStateModel
import javax.inject.Inject

class GameStateDataMapper @Inject constructor() {

    fun GameStateEntity.fromEntityToDomain() = GameStateModel(
        time = time,
        cells = convertString2IntList(cells),
        isCellsUpdated = isCellsUpdated,
        showAlertDialog = showAlertDialog,
        isDialogUpdated = isDialogUpdated
    )

    fun GameStateModel.fromDomainToEntity() = GameStateEntity(
        time = time,
        cells = convertIntList2String(cells),
        isCellsUpdated = isCellsUpdated,
        showAlertDialog = showAlertDialog,
        isDialogUpdated = isDialogUpdated
    )

}