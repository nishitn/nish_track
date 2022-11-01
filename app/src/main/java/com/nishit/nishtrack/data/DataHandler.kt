package com.nishit.nishtrack.data

import com.nishit.nishtrack.dtos.clearid.DataId
import com.nishit.nishtrack.dtos.datalist.DataList
import com.nishit.nishtrack.dtos.dataunit.DataUnit
import com.nishit.nishtrack.model.enums.DataType

interface DataHandler {
    fun getDataUnitById(id: DataId): DataUnit

    fun getDataUnitOrNullById(id: DataId): DataUnit?

    fun getDataUnitsByIds(ids: Collection<DataId>): List<DataUnit>

    fun getDataListByDataType(dataType: DataType): DataList

    fun mergeDataUnit(newDataUnit: DataUnit): Boolean
}