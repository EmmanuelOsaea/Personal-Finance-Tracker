package com.example.financetracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.financetracker.data.Transaction
import com.example.financetracker.databinding.ActivityMainBinding
import com.example.financetracker.ui.TransactionViewModel
import com.example.financetracker.ui.components.TransactionAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: TransactionViewModel
    private lateinit var adapter: TransactionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = TransactionViewModel(this)

        adapter = TransactionAdapter(listOf()) { transaction ->
            viewModel.removeTransaction(transaction)
        }

        binding.rvTransactions.layoutManager = LinearLayoutManager(this)
        binding.rvTransactions.adapter = adapter

        // Observe transactions
        viewModel.transactions.observe(this, Observer { list ->
            adapter.updateList(list)
            binding.tvSummary.text = "Total: $${String.format("%.2f", viewModel.getTotalAmount())}"
        })

        // Add dummy transaction for testing
        binding.btnAddTransaction.setOnClickListener {
            val transaction = Transaction(
                title = "New Expense",
                amount = (10..200).random().toDouble(),
                category = "Misc",
                date = System.currentTimeMillis()
            )
            viewModel.addTransaction(transaction)

       // Add a Spinner for categories in activity_main.xml above RecyclerView
<Spinner
    android:id="@+id/spinnerCategory"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginBottom="12dp" />

// In MainActivity.kt
val categories = listOf("All", "Food", "Transport", "Entertainment", "Misc")
val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
binding.spinnerCategory.adapter = spinnerAdapter

binding.spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedCategory = categories[position]
        val filteredList = if (selectedCategory == "All") {
            viewModel.transactions.value ?: listOf()
        } else {
            viewModel.transactions.value?.filter { it.category == selectedCategory } ?: listOf()
        }
        adapter.updateList(filteredList)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}
        }
    }
}


