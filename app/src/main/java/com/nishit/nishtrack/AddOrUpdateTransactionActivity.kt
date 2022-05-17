package com.nishit.nishtrack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nishit.nishtrack.util.BundleUtil
import java.time.YearMonth

class AddOrUpdateTransactionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.backdrop_view)
        val selectedYearMonth = BundleUtil.getYearMonthFromBundle(savedInstanceState) ?: YearMonth.now()

        setGlanceLayer()
        setFrontLayerFragment(selectedYearMonth)
    }

    private fun setGlanceLayer() {
        val addTransactionGlanceFragment = AddTransactionGlanceFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.glanceLayer, addTransactionGlanceFragment)
            commit()
        }
    }

    private fun setFrontLayerFragment(selectedYearMonth: YearMonth) {
        val monthTransactionsFragment = MonthTransactionsFragment().apply {
            arguments = MonthTransactionsFragment.createBundle(selectedYearMonth)
        }

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frontLayer, monthTransactionsFragment)
            commit()
        }
    }
}