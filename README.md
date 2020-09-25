# recyclerview-editor
Editor with todo capability based on recycler view


Example use

```

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
