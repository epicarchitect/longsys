package application.ui.pages.courses.course.analyses.groups

import application.controllers.course_analyse_groups.CourseAnalyseGroupModel
import application.controllers.course_analyse_groups.CourseAnalyseGroupsController
import application.extentions.*
import application.ui.base.dialogs.model_control.ModelDialog
import application.ui.time.DatePicker
import application.ui.time.TimePicker
import kotlinx.android.synthetic.main.course_analyse_group_dialog.*
import longsys.bb35.extreme.bulk.max.R
import java.util.*

class CourseAnalyseGroupDialog : ModelDialog<CourseAnalyseGroupModel>() {

    lateinit var controller: CourseAnalyseGroupsController

    override val innerLayout = R.layout.course_analyse_group_dialog

    val selectedDate = Calendar.getInstance()

    override fun start() {
        controller = CourseAnalyseGroupsController(app())

        buttonTime.setOnClickListener {
            activity?.let {
                TimePicker(it, selectedDate, this::setTime).show()
            }
        }

        buttonDate.setOnClickListener {
            activity?.let {
                DatePicker(it, selectedDate, this::setDate).show()
            }
        }
    }

    override fun onSave(model: CourseAnalyseGroupModel) {
        super.onSave(model)
        controller.save(model)
    }

    override fun onDelete(model: CourseAnalyseGroupModel) {
        super.onDelete(model)
        controller.delete(model)
    }

    override fun setViewsByModel() {
        etName.setText(model.name)
        model.time.run {
            setTime(hour(), minute())
            setDate(day(), month(), year())
        }
    }

    override fun createModelByViews(oldModel: CourseAnalyseGroupModel) =
        oldModel.copy(
            name = etName.text(),
            time = selectedDate
        )

    override fun onModeView() =
        disableViews(
            etName,
            buttonTime,
            buttonDate
        )

    override fun onModeEdit() =
        enableViews(
            etName,
            buttonTime,
            buttonDate
        )

    fun setTime(hour: Int, minute: Int) =
        selectedDate.run {
            hour(hour)
            minute(minute)
            buttonTime.text = timeText()
        }

    fun setDate(day: Int, month: Int, year: Int) =
        selectedDate.run {
            day(day)
            month(month)
            year(year)
            buttonDate.text = dateText()
        }

    companion object {
        fun build(model: CourseAnalyseGroupModel = CourseAnalyseGroupModel()) =
            CourseAnalyseGroupDialog().apply {
                putModel(model)
            }
    }

}