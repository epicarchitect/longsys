package application.ui.pages.courses.course.analyses.analyses

import application.controllers.analyse_groups.AnalyseGroupModel
import application.controllers.analyse_groups.AnalyseGroupsController
import application.controllers.analyses.AnalyseModel
import application.controllers.analyses.AnalysesController
import application.controllers.course_analyses.CourseAnalyseModel
import application.controllers.course_analyses.CourseAnalysesController
import application.extentions.app
import application.extentions.disableViews
import application.extentions.enableViews
import application.extentions.onSelect
import application.ui.base.adapters.spinner.SpinnerAdapter
import application.ui.base.adapters.spinner.SpinnerItem
import application.ui.base.dialogs.model_control.ModelDialog
import kotlinx.android.synthetic.main.course_analyse_dialog.*
import longsys.bb35.extreme.bulk.max.R

class CourseAnalyseDialog : ModelDialog<CourseAnalyseModel>() {

    lateinit var controller: CourseAnalysesController

    override val innerLayout = R.layout.course_analyse_dialog

    lateinit var groupsController: AnalyseGroupsController
    lateinit var typesController: AnalysesController

    val typesOfGroups = HashMap<AnalyseGroupModel, List<AnalyseModel>>()

    val spinnerGroupModels = ArrayList<AnalyseGroupModel>()
    val spinnerTypeModels = ArrayList<AnalyseModel>()

    override fun start() {
        controller = CourseAnalysesController(app())
        groupsController = AnalyseGroupsController(app())
        typesController = AnalysesController(app())

        setTypesOfGroups()
        setSpinnerGroups()
    }

    override fun onSave(model: CourseAnalyseModel) {
        super.onSave(model)
        controller.save(model)
    }

    override fun onDelete(model: CourseAnalyseModel) {
        super.onDelete(model)
        controller.delete(model)
    }

    fun setTypesOfGroups() {
        typesOfGroups.clear()
        groupsController.getAnalyseGroups().forEach {
            typesOfGroups[it] = typesController.getAnalysesByGroupId(it.id)
        }
    }

    fun setSpinnerTypes(groupId: Int) {
        activity?.let { activity ->
            spinnerTypeModels.clear()

            typesOfGroups.forEach {
                val group = it.key
                val types = it.value
                if (group.id == groupId) {
                    types.forEach { type ->
                        spinnerTypeModels.add(type)
                    }
                }
            }

            spinnerTypeModels.sortBy { it.id }
            val items = spinnerTypeModels.map { SpinnerItem(it.id, it.name) }

            spinnerType.adapter = SpinnerAdapter(activity, items)

            spinnerType.onSelect {
                val type = spinnerTypeModels[it]
                tvDescription.text = if (!type.group.description.trim().isEmpty()) {
                    type.group.description + "\n\n"
                } else ""
                tvDescription.append(type.description)
            }

            setSpinnerTypePositionByModel()
        }
    }

    fun setSpinnerTypePositionByModel() {
        var position = 0
        spinnerTypeModels.forEach { type ->
            if (model.analyse.id == type.id) {
                spinnerType.setSelection(position)
                return
            }
            position++
        }
    }

    fun setSpinnerGroups() {
        activity?.let { activity ->
            spinnerGroupModels.clear()

            typesOfGroups.forEach { spinnerGroupModels.add(it.key) }

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
            if (model.analyse.group.id == group.id) {
                spinnerGroup.setSelection(position)
                return
            }
            position++
        }
    }

    override fun setViewsByModel() {
        setSpinnerGroups()
        checkMandatory.isChecked = model.isMandatory
    }

    override fun createModelByViews(oldModel: CourseAnalyseModel) =
        oldModel.copy(
            isMandatory = checkMandatory.isChecked,
            analyse = spinnerTypeModels[spinnerType.selectedItemPosition]
        )

    override fun onModeView() =
        disableViews(
            spinnerType,
            spinnerGroup,
            checkMandatory
        )


    override fun onModeEdit() =
        enableViews(
            spinnerType,
            spinnerGroup,
            checkMandatory
        )

    companion object {
        fun build(model: CourseAnalyseModel = CourseAnalyseModel()) =
            CourseAnalyseDialog().apply {
                putModel(model)
            }
    }


}