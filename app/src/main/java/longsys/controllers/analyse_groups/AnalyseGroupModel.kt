package longsys.controllers.analyse_groups

import longsys.constants.VOID_ID
import longsys.controllers.BaseModel

data class AnalyseGroupModel(
    override val id: Int = VOID_ID,
    val name: String = "",
    val description: String = ""
) : BaseModel(id)