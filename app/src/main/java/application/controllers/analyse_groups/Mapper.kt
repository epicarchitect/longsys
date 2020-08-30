package application.controllers.analyse_groups

import application.database.entities.analyse_groups.AnalyseGroupEntity

class Mapper {

    fun toModel(entity: AnalyseGroupEntity) =
        entity.run {
            AnalyseGroupModel(
                id,
                name,
                description
            )
        }
    
    fun toEntity(model: AnalyseGroupModel) =
        model.run { 
            AnalyseGroupEntity(
                id,
                name,
                description
            )
        }

    fun toModelList(list: List<AnalyseGroupEntity>) = list.map { toModel(it) }

}

