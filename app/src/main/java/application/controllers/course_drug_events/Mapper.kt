package application.controllers.course_drug_events

import application.controllers.course_drugs.CourseDrugModel
import application.database.entities.course_drug_events.CourseDrugEventEntity
import application.extentions.calendarByTime

class Mapper(
    val courseDrugCallback: (id: Int) -> CourseDrugModel
) {

    fun toModel(entity: CourseDrugEventEntity) =
        entity.run {
            CourseDrugEventModel(
                id,
                courseDrugCallback(courseDrugId),
                calendarByTime(time),
                count,
                isCompleted,
                isNotified
            )
        }

    fun toEntity(model: CourseDrugEventModel) =
        model.run {
            CourseDrugEventEntity(
                id,
                courseDrug.id,
                time.timeInMillis,
                count,
                isCompleted,
                isNotified
            )
        }

    fun toModelList(list: List<CourseDrugEventEntity>) =
        list.map {
            toModel(it)
        }

    fun toEntityList(list: List<CourseDrugEventModel>) =
        list.map {
            toEntity(it)
        }

}