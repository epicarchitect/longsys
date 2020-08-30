package application.ui.pages.courses.course.analyses.groups

import android.annotation.SuppressLint
import android.view.View
import application.controllers.course_analyse_groups.CourseAnalyseGroupModel
import application.extentions.dateText
import application.extentions.timeText
import application.ui.base.adapters.lists.ModelListAdapter
import kotlinx.android.synthetic.main.course_analyse_group_item.view.*
import longsys.bb35.extreme.bulk.max.R

class CourseAnalyseGroupsListAdapter : ModelListAdapter<CourseAnalyseGroupModel, CourseAnalyseGroupsListAdapter.Holder>() {

    private var onShowListener: (CourseAnalyseGroupModel) -> Unit = {}

    fun onClickShow(l: (CourseAnalyseGroupModel) -> Unit) {
        onShowListener = l
    }

    override val itemLayout = R.layout.course_analyse_group_item

    override fun areContentsTheSame(oldItem: CourseAnalyseGroupModel, newItem: CourseAnalyseGroupModel) =
        oldItem.name == newItem.name && oldItem.time.timeInMillis == newItem.time.timeInMillis

    override fun createViewHolder(view: View) = Holder(view)

    inner class Holder(view: View) : ViewHolder<CourseAnalyseGroupModel>(view) {

        init {
            with(view) {
                buttonShow.setOnClickListener {
                    onShowListener(list[adapterPosition])
                }
            }
        }

        @SuppressLint("SetTextI18n")
        override fun bind(model: CourseAnalyseGroupModel) = with(itemView) {
            tvName.text = model.name
            tvDate.text = model.time.dateText() + " в " + model.time.timeText()
        }
    }
}