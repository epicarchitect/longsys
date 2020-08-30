package longsys.extentions

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.res.Resources
import android.os.Build
import kotlin.math.ceil


fun AlarmManager.setExactCompat(time: Long, pendingIntent: PendingIntent) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent)
    } else {
        setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent)
    }
}

fun Double.text() = if (this == ceil(this)) toInt().toString() else toString()

fun Any.dp(dp: Float) = Resources.getSystem().displayMetrics.density * dp