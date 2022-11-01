package com.nishit.nishtrack.rvadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nishit.nishtrack.R
import com.nishit.nishtrack.data.DataHandler
import com.nishit.nishtrack.data.impl.LocalDataHandler
import com.nishit.nishtrack.dtos.datalist.Transactions
import com.nishit.nishtrack.dtos.dataunit.Transaction
import com.nishit.nishtrack.model.enums.DataType
import kotlinx.android.synthetic.main.day_transaction_item.view.*
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

class DayTransactionRvAdapter(
    private val selectedYearMonth: YearMonth
) : RecyclerView.Adapter<DayTransactionRvAdapter.DayTransactionViewHolder>() {
    inner class DayTransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val dataHandler: DataHandler = LocalDataHandler
    private var transactions: List<Transaction>

    init {
        transactions = getSelectedYearMonthsTransactions()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayTransactionViewHolder {
        return DayTransactionViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.day_transaction_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DayTransactionViewHolder, position: Int) {
        val date = LocalDate.of(
            selectedYearMonth.year, selectedYearMonth.month, selectedYearMonth.lengthOfMonth() - position
        )
        val daysTransactions = transactions.filter { transaction -> transaction.date.dayOfMonth == date.dayOfMonth }

        if (daysTransactions.isEmpty()) {
            hideItem(holder)
        } else {
            showItem(holder)    // TODO: Check if this is needed
            setItemDayDetails(holder, date)
            setItemTransactionsList(holder, daysTransactions)
        }
    }

    private fun hideItem(holder: DayTransactionViewHolder) {
        holder.itemView.apply {
            visibility = View.GONE
            layoutParams = RecyclerView.LayoutParams(0, 0)
        }
    }

    private fun showItem(holder: DayTransactionViewHolder) {
        holder.itemView.apply {
            visibility = View.VISIBLE
            layoutParams =
                RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }

    override fun getItemCount(): Int {
        return selectedYearMonth.lengthOfMonth()
    }

    private fun setItemDayDetails(holder: DayTransactionViewHolder, date: LocalDate) {
        val dayMonthText = "${date.dayOfMonth} ${date.month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)}"

        holder.itemView.apply {
            dayMonthTv.text = dayMonthText
            dowTv.text = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
        }
    }

    private fun setItemTransactionsList(holder: DayTransactionViewHolder, daysTransactions: List<Transaction>) {
        val transactionAdapter = TransactionRvAdapter(daysTransactions)

        holder.itemView.apply {
            transactionItemsRv.apply {
                adapter = transactionAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

    fun updateTransactions() {
        transactions = getSelectedYearMonthsTransactions()
    }

    private fun getSelectedYearMonthsTransactions(): List<Transaction> {
        val allTransactions = dataHandler.getDataListByDataType(DataType.Transaction) as Transactions
        return allTransactions.dataUnits.filter { transaction ->
            transaction.date.month == selectedYearMonth.month && transaction.date.year == selectedYearMonth.year
        }
    }
}