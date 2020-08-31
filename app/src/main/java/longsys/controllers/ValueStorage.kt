package longsys.controllers

import android.content.Context

abstract class ValueStorage<T>(context: Context) {

    val valueName = javaClass.simpleName

    val preferences = context.getSharedPreferences(valueName, Context.MODE_PRIVATE)

    abstract fun save(value: T?)

    abstract fun get(): T?

    fun isEmpty() = get() == null

    fun clear() = save(null)

}