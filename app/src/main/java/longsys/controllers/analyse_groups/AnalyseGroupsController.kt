package longsys.controllers.analyse_groups

import android.content.Context
import androidx.lifecycle.Transformations
import longsys.database.repositories.analyse_groups.AnalyseGroupsRepository

class AnalyseGroupsController private constructor(context: Context) {

    val repository = AnalyseGroupsRepository(context)
    val mapper = Mapper()

    fun save(model: AnalyseGroupModel) =
        repository.save(mapper.toEntity(model))

    fun delete(model: AnalyseGroupModel) =
        repository.delete(mapper.toEntity(model))

    fun getLiveAnalyseGroups() =
        Transformations.map(repository.getLiveEntities()) {
            mapper.toModelList(it)
        }

    fun getAnalyseGroups() =
        mapper.toModelList(repository.getEntities())

    fun getAnalyseGroupById(id: Int) =
        repository.getEntityById(id)?.let {
            mapper.toModel(it)
        }

    companion object {
        var instance: AnalyseGroupsController? = null

        operator fun invoke(context: Context) =
            instance ?: AnalyseGroupsController(context.applicationContext).also {
                instance = it
            }
    }
}