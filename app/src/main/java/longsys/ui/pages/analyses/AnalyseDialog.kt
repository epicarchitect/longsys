package longsys.ui.pages.analyses

import kotlinx.android.synthetic.main.analyse_type_dialog.*
import longsys.R
import longsys.controllers.analyses.AnalyseModel
import longsys.controllers.analyses.AnalysesController
import longsys.extentions.app
import longsys.extentions.disableViews
import longsys.extentions.enableViews
import longsys.extentions.text
import longsys.ui.base.dialogs.model_control.ModelDialog

class AnalyseDialog : ModelDialog<AnalyseModel>() {

    lateinit var controller: AnalysesController

    override val innerLayout = R.layout.analyse_type_dialog

    override fun start() {
        controller = AnalysesController(app())
    }

    override fun onSave(model: AnalyseModel) {
        super.onSave(model)
        controller.save(model)
    }

    override fun onDelete(model: AnalyseModel) {
        super.onDelete(model)
        controller.delete(model)
    }

    override fun setViewsByModel() =
        model.run {
            etName.setText(name)
            etDescription.setText(description)
        }


    override fun createModelByViews(oldModel: AnalyseModel) =
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
        fun build(model: AnalyseModel = AnalyseModel()) =
            AnalyseDialog().apply {
                putModel(model)
            }
    }
}