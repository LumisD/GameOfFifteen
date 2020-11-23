package com.lumisdinos.gameoffifteen.data.mapper

import com.lumisdinos.gameoffifteen.data.model.GameEntity
import com.lumisdinos.gameoffifteen.domain.model.GameModel
import javax.inject.Inject

class GameDataMapper @Inject constructor() {

    fun GameEntity.fromEntityToDomain() = GameModel(
        id = id,
        name = name,
        startTime = startTime,
        endTime = endTime,
        time = time,
        digits = digits,
        isSolved = isSolved
    )

    fun GameModel.fromDomainToEntity() = GameEntity(
        id = id,
        name = name,
        startTime = startTime,
        endTime = endTime,
        time = time,
        digits = digits,
        isSolved = isSolved
    )

}