package application.database.entities.course_drugs

import androidx.room.Entity
import androidx.room.PrimaryKey
import application.constants.VOID_ID

@Entity(tableName = "course_drugs")
data class CourseDrugEntity(
    @PrimaryKey
    val id: Int = VOID_ID,
    val drugId: Int = VOID_ID,
    val courseId: Int = VOID_ID,
    val timeStart: Long = 0,
    val timeEnd: Long = 0,
    val activeDays: Int = 0,
    val stopDays: Int = 0,
    val count: Double = 0.0,
    val hour: Int = 0,
    val minute: Int = 0
)