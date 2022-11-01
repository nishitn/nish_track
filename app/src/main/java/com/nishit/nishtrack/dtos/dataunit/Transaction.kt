package com.nishit.nishtrack.dtos.dataunit

import com.google.gson.annotations.SerializedName
import com.nishit.nishtrack.dtos.GroupSupData
import com.nishit.nishtrack.dtos.UserSupData
import com.nishit.nishtrack.dtos.clearid.DataId
import com.nishit.nishtrack.model.enums.Currency
import com.nishit.nishtrack.model.enums.DataType
import java.time.LocalDateTime

data class Transaction(
    @SerializedName("id") override val id: DataId,
    @SerializedName("date") val date: LocalDateTime,
    @SerializedName("currency") val currency: Currency,
    @SerializedName("amount") val amount: Double,
    @SerializedName("note") val note: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("userSupData") val userSupData: UserSupData? = null,
    @SerializedName("groupSupData") val groupSupData: GroupSupData? = null,
) : DataUnit {
    override val dataType: DataType = DataType.Transaction
    override val label: String = ""
}