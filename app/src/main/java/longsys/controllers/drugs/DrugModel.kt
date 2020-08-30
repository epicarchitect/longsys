package longsys.controllers.drugs

import longsys.constants.UnitType
import longsys.constants.VOID_ID
import longsys.controllers.BaseModel
import longsys.controllers.drug_groups.DrugGroupModel

data class DrugModel(
    override val id: Int = VOID_ID,
    val group: DrugGroupModel = DrugGroupModel(),
    val name: String = "",
    val description: String = "",
    val unitType: UnitType = UnitType.AMPOULES
    //val count: Double = 0.0
) : BaseModel(id)