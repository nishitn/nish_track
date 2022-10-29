package com.nishit.nishtrack.dtos.dataunit

import com.google.gson.annotations.SerializedName
import com.nishit.nishtrack.dtos.DataId
import com.nishit.nishtrack.model.enums.Currency
import com.nishit.nishtrack.model.enums.DataType
import java.time.LocalDateTime

data class Transaction(
    @SerializedName("id") override val id: DataId,
    @SerializedName("date") val date: LocalDateTime,
    @SerializedName("chapter") val chapter: DataId,
    @SerializedName("account") val account: DataId,
    @SerializedName("categories") val categories: List<DataId>,
    @SerializedName("note") val note: String? = null,
    @SerializedName("currency") val currency: Currency,
    @SerializedName("amount") val amount: Double,
    @SerializedName("description") val description: String? = null
) : DataUnit {
    override val dataType: DataType = DataType.Transaction
    override val label: String = ""
}