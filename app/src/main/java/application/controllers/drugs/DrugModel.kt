package application.controllers.drugs

import application.constants.UnitType
import application.constants.VOID_ID
import application.controllers.BaseModel
import application.controllers.drug_groups.DrugGroupModel

data class DrugModel(
    override val id: Int = VOID_ID,
    val group: DrugGroupModel = DrugGroupModel(),
    val name: String = "",
    val description: String = "",
    val unitType: UnitType = UnitType.AMPOULES
    //val count: Double = 0.0
) : BaseModel(id)