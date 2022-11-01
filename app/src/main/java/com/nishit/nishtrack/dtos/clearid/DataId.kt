package com.nishit.nishtrack.dtos.clearid

import com.nishit.nishtrack.model.enums.DataType

open class DataId : ClearId {
    constructor(id: String) : super(id)

    constructor(dataType: DataType) : super(dataType)
}