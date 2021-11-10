package longsys.ui.pages.today

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.today_fragment.*
import longsys.R
import longsys.controllers.course_analyse_events.CourseAnalyseEventModel
import longsys.controllers.course_analyse_events.CourseAnalyseEventsController
import longsys.controllers.course_drug_events.CourseDrugEventModel
import longsys.controllers.course_drug_events.CourseDrugEventsController
import longsys.controllers.courses.current_course.CurrentCourseController
import longsys.controllers.events.EventsController
import longsys.extentions.*
import longsys.ui.pages.MainFragment
import longsys.ui.pages.courses.course.plan.EventListAdapter
import longsys.ui.pages.courses.course.plan.course_analyse_events.CourseAnalyseEventDialog
import longsys.ui.pages.courses.course.plan.course_drug_events.CourseDrugEventDialog
import java.util.*

@Suppress("NON_EXHAUSTIVE_WHEN")
class TodayFragment : Fragment() {

    lateinit var eventsController: EventsController
    lateinit var drugsController: CourseDrugEventsController
    lateinit var analysesController: CourseAnalyseEventsController
    lateinit var currentCourseController: CurrentCourseController

    val startDay = Calendar.getInstance().apply {
        hour(0)
        minute(0)
        second(0)
        millisecond(0)
    }

    val endDay = Calendar.getInstance().apply {
        hour(23)
        minute(59)
        second(59)
        millisecond(999)
    }

    val listAdapter = EventListAdapter().apply {
        onClick { event, _ ->
            when (event.type) {
                is CourseAnalyseEventModel ->
                    CourseAnalyseEventDialog.build(event.type).show(parentFragmentManager, "")

                is CourseDrugEventModel ->
                    CourseDrugEventDialog.build(event.type).show(parentFragmentManager, "")
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.today_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retainInstance = true
        eventsController = EventsController(app())
        drugsController = CourseDrugEventsController(app())
        analysesController = CourseAnalyseEventsController(app())
        currentCourseController = CurrentCourseController(app())

        rvEvents.layoutManager = LinearLayoutManager(context)
        rvEvents.adapter = listAdapter

        buttonShowCreateCourse.setOnClickListener {
            MainFragment.showMyCourse()
        }

        currentCourseController.liveCurrentCourse.observe(viewLifecycleOwner, Observer { course ->
            if (course == null) {
                courseNotCreatedLayout.show()
                onEmptyLayout.hide()
                eventsLayout.hide()
            } else {
                courseNotCreatedLayout.hide()
                eventsController.getLiveEvents(course.id, startDay.timeInMillis, endDay.timeInMillis)
                    .observe(viewLifecycleOwner, Observer { events ->
                        listAdapter.setEvents(events, false)
                        if (events.isEmpty()) {
                            onEmptyLayout.show()
                            eventsLayout.hide()
                        } else {
                            onEmptyLayout.hide()
                            eventsLayout.show()
                        }
                    })
            }
        })

    }

}