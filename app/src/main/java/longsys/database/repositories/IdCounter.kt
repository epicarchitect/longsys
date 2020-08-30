package longsys.database.repositories

import android.content.Context

class IdCounter(context: Context) {

    val preferences = context.getSharedPreferences("IdCounter", Context.MODE_PRIVATE)

    fun nextId(): Int {
        var id = preferences.getInt("id", 0)
        id++
        preferences.edit().putInt("id", id).apply()
        return id
    }

}