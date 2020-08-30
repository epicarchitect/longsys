package application.controllers.drug_groups

import android.content.Context
import androidx.lifecycle.Transformations
import application.database.repositories.drug_groups.DrugGroupsRepository

class DrugGroupsController private constructor(context: Context) {

    val repository = DrugGroupsRepository(context)
    val mapper = Mapper()

    fun save(model: DrugGroupModel) =
        repository.save(mapper.toEntity(model))

    fun delete(model: DrugGroupModel) =
        repository.delete(mapper.toEntity(model))

    fun getLiveDrugGroups() =
        Transformations.map(repository.getLiveEntities()) {
            mapper.toModelList(it)
        }

    fun getDrugGroups() =
        mapper.toModelList(repository.getEntities())

    fun getDrugGroupById(id: Int) =
        repository.getEntityById(id)?.let {
            mapper.toModel(it)
        }

    companion object {
        var instance: DrugGroupsController? = null

        operator fun invoke(context: Context) =
            instance ?: DrugGroupsController(context.applicationContext).also {
                instance = it
            }
    }
}