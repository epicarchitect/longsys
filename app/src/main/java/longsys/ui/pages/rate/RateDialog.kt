package longsys.ui.pages.rate

import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.rate_dialog.*
import longsys.R
import longsys.controllers.courses.CoursesController
import longsys.controllers.rate.RateController
import longsys.extentions.*
import java.util.*


class RateDialog : DialogFragment() {

    val controller = RateController(app())

    var isSecondOpened = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.rate_dialog, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        yesButton.setOnClickListener {
            controller.setRated(true)
            goToAppInStore()
            dismiss()
        }

        laterButton.setOnClickListener {
            dismiss()
        }

        noButton.setOnClickListener {
            dismiss()
        }

        setAsFirstOpened()

        if (lastOpenRateDialogStorage.isEmpty()) {
            setAsFirstOpened()
        } else {
            setAsSecondOpened()
        }

        lastOpenRateDialogStorage.save(
            Calendar.getInstance().run {
                millisecond(0)
                second(0)
                minute(0)
                hour(0)
                timeInMillis
            }
        )
    }

    fun setAsFirstOpened() {
        laterButton.isVisible = true
        noButton.isVisible = false
    }

    fun setAsSecondOpened() {
        laterButton.isVisible = false
        noButton.isVisible = true
        isSecondOpened = true
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (isSecondOpened) {
            controller.setRated(true)
        }
    }

    fun goToAppInStore() {
        activity?.run {
            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$packageName")
                    )
                )
            } catch (e: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=$packageName")
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Не удалось открыть Play market", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val FIRST_WAIT_DAYS = 20
        const val FIRST_WAIT_MILLIS = COUNT_MILLIS_IN_DAY * FIRST_WAIT_DAYS

        const val SECOND_WAIT_DAYS = 30
        const val SECOND_WAIT_MILLIS = COUNT_MILLIS_IN_DAY * SECOND_WAIT_DAYS

        val lastOpenRateDialogStorage = LastOpenRateDialogStorage(app())

        fun toast(text: String) {
            //Toast.makeText(app(), text, Toast.LENGTH_SHORT).show()
            Log.d("test", text)
        }

        fun needOpen(): Boolean {
            toast("RateController.isRated: ${RateController(app()).isRated()}")

            if (RateController(app()).isRated()) return false

            val todayTime = Calendar.getInstance().run {
                millisecond(0)
                second(0)
                minute(0)
                hour(0)
                timeInMillis
            }


            toast("openTimeStorage.isEmpty: ${lastOpenRateDialogStorage.isEmpty()}")

            if (lastOpenRateDialogStorage.isEmpty()) {
                toast("firstCourseInstallationTime != null: ${CoursesController(app()).getFirstCourseInstallationTime() != null}")
                val firstCourseInstallationTime = CoursesController(app()).getFirstCourseInstallationTime() ?: return false

                toast("todayTime >= firstCourseInstallationTime + FIRST_WAIT_MILLIS: ${todayTime >= firstCourseInstallationTime + FIRST_WAIT_MILLIS}")
                return todayTime >= firstCourseInstallationTime + FIRST_WAIT_MILLIS
            }

            val lastOpenTime = lastOpenRateDialogStorage.get() ?: return false

            toast("todayTime >= lastOpenTime + SECOND_WAIT_MILLIS: ${todayTime >= lastOpenTime + SECOND_WAIT_MILLIS}")

            return todayTime >= lastOpenTime + SECOND_WAIT_MILLIS
        }
    }
}