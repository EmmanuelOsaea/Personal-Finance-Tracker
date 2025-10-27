package com.example.financetracker.ui

import android.app.Dialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.example.financetracker.R
import com.example.financetracker.data.Transaction
import com.example.financetracker.data.TransactionDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddTransactionDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = requireActivity().layoutInflater.inflate(R.layout.add_transaction_dialog, null)

        val etAmount = view.findViewById<EditText>(R.id.etAmount)
        val etCategory = view.findViewById<EditText>(R.id.etCategory)
        val spinnerType = view.findViewById<Spinner>(R.id.spinnerType)
        val btnSave = view.findViewById<Button>(R.id.btnSave)

        val types = listOf("Income", "Expense")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, types)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerType.adapter = adapter

        val dialog = AlertDialog.Builder(requireContext())
            .setView(view)
            .setTitle("Add Transaction")
            .create()

        btnSave.setOnClickListener {
            val amountText = etAmount.text.toString()
            val category = etCategory.text.toString()
            val type = spinnerType.selectedItem.toString()

            if (amountText.isNotEmpty() && category.isNotEmpty()) {
                val amount = amountText.toDoubleOrNull() ?: 0.0

                val transaction = Transaction(
                    id = 0,
                    amount = amount,
                    category = category,
                    type = type,
                    timestamp = System.currentTimeMillis()
                )

                lifecycleScope.launch(Dispatchers.IO) {
                    TransactionDatabase.getDatabase(requireContext())
                        .transactionDao()
                        .insert(transaction)

                    withContext(Dispatchers.Main) {
                        dismiss()
                    }
                }
            }
        }

        return dialog
    }
}
