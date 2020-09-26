package com.mdlab.recyclervieweditor

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mdlab.recyclervieweditor.databinding.LineItemBinding
import com.mdlab.recyclervieweditor.model.Line

class EditLineAdapter(private val updater: IWriter, private val positionStatus: IPositionStatus, val scroll: (it: Int) -> Unit = {}):
    RecyclerView.Adapter<EditLineViewHolder>(),
    IStoreData {
    companion object {        const val TAG = "EditLineAdapter"    }
    private var _data = mutableListOf<Line>()

    private val data: List<Line> get() = _data

    override fun getItemCount(): Int = data.size
    override fun onBindViewHolder(holder: EditLineViewHolder, position: Int) = holder.bind(_data, position, positionStatus)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditLineViewHolder =
        EditLineViewHolder(
            LineItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false), this
        )

    override fun storeData(pos: Int) {
        Log.d(TAG, "[storeData]: $pos")
        updater.write(data)
        notifyItemInserted(pos)
        scroll(pos)
    }

    /**
     * Join current line with the upper line.
     */
    override fun joinWithUpperLine(pos: Int) {
        if (pos > 0) {
            data[pos - 1].text += data[pos].text
            _data.removeAt(pos)
            updater.write(data)
            notifyItemChanged(pos - 1)
            notifyItemRemoved(pos)
        }
    }

    override fun notifyChanged(pos: Int) = notifyItemChanged(pos)

    fun setData(text: String) =
        _data.run {
            clear()
            addAll( LineTools.stringToLines(text) )
        }
}