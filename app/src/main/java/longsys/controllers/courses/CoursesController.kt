package longsys.controllers.courses

import android.content.Context
import androidx.lifecycle.Transformations
import longsys.database.repositories.courses.CoursesRepository
import longsys.extentions.hour
import longsys.extentions.millisecond
import longsys.extentions.minute
import longsys.extentions.second

class CoursesController private constructor(val context: Context) {

    val repository = CoursesRepository(context)
    val mapper = Mapper()

    fun save(model: CourseModel) =
        repository.save(
            mapper.toEntity(
                model.copy(
                    timeStart = model.timeStart.apply {
                        millisecond(0)
                        second(0)
                        minute(0)
                        hour(0)
                    },
                    timeEnd = model.timeEnd.apply {
                        millisecond(999)
                        second(59)
                        minute(59)
                        hour(23)
                    }
                )
            )
        ).let {
            mapper.toModel(it)
        }
    
    fun delete(model: CourseModel) =
        repository.delete(mapper.toEntity(model))

    fun deleteById(id: Int) =
        repository.deleteById(id)

    fun getLiveCourses() =
        Transformations.map(repository.getLiveEntities()) {
            mapper.toModelList(it)
        }

    fun getLiveCourseById(id: Int) =
        Transformations.map(repository.getLiveEntityById(id)) {
            it?.let {
                mapper.toModel(it)
            }
        }

    fun getCourseById(id: Int) =
        repository.getEntityById(id)?.let {
            mapper.toModel(it)
        }

    companion object {
        var instance: CoursesController? = null

        operator fun invoke(context: Context) =
            instance ?: CoursesController(context.applicationContext).also {
                instance = it
            }
    }
}