# recyclerview-editor
Editor with todo capability based on recycler view


Example use

```
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mdlab.recyclervieweditor.EditLineAdapter
import com.mdlab.recyclervieweditor.TheViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val viewModel: TheViewModel by lazy {
        ViewModelProvider(this).get(TheViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.setData("test")
        val theAdapter = EditLineAdapter(viewModel, viewModel) { recyclerView.scrollToPosition(it) }
        recyclerView.run {
            adapter = theAdapter
            layoutManager = LinearLayoutManager(baseContext)
        }
        viewModel.lines.observe(this, {
            theAdapter.setData(it)
            theAdapter.notifyDataSetChanged()
        }
        )
    }
}
```
