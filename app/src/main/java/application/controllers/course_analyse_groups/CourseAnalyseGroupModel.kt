package application.controllers.course_analyse_groups

import application.constants.VOID_ID
import application.controllers.BaseModel
import application.controllers.courses.CourseModel
import java.util.*

data class CourseAnalyseGroupModel(
    override val id: Int = VOID_ID,
    val course: CourseModel = CourseModel(),
    val name: String = "",
    val time: Calendar = Calendar.getInstance()
) : BaseModel(id)