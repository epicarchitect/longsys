package application.database.repositories.course_drug_events

import android.content.Context
import application.constants.VOID_ID
import application.database.entities.AppDatabase
import application.database.entities.course_drug_events.CourseDrugEventEntity
import application.database.repositories.IdCounter
import application.extentions.timeWithoutSeconds

class CourseDrugEventsRepository private constructor(context: Context) {

    val dao = AppDatabase(context).courseDrugEventsDao()
    val idCounter = IdCounter(context)

    fun entityWithId(e: CourseDrugEventEntity) =
        if (e.id == VOID_ID) e.copy(id = idCounter.nextId()) else e

    fun save(entity: CourseDrugEventEntity) =
        entityWithId(entity).also {
            dao.save(it.copy(
                time = timeWithoutSeconds(it.time) + it.id
            ))
        }

    fun saveList(list: List<CourseDrugEventEntity>) =
        list.map {
            val entity = entityWithId(it)
            entity.copy(
                time = timeWithoutSeconds(entity.time) + entity.id
            )
        }.also {
            dao.saveList(it)
        }

    fun delete(entity: CourseDrugEventEntity) = dao.delete(entity)

    fun deleteAll() = dao.deleteAll()

    fun deleteByCourseDrugId(courseDrugId: Int) = dao.deleteByCourseDrugId(courseDrugId)

    fun deleteByCourseId(id: Int) = dao.deleteByCourseId(id)

    fun getLiveEntities(courseId: Int, timeStart: Long, timeEnd: Long) = dao.getLiveEntities(courseId, timeStart, timeEnd)

    fun getEntities(courseId: Int, timeStart: Long, timeEnd: Long) = dao.getEntities(courseId, timeStart, timeEnd)

    fun getEntities() = dao.getEntities()

    fun getEntityById(id: Int) = dao.getEntityById(id)

    companion object {
        var instance: CourseDrugEventsRepository? = null

        operator fun invoke(context: Context) =
            instance ?: CourseDrugEventsRepository(context.applicationContext).also {
                instance = it
            }
    }
}