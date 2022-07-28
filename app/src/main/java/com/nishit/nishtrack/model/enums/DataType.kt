package com.nishit.nishtrack.model.enums

import com.nishit.nishtrack.model.exceptions.GeneratedException

enum class DataType(val shortId: String, val heading: String) {
    Transaction("tr", "Transactions"),
    Chapter("ch", "Chapters"),
    Category("cg", "Categories"),
    Account("ac", "Accounts"),
    User("us", "Users");

    companion object {
        fun getByShortId(shortId: String): DataType {
            return values().firstOrNull { dataType -> dataType.shortId == shortId }
                ?: throw GeneratedException("DataType with Short ID: \"$shortId\" not found")
        }
    }
}