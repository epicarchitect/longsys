package application.controllers.course_drugs

import android.content.Context
import androidx.lifecycle.Transformations
import application.controllers.ModelObserver
import application.controllers.courses.CourseModel
import application.controllers.courses.CoursesController
import application.controllers.drugs.DrugModel
import application.controllers.drugs.DrugsController
import application.database.repositories.course_drugs.CourseDrugsRepository

class CourseDrugsController private constructor(context: Context) {

    val repository = CourseDrugsRepository(context)
    val observer = ModelObserver<CourseDrugModel>()
    val mapper = Mapper(
        {
            DrugsController(context).getDrugById(it) ?: DrugModel()
        },
        {
            CoursesController(context).getCourseById(it) ?: CourseModel()
        }
    )

    fun save(model: CourseDrugModel) {
        val newEntity = repository.save(mapper.toEntity(model))
        observer.notify(SAVE, mapper.toModel(newEntity))
    }

    fun delete(model: CourseDrugModel) {
        repository.delete(mapper.toEntity(model))
        observer.notify(DELETE, model)
    }

    fun deleteByCourseId(courseId: Int) =
        repository.deleteByCourseId(courseId)

    fun getLiveCourseDrugsByCourseId(courseId: Int) =
        Transformations.map(repository.getLiveEntitiesByCourseId(courseId)) {
            mapper.toModelList(it)
        }

    fun getCourseDrugById(id: Int) =
        repository.getEntityById(id)?.let {
            mapper.toModel(it)
        }

    companion object {
        const val SAVE = 0
        const val DELETE = 1

        var instance: CourseDrugsController? = null

        operator fun invoke(context: Context) =
            instance ?: CourseDrugsController(context.applicationContext).also {
                instance = it
            }
    }
}