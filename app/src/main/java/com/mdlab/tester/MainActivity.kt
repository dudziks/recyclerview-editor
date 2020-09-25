package com.mdlab.tester

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mdlab.recyclervieweditor.EditLineAdapter
import com.mdlab.recyclervieweditor.TheViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    companion object {        const val TAG = "MainActivity"    }
    private val teststr = "A" // """A\n- [x] B\n"
    private val viewModel: TheViewModel by lazy {
        ViewModelProvider(this).get(TheViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTestText()
        val theAdapter = EditLineAdapter(viewModel, viewModel) {
            Log.d(TAG, "scrollToPosition: $it")
            recyclerView.scrollToPosition(it)
        }
        recyclerView.run {
            adapter = theAdapter
            layoutManager = LinearLayoutManager(baseContext)
        }
        viewModel.lines.observe(this, {
            Log.d(TAG, "[observer] ${it.replace("\n", "\\n")}" )
            theAdapter.setData(it)
            theAdapter.notifyDataSetChanged()
            }
        )
        resetButton.setOnClickListener {
            viewModel.setData(teststr)
        }
        buttonSave.setOnClickListener {
            theAdapter.storeData(0)
            viewModel.lines.value?.let {
                Log.d(TAG, "Save button pressed:\n${it}")
            }
        }
    }

    private fun setTestText() =  viewModel.setData(teststr)
}