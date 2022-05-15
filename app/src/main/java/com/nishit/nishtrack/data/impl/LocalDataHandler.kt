package com.nishit.nishtrack.data.impl

import com.nishit.nishtrack.data.DataHandler
import com.nishit.nishtrack.data.DataStore
import com.nishit.nishtrack.dtos.DataId
import com.nishit.nishtrack.dtos.DataList
import com.nishit.nishtrack.dtos.DataUnit
import com.nishit.nishtrack.dtos.impl.Chapters
import com.nishit.nishtrack.dtos.impl.Transactions
import com.nishit.nishtrack.model.enums.DataType
import com.nishit.nishtrack.model.exceptions.GeneratedException

object LocalDataHandler : DataHandler {
    private val dataStore: DataStore = LocalDataStore

    override fun getDataUnitById(id: DataId, dataType: DataType): DataUnit {
        return getDataByDataType(dataType).dataUnits.firstOrNull { dataUnit -> dataUnit.id == id }
            ?: throw GeneratedException("No Data Unit of type: ${dataType.name} found with ID: $id")
    }

    override fun getDataByDataType(dataType: DataType): DataList {
        return when (dataType) {
            DataType.Chapter -> getChapters()
            DataType.Transaction -> getTransactions()
            else -> throw GeneratedException("Data requested for invalid data type: ${dataType.name}")
        }
    }

    private fun getChapters(): Chapters {
        return dataStore.readChapters()
    }

    private fun getTransactions(): Transactions {
        return dataStore.readTransactions()
    }
}