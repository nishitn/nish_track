package com.nishit.nishtrack.dtos.datalist

import com.google.gson.annotations.SerializedName
import com.nishit.nishtrack.dtos.dataunit.Transaction
import com.nishit.nishtrack.model.enums.DataType

data class Transactions(
    @SerializedName("transactions") override val dataUnits: MutableList<Transaction> = mutableListOf()
) : DataList {
    override val dataType = DataType.Transaction
}