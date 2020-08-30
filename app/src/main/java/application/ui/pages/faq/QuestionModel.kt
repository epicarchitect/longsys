package application.ui.pages.faq

import application.constants.VOID_ID
import application.controllers.BaseModel

data class QuestionModel(
    override val id: Int = VOID_ID,
    val question: String = "",
    val answer: String = ""
) : BaseModel(id)