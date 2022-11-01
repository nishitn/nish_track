package com.nishit.nishtrack.dtos.clearid

import com.nishit.nishtrack.model.enums.DataType

class GroupId : EntityId {
    constructor(id: String) : super(id)

    constructor(dataType: DataType) : super(dataType)
}