package com.nishit.nishtrack.data

import com.nishit.nishtrack.dtos.DataList
import com.nishit.nishtrack.model.enums.DataType

interface DataStore {
    fun getDataListByDataType(dataType: DataType): DataList

    fun updateDataList(dataList: DataList): Boolean
}