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
        }
    }
}
