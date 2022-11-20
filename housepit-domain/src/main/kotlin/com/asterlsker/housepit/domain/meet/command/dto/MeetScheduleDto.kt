package com.asterlsker.housepit.domain.meet.command.dto

import com.asterlsker.housepit.domain.meet.entity.MeetSchedule
import java.time.LocalDateTime

data class MeetScheduleDto(
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val requiredPeople: Int,
) {
    companion object {

        fun toEntity(data: MeetScheduleDto): MeetSchedule {
            return MeetSchedule(
                startAt = data.startAt,
                endAt = data.endAt,
                requiredPeople = data.requiredPeople
            )
        }

        fun toEntity(data: List<MeetScheduleDto>): List<MeetSchedule> {
            return data.map { toEntity(it) }
        }
    }
}
