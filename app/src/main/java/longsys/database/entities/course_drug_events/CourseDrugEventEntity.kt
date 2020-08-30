package longsys.database.entities.course_drug_events

import androidx.room.Entity
import androidx.room.PrimaryKey
import longsys.constants.VOID_ID

@Entity(tableName = "course_drug_events")
data class CourseDrugEventEntity(
    @PrimaryKey
    val id: Int = VOID_ID,
    val courseDrugId: Int = VOID_ID,
    val time: Long = 0,
    val count: Double = 0.0,
    val isCompleted: Boolean = false,
    val isNotified: Boolean = false
)