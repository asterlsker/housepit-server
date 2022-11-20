package com.asterlsker.housepit.domain.meet.criteria

data class GetPrivateMeetsCriteria(
    val memberId: String,
    val offset: Int,
    val limit: Int
)