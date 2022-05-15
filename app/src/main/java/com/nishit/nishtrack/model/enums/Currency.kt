package com.nishit.nishtrack.model.enums

import com.nishit.nishtrack.model.exceptions.GeneratedException

enum class Currency(val code: String, val symbol: String) {
    INR("INR", "\u20B9");

    companion object {
        fun getByCode(code: String): Currency {
            return values().firstOrNull { currency -> currency.code == code }
                ?: throw GeneratedException("Currency with code \"$code\" not found")
        }
    }
}