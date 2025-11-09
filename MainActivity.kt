package com.example.financetracker

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.financetracker.data.Transaction
import com.example.financetracker.databinding.ActivityMainBinding
import com.example.financetracker.viewmodel.TransactionViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: TransactionViewModel by viewModels()
    private lateinit var adapter: TransactionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ RecyclerView setup
        adapter = TransactionAdapter { transaction ->
            viewModel.delete(transaction)
            Toast.makeText(this, "Transaction deleted", Toast.LENGTH_SHORT).show()
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // ✅ Observe LiveData
        viewModel.allTransactions.observe(this, Observer {
            adapter.submitList(it)
        })

        viewModel.totalIncome.observe(this, Observer { income ->
            binding.incomeText.text = "Income: ₦${income ?: 0.0}"
        })

        viewModel.totalExpense.observe(this, Observer { expense ->
            binding.expenseText.text = "Expense: ₦${expense ?: 0.0}"
        })

        // ✅ Add new transaction
        binding.addButton.setOnClickListener {
            val title = binding.titleInput.text.toString()
            val amountText = binding.amountInput.text.toString()
            val isExpense = binding.expenseSwitch.isChecked

            if (title.isNotEmpty() && amountText.isNotEmpty()) {
                val amount = amountText.toDoubleOrNull() ?: 0.0
                val transaction = Transaction(
                    title = title,
                    amount = amount,
                    isExpense = isExpense,
                    date = System.currentTimeMillis()
                )
                viewModel.insert(transaction)
                binding.titleInput.text?.clear()
                binding.amountInput.text?.clear()
                binding.expenseSwitch.isChecked = false
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
