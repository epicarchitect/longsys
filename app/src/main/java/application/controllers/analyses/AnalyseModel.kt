package application.controllers.analyses

import application.constants.VOID_ID
import application.controllers.BaseModel
import application.controllers.analyse_groups.AnalyseGroupModel

data class AnalyseModel(
    override val id: Int = VOID_ID,
    val group: AnalyseGroupModel = AnalyseGroupModel(),
    val name: String = "",
    val description: String = ""
) : BaseModel(id)