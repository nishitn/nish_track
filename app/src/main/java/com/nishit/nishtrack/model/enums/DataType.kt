package com.nishit.nishtrack.model.enums

import com.nishit.nishtrack.model.exceptions.GeneratedException

enum class DataType(val shortId: String) {
    Transaction("tr"),
    Chapter("ch"),
    Category("cg"),
    Account("ac"),
    Group("gr"),
    User("us");

    companion object {
        fun getByShortId(shortId: String): DataType {
            return values().firstOrNull { dataType -> dataType.shortId == shortId }
                ?: throw GeneratedException("DataType with Short ID: \"$shortId\" not found")
        }
    }
}