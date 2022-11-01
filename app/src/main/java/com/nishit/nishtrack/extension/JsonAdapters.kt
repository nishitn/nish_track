package com.nishit.nishtrack.extension

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.nishit.nishtrack.dtos.clearid.DataId
import com.nishit.nishtrack.dtos.clearid.EntityId
import com.nishit.nishtrack.model.enums.Currency
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeTypeAdapter : TypeAdapter<LocalDateTime>() {

    override fun read(input: JsonReader): LocalDateTime = LocalDateTime.parse(input.nextString())

    override fun write(out: JsonWriter, value: LocalDateTime) {
        out.value(DateTimeFormatter.ISO_DATE_TIME.format(value))
    }
}

class DataIdTypeAdapter : TypeAdapter<DataId>() {

    override fun read(input: JsonReader): DataId = DataId(input.nextString())

    override fun write(out: JsonWriter, value: DataId) {
        out.value(value.toString())
    }
}

class EntityIdTypeAdapter : TypeAdapter<EntityId>() {

    override fun read(input: JsonReader): EntityId = EntityId(input.nextString())

    override fun write(out: JsonWriter, value: EntityId) {
        out.value(value.toString())
    }
}

class CurrencyTypeAdapter : TypeAdapter<Currency>() {

    override fun read(input: JsonReader): Currency = Currency.getByCode(input.nextString())

    override fun write(out: JsonWriter, value: Currency) {
        out.value(value.code)
    }
}