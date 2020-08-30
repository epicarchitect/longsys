package application.controllers.courses.current_course.notifications

import android.content.Context
import application.controllers.course_analyse_events.CourseAnalyseEventModel
import application.controllers.course_analyse_events.CourseAnalyseEventsController
import application.controllers.course_drug_events.CourseDrugEventModel
import application.controllers.course_drug_events.CourseDrugEventsController
import application.controllers.courses.CoursesController
import application.controllers.courses.current_course.CurrentCourseController
import application.controllers.courses.current_course.notifications.analyses.CurrentCourseAnalyseEventsNotifier
import application.controllers.courses.current_course.notifications.drugs.CurrentCourseDrugEventsNotifier

class CurrentCourseNotificationsController private constructor(val context: Context) {

    val drugEventsController = CourseDrugEventsController(context)
    val analyseEventsController = CourseAnalyseEventsController(context)

    init {
        drugEventsController.observer
            .observe(CourseDrugEventsController.SAVE) { install(it) }
            .observe(CourseDrugEventsController.DELETE) { cancel(it) }

        analyseEventsController.observer
            .observe(CourseAnalyseEventsController.SAVE) { install(it) }
            .observe(CourseAnalyseEventsController.DELETE) { cancel(it) }
    }

    fun install(event: CourseDrugEventModel) =
        CurrentCourseDrugEventsNotifier.install(context, event)

    fun install(event: CourseAnalyseEventModel) =
        CurrentCourseAnalyseEventsNotifier.install(context, event)

    fun cancel(event: CourseDrugEventModel) =
        CurrentCourseDrugEventsNotifier.cancel(context, event)

    fun cancel(event: CourseAnalyseEventModel) =
        CurrentCourseAnalyseEventsNotifier.cancel(context, event)

    fun reinstallNotifications() {
        val currentCourseId = CurrentCourseController(context).getCurrentCourseId()
        CoursesController(context).getCourseById(currentCourseId)?.run {
            val drugEvents = drugEventsController.getEvents(id, timeStart.timeInMillis, timeEnd.timeInMillis)
            val analyseEvents = analyseEventsController.getEvents(id, timeStart.timeInMillis, timeEnd.timeInMillis)

            drugEvents.forEach { install(it) }
            analyseEvents.forEach { install(it) }
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