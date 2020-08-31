package longsys.ui.pages.rate

import android.content.Context
import androidx.core.content.edit
import longsys.controllers.ValueStorage

class LastOpenRateDialogStorage(context: Context) : ValueStorage<Long>(context) {

    override fun save(value: Long?) {
        preferences.edit {
            if (value != null)
                putLong(valueName, value)
            else
                clear()
        }
    }

    override fun get() =
        if (preferences.contains(valueName))
            preferences.getLong(valueName, 0)
        else
            null

}