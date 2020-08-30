package longsys.controllers.courses.current_course.notifications.drugs

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import longsys.constants.VOID_ID
import longsys.controllers.course_drug_events.CourseDrugEventsController
import longsys.extentions.copy
import java.util.*

class PutOffReceiver : BroadcastReceiver() {

    override fun onReceive(c: Context?, intent: Intent?) {
        if (c == null || intent == null) return

        val eventId = intent.getIntExtra(EVENT_ID, VOID_ID)

        val manager = c.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.cancel(eventId)

        if (eventId == VOID_ID) return

        val controller = CourseDrugEventsController(c)
        controller.getEventById(eventId)?.let { event ->
            controller.save(
                event.copy(
                    time = Calendar.getInstance().copy {
                        timeInMillis += 1000 * 60 * 20 // + 20 minute
                    },
                    isNotified = false
                )
            )
        }
    }

    companion object {
        const val EVENT_ID = "event_id"
    }
}