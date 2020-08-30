package longsys.ui.pages.courses.course.drugs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.course_drugs_fragment.*
import longsys.R
import longsys.constants.VOID_ID
import longsys.controllers.course_drugs.CourseDrugModel
import longsys.controllers.course_drugs.CourseDrugsController
import longsys.controllers.courses.CourseModel
import longsys.controllers.courses.current_course.CurrentCourseController
import longsys.extentions.app

class CourseDrugsFragment : Fragment() {

    val courseId get() = arguments?.getInt("courseId", VOID_ID) ?: VOID_ID

    val listAdapter = CourseDrugsListAdapter()

    lateinit var controller: CourseDrugsController

    val isMutable get() = CurrentCourseController(
        app()
    ).getCurrentCourseId() == courseId

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.course_drugs_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retainInstance = true
        controller = CourseDrugsController(app())
        setAdapter()
    }

    override fun onResume() {
        super.onResume()
        if (isMutable) {
            setAsMutable()
        } else {
            setAsImmutable()
        }
    }

    fun setAsMutable() {
        fab.show()
        fab.setOnClickListener {
            val dialog =
                CourseDrugDialog.build(CourseDrugModel(course = CourseModel(id = courseId)))
            dialog.setModeEdit()
            dialog.show(parentFragmentManager, "")
        }

        listAdapter.onClick { model, _ ->
            val dialog = CourseDrugDialog.build(model)
            dialog.setModeView()
            dialog.show(parentFragmentManager, "")
        }
    }

    fun setAsImmutable() {
        fab.hide()
        fab.setOnClickListener {}

        listAdapter.onClick { model, _ ->
            val dialog = CourseDrugDialog.build(model)
            dialog.setModeView()
            dialog.isImmutable = true
            dialog.show(parentFragmentManager, "")
        }
    }

    fun setAdapter() {
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = listAdapter
        controller.getLiveCourseDrugsByCourseId(courseId).observe(viewLifecycleOwner, Observer {
            listAdapter.list = it
        })
    }

    companion object {
        fun build(courseId: Int) =
            CourseDrugsFragment().apply {
                arguments = bundleOf(
                    "courseId" to courseId
                )
            }
    }
}