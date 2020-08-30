package longsys.ui.pages.courses.course.plan

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.event_date_item.view.*
import kotlinx.android.synthetic.main.event_item.view.*
import longsys.R
import longsys.constants.UnitType
import longsys.controllers.course_analyse_events.CourseAnalyseEventModel
import longsys.controllers.course_drug_events.CourseDrugEventModel
import longsys.controllers.events.EventModel
import longsys.extentions.*
import longsys.ui.MainActivity
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class EventListAdapter : RecyclerView.Adapter<EventListAdapter.Holder<*>>() {

    fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        if (oldItem is Calendar && newItem is Calendar)
            return oldItem.timeInMillis == newItem.timeInMillis

        if (oldItem is EventModel && newItem is EventModel)
            return oldItem.id == newItem.id

        return false
    }

    fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        if (oldItem is Calendar && newItem is Calendar)
            return oldItem.timeInMillis == newItem.timeInMillis

        if (oldItem is EventModel && newItem is EventModel)
            return oldItem.type == newItem.type
                    && oldItem.time.timeText() == newItem.time.timeText()

        return false
    }

    inner class DiffCallback(val oldList: List<Any>, val newList: List<Any>) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldPosition: Int, newPosition: Int) =
            areItemsTheSame(oldList[oldPosition], newList[newPosition])

        override fun areContentsTheSame(oldPosition: Int, newPosition: Int) =
            areContentsTheSame(oldList[oldPosition], newList[newPosition])

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

    }

    private var onClickListener: (model: EventModel, position: Int) -> Unit = { _, _ -> }

    fun onClick(l: (model: EventModel, position: Int) -> Unit) {
        onClickListener = l
    }

    private var list = ArrayList<Any>()

    fun setEvents(events: List<EventModel>, withDates: Boolean = true) {
        val eventsOfDays = getEventsOfDays(events)

        val calendars = eventsOfDays.keys.map {
            calendarByTime(it)
        }.sortedBy {
            it.timeInMillis
        }

        val newList = ArrayList<Any>()

        calendars.forEach { calendar ->
            eventsOfDays[calendar.timeInMillis]?.run {
                if (!isEmpty()) {
                    if (withDates)
                        newList.add(calendar)
                    sortBy { it.time.timeInMillis }
                    map { newList.add(it) }
                }
            }
        }

        val diffCallback = DiffCallback(list, newList)
        val result = DiffUtil.calculateDiff(diffCallback)
        list = newList
        result.dispatchUpdatesTo(this)
    }

    fun getEventsOfDays(events: List<EventModel>): HashMap<Long, ArrayList<EventModel>> {
        val eventsOfDays = HashMap<Long, ArrayList<EventModel>>()
        var lastDay = Calendar.getInstance().apply { timeInMillis = 0 }
        events.forEach {
            if (!calendarsEqualsByDay(lastDay, it.time)) {
                lastDay = calendarWithoutHours(it.time.timeInMillis)
            }
            val list = eventsOfDays[lastDay.timeInMillis] ?: ArrayList()
            list.add(it)
            eventsOfDays[lastDay.timeInMillis] = list
        }
        return eventsOfDays
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder<*> {
        fun layout(res: Int) =
            LayoutInflater.from(parent.context).inflate(res, parent, false)

        return when (viewType) {
            TYPE_DATE -> DateHolder(layout(R.layout.event_date_item))
            else -> EventHolder(layout(R.layout.event_item))
        }
    }

    override fun onBindViewHolder(holder: Holder<*>, position: Int) {
        when (val model = list[position]) {
            is Calendar -> (holder as DateHolder).bind(model)
            is EventModel -> (holder as EventHolder).bind(model)
        }
    }

    fun getTodayPosition(): Int {
        var position = 0
        list.forEach {
            if (it is Calendar && it.isToday()) {
                return position
            }
            position++
        }
        return 0
    }


    inner class DateHolder(view: View) : Holder<Calendar>(view) {
        override fun bind(model: Calendar) = with(itemView) {
            tvEventDate.text = model.dateText()

            if (model.isToday()) {
                tvToday.show()
                itemView.setBackgroundColor(MainActivity.instance!!.attr(R.attr.colorAccent))
            } else {
                tvToday.hide()
                itemView.setBackgroundColor(Color.TRANSPARENT)
            }
        }
    }


    inner class EventHolder(view: View) : Holder<EventModel>(view) {

        init {
            view.setOnClickListener {
                onClickListener(list[adapterPosition] as EventModel, adapterPosition)
            }
        }

        @SuppressLint("SetTextI18n")
        override fun bind(model: EventModel) = with(itemView) {
            model.run {
                when (type) {
                    is CourseDrugEventModel -> type.run {
                        tvName.text = courseDrug.drug.name
                        tvDescription.text = count.text() + " " + UnitType.toString(context, courseDrug.drug.unitType)

                        iconDone.visibility =
                            if (isCompleted) {
                                View.VISIBLE
                            } else {
                                View.GONE
                            }
                    }

                    is CourseAnalyseEventModel -> type.run {
                        tvName.text = courseAnalyseGroup.name
                        tvDescription.text =
                            if (comment.trim().isEmpty()) {
                                "Сдача анализов"
                            } else {
                                type.comment
                            }

                        iconDone.visibility =
                            if (isCompleted) {
                                View.VISIBLE
                            } else {
                                View.GONE
                            }
                    }
                }
                tvTime.text = time.timeText()
            }
        }

    }

    abstract inner class Holder<M>(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(model: M)
    }

    override fun getItemViewType(position: Int) =
        when (list[position]) {
            is Calendar -> TYPE_DATE
            else -> TYPE_EVENT
        }

    override fun getItemCount() = list.size

    companion object {
        const val TYPE_DATE = 1
        const val TYPE_EVENT = 0
    }
}