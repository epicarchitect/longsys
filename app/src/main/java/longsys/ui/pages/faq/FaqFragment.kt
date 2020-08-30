package longsys.ui.pages.faq

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.faq_fragment.*
import longsys.R

class FaqFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.faq_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = QuestionsListAdapter().apply {
            list = StaticStorage.list(requireContext())
            onClick { model, _ ->
                val dialog = QuestionDialog(model)
                dialog.show(parentFragmentManager, "")
            }
        }
    }

}