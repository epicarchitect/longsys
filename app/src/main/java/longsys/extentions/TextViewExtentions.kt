package longsys.extentions

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView

fun TextView.colorize(startIndex: Int, endIndex: Int, color: Int) {
    text = SpannableString(text).apply {
        setSpan(
            ForegroundColorSpan(color),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
}