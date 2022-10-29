package com.nishit.nishtrack.factory

import com.nishit.nishtrack.datamerge.*
import com.nishit.nishtrack.model.enums.DataType
import com.nishit.nishtrack.model.exceptions.GeneratedException

class MergeDataUnitFactory {
    companion object {
        fun getMergeDataUnit(dataType: DataType): MergeDataUnit {
            return when (dataType) {
                DataType.Transaction -> TransactionMergeUnit
                DataType.Category -> CategoryMergeUnit
                DataType.Chapter -> ChapterMergeUnit
                DataType.Account -> AccountMergeUnit
                DataType.User -> throw GeneratedException("")
            }
        }
    }
}