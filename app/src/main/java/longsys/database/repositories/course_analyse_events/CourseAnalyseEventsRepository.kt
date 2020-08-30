package longsys.database.repositories.course_analyse_events

import android.content.Context
import longsys.constants.VOID_ID
import longsys.database.entities.AppDatabase
import longsys.database.entities.course_analyse_events.CourseAnalyseEventEntity
import longsys.database.repositories.IdCounter
import longsys.extentions.timeWithoutSeconds

class CourseAnalyseEventsRepository private constructor(context: Context) {

    val dao = AppDatabase(context).courseAnalyseEventsDao()
    val idCounter = IdCounter(context)

    fun entityWithId(e: CourseAnalyseEventEntity) =
        if (e.id == VOID_ID) e.copy(id = idCounter.nextId()) else e

    fun save(entity: CourseAnalyseEventEntity) =
        entityWithId(entity).also {
            dao.save(it.copy(
                time = timeWithoutSeconds(it.time) + it.id
            ))
        }

    fun delete(entity: CourseAnalyseEventEntity) = dao.delete(entity)

    fun deleteAll() = dao.deleteAll()

    fun deleteByCourseAnalyseGroupId(id: Int) = dao.deleteByCourseAnalyseGroupId(id)

    fun deleteByCourseId(id: Int) = dao.deleteByCourseId(id)

    fun getLiveEntities(courseId: Int, timeStart: Long, timeEnd: Long) =
        dao.getLiveEntities(courseId, timeStart, timeEnd)

    fun getEntities(courseId: Int, timeStart: Long, timeEnd: Long) =
        dao.getEntities(courseId, timeStart, timeEnd)

    fun getEntities() = dao.getEntities()

    fun getEntityById(id: Int) = dao.getEntityById(id)

    companion object {
        var instance: CourseAnalyseEventsRepository? = null

        operator fun invoke(context: Context) =
            instance ?: CourseAnalyseEventsRepository(context.applicationContext).also {
                instance = it
            }
    }
}