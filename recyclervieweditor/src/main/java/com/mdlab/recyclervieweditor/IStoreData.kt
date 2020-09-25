package com.mdlab.recyclervieweditor

interface IStoreData {
    fun storeData(pos: Int)
    fun joinWithUpperLine(pos: Int)
    fun notifyChanged(pos: Int)
}