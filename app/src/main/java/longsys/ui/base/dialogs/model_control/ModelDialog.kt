package longsys.ui.base.dialogs.model_control

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.base_model_dialog.*
import longsys.R
import longsys.controllers.BaseModel
import longsys.extentions.hide
import longsys.extentions.hideViews
import longsys.extentions.show
import longsys.ui.base.dialogs.confirm.ConfirmDialog

abstract class ModelDialog<M: BaseModel> : DialogFragment() {

    lateinit var model: M

    abstract val innerLayout: Int
    lateinit var innerView: View

    var mode = Mode.VIEW

    val isModeView get() = mode == Mode.VIEW
    val isModeEdit get() = mode == Mode.EDIT

    var dismissAfterSave = true
    var deleteEnabled = true
    var editEnabled = true
    var cancelEnabled = true
    var hideControlls = false

    var isReady = false
    val onReadyListeners: ArrayList<() -> Unit> = ArrayList()

    fun onReady(l: () -> Unit) {
        if (isReady) l()
        else onReadyListeners.add(l)
    }

    fun ready() {
        isReady = true
        onReadyListeners.forEach { it() }
        onReadyListeners.clear()
    }

    var isImmutable = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.base_model_dialog, container, false)

    abstract fun start()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retainInstance = true
        innerView = createInnerView(requireActivity())
        innerContainer.removeAllViews()
        innerContainer.addView(innerView)

        model = arguments?.getSerializable("model") as M

        savedInstanceState?.let { state ->
            model = state.getSerializable("model") as M
            mode = if (state.getInt("mode", Mode.VIEW.ordinal) == Mode.VIEW.ordinal) Mode.VIEW else Mode.EDIT
            isImmutable = state.getBoolean("isImmutable")
            dismissAfterSave = state.getBoolean("dismissAfterSave")
            deleteEnabled = state.getBoolean("deleteEnabled")
            editEnabled = state.getBoolean("editEnabled")
            cancelEnabled = state.getBoolean("cancelEnabled")
            hideControlls = state.getBoolean("hideControlls")
        }

        buttonDelete.setOnClickListener { delete() }
        buttonCancel.setOnClickListener { cancel() }
        buttonEdit.setOnClickListener { edit() }
        buttonSave.setOnClickListener { save() }

        start()
        setViewsByModel()
        ready()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("mode", mode.ordinal)
        outState.putSerializable("model", createModelByViews(model))
        outState.putBoolean("isImmutable", isImmutable)
        outState.putBoolean("dismissAfterSave", dismissAfterSave)
        outState.putBoolean("deleteEnabled", deleteEnabled)
        outState.putBoolean("editEnabled", editEnabled)
        outState.putBoolean("cancelEnabled", cancelEnabled)
        outState.putBoolean("hideControlls", hideControlls)
    }

    override fun onResume() {
        super.onResume()

        if (isImmutable) {
            hideViews(buttonCancel, buttonDelete, buttonSave, buttonEdit)
            setModeView()
        } else {
            if (mode == Mode.EDIT) {
                buttonCancel.run { if (cancelEnabled) show() else hide() }
                buttonSave.show()
                hideViews(buttonEdit, buttonDelete)

            } else {
                buttonEdit.run { if (editEnabled) show() else hide() }
                buttonDelete.run { if (deleteEnabled) show() else hide() }
                hideViews(buttonCancel, buttonSave)
            }
        }

        if (hideControlls) {
            hideViews(buttonCancel, buttonDelete, buttonSave, buttonEdit)
        }
    }

    fun createInnerView(activity: Activity) = LayoutInflater.from(activity).inflate(innerLayout, innerContainer, false)

    abstract fun setViewsByModel()
    abstract fun createModelByViews(oldModel: M): M

    abstract fun onModeView()
    abstract fun onModeEdit()

    open fun setModeView() {
        mode = Mode.VIEW
        onReady {
            if (!isImmutable) {
                buttonEdit.run { if (editEnabled) show() else hide() }
                buttonDelete.run { if (deleteEnabled) show() else hide() }
                hideViews(buttonCancel, buttonSave)
            }
            onModeView()
            setViewsByModel()
        }
    }

    open fun setModeEdit() {
        mode = Mode.EDIT
        onReady {
            if (!isImmutable) {
                buttonCancel.run { if (cancelEnabled) show() else hide() }
                buttonSave.show()
                hideViews(buttonEdit, buttonDelete)
                onModeEdit()
            }
        }
    }

    open fun onSave(model: M) {}
    open fun onEdit(model: M) {}
    open fun onDelete(model: M) {}
    open fun onCancel(model: M) {}

    fun edit() {
        setModeEdit()
        onEdit(model)
    }

    fun save() {
        model = createModelByViews(model)
        onSave(model)
        setModeView()
        if (dismissAfterSave)
            dismiss()
    }

    fun cancel() {
        dismiss()
        onCancel(model)
    }

    fun delete() {
        ConfirmDialog {
            if (it) {
                onDelete(model)
                dismiss()
            }
        }.show(parentFragmentManager, "")
    }

    fun putModel(m: M) {
        arguments = bundleOf("model" to m)
    }

}