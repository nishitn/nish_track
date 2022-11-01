package com.nishit.nishtrack.updatedataunit

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.nishit.nishtrack.dtos.clearid.DataId
import com.nishit.nishtrack.util.BundleUtil

open class UpdateDataUnitFragment(layout: Int) : Fragment(layout) {
    companion object {
        fun createBundle(selectedDataId: DataId): Bundle {
            return BundleUtil.getDataIdBundle(selectedDataId)
        }
    }
}