package longsys.controllers.rate

import android.content.Context

class RateController(context: Context) {

    private val preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)

    fun isRated() = preferences.getBoolean(KEY, false)

    fun setRated(isRated: Boolean) {
        preferences.edit().putBoolean(KEY, isRated).apply()
    }

    companion object {
        const val NAME = "RateController"
        const val KEY = "isRated"
        var instance: RateController? = null

        operator fun invoke(context: Context) =
            instance ?: RateController(context.applicationContext).also {
                instance = it
            }
    }

}