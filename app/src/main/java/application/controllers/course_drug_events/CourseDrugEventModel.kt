package application.controllers.course_drug_events

import application.constants.VOID_ID
import application.controllers.BaseModel
import application.controllers.course_drugs.CourseDrugModel
import java.util.*

data class CourseDrugEventModel(
    override val id: Int = VOID_ID,
    val courseDrug: CourseDrugModel = CourseDrugModel(),
    val time: Calendar = Calendar.getInstance(),
    val count: Double = 0.0,
    val isCompleted: Boolean = false,
    val isNotified: Boolean = false
) : BaseModel(id)