package application.database.repositories.courses

import android.content.Context
import application.constants.VOID_ID
import application.database.entities.AppDatabase
import application.database.entities.courses.CourseEntity
import application.database.repositories.IdCounter

class CoursesRepository private constructor(context: Context) {

    val dao = AppDatabase(context).coursesDao()
    val idCounter = IdCounter(context)

    fun entityWithId(e: CourseEntity) =
        if (e.id == VOID_ID) e.copy(id = idCounter.nextId()) else e

    fun save(entity: CourseEntity) =
        entityWithId(entity).also {
            dao.save(it)
        }

    fun deleteById(id: Int) = dao.deleteById(id)

    fun delete(entity: CourseEntity) = deleteById(entity.id)

    fun getLiveEntities() = dao.getLiveEntities()

    fun getLiveEntityById(id: Int) = dao.getLiveEntityById(id)

    fun getEntityById(id: Int) = dao.getEntityById(id)

    companion object {
        var instance: CoursesRepository? = null

        operator fun invoke(context: Context) =
            instance ?: CoursesRepository(context.applicationContext).also {
                instance = it
            }
    }
}