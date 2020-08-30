package longsys.ui.pages.drug_box.drugs

import android.annotation.SuppressLint
import android.view.View
import kotlinx.android.synthetic.main.drug_item.view.*
import longsys.R
import longsys.controllers.drugs.DrugModel
import longsys.ui.base.adapters.lists.ModelListAdapter

class DrugsListAdapter : ModelListAdapter<DrugModel, DrugsListAdapter.Holder>() {

    var onCountClickListener: (model: DrugModel) -> Unit = {}

    fun onCountClick(l: (model: DrugModel) -> Unit) {
        onCountClickListener = l
    }

    override val itemLayout = R.layout.drug_item

    override fun areContentsTheSame(oldItem: DrugModel, newItem: DrugModel) =
        oldItem.name == newItem.name

    override fun createViewHolder(view: View) = Holder(view)

    inner class Holder(view: View) : ViewHolder<DrugModel>(view) {

        init {
            with(view) {
               //buttonCount.setOnClickListener {
               //    onCountClickListener(list[adapterPosition])
               //}
            }
        }

        @SuppressLint("SetTextI18n")
        override fun bind(model: DrugModel) = with(itemView) {
            model.run {
                tvName.text = name
                //buttonCount.text = "$count ${UnitType.toString(context, unitType)}"
            }
        }

    }

}