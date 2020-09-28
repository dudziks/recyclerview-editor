package com.mdlab.recyclervieweditor

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.mdlab.recyclervieweditor.component.EditTextK
import com.mdlab.recyclervieweditor.databinding.LineItemBinding
import com.mdlab.recyclervieweditor.model.Line

class EditLineViewHolder(private val binding: LineItemBinding, private val storeDataObj: IStoreData) : RecyclerView.ViewHolder(binding.root) {
    companion object {
        const val TAG = "EditLineViewHolder"
    }

    fun bind(lines: List<Line>, pos: Int, posStatus: IPositionStatus) {

        binding.lineModel = lines[pos]
        binding.editText.apply {
            if (pos == posStatus.getPos()) {
                requestFocus()
            }
            setProcessKeyDelMethod { keyEvent: KeyEvent ->
                keyDelPressed(keyEvent, lines, pos, posStatus)
            }
            setProcessKeyEnterMethod {
                Log.d(TAG, "Enter pressed!")
                keyEnterPressed(lines, pos, posStatus)
            }
            setOnKeyListener { v, keyCode, event ->
                Log.d(TAG, "key pressed")
                if (event.action == KeyEvent.ACTION_UP && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                    Log.d(TAG, "Enter")
                    keyEnterPressed(lines, pos, posStatus)
                    true
                } else {
                    false
                }
            }
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
                override fun afterTextChanged(s: Editable?) {
                    if (pos < lines.size) {
                        // jesli model zawiera \n -> reload
                        if(lines[pos].hasNL()){
                            val textStr = lines[pos].text
                            if (LineTools.isTodo(textStr) || lines[pos].todo != null) {
                                lines[pos].text = textStr.replace(LineTools.SEP, LineTools.SEP + LineTools.TODO_NOT_DONE)
                            }
                            storeDataObj.storeData(pos+1)
                            posStatus.setPos(pos + 1)
                        }
                    }
                }
            })
        }

        binding.checkBox.setOnClickListener {
            val ch = it as CheckBox
            lines[pos].todo = ch.isChecked
            storeDataObj.storeData(0)
        }

        binding.executePendingBindings()
    }

    private fun EditTextK.keyEnterPressed(lines: List<Line>, pos: Int, posStatus: IPositionStatus) {
        Log.d(TAG, "keyEnterPressed")
        val caret = selectionStart
        text?.replace(caret, caret, LineTools.SEP)
        storeDataObj.storeData(pos + 1)
        posStatus.setPos(pos + 1)
    }

    private fun keyDelPressed(keyEvent: KeyEvent, lines: List<Line>, pos: Int, posStatus: IPositionStatus) {
        Log.d(TAG, "keyDelPressed")
        if (keyEvent.action == KeyEvent.ACTION_UP && keyEvent.keyCode == KeyEvent.KEYCODE_DEL) {
            if (lines[pos].todo != null) {
                lines[pos].todo = null
                storeDataObj.notifyChanged(pos)
            } else {
                storeDataObj.joinWithUpperLine(pos)
                if (pos > 0)
                    posStatus.setPos(pos - 1)
            }
        }
    }
}