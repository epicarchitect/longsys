package longsys.ui.pages.courses.current_course

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.course_creation_fragment.*
import longsys.R
import longsys.constants.COUNT_DAYS_IN_COURSE
import longsys.controllers.courses.CourseModel
import longsys.extentions.*
import longsys.ui.time.DatePicker
import java.util.*

class CourseCreationFragment(val onStartListener: (CourseModel) -> Unit = {}) : Fragment() {

    val timeStart = Calendar.getInstance()
    val timeEnd = Calendar.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonDateStart.setOnClickListener {
            activity?.let {
                val picker = DatePicker(it, timeStart, this::setDateStart)
                picker.datePicker.minDate = Calendar.getInstance().timeInMillis
                picker.show()
            }
        }

        buttonDateEnd.setOnClickListener {
            activity?.let {
                val picker = DatePicker(it, timeEnd, this::setDateEnd)
                picker.datePicker.minDate = timeStart.timeInMillis + COUNT_MILLIS_IN_DAY * COUNT_DAYS_IN_COURSE
                picker.show()
            }
        }

        buttonStart.setOnClickListener {
            onStartListener(
                CourseModel(
                    timeStart = timeStart,
                    timeEnd = timeEnd
                )
            )
            buttonStart.disable()
        }

        buttonDateStart.text = timeStart.dateText()
        val c = Calendar.getInstance()
        c.timeInMillis = timeStart.timeInMillis + COUNT_MILLIS_IN_DAY * COUNT_DAYS_IN_COURSE
        setDateEnd(c.day(), c.month(), c.year())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.course_creation_fragment, container, false)

    @SuppressLint("SetTextI18n")
    fun setDateStart(day: Int, month: Int, year: Int) =
        timeStart.run {
            day(day)
            month(month)
            year(year)
            buttonDateStart.text = dateText()

            val c = Calendar.getInstance()
            c.timeInMillis = timeInMillis + COUNT_MILLIS_IN_DAY * COUNT_DAYS_IN_COURSE
            setDateEnd(c.day(), c.month(), c.year())
        }

    @SuppressLint("SetTextI18n")
    fun setDateEnd(day: Int, month: Int, year: Int) =
        timeEnd.run {
            day(day)
            month(month)
            year(year)
            buttonDateEnd.text = dateText()
        }


}