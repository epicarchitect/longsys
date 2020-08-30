package application.controllers

class ModelObserver<M> {

    val listeners = HashMap<Int, ArrayList<(M) -> Unit>>()

    fun notify(key: Int, model: M) =
        listeners[key]?.forEach {
            it(model)
        }

    fun observe(key: Int, l: (M) -> Unit): ModelObserver<M> {
        val list = listeners[key] ?: ArrayList()
        list.add(l)
        listeners[key] = list
        return this
    }

}