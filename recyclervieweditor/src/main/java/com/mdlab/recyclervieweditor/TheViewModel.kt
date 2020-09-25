package com.mdlab.recyclervieweditor

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mdlab.recyclervieweditor.model.Line

class TheViewModel: ViewModel(), IPositionStatus, IWriter {
    companion object { const val TAG = "TheViewModel" }

    var lines: MutableLiveData<String> = MutableLiveData()
    private var position: Int = 0

    fun setData(txt: String) {
        lines.value = txt
    }

    override fun setPos(pos: Int) {
        position = pos
        Log.d(TAG, "Set pos [$pos]")
    }

    override fun getPos(): Int {
        Log.d(TAG, "Get pos [$position]")
        return position
    }

    override fun write(list: List<Line>) {
        val str = LineTools.linesToString(list)
        setData(str)
    }
}