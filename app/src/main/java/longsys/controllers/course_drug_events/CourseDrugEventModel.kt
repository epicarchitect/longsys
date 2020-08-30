package longsys.controllers.course_drug_events

import longsys.constants.VOID_ID
import longsys.controllers.BaseModel
import longsys.controllers.course_drugs.CourseDrugModel
import java.util.*

data class CourseDrugEventModel(
    override val id: Int = VOID_ID,
    val courseDrug: CourseDrugModel = CourseDrugModel(),
    val time: Calendar = Calendar.getInstance(),
    val count: Double = 0.0,
    val isCompleted: Boolean = false,
    val isNotified: Boolean = false
) : BaseModel(id)