package com.nishit.nishtrack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nishit.nishtrack.model.enums.DataType
import com.nishit.nishtrack.model.exceptions.GeneratedException
import com.nishit.nishtrack.util.BundleUtil

class DataUnitSettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.data_unit_settings)

        val dataType = BundleUtil.getDataType(intent.extras) ?: throw GeneratedException("DataType was not provided")

        setGlanceLayer()
        setFrontLayerFragment(dataType)
    }

    private fun setGlanceLayer() {
        val addTransactionGlanceFragment = AddTransactionGlanceFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.dusGlanceLayer, addTransactionGlanceFragment)
            commit()
        }
    }

    private fun setFrontLayerFragment(dataType: DataType) {
        val dataUnitListFragment = DataUnitListFragment().apply {
            arguments = DataUnitListFragment.createBundle(dataType)
        }

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.dusFrontLayer, dataUnitListFragment)
            commit()
        }
    }

    companion object {
        fun createBundle(dataType: DataType): Bundle {
            return BundleUtil.getDataTypeBundle(dataType)
        }
    }
}