package com.nishit.nishtrack

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.backdrop_view.*

class AddOrUpdateTransactionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.backdrop_view)

        setGlanceLayer()
        setFrontLayerFragment()
        hideFab()
    }

    private fun setGlanceLayer() {
        val addTransactionGlanceFragment = AddTransactionGlanceFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.glanceLayer, addTransactionGlanceFragment)
            commit()
        }
    }

    private fun setFrontLayerFragment() {
        val addOrUpdateInputFragment = AddOrUpdateInputFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frontLayer, addOrUpdateInputFragment)
            commit()
        }
    }

    private fun hideFab() {
        addTransactionFab.apply {
            visibility = View.INVISIBLE
        }
    }
}