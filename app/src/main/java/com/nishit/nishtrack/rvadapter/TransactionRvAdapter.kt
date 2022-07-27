package com.nishit.nishtrack.rvadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nishit.nishtrack.R
import com.nishit.nishtrack.dtos.impl.Transaction
import com.nishit.nishtrack.util.DataUnitUtil
import kotlinx.android.synthetic.main.transaction_item.view.*

class TransactionRvAdapter(
    transactionItems: List<Transaction>
) : RecyclerView.Adapter<TransactionRvAdapter.TransactionViewHolder>() {
    inner class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val sortedTransactions: List<Transaction> =
        transactionItems.sortedByDescending { transaction -> transaction.date }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.transaction_item, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transactionItem = sortedTransactions[position]

        holder.itemView.apply {
            categoryTv.text = DataUnitUtil.getCategoryText(transactionItem.categories)
            noteTv.text = transactionItem.note
            currencyTv.text = transactionItem.currency.symbol
            amountTv.text = transactionItem.amount.toString()
        }
    }

    override fun getItemCount(): Int {
        return sortedTransactions.size
    }
}