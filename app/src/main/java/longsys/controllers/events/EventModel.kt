package longsys.controllers.events

import longsys.constants.VOID_ID
import longsys.controllers.BaseModel
import java.util.*

data class EventModel(
    override val id: Int = VOID_ID,
    val type: BaseModel = BaseModel(),
    val time: Calendar = Calendar.getInstance()
): BaseModel(id)