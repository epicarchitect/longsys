package longsys.ui.pages.courses.course.analyses.groups

import android.annotation.SuppressLint
import android.view.View
import kotlinx.android.synthetic.main.course_analyse_group_item.view.*
import longsys.R
import longsys.controllers.course_analyse_groups.CourseAnalyseGroupModel
import longsys.extentions.dateText
import longsys.extentions.timeText
import longsys.ui.base.adapters.lists.ModelListAdapter

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