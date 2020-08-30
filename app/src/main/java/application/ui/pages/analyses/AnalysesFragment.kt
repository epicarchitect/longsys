package application.ui.pages.analyses

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import application.constants.VOID_ID
import application.controllers.analyse_groups.AnalyseGroupModel
import application.controllers.analyses.AnalyseModel
import application.controllers.analyses.AnalysesController
import application.extentions.app
import kotlinx.android.synthetic.main.analyse_types_fragment.*
import longsys.bb35.extreme.bulk.max.R

class AnalysesFragment : Fragment() {

    val listAdapter = AnalysesListAdapter()

    lateinit var controller: AnalysesController

    val groupId get() = arguments?.let {
        AnalysesFragmentArgs.fromBundle(
            it
        ).groupId
    } ?: VOID_ID


    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.analyse_types_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        controller = AnalysesController(app())

        setAdapter()

        fab.setOnClickListener {
            val dialog = AnalyseDialog.build(
                AnalyseModel(
                    group = AnalyseGroupModel(
                        id = groupId
                    )
                )
            )
            dialog.setModeEdit()
            dialog.show(parentFragmentManager, "")
        }

        listAdapter.onClick { model, _ ->
            val dialog = AnalyseDialog.build(
                model.copy(
                    group = AnalyseGroupModel(
                        id = groupId
                    )
                )
            )
            dialog.isImmutable = model.isImmutable
            dialog.setModeView()
            dialog.show(parentFragmentManager, "")
        }
    }

    fun setAdapter() {
        rv.adapter = listAdapter
        rv.layoutManager = LinearLayoutManager(context)
        controller.getLiveAnalysesByGroupId(groupId).observe(viewLifecycleOwner, Observer {
            listAdapter.list = it
        })
    }

}