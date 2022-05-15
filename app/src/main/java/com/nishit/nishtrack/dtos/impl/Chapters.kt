package com.nishit.nishtrack.dtos.impl

import com.google.gson.annotations.SerializedName
import com.nishit.nishtrack.dtos.DataList
import com.nishit.nishtrack.model.enums.DataType

data class Chapters(
    @SerializedName("chapters") override val dataUnits: MutableList<Chapter> = mutableListOf()
) : DataList {
    override val dataType: DataType = DataType.Chapter
}