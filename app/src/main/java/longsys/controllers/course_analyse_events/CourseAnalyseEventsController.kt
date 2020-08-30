package longsys.controllers.course_analyse_events

import android.app.NotificationManager
import android.content.Context
import androidx.lifecycle.Transformations
import longsys.controllers.ModelObserver
import longsys.controllers.course_analyse_groups.CourseAnalyseGroupModel
import longsys.controllers.course_analyse_groups.CourseAnalyseGroupsController
import longsys.database.repositories.course_analyse_events.CourseAnalyseEventsRepository

class CourseAnalyseEventsController private constructor(val context: Context) {

    val repository = CourseAnalyseEventsRepository(context)
    val observer = ModelObserver<CourseAnalyseEventModel>()
    val mapper = Mapper {
        CourseAnalyseGroupsController(context).getCourseAnalyseGroupById(it) ?: CourseAnalyseGroupModel()
    }

    init {
        CourseAnalyseGroupsController(context).observer
            .observe(CourseAnalyseGroupsController.SAVE) {
                deleteByCourseAnalyseGroupId(it.id)
                save(
                    CourseAnalyseEventModel(
                        courseAnalyseGroup = it,
                        time = it.time
                    )
                )
            }
            .observe(CourseAnalyseGroupsController.DELETE) {
                deleteByCourseAnalyseGroupId(it.id)
            }
    }


    fun save(model: CourseAnalyseEventModel) {
        val newEntity = repository.save(mapper.toEntity(model))
        observer.notify(SAVE, mapper.toModel(newEntity))
    }

    fun complete(model: CourseAnalyseEventModel): CourseAnalyseEventModel {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.cancel(model.id)

        val newModel = model.copy(
            isNotified = true,
            isCompleted = true
        )

        save(newModel)

        return newModel
    }

    fun delete(model: CourseAnalyseEventModel) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.cancel(model.id)

        repository.delete(mapper.toEntity(model))
        observer.notify(DELETE, model)
    }

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

    fun deleteByCourseAnalyseGroupId(id: Int) =
        repository.deleteByCourseAnalyseGroupId(id)


    fun deleteByCourseId(id: Int) =
        repository.deleteByCourseId(id)

    companion object {
        const val SAVE = 0
        const val DELETE = 1

        var instance: CourseAnalyseEventsController? = null

        operator fun invoke(context: Context) =
            instance ?: CourseAnalyseEventsController(context.applicationContext).also {
                instance = it
            }
    }
}