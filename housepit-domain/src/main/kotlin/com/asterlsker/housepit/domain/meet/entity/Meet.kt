package com.asterlsker.housepit.domain.meet.entity

import com.asterlsker.housepit.domain.gyul.Gyul
import com.asterlsker.housepit.domain.meet.type.MeetScheduleStatus
import com.asterlsker.housepit.rdbms.base.BaseEntity
import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "meet")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType::class)
class Meet(
    val memberId: String,
    val title: String,
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    val content: Gyul? = null,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null

    @Column(unique = true)
    var meetId: UUID = UUID.randomUUID()
    val deleted: Boolean = false

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "meet")
    private val meetSchedule = mutableListOf<MeetSchedule>()

    // handler
    val meetSchedules get() = meetSchedule.toList()

    fun addSchedule(meetSchedule: MeetSchedule) {
        this.meetSchedule.add(meetSchedule)
        meetSchedule.meet = this
    }

    // logic
    fun nearestSchedule(): MeetSchedule? {
        if (meetSchedules.isNotEmpty()) return meetSchedules.last()
        return null
    }

    fun completedScheduleCount(): Int {
        var count = 0
        meetSchedules.forEach {
            if (it.status == MeetScheduleStatus.DONE) count++
        }
        return count
    }


}

