package application.ui.pages.faq

import android.content.Context
import longsys.bb35.extreme.bulk.max.R

object StaticStorage {

    fun list(context: Context): List<QuestionModel> {
        fun string(res: Int) = context.getString(res)

        fun item(id: Int, questionRes: Int, answerRes: Int) =
            QuestionModel(
                id,
                string(questionRes),
                string(answerRes)
            )

        return listOf(
            item(0, R.string.question_0, R.string.answer_0),
            item(1, R.string.question_1, R.string.answer_1),
            item(2, R.string.question_2, R.string.answer_2),
            item(3, R.string.question_3, R.string.answer_3),
            item(4, R.string.question_4, R.string.answer_4),
            item(5, R.string.question_5, R.string.answer_5),
            item(6, R.string.question_6, R.string.answer_6),
            item(7, R.string.question_7, R.string.answer_7),
            item(8, R.string.question_8, R.string.answer_8),
            item(9, R.string.question_9, R.string.answer_9),
            item(10, R.string.question_10, R.string.answer_10),
            item(11, R.string.question_11, R.string.answer_11),
            item(12, R.string.question_12, R.string.answer_12),
            item(13, R.string.question_13, R.string.answer_13),
            item(14, R.string.question_14, R.string.answer_14)
        )
    }

}