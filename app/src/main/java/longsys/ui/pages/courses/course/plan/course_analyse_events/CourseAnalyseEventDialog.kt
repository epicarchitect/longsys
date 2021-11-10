package longsys.ui.pages.courses.course.plan.course_analyse_events

import kotlinx.android.synthetic.main.course_analyse_event_dialog.*
import longsys.R
import longsys.controllers.course_analyse_events.CourseAnalyseEventModel
import longsys.controllers.course_analyse_events.CourseAnalyseEventsController
import longsys.controllers.course_analyses.CourseAnalyseModel
import longsys.controllers.course_analyses.CourseAnalysesController
import longsys.controllers.courses.current_course.CurrentCourseController
import longsys.extentions.*
import longsys.ui.base.dialogs.model_control.ModelDialog
import longsys.ui.time.DatePicker
import longsys.ui.time.TimePicker
import java.util.*

class CourseAnalyseEventDialog : ModelDialog<CourseAnalyseEventModel>() {

    lateinit var controller: CourseAnalyseEventsController

    val isCurrent get() = CurrentCourseController(app()).getCurrentCourseId() == model.courseAnalyseGroup.course.id

    override val innerLayout = R.layout.course_analyse_event_dialog

    val selectedDate = Calendar.getInstance()

    init {
        hideControlls = true
    }

    override fun start() {
        if (!isCurrent || model.isCompleted) setModeView() else setModeEdit()

        controller = CourseAnalyseEventsController(app())

        tvTitle.text = model.courseAnalyseGroup.name
        setAnalyses()

        buttonTime.setOnClickListener {
            activity?.let {
                TimePicker(it, selectedDate) { hour, minute ->
                    setTime(hour, minute)
                    controller.save(createModelByViews(model).copy(isNotified = false))
                }.show()
            }
        }

        buttonDate.setOnClickListener {
            activity?.let {
                DatePicker(it, selectedDate) { day, month, year ->
                    setDate(day, month, year)
                    controller.save(createModelByViews(model).copy(isNotified = false))
                }.show()
            }
        }

        buttonDone.setOnClickListener {
            buttonDone.disable()
            controller.complete(createModelByViews(model))
            dismiss()
        }
    }

    fun setAnalyses() {
        val analyses = CourseAnalysesController(app()).getCourseAnalyses(model.courseAnalyseGroup.id)

        val list = ArrayList<CourseAnalyseModel>()
        val listNotMandatory = ArrayList<CourseAnalyseModel>()

        analyses.forEach {
            if (it.isMandatory) {
                list.add(it)
            } else {
                listNotMandatory.add(it)
            }
        }

        tvList.text = ""
        var isFirst = true

        list.forEach {
            if (isFirst) {
                isFirst = false
            } else {
                tvList.append("\n")
            }
            tvList.append(" - " + it.analyse.name)
        }

        if (listNotMandatory.isNotEmpty()) {
            tvList.append("\n Не обязательные: ")
            listNotMandatory.forEach {
                tvList.append("\n - " + it.analyse.name)
            }
        }

    }

    override fun onSave(model: CourseAnalyseEventModel) {
        super.onSave(model)
        controller.save(model)
    }

    override fun onDelete(model: CourseAnalyseEventModel) {
        super.onDelete(model)
        controller.delete(model)
    }

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

    override fun setViewsByModel() =
        model.run {
            etComment.setText(comment)
            time.run {
                setTime(hour(), minute())
                setDate(day(), month(), year())
            }
        }

    override fun createModelByViews(oldModel: CourseAnalyseEventModel) =
        oldModel.copy(
            time = selectedDate,
            comment = etComment.text()
        )

    override fun onModeView() {
        disableViews(
            etComment,
            buttonTime,
            buttonDate
        )

        if (model.isCompleted) {
            tvDone.show()
            tvNotDone.hide()
        } else {
            tvDone.hide()
            tvNotDone.show()
        }

        buttonDone.hide()

        if (model.comment.trim().isEmpty()) {
            etCommentLayout.hide()
        } else {
            etCommentLayout.show()
        }
    }

    override fun onModeEdit() {
        enableViews(
            etComment,
            buttonTime,
            buttonDate
        )

        if (model.isCompleted) {
            tvDone.show()
            tvNotDone.hide()
        } else {
            tvDone.hide()
            tvNotDone.show()
        }

        if (selectedDate.timeInMillis <= calendarEndOfToday().timeInMillis) {
            buttonDone.show()
            etCommentLayout.show()
        } else {
            buttonDone.hide()
            etCommentLayout.hide()
        }
    }

    companion object {
        fun build(model: CourseAnalyseEventModel = CourseAnalyseEventModel()) =
            CourseAnalyseEventDialog().apply {
                putModel(model)
            }
    }
}