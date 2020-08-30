package application.ui.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import application.constants.VOID_ID
import application.controllers.courses.current_course.CurrentCourseController
import application.extentions.app
import application.ui.pages.courses.current_course.CurrentCourseFragment
import application.ui.pages.drug_box.DrugBoxFragment
import application.ui.pages.today.TodayFragment
import kotlinx.android.synthetic.main.main_fragment.*
import longsys.bb35.extreme.bulk.max.R

class MainFragment : Fragment() {

    val currentCourseFragment = CurrentCourseFragment()
    val todayFragment = TodayFragment()
    val drugBoxFragment = DrugBoxFragment()

    init {
        instance = this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.main_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNavigation()
        savedInstanceState?.let { state ->
            val default = if (CurrentCourseController(app()).getCurrentCourseId() == VOID_ID) 0 else 1
            selectedId = state.getInt("selectedId", default)
        }
        select(selectedId)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("selectedId", selectedId)
    }

    fun setTitle(res: Int) = activity?.setTitle(res)

    fun setNavigation() {
        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.courseFragment -> {
                    selectedId = 0
                    setTitle(R.string.my_course)
                    currentCourseFragment
                }

                    R.id.planFragment -> {
                    selectedId = 1
                    setTitle(R.string.today)
                    todayFragment
                }

                    else -> {
                    selectedId = 2
                    activity?.title = "Аптечка"
                    drugBoxFragment
                }
            }.let { fragment ->
                childFragmentManager.beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.container, fragment)
                    .commit()
            }
            true
        }
    }

    companion object {
        var selectedId = -1
        var instance: MainFragment? = null

        fun select(id: Int) {
            selectedId = id
            instance?.navigation?.selectedItemId = when (selectedId) {
                0 -> R.id.courseFragment
                1 -> R.id.planFragment
                else -> R.id.drugBoxFragment
            }
        }

        fun showMyCourse() {
            select(0)
        }
    }
}