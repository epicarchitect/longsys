package application.controllers.courses

import application.constants.VOID_ID
import application.controllers.BaseModel
import java.util.*

data class CourseModel(
    override val id: Int = VOID_ID,
    val timeStart: Calendar = Calendar.getInstance(),
    val timeEnd: Calendar = Calendar.getInstance(),
    val timeCompletion: Calendar = Calendar.getInstance(),
    val daysBeforeNotifyAnalyse: Int = 2,
    val isNotificationsEnabled: Boolean = true
) : BaseModel(id)