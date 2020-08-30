package longsys.ui.pages.courses.course.analyses.analyses

import android.annotation.SuppressLint
import android.view.View
import kotlinx.android.synthetic.main.course_analyse_item.view.*
import longsys.R
import longsys.controllers.course_analyses.CourseAnalyseModel
import longsys.ui.base.adapters.lists.ModelListAdapter

class CourseAnalysesListAdapter : ModelListAdapter<CourseAnalyseModel, CourseAnalysesListAdapter.Holder>() {

    override val itemLayout = R.layout.course_analyse_item

    override fun areContentsTheSame(oldItem: CourseAnalyseModel, newItem: CourseAnalyseModel) =
        oldItem.analyse.name == newItem.analyse.name

    override fun createViewHolder(view: View) = Holder(view)

    inner class Holder(view: View) : ViewHolder<CourseAnalyseModel>(view) {

        @SuppressLint("SetTextI18n")
        override fun bind(model: CourseAnalyseModel) = with(itemView) {
            tvName.text = model.analyse.name
        }

    }
}