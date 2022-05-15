package com.nishit.nishtrack.dtos

import com.nishit.nishtrack.model.enums.DataType

interface DataUnit {
    val id: DataId

    val label: String

    val dataType: DataType
}