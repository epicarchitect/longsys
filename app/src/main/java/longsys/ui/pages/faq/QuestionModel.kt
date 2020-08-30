package longsys.ui.pages.faq

import longsys.constants.VOID_ID
import longsys.controllers.BaseModel

data class QuestionModel(
    override val id: Int = VOID_ID,
    val question: String = "",
    val answer: String = ""
) : BaseModel(id)