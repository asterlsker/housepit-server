package com.asterlsker.housepit.domain.meet.command

data class GetPrivateMeetsCriteria(
    val memberId: String,
    val offset: Int,
    val limit: Int
)