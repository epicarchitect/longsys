package application.controllers.drug_groups

import application.constants.VOID_ID
import application.controllers.BaseModel

data class DrugGroupModel(
    override val id: Int = VOID_ID,
    val name: String = ""
) : BaseModel(id)