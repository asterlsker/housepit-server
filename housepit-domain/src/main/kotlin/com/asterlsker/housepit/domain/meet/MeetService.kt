package com.asterlsker.housepit.domain.meet

import com.asterlsker.housepit.domain.meet.command.CreateMeetCommand
import com.asterlsker.housepit.domain.meet.command.GetMeetCriteria
import com.asterlsker.housepit.domain.meet.command.GetPrivateMeetsCriteria
import com.asterlsker.housepit.domain.meet.command.GetPublicMeetsCriteria
import com.asterlsker.housepit.domain.meet.command.dto.MeetScheduleDto
import com.asterlsker.housepit.domain.meet.dto.MeetDetailDto
import com.asterlsker.housepit.domain.meet.dto.MeetResult
import com.asterlsker.housepit.domain.meet.dto.MeetsResult
import com.asterlsker.housepit.domain.meet.entity.Meet
import com.asterlsker.housepit.domain.meet.repository.MeetRepository
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
    fun handleCreateMeetCommand(command: CreateMeetCommand) {
        val meet = Meet(memberId = command.memberId, title = command.title, content = command.content)
        MeetScheduleDto.toEntity(command.schedules).forEach { meet.addSchedule(it) }
        meetRepository.save(meet)
    }

    @Transactional
    fun handleGetMeetCriteria(criteria: GetMeetCriteria): MeetDetailDto.Result {
        val meet = meetRepository.findByMeetId(criteria.meetId) ?: throw Exception()
        return MeetDetailDto.Result.of(meet)
    }

    @Transactional
    fun handleGetMeetsCriteria(criteria: GetPrivateMeetsCriteria): MeetsResult {
        val meets = meetRepository.findByMemberIdOrderByCreatedAt(
            memberId = criteria.memberId,
            pageable = PageRequest.of(criteria.offset, criteria.limit)
        )
        return handleGetMeetsCriteriaCore(meets)
    }

    @Transactional
    fun handleGetMeetsCriteria(criteria: GetPublicMeetsCriteria): MeetsResult {
        val meets = meetRepository.findByOrderByCreatedAt(
            pageable = PageRequest.of(criteria.offset, criteria.limit)
        )
        return handleGetMeetsCriteriaCore(meets)
    }

    // duplication
    private fun handleGetMeetsCriteriaCore(meets: Page<Meet>): MeetsResult {
        val data = meets.content.map { MeetResult.of(it) }
        return MeetsResult(
            meets = data,
            pageOffset = meets.pageable.pageNumber,
            pageSize = meets.pageable.pageSize,
        )
    }
}
