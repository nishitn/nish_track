package com.nishit.nishtrack

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nishit.nishtrack.util.BundleUtil
import kotlinx.android.synthetic.main.backdrop_view.*
import java.time.YearMonth

class HomeBackDropActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this
        setContentView(R.layout.backdrop_view)
        val selectedYearMonth = BundleUtil.getYearMonthFromBundle(savedInstanceState) ?: YearMonth.now()

        setGlanceLayer()
        setFrontLayerFragment(selectedYearMonth)
        setFabFunction()
    }

    private fun setGlanceLayer() {
        val homeGlanceFragment = HomeGlanceFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.glanceLayer, homeGlanceFragment)
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

    private fun setFabFunction() {
        addTransactionFab.setOnClickListener {
            val intent = Intent(this, AddOrUpdateTransactionActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        private lateinit var instance: HomeBackDropActivity

        fun getResources(): Resources? {
            return instance.resources
        }
    }
}