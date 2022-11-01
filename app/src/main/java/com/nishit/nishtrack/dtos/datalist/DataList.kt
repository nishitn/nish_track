package com.nishit.nishtrack.dtos.datalist

import com.nishit.nishtrack.dtos.dataunit.DataUnit

sealed interface DataList {
    val dataUnits: List<DataUnit>
}