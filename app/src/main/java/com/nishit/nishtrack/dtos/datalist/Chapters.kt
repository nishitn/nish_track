package com.nishit.nishtrack.dtos.datalist

import com.google.gson.annotations.SerializedName
import com.nishit.nishtrack.dtos.dataunit.Chapter

data class Chapters(
    @SerializedName("chapters") override val dataUnits: MutableList<Chapter> = mutableListOf()
) : DataList