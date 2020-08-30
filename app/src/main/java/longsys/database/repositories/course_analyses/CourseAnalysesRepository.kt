package longsys.database.repositories.course_analyses

import android.content.Context
import longsys.constants.VOID_ID
import longsys.database.entities.AppDatabase
import longsys.database.entities.course_analyses.CourseAnalyseEntity
import longsys.database.repositories.IdCounter

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