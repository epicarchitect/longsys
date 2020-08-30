package application.ui.pages.courses.course.plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import application.constants.VOID_ID
import application.controllers.course_analyse_events.CourseAnalyseEventModel
import application.controllers.course_analyse_events.CourseAnalyseEventsController
import application.controllers.course_drug_events.CourseDrugEventModel
import application.controllers.course_drug_events.CourseDrugEventsController
import application.controllers.courses.CoursesController
import application.controllers.events.EventsController
import application.extentions.app
import application.ui.pages.courses.course.plan.course_analyse_events.CourseAnalyseEventDialog
import application.ui.pages.courses.course.plan.course_drug_events.CourseDrugEventDialog
import kotlinx.android.synthetic.main.course_plan_fragment.*
import longsys.bb35.extreme.bulk.max.R

class CoursePlanFragment : Fragment() {

    val courseId get() = arguments?.getInt("courseId", VOID_ID) ?: VOID_ID

    lateinit var controller: EventsController
    lateinit var drugsController: CourseDrugEventsController
    lateinit var analysesController: CourseAnalyseEventsController

    var listAdapter = EventListAdapter().apply {
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
        inflater.inflate(R.layout.course_plan_fragment, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retainInstance = true

        controller = EventsController(app())
        drugsController = CourseDrugEventsController(app())
        analysesController = CourseAnalyseEventsController(app())

        setRv()
    }

    fun setRv() {
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = listAdapter

        CoursesController(app()).getCourseById(courseId)?.run {
            controller.getLiveEvents(id, timeStart.timeInMillis, timeEnd.timeInMillis)
                .observe(viewLifecycleOwner, Observer {
                    listAdapter.setEvents(it)
                })
        }
    }

    companion object {
        fun build(courseId: Int) =
            CoursePlanFragment().apply {
                arguments = bundleOf(
                    "courseId" to courseId
                )
            }
    }

}