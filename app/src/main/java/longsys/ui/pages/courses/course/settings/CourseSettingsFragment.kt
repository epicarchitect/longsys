package longsys.ui.pages.courses.course.settings

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.course_settings_fragment.*
import longsys.R
import longsys.constants.COUNT_DAYS_IN_COURSE
import longsys.constants.VOID_ID
import longsys.controllers.courses.CourseModel
import longsys.controllers.courses.CoursesController
import longsys.controllers.courses.current_course.CurrentCourseController
import longsys.extentions.*
import longsys.ui.base.dialogs.confirm.ConfirmDialog
import longsys.ui.time.DatePicker
import java.util.*

class CourseSettingsFragment : Fragment() {

    val courseId get() = arguments?.getInt("courseId", VOID_ID) ?: VOID_ID

    lateinit var controller: CoursesController
    lateinit var currentCourseController: CurrentCourseController

    private var onDeleteListener: () -> Unit = {}

    fun onDelete(l: () -> Unit) {
        onDeleteListener = l
    }

    val isCurrent get() = currentCourseController.getCurrentCourseId() == courseId

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.course_settings_fragment, container, false)

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retainInstance = true
        controller = CoursesController(app())
        currentCourseController = CurrentCourseController(app())

        controller.getCourseById(courseId)?.let {
            setByCourse(it)
        }

        checkNotificationsEnabled.setOnCheckedChangeListener { _, isChecked ->
            currentCourseController.changeEnabledNotifications(isChecked)
            if (isChecked) {
                analyseNotificationsLayout.show()
            } else {
                analyseNotificationsLayout.hide()
                activity?.hideKeyboard()
            }
        }

        buttonStop.apply {
            if (isCurrent) show() else hide()

            setOnClickListener {
                ConfirmDialog {
                    if (it) {
                        hide()
                        tvCompletion.show()
                        tvCompletion.text = "Курс завершен: " + Calendar.getInstance().dateText()
                        etCountDays.isEnabled = false
                        buttonTimeStart.isEnabled = false
                        buttonTimeEnd.isEnabled = false
                        checkNotificationsEnabled.isEnabled = false
                        currentCourseController.complete()
                    }
                }.show(parentFragmentManager, "")
            }
        }

        etCountDays.removeZeroOnClick()

        buttonDelete.setOnClickListener {
            ConfirmDialog {
                if (it) {
                    onDeleteListener()
                    buttonDelete.disable()
                }
            }.show(parentFragmentManager, "")
        }
    }

    override fun onPause() {
        super.onPause()
        if (isCurrent) {
            currentCourseController.changeDaysBeforeNotifyAnalyse(etCountDays.integer())
            currentCourseController.changeEnabledNotifications(checkNotificationsEnabled.isChecked)
            activity?.hideKeyboard()
        }
    }

    fun setByCourse(course: CourseModel) {
        etCountDays.setText(course.daysBeforeNotifyAnalyse.toString())
        buttonTimeStart.text = course.timeStart.dateTextWithoutYear()
        buttonTimeEnd.text = course.timeEnd.dateTextWithoutYear()

        if (course.isNotificationsEnabled) {
            analyseNotificationsLayout.show()
        } else {
            analyseNotificationsLayout.hide()
        }

        if (isCurrent) {
            tvCompletion.hide()
        } else {
            tvCompletion.show()
            tvCompletion.text = "Курс завершен: " + course.timeCompletion.dateText()
        }

        etCountDays.isEnabled = isCurrent
        buttonTimeStart.isEnabled = isCurrent
        buttonTimeEnd.isEnabled = isCurrent
        checkNotificationsEnabled.isChecked = course.isNotificationsEnabled
        checkNotificationsEnabled.isEnabled = isCurrent

        buttonTimeStart.setOnClickListener {
            DatePicker(requireActivity(), course.timeStart) { day, month, year ->
                val time = course.timeStart.copy {
                    day(day)
                    month(month)
                    year(year)
                }

                val dialog = ProgressDialog.show(context, "Пожалуйста подождите", "Переустаналиваем курс")
                currentCourseController.changeTimeStart(time) { course, _ ->
                    dialog.cancel()
                }

            }.show()
        }

        buttonTimeEnd.setOnClickListener {
            val picker = DatePicker(requireActivity(), course.timeEnd) { day, month, year ->
                val time = course.timeEnd.copy {
                    day(day)
                    month(month)
                    year(year)
                }

                val dialog = ProgressDialog.show(context, "Пожалуйста подождите", "Переустаналиваем курс")

                currentCourseController.changeTimeEnd(time) { course, _ ->
                    dialog.cancel()
                }

            }
            picker.datePicker.minDate = course.timeStart.timeInMillis + COUNT_MILLIS_IN_DAY * COUNT_DAYS_IN_COURSE
            picker.show()
        }
    }

    companion object {
        fun build(courseId: Int) =
            CourseSettingsFragment().apply {
                arguments = bundleOf(
                    "courseId" to courseId
                )
            }
    }

}