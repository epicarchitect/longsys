package application.ui.pages.courses.course.drugs

import application.constants.UnitType
import application.controllers.course_drugs.CourseDrugModel
import application.controllers.course_drugs.CourseDrugsController
import application.controllers.drug_groups.DrugGroupModel
import application.controllers.drug_groups.DrugGroupsController
import application.controllers.drugs.DrugModel
import application.controllers.drugs.DrugsController
import application.extentions.*
import application.ui.base.adapters.spinner.SpinnerAdapter
import application.ui.base.adapters.spinner.SpinnerItem
import application.ui.base.dialogs.model_control.ModelDialog
import application.ui.time.DatePicker
import application.ui.time.TimePicker
import kotlinx.android.synthetic.main.course_drug_dialog.*
import longsys.bb35.extreme.bulk.max.R
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class CourseDrugDialog : ModelDialog<CourseDrugModel>() {

    override val innerLayout = R.layout.course_drug_dialog

    lateinit var controller: CourseDrugsController
    lateinit var groupsController: DrugGroupsController
    lateinit var typesController: DrugsController

    val typesOfGroups = HashMap<DrugGroupModel, List<DrugModel>>()

    val spinnerGroupModels = ArrayList<DrugGroupModel>()
    val spinnerTypeModels = ArrayList<DrugModel>()

    val selectedTime = Calendar.getInstance()

    lateinit var selectedTimeStart: Calendar
    lateinit var selectedTimeEnd: Calendar

    fun setTypesOfGroups() {
        groupsController.getDrugGroups().forEach {
            typesOfGroups[it] = typesController.getDrugsByGroupId(it.id)
        }
    }

    override fun start() {
        selectedTimeStart = model.timeStart.copy()
        selectedTimeEnd = model.timeEnd.copy()

        controller = CourseDrugsController(app())
        groupsController = DrugGroupsController(app())
        typesController = DrugsController(app())

        setSpinnerDays()
        setTypesOfGroups()

        etCount.removeDoubleZeroOnClick()
        etActiveDays.removeZeroOnClick()
        etStopDays.removeZeroOnClick()

        buttonTimeStart.setOnClickListener {
            activity?.let {
                DatePicker(it, selectedTimeStart, this::setTimeStart).show()
            }
        }

        buttonTimeEnd.setOnClickListener {
            activity?.let {
                DatePicker(it, selectedTimeEnd, this::setTimeEnd).show()
            }
        }

        buttonTime.setOnClickListener {
            activity?.let {
                TimePicker(it, selectedTime, this::setTime).show()
            }
        }

    }

    override fun onSave(model: CourseDrugModel) {
        super.onSave(model)
        controller.save(model)
    }

    override fun onDelete(model: CourseDrugModel) {
        super.onDelete(model)
        controller.delete(model)
    }

    fun setTime(hour: Int, minute: Int) =
        selectedTime.run {
            hour(hour)
            minute(minute)
            buttonTime.text = timeText()
        }

    fun setSpinnerDays() {
        activity?.let { activity ->
            val items = listOf(
                SpinnerItem(0, "Ежедневно"),
                SpinnerItem(1, "День приема, N дней перерыва"),
                SpinnerItem(2, "X дней приема, N дней перерыва")
            )

            spinnerDays.onSelect {
                when (it) {
                    0 -> {
                        customDaysLayout.hide()

                        etActiveDaysLayout.hide()
                        etStopDaysLayout.hide()

                        etActiveDays.setText("1")
                        etStopDays.setText("0")
                    }

                    1 -> {
                        customDaysLayout.show()

                        etActiveDaysLayout.hide()
                        etStopDaysLayout.show()

                        etActiveDays.setText("1")
                    }

                    2 -> {
                        customDaysLayout.show()

                        etActiveDaysLayout.show()
                        etStopDaysLayout.show()
                    }
                }
            }

            spinnerDays.adapter = SpinnerAdapter(activity, items)
        }
    }

    fun setSpinnerDaysPositionByModel() {
        if (model.activeDays == 1 && model.stopDays == 0) {
            spinnerDays.setSelection(0)
        } else if (model.activeDays == 1 && model.stopDays != 0) {
            spinnerDays.setSelection(1)
        } else {
            spinnerDays.setSelection(2)
        }
    }

    fun setSpinnerTypes(groupId: Int) {
        activity?.let { activity ->
            spinnerTypeModels.clear()

            typesOfGroups.forEach { entry ->
                val group = entry.key
                val types = entry.value
                if (group.id == groupId) {
                    types.forEach { type ->
                        spinnerTypeModels.add(type)
                    }
                    return@forEach
                }
            }

            spinnerTypeModels.sortBy { it.id }
            val items = spinnerTypeModels.map { SpinnerItem(it.id, it.name) }

            spinnerType.onSelect {
                etCountLayout.hint = "Дозировка (" + UnitType.toString(requireContext(), getSelectedUnitType(), true) + ")"
            }

            spinnerType.adapter = SpinnerAdapter(activity, items)

            setSpinnerTypePositionByModel()
        }
    }

    fun getSelectedUnitType() = spinnerTypeModels[spinnerType.selectedItemPosition].unitType

    fun setSpinnerTypePositionByModel() {
        var position = 0
        spinnerTypeModels.forEach { type ->
            if (model.drug.id == type.id) {
                spinnerType.setSelection(position)
                return
            }
            position++
        }
    }

    fun setSpinnerGroups() {
        activity?.let { activity ->
            spinnerGroupModels.clear()

            typesOfGroups.forEach {
                val group = it.key
                spinnerGroupModels.add(group)
            }

            spinnerGroupModels.sortBy { it.id }
            val items = spinnerGroupModels.map { SpinnerItem(it.id, it.name) }

            spinnerGroup.adapter = SpinnerAdapter(activity, items)

            spinnerGroup.onSelect {
                setSpinnerTypes(items[it].id)
            }

            setSpinnerGroupPositionByModel()
        }
    }

    fun setSpinnerGroupPositionByModel() {
        var position = 0
        spinnerGroupModels.forEach { group ->
            if (model.drug.group.id == group.id) {
                spinnerGroup.setSelection(position)
                return
            }
            position++
        }
    }

    fun setTimeStart(day: Int, month: Int, year: Int) =
        selectedTimeStart.run {
            day(day)
            month(month)
            year(year)
            buttonTimeStart.text = dateText()
        }

    fun setTimeEnd(day: Int, month: Int, year: Int) =
        selectedTimeEnd.run {
            day(day)
            month(month)
            year(year)
            buttonTimeEnd.text = dateText()
        }

    override fun setViewsByModel() =
        model.run {
            setSpinnerGroups()
            setSpinnerDaysPositionByModel()
            etCount.setText(count.text())
            etActiveDays.setText(activeDays.toString())
            etStopDays.setText(stopDays.toString())
            timeStart.run { setTimeStart(day(), month(), year()) }
            timeEnd.run { setTimeEnd(day(), month(), year()) }
            setTime(hour, minute)
        }

    override fun createModelByViews(oldModel: CourseDrugModel) =
        oldModel.copy(
            activeDays = etActiveDays.integer(),
            stopDays = etStopDays.integer(),
            timeStart = selectedTimeStart,
            timeEnd = selectedTimeEnd,
            drug = spinnerTypeModels[spinnerType.selectedItemPosition],
            count = etCount.double(),
            hour = selectedTime.hour(),
            minute = selectedTime.minute()
        )

    override fun onModeView() =
        disableViews(
            etCount,
            spinnerType,
            spinnerGroup,
            spinnerDays,
            buttonTimeStart,
            buttonTimeEnd,
            etActiveDays,
            etStopDays,
            buttonTime
        )

    override fun onModeEdit() =
        enableViews(
            etCount,
            spinnerType,
            spinnerGroup,
            spinnerDays,
            buttonTimeStart,
            buttonTimeEnd,
            etActiveDays,
            etStopDays,
            buttonTime
        )

    companion object {
        fun build(model: CourseDrugModel = CourseDrugModel()) =
            CourseDrugDialog().apply {
                putModel(model)
            }
    }

}