package com.asterlsker.housepit.domain.meet.repository

import com.asterlsker.housepit.domain.meet.entity.Meet
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface MeetRepository : JpaRepository<Meet, Long> {
    fun findByMemberIdOrderByCreatedAt(memberId: String, pageable: Pageable): Page<Meet>
    fun findByOrderByCreatedAt(pageable: Pageable): Page<Meet>
}

