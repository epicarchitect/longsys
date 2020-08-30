package longsys.controllers.drug_groups

import longsys.constants.VOID_ID
import longsys.controllers.BaseModel

data class DrugGroupModel(
    override val id: Int = VOID_ID,
    val name: String = ""
) : BaseModel(id)