package application.controllers.analyse_groups

import application.constants.VOID_ID
import application.controllers.BaseModel

data class AnalyseGroupModel(
    override val id: Int = VOID_ID,
    val name: String = "",
    val description: String = ""
) : BaseModel(id)