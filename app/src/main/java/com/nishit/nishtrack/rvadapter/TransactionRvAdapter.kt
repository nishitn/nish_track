package com.nishit.nishtrack.rvadapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nishit.nishtrack.AddOrUpdateTransactionActivity
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
            LayoutInflater.from(parent.context)
                .inflate(R.layout.transaction_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transactionItem = sortedTransactions[position]

        setTransactionItemDetails(holder, transactionItem)
        setTransactionItemBehaviour(holder, transactionItem)
    }

    private fun setTransactionItemDetails(holder: TransactionViewHolder, transactionItem: Transaction) {
        holder.itemView.apply {
            categoryTv.text = DataUnitUtil.getCategoryText(transactionItem.categories)
            noteTv.text = transactionItem.note
            currencyTv.text = transactionItem.currency.symbol
            amountTv.text = transactionItem.amount.toString()
        }
    }

    private fun setTransactionItemBehaviour(holder: TransactionViewHolder, transactionItem: Transaction) {
        holder.itemView.setOnClickListener {
            Log.i(TAG, "Transaction Item clicked")

            Log.i(TAG, "AddOrUpdateTransactionActivity activity creation started")
            val intent = Intent(it.context, AddOrUpdateTransactionActivity::class.java)
            intent.putExtras(AddOrUpdateTransactionActivity.createBundle(transactionItem.id))
            it.context.startActivity(intent)
            Log.i(TAG, "AddOrUpdateTransactionActivity activity creation completed")
        }
    }

    override fun getItemCount(): Int {
        return sortedTransactions.size
    }

    companion object {
        private const val TAG = "TransactionRvAdapter"
    }
}