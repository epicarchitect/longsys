package application.controllers.settings

import android.content.Context
import com.google.gson.Gson

class SettingsController private constructor(context: Context) {

    val preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)

    fun getSettings() =
        preferences.getString(KEY, "").let { json ->
            when {
                json == null || json.isEmpty() -> Settings()
                else -> Gson().fromJson(json, Settings::class.java)
            }
        }

    fun save(settings: Settings) =
        preferences.edit().putString(KEY, Gson().toJson(settings)).apply()

    companion object {
        const val NAME = "SettingsController"
        const val KEY = "Settings"

        var instance: SettingsController? = null

        operator fun invoke(context: Context) =
            instance ?: SettingsController(context.applicationContext).also {
                instance = it
            }
    }
}