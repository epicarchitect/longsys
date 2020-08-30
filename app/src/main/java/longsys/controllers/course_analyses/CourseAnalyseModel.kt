package longsys.controllers.course_analyses

import longsys.constants.VOID_ID
import longsys.controllers.BaseModel
import longsys.controllers.analyses.AnalyseModel
import longsys.controllers.course_analyse_groups.CourseAnalyseGroupModel

data class CourseAnalyseModel(
    override val id: Int = VOID_ID,
    val analyse: AnalyseModel = AnalyseModel(),
    val courseAnalyseGroup: CourseAnalyseGroupModel = CourseAnalyseGroupModel(),
    val isMandatory: Boolean = true
) : BaseModel(id)