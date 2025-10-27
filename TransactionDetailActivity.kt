package com.example.financetracker.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.financetracker.databinding.ActivityTransactionDetailBinding
import com.example.financetracker.data.TransactionEntity
import com.example.financetracker.data.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransactionDetailBinding
    private var transactionId: Int = 0
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getDatabase(this)
        transactionId = intent.getIntExtra("TRANSACTION_ID", 0)

        loadTransactionDetails()

        binding.btnDelete.setOnClickListener {
            deleteTransaction()
        }

        binding.btnSave.setOnClickListener {
            updateTransaction()
        }
    }

    private fun loadTransactionDetails() {
        CoroutineScope(Dispatchers.IO).launch {
            val transaction = db.transactionDao().getAllTransactions().value?.find { it.id == transactionId }
            transaction?.let {
                runOnUiThread {
                    binding.etTitle.setText(it.title)
                    binding.etAmount.setText(it.amount.toString())
                    binding.etCategory.setText(it.category)
                    binding.etDate.setText(it.date)
                }
            }
        }
    }

    private fun updateTransaction() {
        val updated = TransactionEntity(
            id = transactionId,
            title = binding.etTitle.text.toString(),
            amount = binding.etAmount.text.toString().toDoubleOrNull() ?: 0.0,
            category = binding.etCategory.text.toString(),
            date = binding.etDate.text.toString()
        )

        CoroutineScope(Dispatchers.IO).launch {
            db.transactionDao().insert(updated)
            finish()
        }
    }

    private fun deleteTransaction() {
        CoroutineScope(Dispatchers.IO).launch {
            val transaction = TransactionEntity(
                id = transactionId,
                title = binding.etTitle.text.toString(),
                amount = binding.etAmount.text.toString().toDoubleOrNull() ?: 0.0,
                category = binding.etCategory.text.toString(),
                date = binding.etDate.text.toString()
            )
            db.transactionDao().delete(transaction)
            finish()
        }
    }
}
