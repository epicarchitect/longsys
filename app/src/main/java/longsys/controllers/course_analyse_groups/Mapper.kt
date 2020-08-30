package longsys.controllers.course_analyse_groups

import longsys.controllers.courses.CourseModel
import longsys.database.entities.course_analyse_groups.CourseAnalyseGroupEntity
import longsys.extentions.calendarByTime

class Mapper(
    val courseCallback: (courseId: Int) -> CourseModel
) {

    fun toModel(entity: CourseAnalyseGroupEntity) =
        entity.run {
            CourseAnalyseGroupModel(
                id,
                courseCallback(courseId),
                name,
                calendarByTime(time)
            )
        }

    fun toEntity(model: CourseAnalyseGroupModel) =
        model.run {
            CourseAnalyseGroupEntity(
                id,
                course.id,
                name,
                time.timeInMillis
            )
        }

    fun toModelList(list: List<CourseAnalyseGroupEntity>) =
        list.map { toModel(it) }

}