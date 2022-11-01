package com.nishit.nishtrack.dtos.datalist

import com.google.gson.annotations.SerializedName
import com.nishit.nishtrack.dtos.dataunit.User

data class Users(
    @SerializedName("users") override val dataUnits: MutableList<User> = mutableListOf()
) : DataList
