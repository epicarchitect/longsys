package application.ui.pages.courses

import android.annotation.SuppressLint
import android.view.View
import application.controllers.courses.CourseModel
import application.extentions.dateTextWithoutYear
import application.extentions.hide
import application.extentions.show
import application.ui.base.adapters.lists.ModelListAdapter
import kotlinx.android.synthetic.main.course_item.view.*
import longsys.bb35.extreme.bulk.max.R

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