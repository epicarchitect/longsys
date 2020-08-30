package longsys.controllers.course_analyse_events

import longsys.constants.VOID_ID
import longsys.controllers.BaseModel
import longsys.controllers.course_analyse_groups.CourseAnalyseGroupModel
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