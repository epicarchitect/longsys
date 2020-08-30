package application.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import application.constants.UnitType
import application.constants.VOID_ID
import application.controllers.course_drug_events.CourseDrugEventModel
import application.controllers.course_drug_events.CourseDrugEventsController
import application.controllers.settings.SettingsController
import application.extentions.*
import application.ui.time.DatePicker
import application.ui.time.TimePicker
import kotlinx.android.synthetic.main.course_drug_event_dialog_activity.*
import longsys.bb35.extreme.bulk.max.R
import java.util.*

class CourseDrugEventDialogActivity : AppCompatActivity() {

    lateinit var controller: CourseDrugEventsController
    lateinit var model: CourseDrugEventModel

    val selectedDate = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        val eventId = intent.getIntExtra(EVENT_ID, VOID_ID)
        controller = CourseDrugEventsController(this)
        model = controller.getEventById(eventId) ?: CourseDrugEventModel()

        if (model.isVoid) {
            finish()
            return
        }

        super.onCreate(savedInstanceState)
        setTheme(SettingsController(this).getSettings().dialogThemeId())
        setContentView(R.layout.course_drug_event_dialog_activity)
        setViewsByModel()

        etCount.removeDoubleZeroOnClick()
        etCountLayout.hint = "Дозировка (" + UnitType.toString(this, model.courseDrug.drug.unitType, true) + ")"

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
                    count = etCount.double()
                )
            )
            finish()
        }
    }

    fun setViewsByModel() =
        model.run {
            tvTitle.text = courseDrug.drug.name
            etCount.setText(count.text())
            time.run {
                setTime(hour(), minute())
                setDate(day(), month(), year())
            }
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

    companion object {
        const val EVENT_ID = "EVENT_ID"

        fun start(context: Context, eventId: Int) =
            context.startActivity(
                Intent(
                    context,
                    CourseDrugEventDialogActivity::class.java
                ).apply {
                    putExtra(EVENT_ID, eventId)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
            )
    }
}