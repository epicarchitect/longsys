package application.controllers.course_drugs

import application.controllers.courses.CourseModel
import application.controllers.drugs.DrugModel
import application.database.entities.course_drugs.CourseDrugEntity
import application.extentions.calendarByTime

class Mapper(
    val drugCallback: (id: Int) -> DrugModel,
    val courseCallback: (id: Int) -> CourseModel
) {

    fun toModel(entity: CourseDrugEntity) =
        entity.run {
            CourseDrugModel(
                id,
                drugCallback(drugId),
                courseCallback(courseId),
                calendarByTime(timeStart),
                calendarByTime(timeEnd),
                activeDays,
                stopDays,
                count,
                hour,
                minute
            )
        }

    fun toEntity(model: CourseDrugModel) =
        model.run {
            CourseDrugEntity(
                id,
                drug.id,
                course.id,
                timeStart.timeInMillis,
                timeEnd.timeInMillis,
                activeDays,
                stopDays,
                count,
                hour,
                minute
            )
        }

    fun toModelList(list: List<CourseDrugEntity>) =
        list.map { toModel(it) }

}