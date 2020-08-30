package application.ui.pages.faq

import android.view.View
import application.ui.base.adapters.lists.ModelListAdapter
import kotlinx.android.synthetic.main.question_item.view.*
import longsys.bb35.extreme.bulk.max.R

class QuestionsListAdapter : ModelListAdapter<QuestionModel, QuestionsListAdapter.Holder>() {

    override fun areContentsTheSame(oldItem: QuestionModel, newItem: QuestionModel) =
        oldItem.question == newItem.question

    override val itemLayout = R.layout.question_item

    override fun createViewHolder(view: View) = Holder(view)

    inner class Holder(view: View) : ViewHolder<QuestionModel>(view) {
        override fun bind(model: QuestionModel) = with(itemView) {
            tvQuestion.text = model.question
        }
    }
}