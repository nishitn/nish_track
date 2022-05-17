package com.nishit.nishtrack.rvadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nishit.nishtrack.R
import com.nishit.nishtrack.data.DataHandler
import com.nishit.nishtrack.data.impl.LocalDataHandler
import com.nishit.nishtrack.dtos.DataId
import com.nishit.nishtrack.dtos.impl.Transaction
import com.nishit.nishtrack.model.enums.DataType
import com.nishit.nishtrack.model.enums.Separator
import kotlinx.android.synthetic.main.transaction_item.view.*
import org.apache.commons.lang3.StringUtils

class TransactionRvAdapter(
    transactionItems: List<Transaction>
) : RecyclerView.Adapter<TransactionRvAdapter.TransactionViewHolder>() {
    inner class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val dataHandler: DataHandler = LocalDataHandler
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
            categoryTv.text = getCategoryText(transactionItem.categories)
            noteTv.text = transactionItem.note
            currencyTv.text = transactionItem.currency.symbol
            amountTv.text = transactionItem.amount.toString()
        }
    }

    override fun getItemCount(): Int {
        return sortedTransactions.size
    }

    private fun getCategoryText(categoryIds: List<DataId>): String {
        val categoryNames =
            categoryIds.map { categoryId -> dataHandler.getDataUnitById(categoryId, DataType.Category).label }
        return StringUtils.join(categoryNames, Separator.CategorySeparator.separator)
    }
}