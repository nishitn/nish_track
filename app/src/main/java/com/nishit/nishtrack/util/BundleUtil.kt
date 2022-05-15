package com.nishit.nishtrack.util

import android.os.Bundle
import java.time.YearMonth

class BundleUtil {
    companion object {
        fun getYearMonthFromBundle(bundle: Bundle?) : YearMonth? {
            val year = bundle?.getInt("year")
            val month = bundle?.getInt("month")
            return (if (year == null || month == null) null else YearMonth.of(year, month))
        }
    }
}