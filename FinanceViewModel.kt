package com.example.financetracker.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.financetracker.data.AppDatabase
import com.example.financetracker.data.Transaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FinanceViewModel(application: Application) : AndroidViewModel(application) {
    private val transactionDao = AppDatabase.getDatabase(application).transactionDao()
    private val _transactions = MutableLiveData<List<Transaction>>()
    val transactions: LiveData<List<Transaction>> get() = _transactions

    init {
        loadTransactions()
    }

    fun loadTransactions() {
        CoroutineScope(Dispatchers.IO).launch {
            val data = transactionDao.getAllTransactions()
            _transactions.postValue(data)
        }
    }

    fun addTransaction(transaction: Transaction) {
        CoroutineScope(Dispatchers.IO).launch {
            transactionDao.insert(transaction)
            loadTransactions()
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        CoroutineScope(Dispatchers.IO).launch {
            transactionDao.delete(transaction)
            loadTransactions()
        }
    }
}
