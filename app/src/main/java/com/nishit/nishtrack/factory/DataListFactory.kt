package com.nishit.nishtrack.factory

import com.nishit.nishtrack.dtos.datalist.*
import com.nishit.nishtrack.dtos.dataunit.DataUnit
import com.nishit.nishtrack.model.enums.DataType
import com.nishit.nishtrack.model.exceptions.GeneratedException

class DataListFactory {
    companion object {
        fun getDataList(dataUnits: List<DataUnit>, dataType: DataType): DataList {
            return when (dataType) {
                DataType.Transaction -> Transactions(mutableListOf(dataUnits))
                DataType.Chapter -> Chapters(mutableListOf(dataUnits))
                DataType.Category -> Categories(mutableListOf(dataUnits))
                DataType.Account -> Accounts(mutableListOf(dataUnits))
                DataType.User -> TODO()
            }
        }

        inline fun <reified T : DataUnit> mutableListOf(inputList: List<*>): MutableList<T> {
            if (inputList.any { item -> item !is T }) {
                throw GeneratedException("")
            }
            return inputList.map { dataUnit -> dataUnit as T }.toMutableList()
        }
    }
}