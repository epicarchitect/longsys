package application.database.repositories.course_analyse_groups

import android.content.Context
import application.constants.VOID_ID
import application.database.entities.AppDatabase
import application.database.entities.course_analyse_groups.CourseAnalyseGroupEntity
import application.database.repositories.IdCounter

class CourseAnalyseGroupsRepository private constructor(context: Context) {

    val dao = AppDatabase(context).courseAnalyseGroupsDao()
    val idCounter = IdCounter(context)

    fun entityWithId(e: CourseAnalyseGroupEntity) =
        if (e.id == VOID_ID) e.copy(id = idCounter.nextId()) else e

    fun save(entity: CourseAnalyseGroupEntity) =
        entityWithId(entity).also {
            dao.save(it)
        }

    fun delete(entity: CourseAnalyseGroupEntity) = dao.delete(entity)

    fun deleteByCourseId(courseId: Int) = dao.deleteByCourseId(courseId)

    fun getLiveEntitiesByCourseId(courseId: Int) = dao.getLiveEntitiesByCourseId(courseId)

    fun getEntityById(id: Int) = dao.getEntityById(id)

    companion object {
        var instance: CourseAnalyseGroupsRepository? = null

        operator fun invoke(context: Context) =
            instance ?: CourseAnalyseGroupsRepository(context.applicationContext).also {
                instance = it
            }
    }
}