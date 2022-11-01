package com.nishit.nishtrack.data.impl

import com.nishit.nishtrack.data.DataHandler
import com.nishit.nishtrack.data.DataStore
import com.nishit.nishtrack.dtos.DataId
import com.nishit.nishtrack.dtos.datalist.DataList
import com.nishit.nishtrack.dtos.dataunit.DataUnit
import com.nishit.nishtrack.factory.MergeDataUnitFactory
import com.nishit.nishtrack.model.enums.DataType
import com.nishit.nishtrack.model.exceptions.GeneratedException

object LocalDataHandler : DataHandler {
    private val dataStore: DataStore = LocalDataStore

    override fun getDataUnitById(id: DataId): DataUnit {
        return getDataUnitOrNullById(id)
            ?: throw GeneratedException("No Data Unit of type: ${id.dataType.name} found with ID: $id")
    }

    override fun getDataUnitOrNullById(id: DataId): DataUnit? {
        return getDataListByDataType(id.dataType).dataUnits.firstOrNull { dataUnit -> dataUnit.id == id }
    }

    override fun getDataUnitsByIds(ids: Collection<DataId>): List<DataUnit> {
        // TODO: Update to thr exception if any id is not found, and also use set
        if (ids.isEmpty()) {
            return listOf()
        }
        val dataType = ids.first().dataType
        if (ids.any { id -> id.dataType != dataType }) {
            throw GeneratedException("All ids should be of same DataType")
        }
        return getDataListByDataType(dataType).dataUnits.filter { dataUnit -> dataUnit.id in ids }
    }

    override fun getDataListByDataType(dataType: DataType): DataList {
        return dataStore.getDataListByDataType(dataType)
    }

    override fun mergeDataUnit(newDataUnit: DataUnit): Boolean {
        val dataType = newDataUnit.dataType
        val dataList = dataStore.getDataListByDataType(dataType)
        MergeDataUnitFactory.getMergeDataUnit(dataType).merge(dataList, newDataUnit)
        return dataStore.updateDataList(dataList, dataType)
    }
}