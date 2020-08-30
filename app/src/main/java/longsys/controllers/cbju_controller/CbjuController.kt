package longsys.controllers.cbju_controller

import android.content.Context
import com.google.gson.Gson

class CbjuController private constructor(context: Context) {

    val preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)

    fun getCbju() =
        preferences.getString(KEY, "").let { json ->
            when {
                json == null || json.isEmpty() -> Cbju()
                else -> Gson().fromJson(json, Cbju::class.java)
            }
        }

    fun save(model: Cbju) =
        preferences.edit().putString(KEY, Gson().toJson(model)).apply()

    companion object {
        const val NAME = "CbjuController"
        const val KEY = "Cbju"

        var instance: CbjuController? = null

        operator fun invoke(context: Context) =
            instance ?: CbjuController(context.applicationContext).also {
                instance = it
            }
    }

}