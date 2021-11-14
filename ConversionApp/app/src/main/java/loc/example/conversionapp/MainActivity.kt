package loc.example.conversionapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
    private val spinner by lazy<Spinner> { findViewById(R.id.spinner) }
    private val rates = mutableMapOf<String, Double>()
    private val rateItems = mutableListOf<String>()
    private val editText by lazy<EditText> { findViewById(R.id.editText) }
    private val recyclerView by lazy<RecyclerView> { findViewById(R.id.recyclerView) }
    private val currencyAdapter by lazy { CurrencyAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.currencyData.observe(this) {
            Log.d(TAG, "data: ${it}")
            updateSpinner(it.rates)
        }

        viewModel.updatedRateMap.observe(this) {
            Log.d(TAG, "updated rates: ${it}")
            updateList(it)
        }

        viewModel.fetchCurrencies()

        spinner.onItemSelectedListener = this

        // recycler view..
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.adapter = currencyAdapter
    }

    fun updateSpinner(rates: Map<String, Double>) {
        rates.map {
            this.rates[it.key] = it.value
            rateItems.add(it.key)
        }
        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, rateItems)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val rateKey = rateItems[position]
        val rateVal = rates[rateKey]
        Toast.makeText(this, "Rate amount: ${rateVal}", Toast.LENGTH_SHORT).show()

        if (!TextUtils.isEmpty(editText.text.toString())) {
            val euro = viewModel.getEuro(editText.text.toString(), rateVal)
            viewModel.getConvertedRates(euro, rates)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    fun updateList(map: Map<String, Double>) {
        val list = map.map {
            "${it.key} - ${it.value}"
        }
        currencyAdapter.updateItems(list)
    }

    class CurrencyAdapter : RecyclerView.Adapter<CurrencyViewHolder>() {
        private val items = mutableListOf<String>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val itemView: View = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
            return CurrencyViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
            val item = items[position]
            holder.onBindData(item)
        }

        override fun getItemCount() = items.size

        fun updateItems(list: List<String>) {
            items.clear()
            items.addAll(list)
            notifyItemRangeChanged(0, items.size)
        }
    }

    class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView by lazy<TextView> { itemView.findViewById(android.R.id.text1) }

        fun onBindData(item: String) {
            textView.text = item
        }
    }
}