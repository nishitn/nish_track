package com.nishit.nishtrack.model

import com.nishit.nishtrack.dtos.impl.Account
import com.nishit.nishtrack.dtos.impl.Category
import com.nishit.nishtrack.dtos.impl.Chapter
import java.time.LocalDate
import java.time.LocalTime

class TempDataStore {
    var chapter: Chapter? = null
    var account: Account? = null
    var category: Category? = null
    var date: LocalDate? = null
    var time: LocalTime? = null
    var note: String? = null
    var description: String? = null
    var amount: Double = 0.0
}