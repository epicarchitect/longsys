package longsys.ui.pages.drug_box.groups

import android.view.View
import kotlinx.android.synthetic.main.drug_group_item.view.*
import longsys.R
import longsys.controllers.drug_groups.DrugGroupModel
import longsys.ui.base.adapters.lists.ModelListAdapter

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