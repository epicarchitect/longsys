package longsys.ui.pages.analyse_groups

import android.util.Log
import kotlinx.android.synthetic.main.analyses_group_dialog.*
import longsys.R
import longsys.controllers.analyse_groups.AnalyseGroupModel
import longsys.controllers.analyse_groups.AnalyseGroupsController
import longsys.extentions.app
import longsys.extentions.disableViews
import longsys.extentions.enableViews
import longsys.extentions.text
import longsys.ui.base.dialogs.model_control.ModelDialog

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