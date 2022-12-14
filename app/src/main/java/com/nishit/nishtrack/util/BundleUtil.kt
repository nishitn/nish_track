package com.nishit.nishtrack.util

import android.os.Bundle
import com.nishit.nishtrack.dtos.clearid.DataId
import com.nishit.nishtrack.model.enums.DataType
import java.time.YearMonth

class BundleUtil {
    companion object {
        private const val yearKey = "year"
        private const val monthKey = "month"
        private const val dataIdKey = "dataId"
        private const val dataTypeKey = "dataType"

        fun getYearMonthBundle(yearMonth: YearMonth): Bundle {
            val bundle = Bundle()
            bundle.putInt(yearKey, yearMonth.year)
            bundle.putInt(monthKey, yearMonth.monthValue)
            return bundle
        }

        fun getYearMonth(bundle: Bundle?): YearMonth? {
            val year = bundle?.getInt(yearKey)
            val month = bundle?.getInt(monthKey)
            return (if (year == null || month == null) null else YearMonth.of(year, month))
        }

        fun getDataIdBundle(dataId: DataId): Bundle {
            val bundle = Bundle()
            bundle.putString(dataIdKey, dataId.toString())
            return bundle
        }

        fun getDataId(bundle: Bundle?): DataId? {
            val dataId = bundle?.getString(dataIdKey)
            return (if (dataId == null) null else DataId(dataId))
        }

        fun getDataTypeBundle(dataType: DataType): Bundle {
            val bundle = Bundle()
            bundle.putString(dataTypeKey, dataType.shortId)
            return bundle
        }

        fun getDataType(bundle: Bundle?): DataType? {
            val dataTypeShortId = bundle?.getString(dataTypeKey)
            return (if (dataTypeShortId == null) null else DataType.getByShortId(dataTypeShortId))
        }
    }
}