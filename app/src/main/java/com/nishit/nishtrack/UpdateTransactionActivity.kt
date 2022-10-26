package com.nishit.nishtrack

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.nishit.nishtrack.dtos.DataId
import com.nishit.nishtrack.model.enums.DataType
import com.nishit.nishtrack.util.BundleUtil
import kotlinx.android.synthetic.main.backdrop_view.*

class UpdateTransactionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.backdrop_view)
        val selectedDataId = BundleUtil.getSelectedDataIdFromBundle(intent.extras)
            ?: DataId(DataType.Transaction)

        setGlanceLayer()
        setFrontLayerFragment(selectedDataId)
        hideFab()
    }

    private fun setGlanceLayer() {
        val addTransactionGlanceFragment = AddTransactionGlanceFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.glanceLayer, addTransactionGlanceFragment)
            commit()
        }
    }

    private fun setFrontLayerFragment(selectedDataId: DataId) {
        val updateTransactionInputFragment = UpdateTransactionInputFragment().apply {
            arguments = UpdateTransactionInputFragment.createBundle(selectedDataId)
        }

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frontLayer, updateTransactionInputFragment)
            commit()
        }
    }

    private fun hideFab() {
        addTransactionFab.apply {
            visibility = View.INVISIBLE
        }
    }

    companion object {
        fun createBundle(selectedDataId: DataId): Bundle {
            return BundleUtil.createSelectedDataIdBundle(selectedDataId)
        }
    }
}