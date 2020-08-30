package application.database.repositories.course_drugs

import android.content.Context
import application.constants.VOID_ID
import application.database.entities.AppDatabase
import application.database.entities.course_drugs.CourseDrugEntity
import application.database.repositories.IdCounter

class CourseDrugsRepository private constructor(context: Context) {

    val dao = AppDatabase(context).courseDrugsDao()
    val idCounter = IdCounter(context)

    fun entityWithId(e: CourseDrugEntity) =
        if (e.id == VOID_ID) e.copy(id = idCounter.nextId()) else e

    fun save(entity: CourseDrugEntity) =
        entityWithId(entity).also {
            dao.save(it)
        }

    fun delete(entity: CourseDrugEntity) = dao.delete(entity)

    fun deleteByCourseId(courseId: Int) = dao.deleteByCourseId(courseId)

    fun getEntityById(id: Int) = dao.getEntityById(id)

    fun getLiveEntitiesByCourseId(courseId: Int) = dao.getLiveEntitiesByCourseId(courseId)

    companion object {
        var instance: CourseDrugsRepository? = null

        operator fun invoke(context: Context) =
            instance ?: CourseDrugsRepository(context.applicationContext).also {
                instance = it
            }
    }
}