package longsys.ui.pages.courses

import android.annotation.SuppressLint
import android.view.View
import kotlinx.android.synthetic.main.course_item.view.*
import longsys.R
import longsys.controllers.courses.CourseModel
import longsys.extentions.dateTextWithoutYear
import longsys.extentions.hide
import longsys.extentions.show
import longsys.ui.base.adapters.lists.ModelListAdapter

class CoursesListAdapter(
    val isCurrent: (CourseModel) -> Boolean = { false }
) : ModelListAdapter<CourseModel, CoursesListAdapter.Holder>() {

    override val itemLayout = R.layout.course_item

    override fun areContentsTheSame(oldItem: CourseModel, newItem: CourseModel) =
        oldItem.timeStart.timeInMillis == newItem.timeStart.timeInMillis

    override fun createViewHolder(view: View) = Holder(view)

    inner class Holder(view: View) : ViewHolder<CourseModel>(view) {

        @SuppressLint("SetTextI18n")
        override fun bind(model: CourseModel) = with(itemView) {
            tvDate.text = model.timeStart.dateTextWithoutYear()
            if (isCurrent(model)) {
                tvActive.show()
            } else {
                tvActive.hide()
                tvDate.append(" - " + model.timeEnd.dateTextWithoutYear())
            }
        }

    }
}