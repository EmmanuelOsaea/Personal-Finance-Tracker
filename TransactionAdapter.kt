package com.example.financetracker.ui.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.financetracker.data.Transaction
import java.text.SimpleDateFormat
import java.util.*
import com.example.financetracker.databinding.ItemTransactionBinding

class TransactionAdapter(
    private var transactions: List<Transaction>,
    private val onDelete: (Transaction) -> Unit
) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    class TransactionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvAmount: TextView = view.findViewById(R.id.tvAmount)
        val tvCategory: TextView = view.findViewById(R.id.tvCategory)
        val tvDate: TextView = view.findViewById(R.id.tvDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.transaction_item, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.tvTitle.text = transaction.title
        holder.tvAmount.text = "$${transaction.amount}"
        holder.tvCategory.text = transaction.category
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        holder.tvDate.text = sdf.format(Date(transaction.date))

        holder.itemView.setOnClickListener {
            onDelete(transaction)
            true
        }
    }

holder.itemView.setOnClickListener {
    val intent = Intent(context, TransactionDetailActivity::class.java)
    intent.putExtra("TRANSACTION_ID", transaction.id)
    context.startActivity(intent)
}
   
    
    
    override fun getItemCount(): Int = transactions.size

    fun updateList(newList: List<Transaction>) {
        transactions = newList
        notifyDataSetChanged()
    }
}
