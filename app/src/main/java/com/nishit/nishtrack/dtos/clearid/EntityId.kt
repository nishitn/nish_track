package com.nishit.nishtrack.dtos.clearid

import com.nishit.nishtrack.model.enums.DataType

open class EntityId : DataId {
    constructor(id: String) : super(id)

    constructor(dataType: DataType) : super(dataType)
}