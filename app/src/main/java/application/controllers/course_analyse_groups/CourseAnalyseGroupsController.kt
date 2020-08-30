package application.controllers.course_analyse_groups

import android.content.Context
import androidx.lifecycle.Transformations
import application.controllers.ModelObserver
import application.controllers.courses.CourseModel
import application.controllers.courses.CoursesController
import application.database.repositories.course_analyse_groups.CourseAnalyseGroupsRepository

class CourseAnalyseGroupsController private constructor(context: Context) {

    val coursesController = CoursesController(context)
    val repository = CourseAnalyseGroupsRepository(context)
    val observer = ModelObserver<CourseAnalyseGroupModel>()
    val mapper = Mapper {
        coursesController.getCourseById(it) ?: CourseModel()
    }

    fun save(model: CourseAnalyseGroupModel): CourseAnalyseGroupModel {
        val newEntity = repository.save(mapper.toEntity(model))
        val newModel = mapper.toModel(newEntity)
        observer.notify(SAVE, newModel)
        return newModel
    }

    fun delete(model: CourseAnalyseGroupModel) {
        repository.delete(mapper.toEntity(model))
        observer.notify(DELETE, model)
    }

    fun getLiveCourseAnalyseGroups(courseId: Int) =
        Transformations.map(repository.getLiveEntitiesByCourseId(courseId)) {
            mapper.toModelList(it)
        }

    fun getCourseAnalyseGroupById(id: Int) =
        repository.getEntityById(id)?.let {
            mapper.toModel(it)
        }

    fun deleteByCourseId(id: Int) =
        repository.deleteByCourseId(id)

    companion object {
        const val SAVE = 0
        const val DELETE = 1

        var instance: CourseAnalyseGroupsController? = null

        operator fun invoke(context: Context) =
            instance ?: CourseAnalyseGroupsController(context.applicationContext).also {
                instance = it
            }
    }
}