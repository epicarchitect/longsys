package longsys.ui.pages.analyse_groups

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.analyse_groups_fragment.*
import longsys.R
import longsys.controllers.analyse_groups.AnalyseGroupsController
import longsys.extentions.app

@Suppress("NON_EXHAUSTIVE_WHEN")
class AnalyseGroupsFragment : Fragment() {

    val listAdapter = AnalyseGroupsListAdapter()

    lateinit var controller: AnalyseGroupsController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.analyse_groups_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controller = AnalyseGroupsController(app())

        setAdapter()

        fab.setOnClickListener {
            val dialog = AnalyseGroupDialog.build()
            dialog.setModeEdit()
            dialog.show(parentFragmentManager, "")
        }

        listAdapter.onClickShow { model ->
            if (findNavController().currentDestination?.id == R.id.analyseGroupsFragment) {
                val action =
                    AnalyseGroupsFragmentDirections.actionAnalyseGroupsFragmentToAnalysesFragment(
                        model.id
                    )
                findNavController().navigate(action)
                activity?.title = model.name
            }
        }


        listAdapter.onClick { model, _ ->
            val dialog = AnalyseGroupDialog.build(model)
            dialog.isImmutable = model.isImmutable
            dialog.setModeView()
            dialog.show(parentFragmentManager, "")
        }
    }

    fun setAdapter() {
        rv.adapter = listAdapter
        rv.layoutManager = LinearLayoutManager(context)
        controller.getLiveAnalyseGroups().observe(viewLifecycleOwner, Observer {
            listAdapter.list = it
        })
    }

}