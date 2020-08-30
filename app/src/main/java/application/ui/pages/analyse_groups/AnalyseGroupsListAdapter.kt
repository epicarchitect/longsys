package application.ui.pages.analyse_groups

import android.view.View
import application.controllers.analyse_groups.AnalyseGroupModel
import application.extentions.hide
import application.extentions.show
import application.ui.base.adapters.lists.ModelListAdapter
import kotlinx.android.synthetic.main.analyse_group_item.view.*
import longsys.bb35.extreme.bulk.max.R

class AnalyseGroupsListAdapter : ModelListAdapter<AnalyseGroupModel, AnalyseGroupsListAdapter.Holder>() {

    private var onShowListener: (AnalyseGroupModel) -> Unit = {}

    fun onClickShow(l: (AnalyseGroupModel) -> Unit) {
        onShowListener = l
    }

    override val itemLayout = R.layout.analyse_group_item

    override fun areContentsTheSame(oldItem: AnalyseGroupModel, newItem: AnalyseGroupModel) =
        oldItem.name == newItem.name && oldItem.description == newItem.description

    override fun createViewHolder(view: View) = Holder(view)

    inner class Holder(view: View) : ViewHolder<AnalyseGroupModel>(view) {

        init {
            with(view) {
                buttonShow.setOnClickListener {
                    onShowListener(list[adapterPosition])
                }
            }
        }

        override fun bind(model: AnalyseGroupModel) = with(itemView) {
            tvName.text = model.name

            if (model.description.trim().isEmpty()) {
                tvDescription.hide()
            } else {
                tvDescription.show()
                tvDescription.text = model.description
            }
        }

    }

}