package com.nishit.nishtrack.dtos.dataunit

import com.google.gson.annotations.SerializedName
import com.nishit.nishtrack.dtos.clearid.DataId
import com.nishit.nishtrack.model.enums.DataType

data class Category(
    @SerializedName("id") override val id: DataId,
    @SerializedName("label") override val label: String,
    @SerializedName("parentCategory") val parentCategory: DataId?,
    @SerializedName("hasCategories") val hasCategories: MutableSet<DataId> = mutableSetOf()
) : DataUnit {
    constructor(id: DataId, label: String, hasCategories: MutableSet<DataId>) : this(id, label, null, hasCategories)

    override val dataType = DataType.Category
}