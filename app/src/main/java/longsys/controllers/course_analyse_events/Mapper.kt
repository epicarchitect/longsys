package longsys.controllers.course_analyse_events

import longsys.controllers.course_analyse_groups.CourseAnalyseGroupModel
import longsys.database.entities.course_analyse_events.CourseAnalyseEventEntity
import longsys.extentions.calendarByTime

class Mapper(
    val groupCallback: (courseAnalyseGroupId: Int) -> CourseAnalyseGroupModel
) {

    fun toModel(entity: CourseAnalyseEventEntity) =
        entity.run {
            CourseAnalyseEventModel(
                id,
                groupCallback(courseAnalyseGroupId),
                calendarByTime(time),
                comment,
                isCompleted,
                isBeforeNotified,
                isNotified
            )
        }

    fun toEntity(model: CourseAnalyseEventModel) =
        model.run {
            CourseAnalyseEventEntity(
                id,
                courseAnalyseGroup.id,
                time.timeInMillis,
                comment,
                isCompleted,
                isBeforeNotified,
                isNotified
            )
        }

    fun toModelList(list: List<CourseAnalyseEventEntity>) =
        list.map { toModel(it) }

}


