package longsys.controllers.course_drugs

import longsys.controllers.courses.CourseModel
import longsys.controllers.drugs.DrugModel
import longsys.database.entities.course_drugs.CourseDrugEntity
import longsys.extentions.calendarByTime

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