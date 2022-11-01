package com.nishit.nishtrack.dtos

import com.google.gson.annotations.SerializedName
import com.nishit.nishtrack.dtos.clearid.DataId
import com.nishit.nishtrack.dtos.clearid.EntityId
import com.nishit.nishtrack.dtos.clearid.GroupId

class GroupSupData(
    @SerializedName("creatorId") override val creatorId: EntityId,
    @SerializedName("chapter") override val chapter: DataId,
    @SerializedName("account") override val account: DataId,
    @SerializedName("category") override val category: DataId,
    @SerializedName("groupId") val groupId: GroupId
) : SupplementaryData
