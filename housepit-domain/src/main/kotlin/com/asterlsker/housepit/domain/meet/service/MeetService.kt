package com.asterlsker.housepit.domain.meet.service

import com.asterlsker.housepit.domain.gyul.Gyul
import com.asterlsker.housepit.domain.meet.command.CreateMeetCommand
import com.asterlsker.housepit.domain.meet.criteria.GetPrivateMeetsCriteria
import com.asterlsker.housepit.domain.meet.criteria.GetPublicMeetsCriteria
import com.asterlsker.housepit.domain.meet.command.dto.MeetScheduleDto
import com.asterlsker.housepit.domain.meet.entity.Meet
import com.asterlsker.housepit.domain.meet.entity.MeetSchedule
import com.asterlsker.housepit.domain.meet.repository.MeetRepository
import com.asterlsker.housepit.domain.meet.result.MeetResult
import com.asterlsker.housepit.domain.meet.result.MeetsResult
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MeetService(private val meetRepository: MeetRepository) {

    /**
     * 새로운 모임 생성
     */
    @Transactional
    fun createMeet(memberId: String, title: String, content: Gyul?, schedules: List<MeetSchedule>) {
        val meet = Meet(memberId = memberId, title = title, content = content)
        schedules.forEach { meet.addSchedule(it) }
        meetRepository.save(meet)
    }

    @Transactional
    fun handleCreateMeetCommand(command: CreateMeetCommand) {
        val meet = Meet(memberId = command.memberId, title = command.title, content = command.content)
        MeetScheduleDto.toEntity(command.schedules).forEach { meet.addSchedule(it) }
        meetRepository.save(meet)
    }

    @Transactional
    fun handleGetMeets(criteria: GetPrivateMeetsCriteria): MeetsResult {
        val meets = meetRepository.findByMemberIdOrderByCreatedAt(
            memberId = criteria.memberId,
            pageable = PageRequest.of(criteria.offset, criteria.limit)
        )
        return handleGetMeetsCore(meets)
    }

    @Transactional
    fun handleGetMeets(criteria: GetPublicMeetsCriteria): MeetsResult {
        val meets = meetRepository.findByOrderByCreatedAt(
            pageable = PageRequest.of(criteria.offset, criteria.limit)
        )
        return handleGetMeetsCore(meets)
    }

    // duplication
    private fun handleGetMeetsCore(meets: Page<Meet>): MeetsResult {
        val data = meets.content.map { MeetResult.of(it) }
        return MeetsResult(
            meets = data,
            pageOffset = meets.pageable.pageNumber,
            pageSize = meets.pageable.pageSize,
        )
    }
}
