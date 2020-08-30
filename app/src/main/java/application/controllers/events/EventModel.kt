package application.controllers.events

import application.constants.VOID_ID
import application.controllers.BaseModel
import java.util.*

data class EventModel(
    override val id: Int = VOID_ID,
    val type: BaseModel = BaseModel(),
    val time: Calendar = Calendar.getInstance()
): BaseModel(id)