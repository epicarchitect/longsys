package longsys.controllers.analyses

import longsys.constants.VOID_ID
import longsys.controllers.BaseModel
import longsys.controllers.analyse_groups.AnalyseGroupModel

data class AnalyseModel(
    override val id: Int = VOID_ID,
    val group: AnalyseGroupModel = AnalyseGroupModel(),
    val name: String = "",
    val description: String = ""
) : BaseModel(id)