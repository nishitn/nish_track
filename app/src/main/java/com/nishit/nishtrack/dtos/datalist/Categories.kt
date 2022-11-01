package com.nishit.nishtrack.dtos.datalist

import com.google.gson.annotations.SerializedName
import com.nishit.nishtrack.dtos.dataunit.Category

data class Categories(
    @SerializedName("categories") override val dataUnits: MutableList<Category> = mutableListOf()
) : DataList