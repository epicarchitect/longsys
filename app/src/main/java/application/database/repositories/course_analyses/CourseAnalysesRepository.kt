package application.database.repositories.course_analyses

import android.content.Context
import application.constants.VOID_ID
import application.database.entities.AppDatabase
import application.database.entities.course_analyses.CourseAnalyseEntity
import application.database.repositories.IdCounter

class CourseAnalysesRepository private constructor(context: Context) {

    val dao = AppDatabase(context).courseAnalysesDao()
    val idCounter = IdCounter(context)

    fun entityWithId(e: CourseAnalyseEntity) =
        if (e.id == VOID_ID) e.copy(id = idCounter.nextId()) else e

    fun save(entity: CourseAnalyseEntity) =
        entityWithId(entity).also {
            dao.save(it)
        }

    fun delete(entity: CourseAnalyseEntity) = dao.delete(entity)

    fun deleteByCourseId(courseId: Int) = dao.deleteByCourseId(courseId)

    fun getEntityById(id: Int) = dao.getEntityById(id)

    fun getEntities(groupId: Int) = dao.getEntities(groupId)

    fun getLiveEntities(groupId: Int) = dao.getLiveEntities(groupId)

    companion object {
        var instance: CourseAnalysesRepository? = null

        operator fun invoke(context: Context) =
            instance ?: CourseAnalysesRepository(context.applicationContext).also {
                instance = it
            }
    }
}