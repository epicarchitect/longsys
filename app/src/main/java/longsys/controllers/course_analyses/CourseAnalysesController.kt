package longsys.controllers.course_analyses

import android.content.Context
import androidx.lifecycle.Transformations
import longsys.controllers.analyses.AnalyseModel
import longsys.controllers.analyses.AnalysesController
import longsys.controllers.course_analyse_groups.CourseAnalyseGroupModel
import longsys.controllers.course_analyse_groups.CourseAnalyseGroupsController
import longsys.database.repositories.course_analyses.CourseAnalysesRepository

class CourseAnalysesController private constructor(context: Context) {

    val repository = CourseAnalysesRepository(context)
    val mapper = Mapper(
        {
            CourseAnalyseGroupsController(context).getCourseAnalyseGroupById(it) ?: CourseAnalyseGroupModel()
        },
        {
            AnalysesController(context).getAnalyseById(it) ?: AnalyseModel()
        }
    )

    fun save(model: CourseAnalyseModel) =
        repository.save(mapper.toEntity(model))

    fun delete(model: CourseAnalyseModel) =
        repository.delete(mapper.toEntity(model))

    fun getCourseAnalyseById(id: Int) =
        repository.getEntityById(id)?.let { entity ->
            mapper.toModel(entity)
        }

    fun deleteByCourseId(id: Int) =
        repository.deleteByCourseId(id)

    fun getCourseAnalyses(groupId: Int) =
        mapper.toModelList(repository.getEntities(groupId))

    fun getLiveCourseAnalyses(groupId: Int) =
        Transformations.map(repository.getLiveEntities(groupId)) {
            mapper.toModelList(it)
        }

    companion object {
        var instance: CourseAnalysesController? = null

        operator fun invoke(context: Context) =
            instance ?: CourseAnalysesController(context.applicationContext).also {
                instance = it
            }
    }
}