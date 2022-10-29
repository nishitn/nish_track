package com.nishit.nishtrack.dtos.datalist

import com.google.gson.annotations.SerializedName
import com.nishit.nishtrack.dtos.dataunit.Chapter
import com.nishit.nishtrack.model.enums.DataType

data class Chapters(
    @SerializedName("chapters") override val dataUnits: MutableList<Chapter> = mutableListOf()
) : DataList {
    override val dataType: DataType = DataType.Chapter
}