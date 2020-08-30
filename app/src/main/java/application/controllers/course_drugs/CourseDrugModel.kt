package application.controllers.course_drugs

import application.constants.VOID_ID
import application.controllers.BaseModel
import application.controllers.courses.CourseModel
import application.controllers.drugs.DrugModel
import application.extentions.hour
import application.extentions.minute
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
