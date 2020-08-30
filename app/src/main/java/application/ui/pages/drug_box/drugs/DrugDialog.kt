package application.ui.pages.drug_box.drugs

import application.constants.UnitType
import application.controllers.drugs.DrugModel
import application.controllers.drugs.DrugsController
import application.extentions.app
import application.extentions.disableViews
import application.extentions.enableViews
import application.extentions.text
import application.ui.base.adapters.spinner.SpinnerAdapter
import application.ui.base.adapters.spinner.SpinnerItem
import application.ui.base.dialogs.model_control.ModelDialog
import kotlinx.android.synthetic.main.drug_dialog.*
import longsys.bb35.extreme.bulk.max.R

class DrugDialog : ModelDialog<DrugModel>() {

    lateinit var controller: DrugsController

    override val innerLayout = R.layout.drug_dialog

    override fun start() {
        controller = DrugsController(app())
        setUnitSpinner()
    }

    fun setUnitSpinner() {
        unitSpinner.adapter = SpinnerAdapter(requireContext(),
            listOf(
                SpinnerItem(UnitType[UnitType.AMPOULES], getString(R.string.ampoules)),
                SpinnerItem(UnitType[UnitType.VIALS], getString(R.string.vials)),
                SpinnerItem(UnitType[UnitType.FLACONS], getString(R.string.flacons)),
                SpinnerItem(UnitType[UnitType.INJECTION], getString(R.string.injection)),
                SpinnerItem(UnitType[UnitType.CAPSULES], getString(R.string.capsules)),
                SpinnerItem(UnitType[UnitType.TABLETS], getString(R.string.tablets)),
                SpinnerItem(UnitType[UnitType.THINGS], getString(R.string.things)),
                SpinnerItem(UnitType[UnitType.GRAMS], getString(R.string.grams)),
                SpinnerItem(UnitType[UnitType.MILLIGRAMS], getString(R.string.milligrams)),
                SpinnerItem(UnitType[UnitType.MILLILITERS], getString(R.string.milliliters)),
                SpinnerItem(UnitType[UnitType.UNITS], getString(R.string.units)),
                SpinnerItem(UnitType[UnitType.ME], getString(R.string.me))
            )
        )
    }

    override fun onSave(model: DrugModel) {
        super.onSave(model)
        controller.save(model)
    }

    override fun onDelete(model: DrugModel) {
        super.onDelete(model)
        controller.delete(model)
    }

    override fun setViewsByModel() {
        etName.setText(model.name)
        etDescription.setText(model.description)
        unitSpinner.setSelection(UnitType[model.unitType])
    }

    override fun createModelByViews(oldModel: DrugModel) =
        oldModel.copy(
            name = etName.text(),
            description = etDescription.text(),
            unitType = UnitType[(unitSpinner.selectedItem as SpinnerItem).id]
        )

    override fun onModeView() {
        disableViews(
            etName,
            etDescription,
            unitSpinner
        )
    }

    override fun onModeEdit() {
        enableViews(
            etName,
            etDescription,
            unitSpinner
        )
    }

    companion object {
        fun build(model: DrugModel = DrugModel()) =
            DrugDialog().apply {
                putModel(model)
            }
    }

}