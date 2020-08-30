package application.controllers.analyses

import application.controllers.analyse_groups.AnalyseGroupModel
import application.database.entities.analyses.AnalyseEntity

class Mapper(
    val groupCallback: (id: Int) -> AnalyseGroupModel
) {

    fun toModel(entity: AnalyseEntity) =
        entity.run { 
            AnalyseModel(
                id,
                groupCallback(groupId),
                name,
                description
            )
        }

    fun toEntity(model: AnalyseModel) =
        model.run { 
            AnalyseEntity(
                id,
                group.id,
                name,
                description
            )
        }

    fun toModelList(list: List<AnalyseEntity>) =
        list.map { toModel(it) }

}