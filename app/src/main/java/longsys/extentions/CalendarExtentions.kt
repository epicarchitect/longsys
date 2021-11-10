package longsys.extentions

import android.annotation.SuppressLint
import android.text.format.DateFormat
import longsys.ui.MainActivity
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.*

const val COUNT_MILLIS_IN_DAY: Long = 1000 * 60 * 60 * 24
const val COUNT_MILLIS_IN_HOUR: Long = 1000 * 60 * 60

fun Calendar.millisecond() = get(MILLISECOND)
fun Calendar.second() = get(SECOND)
fun Calendar.minute() = get(MINUTE)
fun Calendar.hour() = get(HOUR_OF_DAY)
fun Calendar.day() = get(DAY_OF_MONTH)
fun Calendar.month() = get(MONTH)
fun Calendar.year() = get(YEAR)


fun Calendar.millisecond(value: Int) = set(MILLISECOND, value)
fun Calendar.second(value: Int) = set(SECOND, value)
fun Calendar.minute(value: Int) = set(MINUTE, value)
fun Calendar.hour(value: Int) = set(HOUR_OF_DAY, value)
fun Calendar.day(value: Int) = set(DAY_OF_MONTH, value)
fun Calendar.month(value: Int) = set(MONTH, value)
fun Calendar.year(value: Int) = set(YEAR, value)

@SuppressLint("SimpleDateFormat")
fun Calendar.timeText(): String {
    var is24 = true

    MainActivity.instance?.let {
        is24 = DateFormat.is24HourFormat(it)
    }

    val format = SimpleDateFormat(if (is24) "HH:mm" else "hh:mm:a")
    return format.format(time)
}

@SuppressLint("SimpleDateFormat")
fun Calendar.dateTextWithoutYear(): String {
    val format = SimpleDateFormat("d MMMM")
    return format.format(time)
}

@SuppressLint("SimpleDateFormat")
fun Calendar.dateText(): String {
    val format = SimpleDateFormat("d MMMM")
    return format.format(time)
}

//@SuppressLint("SimpleDateFormat")
//fun Calendar.dateText(): String {
//    val format = SimpleDateFormat("d MMMМ yyyy")
//    return format.format(time)
//}

fun timeWithoutSeconds(time: Long) = time - time % (1000 * 60)

fun Calendar.dateAndTimeText() = dateText() + ", " + timeText()

fun Calendar.copy(block: Calendar.() -> Unit = {}): Calendar {
    val c = getInstance()
    c.timeInMillis = timeInMillis
    return c.apply(block)
}

fun calendarWithoutHours(time: Long) =
    calendarByTime(time).apply {
        hour(0)
        minute(0)
        second(0)
        millisecond(0)
    }

fun calendarsEqualsByDay(c1: Calendar, c2: Calendar) =
    c1.day() == c2.day() && c1.month() == c2.month() && c1.year() == c2.year()

fun timesEqualsByDay(t1: Long, t2: Long) =
    calendarsEqualsByDay(
        calendarByTime(t1),
        calendarByTime(t2)
    )

fun calendarByTime(t: Long) = getInstance().apply { timeInMillis = t }

fun Calendar.isToday(): Boolean {
    val startDay = copy {
        millisecond(0)
        second(0)
        minute(0)
        hour(0)
    }.timeInMillis

    val endDay = copy {
        millisecond(999)
        second(59)
        minute(59)
        hour(23)
    }.timeInMillis

    val today = System.currentTimeMillis()

    return today in startDay until endDay
}

fun calendarEndOfToday() = Calendar.getInstance().apply {
    millisecond(999)
    second(59)
    minute(59)
    hour(23)
}