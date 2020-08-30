package longsys.ui.pages.drug_box.drugs

import kotlinx.android.synthetic.main.drug_dialog.*
import longsys.R
import longsys.constants.UnitType
import longsys.controllers.drugs.DrugModel
import longsys.controllers.drugs.DrugsController
import longsys.extentions.app
import longsys.extentions.disableViews
import longsys.extentions.enableViews
import longsys.extentions.text
import longsys.ui.base.adapters.spinner.SpinnerAdapter
import longsys.ui.base.adapters.spinner.SpinnerItem
import longsys.ui.base.dialogs.model_control.ModelDialog

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