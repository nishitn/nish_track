package com.nishit.nishtrack.dtos.impl

import com.google.gson.annotations.SerializedName
import com.nishit.nishtrack.dtos.DataList
import com.nishit.nishtrack.model.enums.DataType

class Accounts(
    @SerializedName("accounts") override val dataUnits: MutableList<Account> = mutableListOf()
) : DataList {
    override val dataType: DataType = DataType.Account
}