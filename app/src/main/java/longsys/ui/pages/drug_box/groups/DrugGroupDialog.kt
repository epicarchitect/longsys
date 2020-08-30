package longsys.ui.pages.drug_box.groups

import kotlinx.android.synthetic.main.drug_group_dialog.*
import longsys.R
import longsys.controllers.drug_groups.DrugGroupModel
import longsys.controllers.drug_groups.DrugGroupsController
import longsys.extentions.app
import longsys.extentions.disableViews
import longsys.extentions.enableViews
import longsys.extentions.text
import longsys.ui.base.dialogs.model_control.ModelDialog

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