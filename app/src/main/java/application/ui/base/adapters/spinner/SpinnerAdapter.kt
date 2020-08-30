package application.ui.base.adapters.spinner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.base_spinner_item.view.*
import longsys.bb35.extreme.bulk.max.R

class SpinnerAdapter(context: Context, list: List<SpinnerItem>) : ArrayAdapter<SpinnerItem>(context, 0, list) {

    override fun getView(position: Int, v: View?, parent: ViewGroup) = buildView(position, v, parent)

    override fun getDropDownView(position: Int, v: View?, parent: ViewGroup) = buildView(position, v, parent)

    fun buildView(position: Int, view: View?, parent: ViewGroup): View {
        val v = view ?: LayoutInflater.from(context).inflate(R.layout.base_spinner_item, parent, false)
        val item = getItem(position)
        with(v) {
            tvContent.text = item?.content ?: ""
        }
        return v
    }

}