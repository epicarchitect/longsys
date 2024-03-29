package longsys.ui.pages.drug_box

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.drug_box_fragment.*
import longsys.R
import longsys.controllers.drug_groups.DrugGroupModel
import longsys.controllers.drug_groups.DrugGroupsController
import longsys.controllers.drugs.DrugModel
import longsys.controllers.drugs.DrugsController
import longsys.extentions.app
import longsys.extentions.hide
import longsys.extentions.show
import longsys.ui.MainActivity
import longsys.ui.pages.drug_box.drugs.DrugDialog
import longsys.ui.pages.drug_box.drugs.DrugsListAdapter
import longsys.ui.pages.drug_box.groups.DrugGroupDialog
import longsys.ui.pages.drug_box.groups.DrugGroupsListAdapter

class DrugBoxFragment : Fragment() {

    lateinit var groupsController: DrugGroupsController
    lateinit var drugsController: DrugsController

    val backCallback = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            showGroups()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.drug_box_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        groupsController = DrugGroupsController(app())
        drugsController = DrugsController(app())

        MainActivity.instance
            ?.onBackPressedDispatcher
            ?.addCallback(viewLifecycleOwner, backCallback)

        rv.layoutManager = LinearLayoutManager(context)
        showGroups()
        buttonBack.setOnClickListener {
            showGroups()
        }
    }

    fun showGroups() {
        backCallback.isEnabled = false
        tvTitle.text = "Группы препаратов"
        buttonBack.hide()
        rv.adapter = DrugGroupsListAdapter().apply {
            onClickShow { model ->
                showDrugs(model)
            }

            onClick { model, _ ->
                val dialog = DrugGroupDialog.build(model)
                dialog.isImmutable = model.isImmutable
                dialog.setModeView()
                dialog.show(parentFragmentManager, "")
            }

            groupsController.getLiveDrugGroups().observe(viewLifecycleOwner, Observer {
                list = it
            })
        }

        fab.setOnClickListener {
            DrugGroupDialog.build().apply {
                setModeEdit()
            }.show(parentFragmentManager, "")
        }
    }

    fun showDrugs(group: DrugGroupModel) {
        backCallback.isEnabled = true
        tvTitle.text = group.name
        buttonBack.show()
        rv.adapter = DrugsListAdapter().apply {
            onClick { model, _ ->
                DrugDialog.build(model.copy(group = group)).apply {
                    setModeView()
                    isImmutable = model.isImmutable
                }.show(this@DrugBoxFragment)
            }

            drugsController.getLiveDrugsByGroupId(group.id).observe(viewLifecycleOwner, Observer {
                list = it
            })
        }

        fab.setOnClickListener {
            DrugDialog.build(DrugModel(group = group)).apply {
                setModeEdit()
            }.show(parentFragmentManager, "")
        }
    }

}