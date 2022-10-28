package com.nishit.nishtrack.data.impl

import com.nishit.nishtrack.data.DataHandler
import com.nishit.nishtrack.data.DataStore
import com.nishit.nishtrack.dtos.DataId
import com.nishit.nishtrack.dtos.DataList
import com.nishit.nishtrack.dtos.DataUnit
import com.nishit.nishtrack.dtos.impl.Transaction
import com.nishit.nishtrack.dtos.impl.Transactions
import com.nishit.nishtrack.model.enums.DataType
import com.nishit.nishtrack.model.exceptions.GeneratedException

object LocalDataHandler : DataHandler {
    private val dataStore: DataStore = LocalDataStore

    override fun getDataUnitById(id: DataId): DataUnit {
        return getDataListByDataType(id.dataType).dataUnits.firstOrNull { dataUnit -> dataUnit.id == id }
            ?: throw GeneratedException("No Data Unit of type: ${id.dataType.name} found with ID: $id")
    }

    override fun getDataUnitOrNullById(id: DataId): DataUnit? {
        return getDataListByDataType(id.dataType).dataUnits.firstOrNull { dataUnit -> dataUnit.id == id }
    }

    override fun getDataUnitsById(ids: List<DataId>): List<DataUnit> {
        // TODO: Update to thr exception if any id is not found, and also use set
        if (ids.isEmpty()) {
            return listOf()
        }
        val dataType = ids.first().dataType
        if (ids.any { id -> id.dataType != dataType }) {
            throw GeneratedException("All ids should be of same DataType")
        }
        return getDataListByDataType(dataType).dataUnits.filter { dataUnit -> ids.contains(dataUnit.id) }.toList()
    }

    override fun getDataListByDataType(dataType: DataType): DataList {
        return dataStore.getDataListByDataType(dataType)
    }

    override fun mergeDataUnit(newDataUnit: DataUnit): Boolean {
        val dataList = dataStore.getDataListByDataType(newDataUnit.dataType)
        when (newDataUnit.dataType) {
            DataType.Transaction -> mergeTransactions(dataList, newDataUnit)
            else -> throw GeneratedException("")
        }
        return dataStore.updateDataList(dataList)
    }

    private fun mergeTransactions(dataList: DataList, newDataUnit: DataUnit) {
        dataList as Transactions
        dataList.dataUnits.removeIf { dataUnit -> dataUnit.id == newDataUnit.id }
        dataList.dataUnits.add(newDataUnit as Transaction)
    }
}