package longsys

import android.app.Application
import android.os.Handler
import android.util.Log
import longsys.controllers.analyse_groups.AnalyseGroupsController
import longsys.controllers.analyses.AnalysesController
import longsys.controllers.course_analyse_events.CourseAnalyseEventsController
import longsys.controllers.course_analyse_groups.CourseAnalyseGroupsController
import longsys.controllers.course_analyses.CourseAnalysesController
import longsys.controllers.course_drug_events.CourseDrugEventsController
import longsys.controllers.course_drugs.CourseDrugsController
import longsys.controllers.courses.CoursesController
import longsys.controllers.courses.current_course.CurrentCourseController
import longsys.controllers.courses.current_course.notifications.CurrentCourseNotificationsController
import longsys.controllers.drug_groups.DrugGroupsController
import longsys.controllers.drugs.DrugsController
import longsys.controllers.events.EventsController
import longsys.controllers.restarting.RestartController
import longsys.controllers.settings.SettingsController
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