package com.nishit.nishtrack

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nishit.nishtrack.model.exceptions.GeneratedException
import com.nishit.nishtrack.rvadapter.DayTransactionRvAdapter
import com.nishit.nishtrack.util.BundleUtil
import kotlinx.android.synthetic.main.transaction_list.*
import java.time.YearMonth

class MonthTransactionsFragment : Fragment(R.layout.transaction_list) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selectedYearMonth = BundleUtil.getYearMonthFromBundle(arguments)
            ?: throw GeneratedException("Year Month arguments were not passed")

        val dayTransactionAdapter = DayTransactionRvAdapter(selectedYearMonth)

        dayTransactionsRv.apply {
            adapter = dayTransactionAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    companion object {
        fun createBundle(yearMonth: YearMonth): Bundle {
            return BundleUtil.createYearMonthBundle(yearMonth)
        }
    }
}