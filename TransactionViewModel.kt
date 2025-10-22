mypackage com.example.financetracker.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.financetracker.data.Transaction
import com.example.financetracker.data.TransactionRepository
import kotlinx.coroutines.Dispatchers

class TransactionViewModel(context: Context) : ViewModel() {
    private val repository = TransactionRepository(context)

    val transactions = liveData(Dispatchers.IO) {
        emit(repository.getAll())
    }

    suspend fun addTransaction(transaction: Transaction) {
        repository.insert(transaction)
    }

    suspend fun removeTransaction(transaction: Transaction) {
        repository.delete(transaction)
    }
}
