package com.nishit.nishtrack

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.nishit.nishtrack.dtos.DataId
import com.nishit.nishtrack.model.enums.DataType
import com.nishit.nishtrack.model.exceptions.GeneratedException
import com.nishit.nishtrack.updatedataunit.UpdateDataUnitFragment
import com.nishit.nishtrack.updatedataunit.impl.UpdateChapterFragment
import com.nishit.nishtrack.updatedataunit.impl.UpdateTransactionFragment
import com.nishit.nishtrack.util.BundleUtil
import kotlinx.android.synthetic.main.backdrop_view.*

class UpdateDataUnitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.backdrop_view)

        val dataIdFromBundle = BundleUtil.getDataIdFromBundle(intent.extras)
        val dataTypeFromBundle = BundleUtil.getDataTypeFromBundle(intent.extras)

        val selectedDataId = when {
            dataIdFromBundle != null -> dataIdFromBundle
            dataTypeFromBundle != null -> DataId(dataTypeFromBundle)
            else -> throw GeneratedException("DataType was not provided")
        }

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
        val updateDataUnitFragment: Fragment = when (val dataType = selectedDataId.dataType) {
            DataType.Transaction -> UpdateTransactionFragment().apply {
                arguments = UpdateDataUnitFragment.createBundle(selectedDataId)
            }
            DataType.Chapter -> UpdateChapterFragment().apply {
                arguments = UpdateDataUnitFragment.createBundle(selectedDataId)
            }
            else -> throw GeneratedException("Update Data Unit not supported for DataType $dataType")
        }

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frontLayer, updateDataUnitFragment)
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
            return BundleUtil.addDataIdToBundle(selectedDataId)
        }

        fun createBundle(dataType: DataType): Bundle {
            return BundleUtil.addDataTypeToBundle(dataType)
        }
    }
}