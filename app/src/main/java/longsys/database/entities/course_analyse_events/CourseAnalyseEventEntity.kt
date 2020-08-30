package longsys.database.entities.course_analyse_events

import androidx.room.Entity
import androidx.room.PrimaryKey
import longsys.constants.VOID_ID

@Entity(tableName = "course_analyse_events")
data class CourseAnalyseEventEntity(
    @PrimaryKey
    val id: Int = VOID_ID,
    val courseAnalyseGroupId: Int = VOID_ID,
    val time: Long = 0,
    val comment: String = "",
    val isCompleted: Boolean = false,
    val isBeforeNotified: Boolean = false,
    val isNotified: Boolean = false
)