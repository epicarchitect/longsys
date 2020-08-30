package longsys.ui.pages.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.about_fragment.*
import longsys.R
import longsys.extentions.colorize

class AboutFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.about_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        colorizeText1()
        colorizeText19()
        colorizeText25()
        colorizeText27()
        colorizeText28()
    }

    fun colorizeText1() = text1.colorize(93, 164, blue())

    fun colorizeText19() = text19.colorize(75, 128, red())

    fun colorizeText25() = text25.colorize(260, 315, red())

    fun colorizeText27() = text27.colorize(196, 200, red())

    fun colorizeText28() = text28.colorize(80, 101, red())

    fun blue() = ContextCompat.getColor(requireContext(), R.color.blue)

    fun red() = ContextCompat.getColor(requireContext(), R.color.red)

}