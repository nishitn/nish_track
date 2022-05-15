package com.nishit.nishtrack.dtos

import com.nishit.nishtrack.model.enums.DataType

interface DataList {
    val dataUnits: List<DataUnit>

    val dataType: DataType
}