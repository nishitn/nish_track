package com.nishit.nishtrack.data.impl

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nishit.nishtrack.HomeBackDropActivity
import com.nishit.nishtrack.data.DataStore
import com.nishit.nishtrack.dtos.clearid.DataId
import com.nishit.nishtrack.dtos.clearid.EntityId
import com.nishit.nishtrack.dtos.datalist.*
import com.nishit.nishtrack.extension.CurrencyTypeAdapter
import com.nishit.nishtrack.extension.DataIdTypeAdapter
import com.nishit.nishtrack.extension.EntityIdTypeAdapter
import com.nishit.nishtrack.extension.LocalDateTimeTypeAdapter
import com.nishit.nishtrack.model.enums.Currency
import com.nishit.nishtrack.model.enums.DataType
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
        .registerTypeAdapter(EntityId::class.java, EntityIdTypeAdapter().nullSafe())
        .registerTypeAdapter(Currency::class.java, CurrencyTypeAdapter().nullSafe())
        .create()

    private fun getPathForDataType(dataType: DataType): String {
        val basePath = Paths.get("")

        val path = when (dataType) {
            DataType.Category -> Paths.get(basePath.pathString, "category.json")
            DataType.Chapter -> Paths.get(basePath.pathString, "chapter.json")
            DataType.Transaction -> Paths.get(basePath.pathString, "transaction.json")
            DataType.Account -> Paths.get(basePath.pathString, "account.json")
            DataType.Group -> TODO()
            DataType.User -> Paths.get(basePath.pathString, "user.json")
        }

        return path.pathString
    }

    private fun readFileForDataType(dataType: DataType): String {
        val context = HomeBackDropActivity.getContext()
        val filePath = getPathForDataType(dataType)
        return try {
            val file = context.getFileStreamPath(filePath)
            if (file == null || !file.exists()) {
                val assetFileIn = context.assets.open(filePath)
                Files.copy(assetFileIn, file.toPath(), StandardCopyOption.REPLACE_EXISTING)
                assetFileIn.close()
            }
            val bufferedReader = context.openFileInput(filePath).bufferedReader()
            bufferedReader.use { it.readText() }
        } catch (ex: Exception) {
            ex.printStackTrace()
            ""
        }
    }

    private fun replaceFileForDataType(dataType: DataType, data: String): Boolean {
        val context: Context = HomeBackDropActivity.getContext()
        val filePath = getPathForDataType(dataType)
        return try {
            context.openFileOutput(filePath, Context.MODE_PRIVATE).apply {
                flush()
                write(data.toByteArray())
                close()
            }
            true
        } catch (ex: Exception) {
            Log.e(TAG, "Error updating File {$filePath} for DataType {$dataType}", ex)
            false
        }
    }

    override fun getDataListByDataType(dataType: DataType): DataList {
        val jsonString = readFileForDataType(dataType)
        return when (dataType) {
            DataType.Category -> gson.fromJson(jsonString, Categories::class.java)
            DataType.Chapter -> gson.fromJson(jsonString, Chapters::class.java)
            DataType.Transaction -> gson.fromJson(jsonString, Transactions::class.java)
            DataType.Account -> gson.fromJson(jsonString, Accounts::class.java)
            DataType.Group -> TODO()
            DataType.User -> gson.fromJson(jsonString, Users::class.java)
        }
    }

    override fun updateDataList(dataList: DataList, dataType: DataType): Boolean {
        val data = when (dataType) {
            DataType.Transaction -> gson.toJson(dataList, Transactions::class.java)
            DataType.Chapter -> gson.toJson(dataList, Chapters::class.java)
            DataType.Category -> gson.toJson(dataList, Categories::class.java)
            DataType.Account -> gson.toJson(dataList, Accounts::class.java)
            DataType.Group -> TODO()
            DataType.User -> gson.toJson(dataList, Users::class.java)
        }

        return replaceFileForDataType(dataType, data)
    }
}