package application.ui.pages.analyses

import application.controllers.analyses.AnalyseModel
import application.controllers.analyses.AnalysesController
import application.extentions.app
import application.extentions.disableViews
import application.extentions.enableViews
import application.extentions.text
import application.ui.base.dialogs.model_control.ModelDialog
import kotlinx.android.synthetic.main.analyse_type_dialog.*
import longsys.bb35.extreme.bulk.max.R

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