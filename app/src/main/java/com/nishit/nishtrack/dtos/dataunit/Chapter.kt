package com.nishit.nishtrack.dtos.dataunit

import com.google.gson.annotations.SerializedName
import com.nishit.nishtrack.dtos.clearid.DataId
import com.nishit.nishtrack.model.enums.DataType

data class Chapter(
    @SerializedName("id") override val id: DataId,
    @SerializedName("label") override val label: String,
    @SerializedName("hasCategories") val hasCategories: MutableSet<DataId> = mutableSetOf()
) : DataUnit {
    override val dataType = DataType.Chapter
}