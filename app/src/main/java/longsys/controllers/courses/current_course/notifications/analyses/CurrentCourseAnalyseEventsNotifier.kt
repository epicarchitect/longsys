package longsys.controllers.courses.current_course.notifications.analyses

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import longsys.constants.VOID_ID
import longsys.controllers.course_analyse_events.CourseAnalyseEventModel
import longsys.controllers.course_analyse_events.CourseAnalyseEventsController
import longsys.controllers.courses.current_course.CurrentCourseController
import longsys.controllers.courses.current_course.notifications.analyses.before.CurrentCourseAnalyseEventsBeforeNotifier
import longsys.extentions.isToday
import longsys.extentions.setExactCompat

class CurrentCourseAnalyseEventsNotifier : BroadcastReceiver() {

    override fun onReceive(c: Context?, intent: Intent?) {
        if (c == null || intent == null) return
        val context = c.applicationContext

        val eventId = intent.getIntExtra(EVENT_ID, VOID_ID)
        if (eventId == VOID_ID) return

        val controller = CourseAnalyseEventsController(context)
        val event = controller.getEventById(eventId) ?: return

        val currentCourseId = CurrentCourseController(context).getCurrentCourseId()

        if (currentCourseId == VOID_ID) return

        if (event.courseAnalyseGroup.course.id != currentCourseId) return

        if (!event.isNotified && event.time.isToday()) {
            if (event.courseAnalyseGroup.course.isNotificationsEnabled) {
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

        fun install(context: Context, event: CourseAnalyseEventModel) {
            val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, CurrentCourseAnalyseEventsNotifier::class.java).apply {
                putExtra(EVENT_ID, event.id)
            }
            val pendingIntent = PendingIntent.getBroadcast(context, event.id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            manager.setExactCompat(event.time.timeInMillis, pendingIntent)

            CurrentCourseAnalyseEventsBeforeNotifier.install(context, event)
        }

        fun cancel(context: Context, event: CourseAnalyseEventModel) {
            val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, CurrentCourseAnalyseEventsNotifier::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, event.id, intent, PendingIntent.FLAG_NO_CREATE)
            if (pendingIntent != null) {
                manager.cancel(pendingIntent)
                pendingIntent.cancel()
            }

            CurrentCourseAnalyseEventsBeforeNotifier.cancel(context, event)
        }
    }
}