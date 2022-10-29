package com.nishit.nishtrack.dtos.dataunit

import com.google.gson.annotations.SerializedName
import com.nishit.nishtrack.dtos.DataId
import com.nishit.nishtrack.model.enums.DataType

class Chapter(
    @SerializedName("id") override val id: DataId,
    @SerializedName("label") override val label: String,
    @SerializedName("hasCategories") val hasCategories: MutableList<DataId> = mutableListOf()
) : DataUnit {
    override val dataType = DataType.Chapter
}