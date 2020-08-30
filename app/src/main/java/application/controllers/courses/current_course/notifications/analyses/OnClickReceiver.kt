package application.controllers.courses.current_course.notifications.analyses

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import application.constants.VOID_ID
import application.ui.CourseAnalyseEventDialogActivity

class OnClickReceiver : BroadcastReceiver() {

    override fun onReceive(c: Context?, intent: Intent?) {
        if (c == null || intent == null) return

        val eventId = intent.getIntExtra(EVENT_ID, VOID_ID)

        if (eventId == VOID_ID) return
        
        CourseAnalyseEventDialogActivity.start(c, eventId)
    }

    companion object {
        const val EVENT_ID = "event_id"
    }
}