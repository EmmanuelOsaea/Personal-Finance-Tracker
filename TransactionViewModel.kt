package com.example.financetracker.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker.data.Transaction
import com.example.financetracker.data.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionViewModel(context: Context) : ViewModel() {
    private val repository = TransactionRepository(context)

    private val _transactions = MutableLiveData<List<Transaction>>(listOf())
    val transactions: LiveData<List<Transaction>> get() = _transactions

    init {
        refreshTransactions()
    }

    fun refreshTransactions() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = repository.getAll()
            _transactions.postValue(list)
        }
    }

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(transaction)
            refreshTransactions()
        }
    }

    fun removeTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(transaction)
            refreshTransactions()
        }
    }

    fun getTotalAmount(): Double {
        return _transactions.value?.sumOf { it.amount } ?: 0.0
    }
}
