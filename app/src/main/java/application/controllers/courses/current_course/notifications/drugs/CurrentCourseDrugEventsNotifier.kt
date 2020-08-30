package application.controllers.courses.current_course.notifications.drugs

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import application.constants.VOID_ID
import application.controllers.course_drug_events.CourseDrugEventModel
import application.controllers.course_drug_events.CourseDrugEventsController
import application.controllers.courses.current_course.CurrentCourseController
import application.extentions.isToday
import application.extentions.setExactCompat

class CurrentCourseDrugEventsNotifier : BroadcastReceiver() {

    override fun onReceive(c: Context?, intent: Intent?) {
        if (c == null || intent == null) return
        val context = c.applicationContext

        val eventId = intent.getIntExtra(EVENT_ID, VOID_ID)
        if (eventId == VOID_ID) return

        val controller = CourseDrugEventsController(context)
        val event = controller.getEventById(eventId) ?: return

        val currentCourseId = CurrentCourseController(context).getCurrentCourseId()

        if (currentCourseId == VOID_ID) return

        if (event.courseDrug.course.id != currentCourseId) return

        if (!event.isNotified && event.time.isToday()) {
            if (event.courseDrug.course.isNotificationsEnabled) {
                NotificationSender(context).send(event)
            }

            controller.save(
                event.copy(
                    isNotified = true
                )
            )
        }
    }

    companion object {
        const val EVENT_ID = "EVENT_ID"

        fun install(context: Context, event: CourseDrugEventModel) {
            val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, CurrentCourseDrugEventsNotifier::class.java).apply {
                putExtra(EVENT_ID, event.id)
            }
            val pendingIntent = PendingIntent.getBroadcast(context, event.id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            manager.setExactCompat(event.time.timeInMillis, pendingIntent)
        }

        fun cancel(context: Context, event: CourseDrugEventModel) {
            val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, CurrentCourseDrugEventsNotifier::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, event.id, intent, PendingIntent.FLAG_NO_CREATE)
            if (pendingIntent != null) {
                manager.cancel(pendingIntent)
                pendingIntent.cancel()
            }
        }
    }
}