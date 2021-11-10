package longsys.controllers.courses.current_course.notifications

import android.content.Context
import android.util.Log
import android.widget.Toast
import longsys.controllers.course_analyse_events.CourseAnalyseEventModel
import longsys.controllers.course_analyse_events.CourseAnalyseEventsController
import longsys.controllers.course_drug_events.CourseDrugEventModel
import longsys.controllers.course_drug_events.CourseDrugEventsController
import longsys.controllers.courses.CoursesController
import longsys.controllers.courses.current_course.CurrentCourseController
import longsys.controllers.courses.current_course.notifications.analyses.CurrentCourseAnalyseEventsNotifier
import longsys.controllers.courses.current_course.notifications.drugs.CurrentCourseDrugEventsNotifier

class CurrentCourseNotificationsController private constructor(val context: Context) {

    val drugEventsController = CourseDrugEventsController(context)
    val analyseEventsController = CourseAnalyseEventsController(context)
    val currentCourseController = CurrentCourseController(context)

    init {
        currentCourseController.liveCurrentCourseId.observeForever {
            reinstallNotifications()
        }

        drugEventsController.observer.observe(CourseDrugEventsController.DELETE) { cancel(it) }
        analyseEventsController.observer.observe(CourseAnalyseEventsController.DELETE) { cancel(it) }
    }

    fun install(event: CourseDrugEventModel) {
        runCatching {
            CurrentCourseDrugEventsNotifier.install(context, event)
        }
    }

    fun install(event: CourseAnalyseEventModel) {
        runCatching {
            CurrentCourseAnalyseEventsNotifier.install(context, event)
        }
    }

    fun cancel(event: CourseDrugEventModel) {
        runCatching {
            CurrentCourseDrugEventsNotifier.cancel(context, event)
        }
    }

    fun cancel(event: CourseAnalyseEventModel) {
        runCatching {
            CurrentCourseAnalyseEventsNotifier.cancel(context, event)
        }
    }

    fun reinstallNotifications() {
        val currentCourseId = CurrentCourseController(context).getCurrentCourseId()
        CoursesController(context).getCourseById(currentCourseId)?.run {
            val drugEvents = drugEventsController.getEvents(id, timeStart.timeInMillis, timeEnd.timeInMillis)
                    .filter { !it.isNotified && !it.isCompleted }

            val analyseEvents = analyseEventsController.getEvents(id, timeStart.timeInMillis, timeEnd.timeInMillis)
                    .filter { !it.isNotified && !it.isCompleted }

            var installed = 0

            analyseEvents.forEach {
                if (installed < 100) {
                    installed++
                    install(it)
                }
            }

            drugEvents.forEach {
                if (installed < 400) {
                    installed++
                    install(it)
                }
            }
        }
    }

    companion object {
        var instance: CurrentCourseNotificationsController? = null

        operator fun invoke(context: Context) =
            instance ?: CurrentCourseNotificationsController(context.applicationContext).also {
                instance = it
            }
    }
}