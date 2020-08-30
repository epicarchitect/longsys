package application.controllers.drug_groups

import application.database.entities.drug_groups.DrugGroupEntity


class Mapper {

    fun toModel(entity: DrugGroupEntity) =
        entity.run {
            DrugGroupModel(
                id,
                name
            )
        }


    fun toEntity(model: DrugGroupModel) =
        model.run {
            DrugGroupEntity(
                id,
                name
            )
        }

    fun toModelList(list: List<DrugGroupEntity>) =
        list.map { toModel(it) }

}