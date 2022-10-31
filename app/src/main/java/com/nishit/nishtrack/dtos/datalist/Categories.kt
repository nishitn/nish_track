package com.nishit.nishtrack.dtos.datalist

import com.google.gson.annotations.SerializedName
import com.nishit.nishtrack.dtos.dataunit.Category
import com.nishit.nishtrack.model.enums.DataType

class Categories(
    @SerializedName("categories") override val dataUnits: MutableList<Category> = mutableListOf()
) : DataList {
    override val dataType: DataType = DataType.Category
}