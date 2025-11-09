package com.example.financetracker.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.financetracker.data.*
import kotlinx.coroutines.launch

class TransactionViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TransactionRepository
    val allTransactions: LiveData<List<Transaction>>
    val totalIncome: LiveData<Double?>
    val totalExpense: LiveData<Double?>

    init {
        val dao = AppDatabase.getDatabase(application).transactionDao()
        repository = TransactionRepository(dao)
        allTransactions = repository.allTransactions
        totalIncome = repository.totalIncome
        totalExpense = repository.totalExpense
    }

    fun insert(transaction: Transaction) = viewModelScope.launch {
        repository.insert(transaction)
    }

    fun delete(transaction: Transaction) = viewModelScope.launch {
        repository.delete(transaction)
    }
}
