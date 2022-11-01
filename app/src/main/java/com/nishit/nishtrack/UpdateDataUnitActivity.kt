package com.nishit.nishtrack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.nishit.nishtrack.dtos.DataId
import com.nishit.nishtrack.model.enums.DataType
import com.nishit.nishtrack.model.exceptions.GeneratedException
import com.nishit.nishtrack.updatedataunit.UpdateDataUnitFragment
import com.nishit.nishtrack.updatedataunit.impl.UpdateAccountFragment
import com.nishit.nishtrack.updatedataunit.impl.UpdateCategoryFragment
import com.nishit.nishtrack.updatedataunit.impl.UpdateChapterFragment
import com.nishit.nishtrack.updatedataunit.impl.UpdateTransactionFragment
import com.nishit.nishtrack.util.BundleUtil

class UpdateDataUnitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_data_unit_view)

        val dataIdFromBundle = BundleUtil.getDataId(intent.extras)
        val dataTypeFromBundle = BundleUtil.getDataType(intent.extras)

        val selectedDataId = when {
            dataIdFromBundle != null -> dataIdFromBundle
            dataTypeFromBundle != null -> DataId(dataTypeFromBundle)
            else -> throw GeneratedException("DataType was not provided")
        }

        setGlanceLayer(selectedDataId.dataType)
        setFrontLayerFragment(selectedDataId)
    }

    private fun setGlanceLayer(dataType: DataType) {
        val appBarGlanceFragment = AppBarGlanceFragment(dataType)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.duvGlanceLayer, appBarGlanceFragment)
            commit()
        }
    }

    private fun setFrontLayerFragment(selectedDataId: DataId) {
        val updateDataUnitFragment: Fragment = when (val dataType = selectedDataId.dataType) {
            DataType.Transaction -> UpdateTransactionFragment()
            DataType.Chapter -> UpdateChapterFragment()
            DataType.Account -> UpdateAccountFragment()
            DataType.Category -> UpdateCategoryFragment()
            else -> throw GeneratedException("Update Data Unit not supported for DataType $dataType")
        }
        updateDataUnitFragment.apply {
            arguments = UpdateDataUnitFragment.createBundle(selectedDataId)
        }

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.duvFrontLayer, updateDataUnitFragment)
            commit()
        }
    }

    companion object {
        fun createBundle(selectedDataId: DataId): Bundle {
            return BundleUtil.getDataIdBundle(selectedDataId)
        }

        fun createBundle(dataType: DataType): Bundle {
            return BundleUtil.getDataTypeBundle(dataType)
        }
    }
}