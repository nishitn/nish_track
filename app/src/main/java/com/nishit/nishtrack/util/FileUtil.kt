package com.nishit.nishtrack.util

import com.nishit.nishtrack.model.enums.DataType
import com.nishit.nishtrack.model.exceptions.GeneratedException
import java.io.File
import java.nio.file.Paths
import kotlin.io.path.pathString

class FileUtil {
    companion object {
        fun getPathForDataType(dataType: DataType): String {
            val basePath = Paths.get("C:\\All\\Repos\\nishTrack\\app\\src\\main\\assets\\")

            val path = when (dataType) {
                DataType.Category -> Paths.get(basePath.pathString, "category.json")
                DataType.Chapter -> Paths.get(basePath.pathString, "chapter.json")
                DataType.Transaction -> Paths.get(basePath.pathString, "transaction.json")
                else -> throw GeneratedException("Data requested for invalid data type: ${dataType.name}")
            }

            return path.pathString
        }
    }

    fun readFile(filePath: String): String {
        val bufferedReader = File(filePath).bufferedReader()
        return bufferedReader.use { it.readText() }
    }
}