package com.nishit.nishtrack.data.impl

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nishit.nishtrack.HomeBackDropActivity
import com.nishit.nishtrack.data.DataStore
import com.nishit.nishtrack.dtos.DataId
import com.nishit.nishtrack.dtos.DataList
import com.nishit.nishtrack.dtos.impl.Categories
import com.nishit.nishtrack.dtos.impl.Chapters
import com.nishit.nishtrack.dtos.impl.Transactions
import com.nishit.nishtrack.extension.CurrencyTypeAdapter
import com.nishit.nishtrack.extension.DataIdTypeAdapter
import com.nishit.nishtrack.extension.LocalDateTimeTypeAdapter
import com.nishit.nishtrack.model.enums.Currency
import com.nishit.nishtrack.model.enums.DataType
import com.nishit.nishtrack.model.exceptions.GeneratedException
import java.nio.file.Paths
import java.time.LocalDateTime
import kotlin.io.path.pathString

object LocalDataStore : DataStore {
    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeTypeAdapter().nullSafe())
        .registerTypeAdapter(DataId::class.java, DataIdTypeAdapter().nullSafe())
        .registerTypeAdapter(Currency::class.java, CurrencyTypeAdapter().nullSafe())
        .create()

    private fun getPathForDataType(dataType: DataType): String {
        val basePath = Paths.get("")

        val path = when (dataType) {
            DataType.Category -> Paths.get(basePath.pathString, "category.json")
            DataType.Chapter -> Paths.get(basePath.pathString, "chapter.json")
            DataType.Transaction -> Paths.get(basePath.pathString, "transaction.json")
            else -> throw GeneratedException("Data requested for invalid data type: ${dataType.name}")
        }

        return path.pathString
    }

    private fun readFileForDataType(dataType: DataType): String {
        val resources = HomeBackDropActivity.getResources()!!
        val filePath = getPathForDataType(dataType)
        val bufferedReader = resources.assets.open(filePath).bufferedReader()
        return bufferedReader.use { it.readText() }
    }

    override fun getDataList(dataType: DataType): DataList {
        val jsonString = readFileForDataType(dataType)
        return when (dataType) {
            DataType.Category -> gson.fromJson(jsonString, Categories::class.java)
            DataType.Chapter -> gson.fromJson(jsonString, Chapters::class.java)
            DataType.Transaction -> gson.fromJson(jsonString, Transactions::class.java)
            else -> throw GeneratedException("Data requested for invalid data type: ${dataType.name}")
        }
    }
}