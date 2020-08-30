package application.controllers.course_analyse_groups

import application.controllers.courses.CourseModel
import application.database.entities.course_analyse_groups.CourseAnalyseGroupEntity
import application.extentions.calendarByTime

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