package com.example.financetracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.financetracker.databinding.ActivityMainBinding
import com.example.financetracker.ui.TransactionViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: TransactionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = TransactionViewModel(this)

        // Inside onCreate()
val adapter = TransactionAdapter(listOf())  // empty initially
binding.rvTransactions.adapter = adapter

binding.btnAddTransaction.setOnClickListener {
    // Example: add a dummy transaction
    val transaction = Transaction(
        title = "Groceries",
        amount = 50.0,
        category = "Food",
        date = System.currentTimeMillis()
    )

    // save using ViewModel
    CoroutineScope(Dispatchers.IO).launch {
        viewModel.addTransaction(transaction)

        // refresh UI
        val updatedList = viewModel.transactions.value ?: listOf()
        withContext(Dispatchers.Main) {
            binding.rvTransactions.adapter = TransactionAdapter(updatedList)
        }
    }
}
        
        
        // TODO: Display transaction list
        // TODO: Add new transaction
        // TODO: Delete transaction
    }
}
