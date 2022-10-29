package com.nishit.nishtrack

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nishit.nishtrack.model.exceptions.GeneratedException
import com.nishit.nishtrack.rvadapter.DayTransactionRvAdapter
import com.nishit.nishtrack.util.BundleUtil
import kotlinx.android.synthetic.main.transaction_list.*
import java.time.YearMonth

class MonthTransactionsFragment : Fragment(R.layout.transaction_list) {
    private lateinit var dayTransactionAdapter: DayTransactionRvAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selectedYearMonth =
            BundleUtil.getYearMonth(arguments) ?: throw GeneratedException("Year Month arguments were not passed")

        dayTransactionAdapter = DayTransactionRvAdapter(selectedYearMonth)

        Log.i(TAG, "DayTransactionRvAdapter adapter apply started")
        dayTransactionsRv.apply {
            adapter = dayTransactionAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }
        Log.i(TAG, "DayTransactionRvAdapter adapter apply completed")
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        dayTransactionAdapter.updateTransactions()
        dayTransactionAdapter.notifyDataSetChanged()
        super.onResume()
    }

    companion object {
        private const val TAG = "MonthTransactionsFragment"

        fun createBundle(yearMonth: YearMonth): Bundle {
            return BundleUtil.getYearMonthBundle(yearMonth)
        }
    }
}