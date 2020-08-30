package application.ui.pages.drug_box.groups

import application.controllers.drug_groups.DrugGroupModel
import application.controllers.drug_groups.DrugGroupsController
import application.extentions.app
import application.extentions.disableViews
import application.extentions.enableViews
import application.extentions.text
import application.ui.base.dialogs.model_control.ModelDialog
import kotlinx.android.synthetic.main.drug_group_dialog.*
import longsys.bb35.extreme.bulk.max.R

class DrugGroupDialog : ModelDialog<DrugGroupModel>() {

    lateinit var controller: DrugGroupsController

    override val innerLayout = R.layout.drug_group_dialog

    override fun start() {
        controller = DrugGroupsController(app())
    }

    override fun onSave(model: DrugGroupModel) {
        super.onSave(model)
        controller.save(model)
    }

    override fun onDelete(model: DrugGroupModel) {
        super.onDelete(model)
        controller.delete(model)
    }

    override fun setViewsByModel() {
        etName.setText(model.name)
    }

    override fun createModelByViews(oldModel: DrugGroupModel) =
        oldModel.copy(
            name = etName.text()
        )

    override fun onModeView() {
        disableViews(etName)
    }

    override fun onModeEdit() {
        enableViews(etName)
    }

    companion object {
        fun build(model: DrugGroupModel = DrugGroupModel()) =
            DrugGroupDialog().apply {
                putModel(model)
            }
    }

}