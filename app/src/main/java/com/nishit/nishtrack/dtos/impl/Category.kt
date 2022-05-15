package com.nishit.nishtrack.dtos.impl

import com.google.gson.annotations.SerializedName
import com.nishit.nishtrack.dtos.DataId
import com.nishit.nishtrack.dtos.DataUnit
import com.nishit.nishtrack.model.enums.DataType

class Category(
    @SerializedName("id") override val id: DataId,
    @SerializedName("label") override val label: String,
    @SerializedName("hasCategories") val hasCategories: List<DataId> = listOf()
) : DataUnit {
    override val dataType = DataType.Category
}