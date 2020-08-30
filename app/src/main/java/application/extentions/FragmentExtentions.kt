package application.extentions

import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import application.App
import kotlin.math.roundToInt

fun Fragment.dp(dp: Int): Int {
    activity?.let {
        return (it.resources.displayMetrics.density * dp).roundToInt()
    }
    return 0
}

fun Fragment.isNavControllerIn(resId: Int) = findNavController().currentDestination?.let { resId == it.id } ?: false

fun Fragment.navigate(resId: Int) = findNavController().navigate(resId)

fun Fragment.disableViews(vararg views: View) {
    views.forEach { it.disable() }
}

fun Fragment.enableViews(vararg views: View) {
    views.forEach { it.enable() }
}

fun Fragment.hideViews(vararg views: View) {
    views.forEach { it.hide() }
}

fun Fragment.showViews(vararg views: View) {
    views.forEach { it.show() }
}

fun Fragment.app(): App = App.instance!!

fun DialogFragment.show(f: Fragment) = show(f.parentFragmentManager, "")