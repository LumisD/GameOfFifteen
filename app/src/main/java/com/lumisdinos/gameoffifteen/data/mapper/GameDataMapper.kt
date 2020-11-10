package com.lumisdinos.gameoffifteen.data.mapper

import com.lumisdinos.gameoffifteen.data.model.GameEntity
import com.lumisdinos.gameoffifteen.domain.model.GameModel
import javax.inject.Inject

class GameDataMapper @Inject constructor() {

    fun GameEntity.fromEntityToDomain() = GameModel(
        id = id,
        name = name,
        time = time
    )

    fun GameModel.fromDomainToEntity() = GameEntity(
        id = id,
        name = name,
        time = time
    )

}