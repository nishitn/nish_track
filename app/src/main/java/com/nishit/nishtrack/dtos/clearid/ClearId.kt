package com.nishit.nishtrack.dtos.clearid

import com.nishit.nishtrack.model.enums.DataType
import java.util.*

open class ClearId {
    val dataType: DataType
    val guid: UUID

    constructor(id: String) {
        val split = id.split('_')
        this.dataType = DataType.getByShortId(split[0])
        this.guid = UUID.fromString(split[1])
    }

    constructor(dataType: DataType) {
        this.dataType = dataType
        this.guid = UUID.randomUUID()
    }

    override fun toString(): String {
        return "${dataType.shortId}_$guid"
    }

    override fun equals(other: Any?): Boolean {
        if (other is DataId) {
            return dataType == other.dataType && guid == other.guid
        } else if (other is String) {
            return dataType.toString() == other
        }
        return false
    }

    override fun hashCode(): Int {
        return toString().hashCode()
    }
}