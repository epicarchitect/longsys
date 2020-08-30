package application

import android.app.Application
import android.os.Handler
import android.util.Log
import application.controllers.analyse_groups.AnalyseGroupsController
import application.controllers.analyses.AnalysesController
import application.controllers.course_analyse_events.CourseAnalyseEventsController
import application.controllers.course_analyse_groups.CourseAnalyseGroupsController
import application.controllers.course_analyses.CourseAnalysesController
import application.controllers.course_drug_events.CourseDrugEventsController
import application.controllers.course_drugs.CourseDrugsController
import application.controllers.courses.CoursesController
import application.controllers.courses.current_course.CurrentCourseController
import application.controllers.courses.current_course.notifications.CurrentCourseNotificationsController
import application.controllers.drug_groups.DrugGroupsController
import application.controllers.drugs.DrugsController
import application.controllers.events.EventsController
import application.controllers.restarting.RestartController
import application.controllers.settings.SettingsController
import java.util.concurrent.Executors

class App : Application() {

    var mainHandler: Handler? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        mainHandler = Handler(mainLooper)

        AnalyseGroupsController(this)
        AnalysesController(this)

        CourseAnalyseEventsController(this)
        CourseAnalyseGroupsController(this)
        CourseAnalysesController(this)

        CourseDrugEventsController(this)
        CourseDrugsController(this)

        CoursesController(this)
        CurrentCourseController(this)
        CurrentCourseNotificationsController(this)

        DrugGroupsController(this)
        DrugsController(this)

        EventsController(this)
        RestartController(this)
        SettingsController(this)
    }

    companion object {
        val executor = Executors.newSingleThreadExecutor()
        var instance: App? = null

        fun log(s: String) {
            Log.d("__APP__", s)
        }

        fun async(l: () -> Unit) {
            executor.execute(l)
        }

        fun runOnUi(l: () -> Unit) {
            instance?.mainHandler?.post(l)
        }
    }
}