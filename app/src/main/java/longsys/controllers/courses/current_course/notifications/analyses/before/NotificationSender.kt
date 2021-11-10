package longsys.controllers.courses.current_course.notifications.analyses.before

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import longsys.R
import longsys.controllers.course_analyse_events.CourseAnalyseEventModel
import longsys.controllers.courses.current_course.notifications.analyses.OnClickReceiver
import longsys.extentions.COUNT_MILLIS_IN_DAY
import longsys.extentions.COUNT_MILLIS_IN_HOUR
import longsys.extentions.copy
import longsys.extentions.dateAndTimeText

class NotificationSender(val context: Context) {

    val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun send(event: CourseAnalyseEventModel) {
        createChannelAndGroup()

        val pendingIntentOnClick = PendingIntent.getBroadcast(
            context,
            event.id,
            Intent(context, OnClickReceiver::class.java).apply {
                putExtra(OnClickReceiver.EVENT_ID, event.id)
            },
            PendingIntent.FLAG_CANCEL_CURRENT.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    it or PendingIntent.FLAG_IMMUTABLE
                else it
            }
        )

        val startDay = event.time.copy {
            timeInMillis -= COUNT_MILLIS_IN_DAY * event.courseAnalyseGroup.course.daysBeforeNotifyAnalyse
        }.timeInMillis

        val endDay = event.time.timeInMillis

        val days = ((endDay - startDay) / COUNT_MILLIS_IN_DAY).toInt()
        val hours = (endDay - startDay) % COUNT_MILLIS_IN_HOUR

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setDefaults(Notification.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setChannelId(CHANNEL_ID)
            .setGroup(GROUP_NAME)
            .setContentTitle("Сдача анализов ${event.time.dateAndTimeText()}")
            .setContentText(event.courseAnalyseGroup.name)
            .setContentIntent(pendingIntentOnClick)
            .setSmallIcon(R.drawable.ic_notifications)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    context.resources,
                    R.mipmap.ic_launcher
                )
            )
        manager.notify(event.id, builder.build())
    }

    private fun createChannelAndGroup() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
            createGroup()
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
        channel.enableLights(true)
        channel.lightColor = Color.RED
        channel.description = CHANNEL_DESCRIPTION
        channel.enableVibration(true)
        channel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
        channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        manager.createNotificationChannel(channel)
    }

    private fun createGroup() {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentInfo(CHANNEL_NAME)
            .setSmallIcon(R.drawable.ic_notifications)
            .setChannelId(CHANNEL_ID)
            .setGroup(GROUP_NAME)
            .setGroupSummary(true)
        manager.notify(GROUP_ID, builder.build())
    }

    companion object {
        const val GROUP_ID = 3
        const val CHANNEL_NAME = "Before Analyses"
        const val CHANNEL_ID = "Before Analyses"
        const val CHANNEL_DESCRIPTION = "Before Analyses"
        const val GROUP_NAME = "Before Analyses"
    }
}