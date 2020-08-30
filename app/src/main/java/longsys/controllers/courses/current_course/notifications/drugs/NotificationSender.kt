package longsys.controllers.courses.current_course.notifications.drugs

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
import longsys.constants.UnitType
import longsys.controllers.course_drug_events.CourseDrugEventModel
import longsys.extentions.text
import longsys.extentions.timeText

class NotificationSender(val context: Context) {

    val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun send(event: CourseDrugEventModel) {
        createChannelAndGroup()

        val pendingIntentOnClick = PendingIntent.getBroadcast(
            context,
            event.id,
            Intent(context, OnClickReceiver::class.java).apply {
                putExtra(OnClickReceiver.EVENT_ID, event.id)
            },
            PendingIntent.FLAG_CANCEL_CURRENT
        )

        val pendingIntentOnDone = PendingIntent.getBroadcast(
            context,
            event.id,
            Intent(context, DoneReceiver::class.java).apply {
                putExtra(DoneReceiver.EVENT_ID, event.id)
            },
            PendingIntent.FLAG_CANCEL_CURRENT
        )

        val pendingIntentOnPutOff = PendingIntent.getBroadcast(
            context,
            event.id,
            Intent(context, PutOffReceiver::class.java).apply {
                putExtra(PutOffReceiver.EVENT_ID, event.id)
            },
            PendingIntent.FLAG_CANCEL_CURRENT
        )


        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setDefaults(Notification.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setChannelId(CHANNEL_ID)
            .setGroup(GROUP_NAME)
            .setContentTitle("Принять препарат в ${event.time.timeText()}")
            .addAction(R.drawable.ic_time, "20мин. позже", pendingIntentOnPutOff)
            .addAction(R.drawable.ic_done, "Принято", pendingIntentOnDone)
            .setContentText(event.courseDrug.drug.name + " " + event.count.text() + " " + UnitType.toString(context, event.courseDrug.drug.unitType))
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
    private fun createChannel() =
        NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH).apply {
            enableLights(true)
            lightColor = Color.RED
            description = CHANNEL_DESCRIPTION
            enableVibration(true)
            vibrationPattern = longArrayOf(0, 1000, 500, 1000)
            lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            manager.createNotificationChannel(this)
        }

    private fun createGroup() =
        manager.notify(
            GROUP_ID,
            NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentInfo(CHANNEL_NAME)
                .setSmallIcon(R.drawable.ic_notifications)
                .setChannelId(CHANNEL_ID)
                .setGroup(GROUP_NAME)
                .setGroupSummary(true)
                .build()
        )

    companion object {
        const val GROUP_ID = 1
        const val CHANNEL_NAME = "Drugs"
        const val CHANNEL_ID = "Drugs"
        const val CHANNEL_DESCRIPTION = "Drugs"
        const val GROUP_NAME = "Drugs"
    }
}

