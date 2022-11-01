package com.nishit.nishtrack.dtos

import com.nishit.nishtrack.dtos.clearid.DataId
import com.nishit.nishtrack.dtos.clearid.EntityId

sealed interface SupplementaryData {
    val creatorId: EntityId
    val chapter: DataId
    val account: DataId
    val category: DataId
}