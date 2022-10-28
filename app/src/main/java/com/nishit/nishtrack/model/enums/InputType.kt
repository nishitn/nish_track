package com.nishit.nishtrack.model.enums

enum class InputType(val heading: String, val defaultText: String) {
    DATE("Date", ""),
    TIME("Time", ""),
    CHAPTER("Chapter", "CHAPTER"),
    ACCOUNT("Accounts", "ACCOUNT"),
    CATEGORY("Category", "CATEGORY"),
    AMOUNT("Amount", ""),
    NOTE("Note", ""),
    DESCRIPTION("Description", ""),
    CURRENCY("Currency", ""),
    LABEL("Label", "")
}