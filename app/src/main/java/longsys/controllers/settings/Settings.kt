package longsys.controllers.settings

import longsys.R
import longsys.controllers.settings.Settings.ColorAccent.*
import longsys.controllers.settings.Settings.Theme.DARK
import longsys.controllers.settings.Settings.Theme.LIGHT

data class Settings(
    var theme: Theme = DARK,
    var colorAccent: ColorAccent = BLUE
) {

    enum class Theme { DARK, LIGHT }

    enum class ColorAccent { BLUE, GREEN, YELLOW }

    fun themeId() = when (theme) {
        LIGHT -> when (colorAccent) {
            BLUE -> R.style.BlueLightTheme
            GREEN -> R.style.GreenLightTheme
            YELLOW -> R.style.YellowLightTheme
        }

        DARK -> when (colorAccent) {
            BLUE -> R.style.BlueDarkTheme
            GREEN -> R.style.GreenDarkTheme
            YELLOW -> R.style.YellowDarkTheme
        }
    }

    fun dialogThemeId() = when (theme) {
        LIGHT -> when (colorAccent) {
            BLUE -> R.style.BlueLightDialog
            GREEN -> R.style.GreenLightDialog
            YELLOW -> R.style.YellowLightDialog
        }

        DARK -> when (colorAccent) {
            BLUE -> R.style.BlueDarkDialog
            GREEN -> R.style.GreenDarkDialog
            YELLOW -> R.style.YellowDarkDialog
        }
    }

    fun pickerDialogThemeId() = when (theme) {
        LIGHT -> when (colorAccent) {
            BLUE -> R.style.BlueLightPickerDialog
            GREEN -> R.style.GreenLightPickerDialog
            YELLOW -> R.style.YellowLightPickerDialog
        }

        DARK -> when (colorAccent) {
            BLUE -> R.style.BlueDarkPickerDialog
            GREEN -> R.style.GreenDarkPickerDialog
            YELLOW -> R.style.YellowDarkPickerDialog
        }
    }

}