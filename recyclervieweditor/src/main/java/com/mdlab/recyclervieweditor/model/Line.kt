package com.mdlab.recyclervieweditor.model

import com.mdlab.recyclervieweditor.LineTools

data class Line  (var text: String = "", var todo: Boolean?  = null) {

    override fun toString(): String = StringBuilder().apply {
        todo?.let {
            append(if (it) LineTools.TODO_DONE else LineTools.TODO_NOT_DONE)
        }
        append(text)
    }.toString()

    fun hasNL(): Boolean  = text.contains("\n")
}