package com.nishit.nishtrack

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.nishit.nishtrack.util.BundleUtil
import kotlinx.android.synthetic.main.backdrop_view.*
import java.time.YearMonth

class HomeBackDropActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this
        Log.i(TAG, "Setting content view")
        setContentView(R.layout.backdrop_view)
        val selectedYearMonth = BundleUtil.getYearMonthFromBundle(savedInstanceState) ?: YearMonth.now()

        Log.i(TAG, "Setting Glance Layer")
        setGlanceLayer()

        Log.i(TAG, "Setting Front Layer")
        setFrontLayerFragment(selectedYearMonth)

        Log.i(TAG, "Setting Floating Action Button")
        setFabFunction()
    }

    private fun setGlanceLayer() {
        val homeGlanceFragment = HomeGlanceFragment()

        Log.i(TAG, "HomeGlanceFragment transaction started")
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.glanceLayer, homeGlanceFragment)
            commit()
        }
        Log.i(TAG, "HomeGlanceFragment transaction completed")
    }

    private fun setFrontLayerFragment(selectedYearMonth: YearMonth) {
        val monthTransactionsFragment = MonthTransactionsFragment().apply {
            arguments = MonthTransactionsFragment.createBundle(selectedYearMonth)
        }

        Log.i(TAG, "MonthTransactionsFragment transaction started")
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frontLayer, monthTransactionsFragment)
            commit()
        }
        Log.i(TAG, "MonthTransactionsFragment transaction started")
    }

    private fun setFabFunction() {
        Log.i(TAG, "HomeFAB listener creation started")
        addTransactionFab.setOnClickListener {
            Log.i(TAG, "HomeFAB Button clicked")
            Log.i(TAG, "AddOrUpdateTransactionActivity activity creation started")
            val intent = Intent(this, AddOrUpdateTransactionActivity::class.java)
            startActivity(intent)
            Log.i(TAG, "AddOrUpdateTransactionActivity activity creation completed")
        }
        Log.i(TAG, "HomeFAB listener creation completed")
    }

    companion object {
        private const val TAG = "HomeBackDropActivity"
        private lateinit var instance: HomeBackDropActivity

        fun getContext(): Context {
            return instance
        }
    }
}