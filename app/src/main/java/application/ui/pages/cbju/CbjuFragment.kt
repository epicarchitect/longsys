package application.ui.pages.cbju

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import application.controllers.cbju_controller.Cbju
import application.controllers.cbju_controller.CbjuController
import application.extentions.*
import application.ui.base.adapters.spinner.SpinnerAdapter
import application.ui.base.adapters.spinner.SpinnerItem
import kotlinx.android.synthetic.main.cbju_fragment.*
import longsys.bb35.extreme.bulk.max.R

// cbju - кбжу (калории белки жиры углеводы)
class CbjuFragment : Fragment() {

    lateinit var controller: CbjuController
    var model = Cbju()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.cbju_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        controller = CbjuController(app())
        model = controller.getCbju()

        etWeight.removeDoubleZeroOnClick()

        setSpinners()
        setViewsByModel()

        buttonCalculate.setOnClickListener {
            setModelByViews()
            setResultsByModel()
        }
    }

    fun setSpinners() {
        setActivitySpinner()
        setDeficitSpinner()
        setProteinSpinner()
        setFatsSpinner()
        setWaterSpinner()
    }

    fun setActivitySpinner() {
        val items = listOf(
            SpinnerItem(0, "Сидячая работа"),
            SpinnerItem(1, "Минимум активности"),
            SpinnerItem(2, "Средняя активность"),
            SpinnerItem(3, "Высокая активность")
        )

        activitySpinner.adapter = SpinnerAdapter(requireContext(), items)
        activitySpinner.onSelect {
            when (it) {
                0 -> model.activityLevel = Cbju.ActivityLevel.SUPER_MIN
                1 -> model.activityLevel = Cbju.ActivityLevel.MIN
                2 -> model.activityLevel = Cbju.ActivityLevel.MIDDLE
                3 -> model.activityLevel = Cbju.ActivityLevel.MAX
            }
            activity?.hideKeyboard()
        }
    }

    fun setDeficitSpinner() {
        val items = listOf(
            SpinnerItem(0, "Средняя скорость похудения"),
            SpinnerItem(1, "Быстрая скорость похудения")
        )

        deficitSpinner.adapter = SpinnerAdapter(requireContext(), items)
        deficitSpinner.onSelect {
            when (it) {
                0 -> model.deficitLevel = Cbju.DeficitLevel.MIDDLE
                1 -> model.deficitLevel = Cbju.DeficitLevel.MAX
            }
            activity?.hideKeyboard()
        }
    }

    fun setProteinSpinner() {
        val items = listOf(
            SpinnerItem(0, "Минимальный уровень"),
            SpinnerItem(1, "Универсальный уровень"),
            SpinnerItem(2, "\"Спортивный\" уровень №1"),
            SpinnerItem(3, "\"Спортивный\" уровень №2"),
            SpinnerItem(4, "Максимальный уровень")
        )

        proteinsSpinner.adapter = SpinnerAdapter(requireContext(), items)
        proteinsSpinner.onSelect {
            when (it) {
                0 -> model.proteinLevel = Cbju.ProteinLevel.MIN
                1 -> model.proteinLevel = Cbju.ProteinLevel.UNIVERSAL
                2 -> model.proteinLevel = Cbju.ProteinLevel.SPORT_1
                3 -> model.proteinLevel = Cbju.ProteinLevel.SPORT_2
                4 -> model.proteinLevel = Cbju.ProteinLevel.MAX
            }
            activity?.hideKeyboard()
        }
    }

    fun setFatsSpinner() {
        val items = listOf(
            SpinnerItem(0, "Минимально разумный уровень"),
            SpinnerItem(1, "Универсальный уровень")
        )

        fatsSpinner.adapter = SpinnerAdapter(requireContext(), items)
        fatsSpinner.onSelect {
            when (it) {
                0 -> model.fatLevel = Cbju.FatLevel.BAD
                1 -> model.fatLevel = Cbju.FatLevel.UNIVERSAL
            }
            activity?.hideKeyboard()
        }
    }

    fun setWaterSpinner() {
        val items = listOf(
            SpinnerItem(0, "Универсальный уровень"),
            SpinnerItem(1, "Средние нагрузки"),
            SpinnerItem(2, "Высокие нагрузки")
        )

        waterSpinner.adapter = SpinnerAdapter(requireContext(), items)
        waterSpinner.onSelect {
            when (it) {
                0 -> model.waterLevel = Cbju.WaterLevel.UNIVERSAL
                1 -> model.waterLevel = Cbju.WaterLevel.MIDDLE
                2 -> model.waterLevel = Cbju.WaterLevel.HIGH
            }
            activity?.hideKeyboard()
        }
    }


    fun setViewsByModel() {
        etWeight.setText(model.weight.text())
        setSpinnersByModel()
        setResultsByModel()
    }

    fun setModelByViews() =
        model.run {
            weight = etWeight.double()
            controller.save(this)
        }

    fun setSpinnersByModel() =
        model.run {
            activitySpinner.setSelection(activityLevel.ordinal, false)
            deficitSpinner.setSelection(deficitLevel.ordinal, false)
            proteinsSpinner.setSelection(proteinLevel.ordinal, false)
            fatsSpinner.setSelection(fatLevel.ordinal, false)
            waterSpinner.setSelection(waterLevel.ordinal, false)
        }

    private fun setResultsByModel() =
        model.run {
            tvCalories.text = calories().toInt().toString()
            tvProteins.text = proteins().toInt().toString()
            tvFats.text = fats().toInt().toString()
            tvCarbs.text = carbs().toInt().toString()
            tvWater.text = water().toInt().toString()
        }

}