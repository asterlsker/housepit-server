package com.asterlsker.housepit.domain.meet.dto

import com.asterlsker.housepit.auth.domain.model.Member
import com.asterlsker.housepit.domain.meet.entity.Meet


data class MeetResult(
    val meetId: String,
    val member: Member,
    val title: String
) {
    companion object {
        fun of(entity: Meet): MeetResult {
            return MeetResult(
                meetId = entity.meetId.toString(),
                member = Member(
                    memberId = entity.memberId,
                ),
                title = entity.title
            )
        }
    }
}
