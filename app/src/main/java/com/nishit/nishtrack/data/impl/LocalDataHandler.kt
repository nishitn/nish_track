package com.nishit.nishtrack.data.impl

import com.nishit.nishtrack.data.DataHandler
import com.nishit.nishtrack.data.DataStore
import com.nishit.nishtrack.dtos.DataId
import com.nishit.nishtrack.dtos.DataList
import com.nishit.nishtrack.dtos.DataUnit
import com.nishit.nishtrack.model.enums.DataType
import com.nishit.nishtrack.model.exceptions.GeneratedException

object LocalDataHandler : DataHandler {
    private val dataStore: DataStore = LocalDataStore

    override fun getDataUnitById(id: DataId): DataUnit {
        return getDataByDataType(id.dataType).dataUnits.firstOrNull { dataUnit -> dataUnit.id == id }
            ?: throw GeneratedException("No Data Unit of type: ${id.dataType.name} found with ID: $id")
    }

    override fun getDataUnitOrNullById(id: DataId): DataUnit? {
        return getDataByDataType(id.dataType).dataUnits.firstOrNull { dataUnit -> dataUnit.id == id }
    }

    override fun getDataByDataType(dataType: DataType): DataList {
        return dataStore.getDataList(dataType)
    }
}