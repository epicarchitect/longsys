package application.ui.time

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import application.controllers.settings.SettingsController
import application.extentions.attr
import application.extentions.day
import application.extentions.month
import application.extentions.year
import longsys.bb35.extreme.bulk.max.R
import java.util.*

class DatePicker {

    companion object {

        operator fun invoke(activity: Activity, day: Int, month: Int, year: Int, l: ((day: Int, month: Int, year: Int) -> Unit)) =
            DatePickerDialog(
                activity,
                SettingsController(activity).getSettings().pickerDialogThemeId(),
                DatePickerDialog.OnDateSetListener { _, y, m, d ->  l(d, m, y) },
                year,
                month,
                day
            ).apply {
                setOnShowListener {
                    setTitle("Выберите дату")
                    getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(activity.attr(R.attr.colorAccent))
                    getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(activity.attr(R.attr.colorAccent))
                }
            }

        operator fun invoke(activity: Activity, calendar: Calendar, l: ((day: Int, month: Int, year: Int) -> Unit)) =
            invoke(
                activity,
                calendar.day(),
                calendar.month(),
                calendar.year(),
                l
            )

        operator fun invoke(activity: Activity, l: ((day: Int, month: Int, year: Int) -> Unit)): DatePickerDialog {
            val c = Calendar.getInstance()
            return invoke(activity, c.day(), c.month(), c.year(), l)
        }

    }

}