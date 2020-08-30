package longsys.controllers.drugs

import android.content.Context
import androidx.lifecycle.Transformations
import longsys.controllers.drug_groups.DrugGroupModel
import longsys.controllers.drug_groups.DrugGroupsController
import longsys.database.repositories.drugs.DrugsRepository

class DrugsController private constructor(context: Context) {

    val repository = DrugsRepository(context)
    val groupsController = DrugGroupsController(context)
    val mapper = Mapper {
        groupsController.getDrugGroupById(it) ?: DrugGroupModel()
    }

    fun save(model: DrugModel) =
        repository.save(mapper.toEntity(model))

    fun delete(model: DrugModel) =
        repository.delete(mapper.toEntity(model))

    fun getLiveDrugsByGroupId(groupId: Int) =
        Transformations.map(repository.getLiveEntitiesByGroupId(groupId)) {
            mapper.toModelList(it)
        }

    fun getDrugsByGroupId(groupId: Int) =
        mapper.toModelList(repository.getEntitiesByGroupId(groupId))

    fun getDrugById(id: Int) =
        repository.getEntityById(id)?.let {
            mapper.toModel(it)
        }

    companion object {
        var instance: DrugsController? = null

        operator fun invoke(context: Context) =
            instance ?: DrugsController(context.applicationContext).also {
                instance = it
            }
    }
}