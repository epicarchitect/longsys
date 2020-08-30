package application.controllers.courses

import application.database.entities.courses.CourseEntity
import application.extentions.calendarByTime

class Mapper {

    fun toModel(entity: CourseEntity) =
        entity.run {
            CourseModel(
                id,
                calendarByTime(timeStart),
                calendarByTime(timeEnd),
                calendarByTime(timeCompletion),
                daysBeforeNotifyAnalyse,
                isNotificationsEnabled
            )
        }

    fun toEntity(model: CourseModel) =
        model.run {
            CourseEntity(
                id,
                timeStart.timeInMillis,
                timeEnd.timeInMillis,
                timeCompletion.timeInMillis,
                daysBeforeNotifyAnalyse,
                isNotificationsEnabled
            )
        }


    fun toModelList(list: List<CourseEntity>) =
        list.map { toModel(it) }

}