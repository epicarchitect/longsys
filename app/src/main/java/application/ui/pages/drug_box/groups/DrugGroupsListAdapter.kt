package application.ui.pages.drug_box.groups

import android.view.View
import application.controllers.drug_groups.DrugGroupModel
import application.ui.base.adapters.lists.ModelListAdapter
import kotlinx.android.synthetic.main.drug_group_item.view.*
import longsys.bb35.extreme.bulk.max.R

class DrugGroupsListAdapter : ModelListAdapter<DrugGroupModel, DrugGroupsListAdapter.Holder>() {

    private var onShowListener: (DrugGroupModel) -> Unit = {}

    fun onClickShow(l: (DrugGroupModel) -> Unit) {
        onShowListener = l
    }

    override val itemLayout = R.layout.drug_group_item

    override fun areContentsTheSame(oldItem: DrugGroupModel, newItem: DrugGroupModel) =
        oldItem.name == newItem.name

    override fun createViewHolder(view: View) = Holder(view)

    inner class Holder(view: View) : ViewHolder<DrugGroupModel>(view) {

        init {
            with(view) {
                buttonShow.setOnClickListener {
                    onShowListener(list[adapterPosition])
                }
            }
        }

        override fun bind(model: DrugGroupModel) = with(itemView) {
            tvName.text = model.name
        }
    }
}