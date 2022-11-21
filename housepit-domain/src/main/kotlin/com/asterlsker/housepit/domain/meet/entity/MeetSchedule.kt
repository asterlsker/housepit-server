package com.asterlsker.housepit.domain.meet.entity

import com.asterlsker.housepit.domain.meet.type.MeetScheduleStatus
import com.asterlsker.housepit.rdbms.base.BaseEntity
import java.time.LocalDateTime
import javax.persistence.*


@Entity
@Table(name = "meet_schedule")
class MeetSchedule(
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val requiredPeople: Int
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meet_id", referencedColumnName = "id")
    var meet: Meet? = null

    @Enumerated(EnumType.STRING)
    val status: MeetScheduleStatus = MeetScheduleStatus.RECRUIT

    val deleted: Boolean = false

    // handler

    // filter
    fun isNotCanceled(): Boolean = status != MeetScheduleStatus.CANCEL

    // logic
}

