package com.asterlsker.housepit.domain.meet.command

import com.asterlsker.housepit.domain.gyul.Gyul
import com.asterlsker.housepit.domain.meet.command.dto.MeetScheduleDto

data class CreateMeetCommand(
    val memberId: String,
    val title: String,
    val content: Gyul?,
    val schedules: List<MeetScheduleDto>
)