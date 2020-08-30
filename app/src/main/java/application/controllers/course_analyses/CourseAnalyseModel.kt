package application.controllers.course_analyses

import application.constants.VOID_ID
import application.controllers.BaseModel
import application.controllers.analyses.AnalyseModel
import application.controllers.course_analyse_groups.CourseAnalyseGroupModel

data class CourseAnalyseModel(
    override val id: Int = VOID_ID,
    val analyse: AnalyseModel = AnalyseModel(),
    val courseAnalyseGroup: CourseAnalyseGroupModel = CourseAnalyseGroupModel(),
    val isMandatory: Boolean = true
) : BaseModel(id)