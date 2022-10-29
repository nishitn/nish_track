package com.nishit.nishtrack.dtos.datalist

import com.nishit.nishtrack.dtos.dataunit.DataUnit
import com.nishit.nishtrack.model.enums.DataType

sealed interface DataList {
    val dataUnits: List<DataUnit>

    val dataType: DataType
}