package com.nishit.nishtrack.dtos.datalist

import com.google.gson.annotations.SerializedName
import com.nishit.nishtrack.dtos.dataunit.Account

data class Accounts(
    @SerializedName("accounts") override val dataUnits: MutableList<Account> = mutableListOf()
) : DataList