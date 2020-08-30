package application.controllers.settings

import application.controllers.settings.Settings.ColorAccent.*
import application.controllers.settings.Settings.Theme.DARK
import application.controllers.settings.Settings.Theme.LIGHT
import longsys.bb35.extreme.bulk.max.R

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