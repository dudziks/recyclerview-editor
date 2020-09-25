package com.mdlab.recyclervieweditor

import com.mdlab.recyclervieweditor.model.Line

interface IWriter {
    fun write(list: List<Line>)
}