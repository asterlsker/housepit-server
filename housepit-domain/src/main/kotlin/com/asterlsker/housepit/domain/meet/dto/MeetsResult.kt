package com.asterlsker.housepit.domain.meet.dto

data class MeetsResult(
    val meets: List<MeetResult>,
    val pageOffset: Int,
    val pageSize: Int
)
