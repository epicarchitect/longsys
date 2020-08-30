package application.ui.pages.analyse_groups

import application.controllers.analyse_groups.AnalyseGroupModel
import application.controllers.analyse_groups.AnalyseGroupsController
import application.extentions.app
import application.extentions.disableViews
import application.extentions.enableViews
import application.extentions.text
import application.ui.base.dialogs.model_control.ModelDialog
import kotlinx.android.synthetic.main.analyses_group_dialog.*
import longsys.bb35.extreme.bulk.max.R

class AnalyseGroupDialog : ModelDialog<AnalyseGroupModel>() {

    lateinit var controller: AnalyseGroupsController

    override val innerLayout = R.layout.analyses_group_dialog

    override fun start() {
        controller = AnalyseGroupsController(app())
    }

    override fun onSave(model: AnalyseGroupModel) {
        super.onSave(model)
        controller.save(model)
    }

    override fun onDelete(model: AnalyseGroupModel) {
        super.onDelete(model)
        controller.delete(model)
    }

    override fun setViewsByModel() =
        model.run {
            etName.setText(name)
            etDescription.setText(description)
        }

    override fun createModelByViews(oldModel: AnalyseGroupModel) =
        oldModel.copy(
            name = etName.text(),
            description = etDescription.text()
        )

    override fun onModeView() =
        disableViews(
            etName,
            etDescription
        )

    override fun onModeEdit() =
        enableViews(
            etName,
            etDescription
        )

    companion object {
        fun build(model: AnalyseGroupModel = AnalyseGroupModel()) =
            AnalyseGroupDialog().apply {
                putModel(model)
            }
    }

}