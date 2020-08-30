package application.database.entities.course_analyse_groups

import androidx.room.Entity
import androidx.room.PrimaryKey
import application.constants.VOID_ID

@Entity(tableName = "course_analyse_groups")
data class CourseAnalyseGroupEntity(
    @PrimaryKey
    val id: Int = VOID_ID,
    val courseId: Int = VOID_ID,
    val name: String = "",
    val time: Long = 0
)