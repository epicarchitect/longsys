package application.ui.pages.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import application.controllers.settings.Settings
import application.controllers.settings.SettingsController
import application.extentions.app
import application.ui.MainActivity
import kotlinx.android.synthetic.main.settings_fragment.*
import longsys.bb35.extreme.bulk.max.R

class SettingsFragment : Fragment() {

    lateinit var settings: Settings
    var selectedColorAccent = Settings.ColorAccent.BLUE

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.settings_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val controller = SettingsController(app())
        settings = controller.getSettings()

        setAccentColor(settings.colorAccent)

        when (settings.theme) {
            Settings.Theme.LIGHT -> radioThemes.check(R.id.radioLight)
            Settings.Theme.DARK -> radioThemes.check(R.id.radioDark)
        }

        buttonBlue.setOnClickListener {
            setAccentColor(Settings.ColorAccent.BLUE)
        }

        buttonGreen.setOnClickListener {
            setAccentColor(Settings.ColorAccent.GREEN)
        }

        buttonYellow.setOnClickListener {
            setAccentColor(Settings.ColorAccent.YELLOW)
        }

        buttonSave.setOnClickListener {
            when (radioThemes.checkedRadioButtonId) {
                R.id.radioLight -> settings.theme = Settings.Theme.LIGHT
                R.id.radioDark -> settings.theme = Settings.Theme.DARK
            }

            settings.colorAccent = selectedColorAccent

            controller.save(settings)
            requireActivity().apply {
                finish()
                startActivity(
                    Intent(this, MainActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    }
                )
            }
        }
    }

    fun setAccentColor(color: Settings.ColorAccent) {
        when (color) {
            Settings.ColorAccent.BLUE -> {
                buttonBlue.setImageResource(R.drawable.ic_done)
                buttonGreen.setImageResource(R.drawable.ic_void)
                buttonYellow.setImageResource(R.drawable.ic_void)
            }

            Settings.ColorAccent.GREEN -> {
                buttonBlue.setImageResource(R.drawable.ic_void)
                buttonGreen.setImageResource(R.drawable.ic_done)
                buttonYellow.setImageResource(R.drawable.ic_void)
            }

            Settings.ColorAccent.YELLOW -> {
                buttonBlue.setImageResource(R.drawable.ic_void)
                buttonGreen.setImageResource(R.drawable.ic_void)
                buttonYellow.setImageResource(R.drawable.ic_done)
            }
        }
        selectedColorAccent = color
    }

}