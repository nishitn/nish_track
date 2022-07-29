package com.nishit.nishtrack.data

import com.nishit.nishtrack.dtos.DataId
import com.nishit.nishtrack.dtos.DataList
import com.nishit.nishtrack.dtos.DataUnit
import com.nishit.nishtrack.dtos.impl.Transaction
import com.nishit.nishtrack.model.enums.DataType

interface DataHandler {
    fun getDataUnitById(id: DataId): DataUnit

    fun getDataUnitOrNullById(id: DataId): DataUnit?

    fun getDataByDataType(dataType: DataType): DataList

    fun addOrUpdateDataList(newDataUnit: DataUnit): Boolean
}