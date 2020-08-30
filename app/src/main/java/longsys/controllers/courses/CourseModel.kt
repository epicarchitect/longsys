package longsys.controllers.courses

import longsys.constants.VOID_ID
import longsys.controllers.BaseModel
import java.util.*

data class CourseModel(
    override val id: Int = VOID_ID,
    val timeStart: Calendar = Calendar.getInstance(),
    val timeEnd: Calendar = Calendar.getInstance(),
    val timeCompletion: Calendar = Calendar.getInstance(),
    val daysBeforeNotifyAnalyse: Int = 2,
    val isNotificationsEnabled: Boolean = true
) : BaseModel(id)