package longsys.ui.pages.consent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.consent_fragment.*
import longsys.R

class ConsentFragment : Fragment() {

    private var onDoneListener: () -> Unit = {}

    fun onDone(l: () -> Unit) {
        onDoneListener = l
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.consent_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retainInstance = true

        checkbox.setOnCheckedChangeListener { _, isChecked ->
            buttonDone.isEnabled = isChecked
        }

        buttonDone.setOnClickListener {
            if (checkbox.isChecked) {
                onDoneListener()
            }
        }
    }

}