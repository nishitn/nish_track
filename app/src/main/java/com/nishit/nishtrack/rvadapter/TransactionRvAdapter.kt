package com.nishit.nishtrack.rvadapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nishit.nishtrack.R
import com.nishit.nishtrack.UpdateDataUnitActivity
import com.nishit.nishtrack.dtos.SupplementaryData
import com.nishit.nishtrack.dtos.dataunit.Transaction
import com.nishit.nishtrack.model.exceptions.GeneratedException
import com.nishit.nishtrack.util.DataUnitUtil
import kotlinx.android.synthetic.main.transaction_item.view.*

class TransactionRvAdapter(
    transactionItems: List<Transaction>
) : RecyclerView.Adapter<TransactionRvAdapter.TransactionViewHolder>() {
    inner class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val sortedTransactions = transactionItems.sortedByDescending { transaction -> transaction.date }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.transaction_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transactionItem = sortedTransactions[position]

        setTransactionItemDetails(holder, transactionItem)
        setTransactionItemBehaviour(holder, transactionItem)
    }

    private fun setTransactionItemDetails(holder: TransactionViewHolder, transactionItem: Transaction) {
        holder.itemView.apply {
            val supData: SupplementaryData
             if (transactionItem.userSupData != null) {
                 supData = transactionItem.userSupData
                 personIcon.setBackgroundResource(R.drawable.person_icon)
            } else if(transactionItem.groupSupData != null) {
                 supData = transactionItem.groupSupData
                 personIcon.setBackgroundResource(R.drawable.ic_baseline_group_24)
            } else {
                throw GeneratedException("")
            }
            accountInfoTv.text = DataUnitUtil.getDataUnitText(supData.account)
            categoryTv.text = DataUnitUtil.getDataUnitText(supData.category)
            userInfoTv.text = DataUnitUtil.getDataUnitText(supData.creatorId)
            noteTv.text = transactionItem.note
            currencyTv.text = transactionItem.currency.symbol
            amountTv.text = transactionItem.amount.toString()
        }
    }

    private fun setTransactionItemBehaviour(holder: TransactionViewHolder, transactionItem: Transaction) {
        holder.itemView.setOnClickListener {
            Log.i(TAG, "Transaction Item clicked")

            Log.i(TAG, "UpdateTransactionActivity activity creation started")
            val intent = Intent(it.context, UpdateDataUnitActivity::class.java)
            intent.putExtras(UpdateDataUnitActivity.createBundle(transactionItem.id))
            it.context.startActivity(intent)
            Log.i(TAG, "UpdateTransactionActivity activity creation completed")
        }
    }

    override fun getItemCount(): Int {
        return sortedTransactions.size
    }

    companion object {
        private const val TAG = "TransactionRvAdapter"
    }
}