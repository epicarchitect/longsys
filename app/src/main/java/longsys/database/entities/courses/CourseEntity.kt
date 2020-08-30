package longsys.database.entities.courses

import androidx.room.Entity
import androidx.room.PrimaryKey
import longsys.constants.VOID_ID

@Entity(tableName = "courses")
data class CourseEntity(
    @PrimaryKey
    val id: Int = VOID_ID,
    val timeStart: Long = 0,
    val timeEnd: Long = 0,
    val timeCompletion: Long = 0,
    val daysBeforeNotifyAnalyse: Int = 2,
    val isNotificationsEnabled: Boolean = true
)