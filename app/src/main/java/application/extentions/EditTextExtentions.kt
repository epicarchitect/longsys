package application.extentions

import android.text.Editable
import android.text.TextWatcher
import android.view.View.OnFocusChangeListener
import android.widget.EditText


fun EditText.onTextChanged(l: (text: String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, c: Int) {
            l(s.toString())
        }
    })
}

fun EditText.text() = text(true)

fun EditText.text(withTrim: Boolean) = if (withTrim) text.trim().toString() else text.toString()

fun EditText.integer(): Int {
    var str = text.toString()
    try {
        str = str.toInt().toString() + ""
    } catch (e: Exception) {
        e.printStackTrace()
    }
    if (str == "") setText("0")
    var value = 0
    try {
        value = str.toInt()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return value
}

fun EditText.double(): Double {
    var str = text.toString().replace(",", ".")
    try {
        str = str.toDouble().toString() + ""
    } catch (e: Exception) {
        e.printStackTrace()
    }
    if (str == "") setText("")
    var value = 0.0
    try {
        value = str.toDouble()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return value
}

fun EditText.removeZeroOnClick() {
    onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
        if (hasFocus && integer() == 0) {
            setText("")
        }
    }
}

fun EditText.removeDoubleZeroOnClick() {
    onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
        if (hasFocus && double() == 0.0) {
            setText("")
        }
    }
}