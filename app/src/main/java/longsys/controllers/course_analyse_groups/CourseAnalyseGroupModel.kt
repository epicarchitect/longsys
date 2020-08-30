package longsys.controllers.course_analyse_groups

import longsys.constants.VOID_ID
import longsys.controllers.BaseModel
import longsys.controllers.courses.CourseModel
import java.util.*

data class CourseAnalyseGroupModel(
    override val id: Int = VOID_ID,
    val course: CourseModel = CourseModel(),
    val name: String = "",
    val time: Calendar = Calendar.getInstance()
) : BaseModel(id)