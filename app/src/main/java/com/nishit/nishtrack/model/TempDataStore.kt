package com.nishit.nishtrack.model

import com.nishit.nishtrack.dtos.impl.Account
import com.nishit.nishtrack.dtos.impl.Category
import com.nishit.nishtrack.dtos.impl.Chapter
import com.nishit.nishtrack.model.enums.Currency
import java.time.LocalDate
import java.time.LocalTime

class TempDataStore {
    var chapter: Chapter? = null
    var account: Account? = null
    var category: Category? = null
    var date: LocalDate? = null
    var time: LocalTime? = null
    var currency: Currency = Currency.INR
    var amount: Double? = null
    var note: String? = null
    var description: String? = null

    fun isValid(): Boolean {
        return chapter != null && account != null && category != null && date != null && time != null
    }
}