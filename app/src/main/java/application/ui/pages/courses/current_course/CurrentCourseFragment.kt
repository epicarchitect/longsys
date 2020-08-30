package application.ui.pages.courses.current_course

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import application.constants.VOID_ID
import application.controllers.courses.current_course.CurrentCourseController
import application.extentions.app
import application.ui.pages.courses.course.CourseFragment
import longsys.bb35.extreme.bulk.max.R

class CurrentCourseFragment : Fragment() {

    lateinit var controller: CurrentCourseController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.mycourse_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controller = CurrentCourseController(app())

        controller.getLiveCurrentCourse().observe(viewLifecycleOwner, Observer { course ->
            if (course == null) {
                val creationFragment = CourseCreationFragment {
                    val dialog = ProgressDialog.show(context, "Пожалуйста подождите", "Устанавливаем курс")
                    controller.install(it) { _, isSucceeded ->
                        if (!isSucceeded) {
                            Toast.makeText(requireContext(), "Ошибка во время создания курса", Toast.LENGTH_SHORT).show()
                        }
                        dialog.cancel()
                    }
                }

                childFragmentManager.beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.container, creationFragment)
                    .commit()

            } else {
                val courseFragment = CourseFragment()
                courseFragment.arguments = bundleOf("courseId" to course.id)
                childFragmentManager.beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.container, courseFragment)
                    .commit()

                courseFragment.onDelete {
                    controller.setCurrentCourseId(VOID_ID)
                }
            }
        })


    }

}