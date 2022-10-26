package com.nishit.nishtrack.data.impl

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nishit.nishtrack.HomeBackDropActivity
import com.nishit.nishtrack.data.DataStore
import com.nishit.nishtrack.dtos.DataId
import com.nishit.nishtrack.dtos.DataList
import com.nishit.nishtrack.dtos.impl.Accounts
import com.nishit.nishtrack.dtos.impl.Categories
import com.nishit.nishtrack.dtos.impl.Chapters
import com.nishit.nishtrack.dtos.impl.Transactions
import com.nishit.nishtrack.extension.CurrencyTypeAdapter
import com.nishit.nishtrack.extension.DataIdTypeAdapter
import com.nishit.nishtrack.extension.LocalDateTimeTypeAdapter
import com.nishit.nishtrack.model.enums.Currency
import com.nishit.nishtrack.model.enums.DataType
import com.nishit.nishtrack.model.exceptions.GeneratedException
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.time.LocalDateTime
import kotlin.io.path.pathString

object LocalDataStore : DataStore {
    private const val TAG = "LocalDataStore"

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
            DataType.Account -> Paths.get(basePath.pathString, "account.json")
            else -> throw GeneratedException("Data requested for invalid data type: ${dataType.name}")
        }

        return path.pathString
    }

    private fun readFileForDataType(dataType: DataType): String {
        val context = HomeBackDropActivity.getContext()
        val filePath = getPathForDataType(dataType)
        try {
            val file = context.getFileStreamPath(filePath)
            if (file == null || !file.exists()) {
                val assetFileIn = context.assets.open(filePath)
                Files.copy(assetFileIn, file.toPath(), StandardCopyOption.REPLACE_EXISTING)
                assetFileIn.close()
            }
            val bufferedReader = context.openFileInput(filePath).bufferedReader()
            return bufferedReader.use { it.readText() }
        } catch (ex: Exception) {
            ex.printStackTrace()
            return ""
        }
    }

    private fun replaceFileForDataType(dataType: DataType, data: String): Boolean {
        val context: Context = HomeBackDropActivity.getContext()
        val filePath = getPathForDataType(dataType)
        try {
            context.openFileOutput(filePath, Context.MODE_PRIVATE).apply {
                flush()
                write(data.toByteArray())
                close()
            }
            return true
        } catch (ex: Exception) {
            Log.e(TAG, "Error updating File {$filePath} for DataType {$dataType}", ex)
            return false
        }
    }

    override fun getDataListByDataType(dataType: DataType): DataList {
        val jsonString = readFileForDataType(dataType)
        return when (dataType) {
            DataType.Category -> gson.fromJson(jsonString, Categories::class.java)
            DataType.Chapter -> gson.fromJson(jsonString, Chapters::class.java)
            DataType.Transaction -> gson.fromJson(jsonString, Transactions::class.java)
            DataType.Account -> gson.fromJson(jsonString, Accounts::class.java)
            else -> throw GeneratedException("Data requested for invalid data type: ${dataType.name}")
        }
    }

    override fun updateDataList(dataList: DataList): Boolean {
        val data = when(dataList.dataType) {
            DataType.Transaction -> gson.toJson(dataList, Transactions::class.java)
            DataType.Chapter -> gson.toJson(dataList, Chapters::class.java)
            DataType.Category -> gson.toJson(dataList, Categories::class.java)
            DataType.Account -> gson.toJson(dataList, Accounts::class.java)
            DataType.User -> gson.toJson(dataList)
        }

        return replaceFileForDataType(dataList.dataType, data)
    }
}