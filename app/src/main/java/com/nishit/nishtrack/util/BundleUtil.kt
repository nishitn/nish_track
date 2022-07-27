package com.nishit.nishtrack.util

import android.os.Bundle
import com.nishit.nishtrack.dtos.DataId
import java.time.YearMonth

class BundleUtil {
    companion object {
        fun createYearMonthBundle(yearMonth: YearMonth): Bundle {
            val bundle = Bundle()
            bundle.putInt("year", yearMonth.year)
            bundle.putInt("month", yearMonth.monthValue)
            return bundle
        }

        fun getYearMonthFromBundle(bundle: Bundle?): YearMonth? {
            val year = bundle?.getInt("year")
            val month = bundle?.getInt("month")
            return (if (year == null || month == null) null else YearMonth.of(year, month))
        }

        fun createSelectedDataIdBundle(selectedDataId: DataId): Bundle {
            val bundle = Bundle()
            bundle.putString("selectedDataId", selectedDataId.toString())
            return bundle
        }

        fun getSelectedDataIdFromBundle(bundle: Bundle?): DataId? {
            val dataId = bundle?.getString("selectedDataId")
            return (if (dataId == null) null else DataId(dataId))
        }
    }
}