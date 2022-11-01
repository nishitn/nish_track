package com.nishit.nishtrack.dtos

import com.google.gson.annotations.SerializedName
import com.nishit.nishtrack.dtos.clearid.DataId
import com.nishit.nishtrack.dtos.clearid.EntityId

data class UserSupData(
    @SerializedName("creatorId") override val creatorId: EntityId,
    @SerializedName("chapter") override val chapter: DataId,
    @SerializedName("account") override val account: DataId,
    @SerializedName("category") override val category: DataId,
) : SupplementaryData
