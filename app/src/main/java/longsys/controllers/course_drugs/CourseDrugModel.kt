package longsys.controllers.course_drugs

import longsys.constants.VOID_ID
import longsys.controllers.BaseModel
import longsys.controllers.courses.CourseModel
import longsys.controllers.drugs.DrugModel
import longsys.extentions.hour
import longsys.extentions.minute
import java.util.*

data class CourseDrugModel(
    override val id: Int = VOID_ID,
    val drug: DrugModel = DrugModel(),
    val course: CourseModel = CourseModel(),
    val timeStart: Calendar = Calendar.getInstance(),
    val timeEnd: Calendar = Calendar.getInstance(),
    val activeDays: Int = 1,
    val stopDays: Int = 0,
    val count: Double = 0.0,
    val hour: Int = Calendar.getInstance().hour(),
    val minute: Int = Calendar.getInstance().minute()
) : BaseModel(id)
