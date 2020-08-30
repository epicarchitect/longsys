package longsys.controllers.restarting

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import longsys.controllers.courses.current_course.notifications.CurrentCourseNotificationsController

class RestartWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        CurrentCourseNotificationsController(applicationContext).reinstallNotifications()
        return Result.success()
    }

}