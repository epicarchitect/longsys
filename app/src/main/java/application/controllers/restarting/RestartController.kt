package application.controllers.restarting

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager

class RestartController private constructor(val context: Context) {

    fun restart() {
        WorkManager.getInstance(context)
            .enqueueUniqueWork(
                "RestartController",
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequest.from(RestartWorker::class.java)
            )
    }

    companion object {
        var instance: RestartController? = null

        operator fun invoke(context: Context) =
            instance ?: RestartController(context.applicationContext).also {
                instance = it
            }
    }
}