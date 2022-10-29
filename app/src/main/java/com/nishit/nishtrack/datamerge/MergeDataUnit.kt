package com.nishit.nishtrack.datamerge

import com.nishit.nishtrack.dtos.datalist.*
import com.nishit.nishtrack.dtos.dataunit.*

sealed interface MergeDataUnit {
    fun merge(dataList: DataList, newDataUnit: DataUnit)
}

object TransactionMergeUnit : MergeDataUnit {
    override fun merge(dataList: DataList, newDataUnit: DataUnit) {
        dataList as Transactions
        dataList.dataUnits.removeIf { dataUnit -> dataUnit.id == newDataUnit.id }
        dataList.dataUnits.add(newDataUnit as Transaction)
    }
}

object ChapterMergeUnit : MergeDataUnit {
    override fun merge(dataList: DataList, newDataUnit: DataUnit) {
        dataList as Chapters
        dataList.dataUnits.removeIf { dataUnit -> dataUnit.id == newDataUnit.id }
        dataList.dataUnits.add(newDataUnit as Chapter)
    }
}

object CategoryMergeUnit : MergeDataUnit {
    override fun merge(dataList: DataList, newDataUnit: DataUnit) {
        dataList as Categories
        dataList.dataUnits.removeIf { dataUnit -> dataUnit.id == newDataUnit.id }
        dataList.dataUnits.add(newDataUnit as Category)
    }
}

object AccountMergeUnit : MergeDataUnit {
    override fun merge(dataList: DataList, newDataUnit: DataUnit) {
        dataList as Accounts
        dataList.dataUnits.removeIf { dataUnit -> dataUnit.id == newDataUnit.id }
        dataList.dataUnits.add(newDataUnit as Account)
    }
}