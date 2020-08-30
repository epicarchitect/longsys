package application.ui.pages.courses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import application.controllers.courses.CoursesController
import application.controllers.courses.current_course.CurrentCourseController
import application.extentions.app
import kotlinx.android.synthetic.main.courses_fragment.*
import longsys.bb35.extreme.bulk.max.R

class CoursesFragment : Fragment() {

    lateinit var controller: CoursesController

    val listAdapter = CoursesListAdapter {
        CurrentCourseController(app()).getCurrentCourseId() == it.id
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.courses_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controller = CoursesController(app())

        setAdapter()

        listAdapter.onClick { model, _ ->
            val action = CoursesFragmentDirections.actionCoursesFragmentToCourseFragment(model.id)
            findNavController().navigate(action)
        }
    }

    override fun onResume() {
        super.onResume()
        listAdapter.notifyDataSetChanged() // for current update
    }

    fun setAdapter() {
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = listAdapter
        controller.getLiveCourses().observe(viewLifecycleOwner, Observer {
            listAdapter.list = it
        })
    }
}