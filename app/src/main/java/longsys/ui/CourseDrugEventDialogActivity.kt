package longsys.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.course_drug_event_dialog_activity.*
import longsys.R
import longsys.constants.UnitType
import longsys.constants.VOID_ID
import longsys.controllers.course_drug_events.CourseDrugEventModel
import longsys.controllers.course_drug_events.CourseDrugEventsController
import longsys.controllers.settings.SettingsController
import longsys.extentions.*
import longsys.ui.time.DatePicker
import longsys.ui.time.TimePicker
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