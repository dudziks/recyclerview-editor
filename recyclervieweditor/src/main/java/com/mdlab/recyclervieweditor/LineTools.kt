package com.mdlab.recyclervieweditor

import com.mdlab.recyclervieweditor.model.Line
import java.util.*


object LineTools {
    const val TODO_DONE = "- [x] "
    const val TODO_NOT_DONE = "- [ ] "
    const val SEP="\n"

    fun isTodo(str: String): Boolean = str.toLowerCase(Locale.ROOT).startsWith(TODO_DONE) || str.toLowerCase(Locale.ROOT).startsWith(TODO_NOT_DONE)
    private fun isTodoDone(str: String): Boolean = str.toLowerCase(Locale.ROOT).startsWith(TODO_DONE)

    private fun extractText(str: String): String = if (isTodo(str)) str.substring(TODO_DONE.length) else str
    private fun createLine(str: String): Line = if(isTodo(str)) Line(extractText(str), isTodoDone(str)) else Line(text=str)
    fun stringToLines(str: String): List<Line> = str.split(SEP).toList().map { createLine(it)}
    fun linesToString(lines: List<Line>): String = lines.map{it.toString()}.joinToString(SEP)
}