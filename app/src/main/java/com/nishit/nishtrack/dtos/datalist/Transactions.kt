package com.nishit.nishtrack.dtos.datalist

import com.google.gson.annotations.SerializedName
import com.nishit.nishtrack.dtos.dataunit.Transaction

data class Transactions(
    @SerializedName("transactions") override val dataUnits: MutableList<Transaction> = mutableListOf()
) : DataList