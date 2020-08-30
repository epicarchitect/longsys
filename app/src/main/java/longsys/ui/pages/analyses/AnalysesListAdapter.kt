package longsys.ui.pages.analyses

import android.annotation.SuppressLint
import android.view.View
import kotlinx.android.synthetic.main.analyse_item.view.*
import longsys.R
import longsys.controllers.analyses.AnalyseModel
import longsys.ui.base.adapters.lists.ModelListAdapter

class AnalysesListAdapter : ModelListAdapter<AnalyseModel, AnalysesListAdapter.Holder>() {

    override val itemLayout = R.layout.analyse_item

    override fun areContentsTheSame(oldItem: AnalyseModel, newItem: AnalyseModel)
            = oldItem.name == newItem.name && oldItem.description == newItem.description

    override fun createViewHolder(view: View) = Holder(view)

    inner class Holder(view: View) : ViewHolder<AnalyseModel>(view) {

        @SuppressLint("SetTextI18n")
        override fun bind(model: AnalyseModel) = with(itemView) {
            tvName.text = model.name
            tvDescription.text = model.description
        }

    }
}