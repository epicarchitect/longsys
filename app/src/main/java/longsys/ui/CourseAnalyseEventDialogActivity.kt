package longsys.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.course_analyse_event_dialog_activity.*
import longsys.R
import longsys.constants.VOID_ID
import longsys.controllers.course_analyse_events.CourseAnalyseEventModel
import longsys.controllers.course_analyse_events.CourseAnalyseEventsController
import longsys.controllers.course_analyses.CourseAnalyseModel
import longsys.controllers.course_analyses.CourseAnalysesController
import longsys.controllers.settings.SettingsController
import longsys.extentions.*
import longsys.ui.time.DatePicker
import longsys.ui.time.TimePicker
import java.util.*

class CourseAnalyseEventDialogActivity : AppCompatActivity() {

    lateinit var controller: CourseAnalyseEventsController
    lateinit var model: CourseAnalyseEventModel

    val selectedDate = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        val eventId = intent.getIntExtra(EVENT_ID, VOID_ID)
        controller = CourseAnalyseEventsController(this)
        model = controller.getEventById(eventId) ?: CourseAnalyseEventModel()

        if (model.isVoid) {
            finish()
            return
        }

        super.onCreate(savedInstanceState)
        setTheme(SettingsController(this).getSettings().dialogThemeId())
        setContentView(R.layout.course_analyse_event_dialog_activity)
        setViewsByModel()

        buttonTime.setOnClickListener {
            TimePicker(this, selectedDate) { hour, minute ->
                setTime(hour, minute)
                controller.save(
                    model.copy(
                        time = selectedDate,
                        isNotified = false
                    )
                )
            }.show()
        }

        buttonDate.setOnClickListener {
            DatePicker(this, selectedDate) { day, month, year ->
                setDate(day, month, year)
                controller.save(
                    model.copy(
                        time = selectedDate,
                        isNotified = false
                    )
                )
            }.show()
        }

        buttonDone.setOnClickListener {
            buttonDone.disable()
            controller.complete(
                model.copy(
                    time = selectedDate,
                    comment = etComment.text()
                )
            )
            finish()
        }
    }

    fun setViewsByModel() {
        tvTitle.text = model.courseAnalyseGroup.name
        etComment.setText(model.comment)
        model.time.run {
            setTime(hour(), minute())
            setDate(day(), month(), year())
        }
        setAnalyses()
    }

    fun setTime(hour: Int, minute: Int) =
        selectedDate.run {
            hour(hour)
            minute(minute)
            buttonTime.text = timeText()
        }

    fun setDate(day: Int, month: Int, year: Int) =
        selectedDate.run {
            day(day)
            month(month)
            year(year)
            buttonDate.text = dateText()
        }

    fun setAnalyses() {
        val analyses = CourseAnalysesController(this).getCourseAnalyses(model.courseAnalyseGroup.id)

        val list = ArrayList<CourseAnalyseModel>()
        val listNotMandatory = ArrayList<CourseAnalyseModel>()

        analyses.forEach {
            if (it.isMandatory) {
                list.add(it)
            } else {
                listNotMandatory.add(it)
            }
        }

        tvList.text = ""
        var isFirst = true

        list.forEach {
            if (isFirst) {
                isFirst = false
            } else {
                tvList.append("\n")
            }
            tvList.append(" - " + it.analyse.name)
        }

        if (listNotMandatory.isNotEmpty()) {
            tvList.append("\n Не обязательные: ")
            listNotMandatory.forEach {
                tvList.append("\n - " + it.analyse.name)
            }
        }

    }

    companion object {
        const val EVENT_ID = "EVENT_ID"

        fun start(context: Context, eventId: Int) =
            context.startActivity(
                Intent(
                    context,
                    CourseAnalyseEventDialogActivity::class.java
                ).apply {
                    putExtra(EVENT_ID, eventId)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
            )
    }
}