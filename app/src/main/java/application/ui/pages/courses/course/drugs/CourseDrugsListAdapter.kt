package application.ui.pages.courses.course.drugs

import android.annotation.SuppressLint
import android.view.View
import application.controllers.course_drugs.CourseDrugModel
import application.extentions.*
import application.ui.base.adapters.lists.ModelListAdapter
import kotlinx.android.synthetic.main.course_drug_item.view.*
import longsys.bb35.extreme.bulk.max.R
import java.util.*

class CourseDrugsListAdapter : ModelListAdapter<CourseDrugModel, CourseDrugsListAdapter.Holder>() {

    override val itemLayout = R.layout.course_drug_item

    override fun areContentsTheSame(oldItem: CourseDrugModel, newItem: CourseDrugModel) =
        oldItem.drug == newItem.drug
                && oldItem.timeStart.timeInMillis == newItem.timeStart.timeInMillis
                && oldItem.timeEnd.timeInMillis == newItem.timeEnd.timeInMillis
                && oldItem.hour == newItem.hour
                && oldItem.minute == newItem.minute
                && oldItem.activeDays == newItem.activeDays
                && oldItem.stopDays == newItem.stopDays
                && oldItem.count == newItem.count

    override fun createViewHolder(view: View) = Holder(view)

    inner class Holder(view: View) : ViewHolder<CourseDrugModel>(view) {

        @SuppressLint("SetTextI18n")
        override fun bind(model: CourseDrugModel) = with(itemView) {
            model.run {
                tvName.text = drug.name

                val dateStart = timeStart.dateTextWithoutYear()
                val dateEnd = timeEnd.dateTextWithoutYear()

                val time = Calendar.getInstance().run {
                    hour(hour)
                    minute(minute)
                    timeText()
                }

                if (dateStart == dateEnd) {
                    tvInterval.text = "${count.text()} ${application.constants.UnitType.toString(context, drug.unitType)}, $dateStart в $time"
                    tvPeriod.hide()
                } else {
                    tvInterval.text = "С $dateStart по $dateEnd"

                    tvPeriod.show()
                    tvPeriod.text = "${count.text()} ${application.constants.UnitType.toString(context, drug.unitType)}, " + when {
                        activeDays == 1 && stopDays == 0 -> "ежедневно"

                        activeDays == 1 && stopDays == 1 -> "через день"

                        activeDays == 1 -> "день приема, $stopDays перерыва"

                        else -> "дней приема: $activeDays, дней перерыва: $stopDays"
                    } + " в $time"
                }

            }
        }
    }
}