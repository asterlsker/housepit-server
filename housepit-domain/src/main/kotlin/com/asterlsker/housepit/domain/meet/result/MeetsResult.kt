package com.asterlsker.housepit.domain.meet.result

data class MeetsResult(
    val meets: List<MeetResult>,
    val pageOffset: Int,
    val pageSize: Int
)
