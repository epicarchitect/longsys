package application.database.entities.course_analyses

import androidx.room.Entity
import androidx.room.PrimaryKey
import application.constants.VOID_ID

@Entity(tableName = "course_analyses")
data class CourseAnalyseEntity(
    @PrimaryKey
    val id: Int = VOID_ID,
    val analyseId: Int = VOID_ID,
    val courseAnalyseGroupId: Int = VOID_ID,
    val isMandatory: Boolean = true
)