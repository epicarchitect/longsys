package application.controllers.analyses

import android.content.Context
import androidx.lifecycle.Transformations
import application.controllers.analyse_groups.AnalyseGroupModel
import application.controllers.analyse_groups.AnalyseGroupsController
import application.database.repositories.analyses.AnalysesRepository

class AnalysesController private constructor(context: Context) {

    val groupsController = AnalyseGroupsController(context)
    val repository = AnalysesRepository(context)
    val mapper = Mapper {
        groupsController.getAnalyseGroupById(it) ?: AnalyseGroupModel()
    }

    fun save(model: AnalyseModel) =
        repository.save(mapper.toEntity(model))

    fun delete(model: AnalyseModel) =
        repository.delete(mapper.toEntity(model))

    fun getLiveAnalysesByGroupId(groupId: Int) =
        Transformations.map(repository.getLiveEntitiesByGroupId(groupId)) {
            mapper.toModelList(it)
        }

    fun getAnalysesByGroupId(groupId: Int) =
        mapper.toModelList(repository.getEntitiesByGroupId(groupId))

    fun getAnalyseById(id: Int) =
        repository.getEntityById(id)?.let { entity ->
            mapper.toModel(entity)
        }

    companion object {
        var instance: AnalysesController? = null

        operator fun invoke(context: Context) =
            instance ?: AnalysesController(context.applicationContext).also {
                instance = it
            }
    }
}