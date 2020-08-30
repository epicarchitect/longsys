package longsys.controllers.course_drug_events

import android.app.NotificationManager
import android.content.Context
import androidx.lifecycle.Transformations
import longsys.controllers.ModelObserver
import longsys.controllers.course_drugs.CourseDrugModel
import longsys.controllers.course_drugs.CourseDrugsController
import longsys.database.repositories.course_drug_events.CourseDrugEventsRepository
import longsys.extentions.*

class CourseDrugEventsController private constructor(val context: Context) {

    val repository = CourseDrugEventsRepository(context)
    val observer = ModelObserver<CourseDrugEventModel>()
    val mapper = Mapper {
        CourseDrugsController(context).getCourseDrugById(it) ?: CourseDrugModel()
    }

    init {
        CourseDrugsController(context).observer
            .observe(CourseDrugsController.SAVE) { courseDrug ->
                deleteByCourseDrugId(courseDrug.id)

                courseDrug.timeStart.run {
                    hour(0)
                    minute(0)
                    second(0)
                    millisecond(0)
                }

                courseDrug.timeEnd.run {
                    hour(23)
                    minute(59)
                    second(59)
                    millisecond(999)
                }

                val newEvents = ArrayList<CourseDrugEventModel>()
                val cursor = courseDrug.timeStart.copy()

                while (cursor.timeInMillis <= courseDrug.timeEnd.timeInMillis) {
                    for (i in 0 until courseDrug.activeDays) {
                        newEvents.add(
                            CourseDrugEventModel(
                                courseDrug = courseDrug,
                                count = courseDrug.count,
                                time = cursor.copy {
                                    hour(courseDrug.hour)
                                    minute(courseDrug.minute)
                                }
                            )
                        )
                        cursor.timeInMillis += COUNT_MILLIS_IN_DAY
                    }
                    cursor.timeInMillis += COUNT_MILLIS_IN_DAY * courseDrug.stopDays
                }

                saveList(newEvents)
            }
            .observe(CourseDrugsController.DELETE) {
                deleteByCourseDrugId(it.id)
            }
    }

    fun save(model: CourseDrugEventModel) {
        val newEntity = repository.save(mapper.toEntity(model))
        observer.notify(SAVE, mapper.toModel(newEntity))
    }

    fun complete(model: CourseDrugEventModel): CourseDrugEventModel {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.cancel(model.id)

        val newModel = model.copy(
            isNotified = true,
            isCompleted = true
        )

        save(newModel)

        return newModel
    }

    fun saveList(list: List<CourseDrugEventModel>) =
        repository.saveList(mapper.toEntityList(list)).map { entity ->
            mapper.toModel(entity).also { newModel ->
                observer.notify(SAVE, newModel)
            }
        }

    fun delete(model: CourseDrugEventModel) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.cancel(model.id)

        repository.delete(mapper.toEntity(model))
        observer.notify(DELETE, model)
    }

    fun deleteByCourseDrugId(id: Int) =
        repository.deleteByCourseDrugId(id)

    fun getLiveEvents(courseId: Int, timeStart: Long, timeEnd: Long) =
        Transformations.map(repository.getLiveEntities(courseId, timeStart, timeEnd)) {
            mapper.toModelList(it)
        }

    fun getEvents(courseId: Int, timeStart: Long, timeEnd: Long) =
        mapper.toModelList(repository.getEntities(courseId, timeStart, timeEnd))

    fun getEventById(id: Int) =
        repository.getEntityById(id)?.let {
            mapper.toModel(it)
        }

    fun getEvents() =
        mapper.toModelList(repository.getEntities())

    fun deleteByCourseId(id: Int) =
        repository.deleteByCourseId(id)

    companion object {
        const val SAVE = 0
        const val DELETE = 1

        var instance: CourseDrugEventsController? = null

        operator fun invoke(context: Context) =
            instance ?: CourseDrugEventsController(context.applicationContext).also {
                instance = it
            }
    }
}