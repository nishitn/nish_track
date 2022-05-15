package com.nishit.nishtrack.data

import com.nishit.nishtrack.dtos.DataId
import com.nishit.nishtrack.dtos.DataList
import com.nishit.nishtrack.dtos.DataUnit
import com.nishit.nishtrack.model.enums.DataType

interface DataHandler {
    fun getDataUnitById(id: DataId, dataType: DataType): DataUnit

    fun getDataByDataType(dataType: DataType): DataList
}