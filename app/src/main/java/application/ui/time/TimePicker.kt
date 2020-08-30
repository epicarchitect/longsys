package application.ui.time

import android.app.Activity
import android.app.AlertDialog
import android.app.TimePickerDialog
import application.controllers.settings.SettingsController
import application.extentions.attr
import application.extentions.hour
import application.extentions.minute
import longsys.bb35.extreme.bulk.max.R
import java.util.*

class TimePicker {

    companion object {

        operator fun invoke(activity: Activity, hour: Int, minute: Int, l: ((hour: Int, minute: Int) -> Unit)) =
            TimePickerDialog(
                activity,
                SettingsController(activity).getSettings().pickerDialogThemeId(),
                TimePickerDialog.OnTimeSetListener { _, h, m -> l(h, m) },
                hour,
                minute,
                true
            ).apply {
                setOnShowListener {
                    setTitle("Выберите время")
                    getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(activity.attr(R.attr.colorAccent))
                    getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(activity.attr(R.attr.colorAccent))
                }
            }

        operator fun invoke(activity: Activity, calendar: Calendar, l: ((hour: Int, minute: Int) -> Unit)) =
            invoke(activity, calendar.hour(), calendar.minute(), l)

        operator fun invoke(activity: Activity, l: ((hour: Int, minute: Int) -> Unit)): TimePickerDialog {
            val c = Calendar.getInstance()
            return invoke(activity, c.hour(), c.minute(), l)
        }

    }

}