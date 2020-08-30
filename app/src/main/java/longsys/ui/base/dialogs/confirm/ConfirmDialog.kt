package longsys.ui.base.dialogs.confirm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.confirm_dialog.*
import longsys.R

class ConfirmDialog(
    val messageRes: Int = R.string.confirm_message,
    val listener: (yes: Boolean) -> Unit
) : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.confirm_dialog, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvMessage.text = getString(messageRes)

        buttonYes.setOnClickListener {
            listener(true)
            dismiss()
        }

        buttonNo.setOnClickListener {
            listener(false)
            dismiss()
        }
    }
}