package application.controllers.course_analyses

import application.controllers.analyses.AnalyseModel
import application.controllers.course_analyse_groups.CourseAnalyseGroupModel
import application.database.entities.course_analyses.CourseAnalyseEntity

class Mapper(
    val groupCallback: (id: Int) -> CourseAnalyseGroupModel,
    val analyseCallback: (id: Int) -> AnalyseModel
) {

    fun toModel(entity: CourseAnalyseEntity) =
        entity.run {
            CourseAnalyseModel(
                id,
                analyseCallback(analyseId),
                groupCallback(courseAnalyseGroupId),
                isMandatory
            )
        }

    fun toEntity(model: CourseAnalyseModel) =
        model.run {
            CourseAnalyseEntity(
                id,
                analyse.id,
                courseAnalyseGroup.id,
                isMandatory
            )
        }

    fun toModelList(list: List<CourseAnalyseEntity>) =
        list.map { toModel(it) }

}