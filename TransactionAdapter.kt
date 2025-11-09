package com.example.financetracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.financetracker.data.Transaction
import java.text.SimpleDateFormat
import java.util.*

class TransactionAdapter(
    private val onDeleteClick: (Transaction) -> Unit
) : ListAdapter<Transaction, TransactionAdapter.TransactionViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = getItem(position)
        holder.bind(transaction, onDeleteClick)
    }

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleText: TextView = itemView.findViewById(R.id.titleText)
        private val amountText: TextView = itemView.findViewById(R.id.amountText)
        private val dateText: TextView = itemView.findViewById(R.id.dateText)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)

        fun bind(transaction: Transaction, onDeleteClick: (Transaction) -> Unit) {
            titleText.text = transaction.title
            val sign = if (transaction.isExpense) "-" else "+"
            val color = if (transaction.isExpense) 0xFFFF4444.toInt() else 0xFF00C853.toInt()
            amountText.setTextColor(color)
            amountText.text = "$sign₦${transaction.amount}"

            val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            dateText.text = dateFormat.format(Date(transaction.date))

            deleteButton.setOnClickListener {
                onDeleteClick(transaction)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction) =
            oldItem == newItem
    }
}
