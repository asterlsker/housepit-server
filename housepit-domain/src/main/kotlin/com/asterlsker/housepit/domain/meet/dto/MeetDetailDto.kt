package com.asterlsker.housepit.domain.meet.dto

import com.asterlsker.housepit.auth.domain.model.Member
import com.asterlsker.housepit.domain.gyul.Gyul
import com.asterlsker.housepit.domain.meet.entity.Meet
import com.asterlsker.housepit.domain.meet.entity.MeetSchedule
import com.asterlsker.housepit.domain.meet.type.MeetScheduleStatus
import java.time.LocalDateTime

class MeetDetailDto {

    data class Result(
        val meetId: String,
        val member: Member,
        val title: String,
        val content: Gyul?,
        val schedules: List<Schedule> = emptyList()
    ) {
        companion object {
            fun of(entity: Meet): Result = Result(
                meetId = entity.meetId.toString(),
                member = Member(entity.memberId),
                title = entity.title,
                content = entity.content,
                schedules = Schedule.of(entity.meetSchedules)
            )
        }
    }

    data class Schedule(
        val startAt: LocalDateTime,
        val endAt: LocalDateTime,
        val requiredPeople: Int,
        val status: MeetScheduleStatus,
    ) {
        companion object {
            fun of(entity: MeetSchedule): Schedule = Schedule(
                startAt = entity.startAt,
                endAt = entity.endAt,
                requiredPeople = entity.requiredPeople,
                status = entity.status
            )

            fun of(entities: List<MeetSchedule>): List<Schedule> = entities.map { of(it) }.toList()
        }
    }
}
