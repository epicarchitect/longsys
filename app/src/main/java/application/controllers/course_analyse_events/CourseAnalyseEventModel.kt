package application.controllers.course_analyse_events

import application.constants.VOID_ID
import application.controllers.BaseModel
import application.controllers.course_analyse_groups.CourseAnalyseGroupModel
import java.util.*

data class CourseAnalyseEventModel(
    override val id: Int = VOID_ID,
    val courseAnalyseGroup: CourseAnalyseGroupModel = CourseAnalyseGroupModel(),
    val time: Calendar = Calendar.getInstance(),
    val comment: String = "",
    val isCompleted: Boolean = false,
    val isBeforeNotified: Boolean = false,
    val isNotified: Boolean = false
) : BaseModel(id)