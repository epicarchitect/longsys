package longsys.ui.pages.courses.course

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.course_fragment.*
import longsys.R
import longsys.constants.VOID_ID
import longsys.controllers.courses.CoursesController
import longsys.extentions.app
import longsys.ui.pages.courses.course.analyses.CourseAnalysesFragment
import longsys.ui.pages.courses.course.drugs.CourseDrugsFragment
import longsys.ui.pages.courses.course.plan.CoursePlanFragment
import longsys.ui.pages.courses.course.settings.CourseSettingsFragment

class CourseFragment : Fragment() {

    lateinit var controller: CoursesController

    lateinit var tab0: TabLayout.Tab
    lateinit var tab1: TabLayout.Tab
    lateinit var tab2: TabLayout.Tab
    lateinit var tab3: TabLayout.Tab

    var onDeleteListener: (() -> Unit)? = null

    fun onDelete(l: () -> Unit) {
        onDeleteListener = l
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.course_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controller = CoursesController(app())
        retainInstance = true

        val courseId = arguments?.getInt("courseId") ?: VOID_ID

        tabLayout.isSmoothScrollingEnabled = true
        tab0 = tabLayout.newTab().setText(R.string.preparations)
        tab1 = tabLayout.newTab().setText(R.string.analyses)
        tab2 = tabLayout.newTab().setText(R.string.plan)
        tab3 = tabLayout.newTab().setText(R.string.settings)

        pager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = 4

            override fun createFragment(position: Int) = when (position) {
                0 -> CourseDrugsFragment.build(courseId)
                1 -> CourseAnalysesFragment.build(courseId)
                2 -> CoursePlanFragment.build(courseId)
                else -> CourseSettingsFragment.build(courseId).apply {
                    onDelete {
                        controller.deleteById(courseId)
                        onDeleteListener?.let {
                            it()
                        } ?: findNavController().popBackStack()
                    }
                    Log.d("test", "CourseSettingsFragment.build")
                }
            }
        }

        tabLayout.apply {
            addTab(tab0)
            addTab(tab1)
            addTab(tab2)
            addTab(tab3)

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val position = tab?.position ?: 0
                    Log.d("test", "onTabSelected: $position")
                    pager.currentItem = position
                }
            })
        }

        pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.d("test", "onPageSelected $position")
                when (position) {
                    0 -> tab0.select()
                    1 -> tab1.select()
                    2 -> tab2.select()
                    3 -> tab3.select()
                }
            }
        })

    }

}