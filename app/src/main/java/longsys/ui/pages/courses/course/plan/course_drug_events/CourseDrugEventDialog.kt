package longsys.ui.pages.courses.course.plan.course_drug_events

import android.annotation.SuppressLint
import kotlinx.android.synthetic.main.base_model_dialog.*
import kotlinx.android.synthetic.main.course_drug_event_dialog.*
import longsys.R
import longsys.constants.UnitType
import longsys.controllers.course_drug_events.CourseDrugEventModel
import longsys.controllers.course_drug_events.CourseDrugEventsController
import longsys.controllers.courses.current_course.CurrentCourseController
import longsys.extentions.*
import longsys.ui.base.dialogs.model_control.ModelDialog
import longsys.ui.time.DatePicker
import longsys.ui.time.TimePicker
import java.util.*

class CourseDrugEventDialog : ModelDialog<CourseDrugEventModel>() {

    lateinit var controller: CourseDrugEventsController

    val isCurrent get() = CurrentCourseController(app()).getCurrentCourseId() == model.courseDrug.course.id

    override val innerLayout = R.layout.course_drug_event_dialog

    val selectedDate = Calendar.getInstance()

    init {
        hideControlls = true
    }

    override fun start() {
        if (!isCurrent || model.isCompleted) setModeView() else setModeEdit()

        controller = CourseDrugEventsController(app())

        etCount.removeDoubleZeroOnClick()
        etCountLayout.hint = "Дозировка (" + UnitType.toString(requireContext(), model.courseDrug.drug.unitType, true) + ")"

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

    override fun onSave(model: CourseDrugEventModel) {
        super.onSave(model)
        controller.save(model)
    }

    override fun onDelete(model: CourseDrugEventModel) {
        super.onDelete(model)
        controller.delete(model)
    }

    fun setModeNotCompleted() {
        setModeEdit()
        cancelEnabled = false
        dismissAfterSave = true
        onReady {
            buttonDelete.show()
        }
    }

    @SuppressLint("SetTextI18n")
    fun setTime(hour: Int, minute: Int) =
        selectedDate.run {
            hour(hour)
            minute(minute)
            buttonTime.text = timeText()
        }


    @SuppressLint("SetTextI18n")
    fun setDate(day: Int, month: Int, year: Int) =
        selectedDate.run {
            day(day)
            month(month)
            year(year)
            buttonDate.text = dateText()
        }

    override fun setViewsByModel() =
        model.run {
            etCount.setText(count.text())
            time.run {
                setTime(hour(), minute())
                setDate(day(), month(), year())
            }
        }

    override fun createModelByViews(oldModel: CourseDrugEventModel) =
        oldModel.copy(
            time = selectedDate,
            count = etCount.double()
        )

    @SuppressLint("SetTextI18n")
    override fun onModeView() {
        disableViews(
            etCount,
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
        etCountLayout.hide()

        tvTitle.text = model.courseDrug.drug.name + "\n" + model.count.text() + " " + UnitType.toString(requireContext(), model.courseDrug.drug.unitType)
    }

    override fun onModeEdit() {
        enableViews(
            etCount,
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

        buttonDone.show()
        etCountLayout.show()
        tvTitle.text = model.courseDrug.drug.name
    }


    companion object {
        fun build(model: CourseDrugEventModel = CourseDrugEventModel()) =
            CourseDrugEventDialog().apply {
                putModel(model)
            }
    }
}