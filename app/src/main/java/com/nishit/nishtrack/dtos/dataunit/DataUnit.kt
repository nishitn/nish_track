package com.nishit.nishtrack.dtos.dataunit

import com.nishit.nishtrack.dtos.clearid.DataId
import com.nishit.nishtrack.model.enums.DataType

sealed interface DataUnit {
    val id: DataId

    val label: String

    val dataType: DataType
}