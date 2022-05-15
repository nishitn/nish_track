package com.nishit.nishtrack

import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nishit.nishtrack.util.BundleUtil
import java.time.YearMonth

class HomeBackDropActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this
        setContentView(R.layout.backdrop_view)
        val selectedYearMonth = BundleUtil.getYearMonthFromBundle(savedInstanceState) ?: YearMonth.now()

        setGlanceLayer()
        setFrontLayerFragment(selectedYearMonth)
    }

    private fun setGlanceLayer() {
        return
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

    companion object {
        protected lateinit var instance: HomeBackDropActivity

        fun getResources(): Resources? {
            return instance.resources
        }
    }
}