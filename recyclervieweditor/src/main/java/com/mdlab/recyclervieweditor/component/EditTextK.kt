package com.mdlab.recyclervieweditor.component

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.view.inputmethod.InputConnectionWrapper

class EditTextK(
        context: Context,
        attrs: AttributeSet
    ):
        androidx.appcompat.widget.AppCompatEditText(context, attrs) {
    companion object {        const val TAG = "EditTextK"    }

    private lateinit var processKey: (event: KeyEvent) -> Unit
    private lateinit var processEnterKey: (id: Int) -> Unit

    fun setProcessKeyDelMethod(processK: (event: KeyEvent) -> Unit){
        processKey = processK
    }

    fun setProcessKeyEnterMethod(processEnterK: (event: Int) -> Unit){
        processEnterKey = processEnterK
    }

    override fun onCreateInputConnection(outAttrs: EditorInfo?): InputConnection {
        return ThisInputConnection(super.onCreateInputConnection(outAttrs), true,  processKey, processEnterKey)
    }

    class ThisInputConnection(target: InputConnection, mutable: Boolean,
                              var processKey: (event: KeyEvent) -> Unit,
                              var processEnterKey: (editorAction: Int) -> Unit

    ) : InputConnectionWrapper(target, mutable) {

        override fun sendKeyEvent(event: KeyEvent?): Boolean {
            Log.d(TAG, "[sendKeyEvent]: $event")
            event?.let {
                processKey(it)
            }
            return super.sendKeyEvent(event)
        }

        override fun performEditorAction(editorAction: Int): Boolean {
            Log.d(TAG, "[performEditorAction]: $editorAction")
            processEnterKey(editorAction)
            return false //super.performEditorAction(editorAction)
        }
    }

}