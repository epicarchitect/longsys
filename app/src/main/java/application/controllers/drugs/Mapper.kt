package application.controllers.drugs

import application.constants.UnitType
import application.controllers.drug_groups.DrugGroupModel
import application.database.entities.drugs.DrugEntity

class Mapper(
    val groupCallback: (id: Int) -> DrugGroupModel
) {

    fun toModel(entity: DrugEntity) =
        entity.run {
            DrugModel(
                id,
                groupCallback(groupId),
                name,
                description,
                UnitType[unitType]
            )
        }

    fun toEntity(model: DrugModel) =
        model.run {
            DrugEntity(
                id,
                group.id,
                name,
                description,
                UnitType[unitType]
            )
        }

    fun toModelList(list: List<DrugEntity>) =
        list.map { toModel(it) }

}