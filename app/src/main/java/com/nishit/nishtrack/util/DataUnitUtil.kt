package com.nishit.nishtrack.util

import com.nishit.nishtrack.data.DataHandler
import com.nishit.nishtrack.data.impl.LocalDataHandler
import com.nishit.nishtrack.dtos.DataId
import com.nishit.nishtrack.dtos.DataUnit
import com.nishit.nishtrack.model.enums.Separator
import org.apache.commons.lang3.StringUtils

class DataUnitUtil {
    companion object {
        private val dataHandler: DataHandler = LocalDataHandler

        fun getCategoryText(categoryIds: List<DataId>): String {
            val categoryNames =
                categoryIds.map { categoryId -> dataHandler.getDataUnitById(categoryId).label }
            return StringUtils.join(categoryNames, Separator.CategorySeparator.separator)
        }

        fun getChapterText(chapterId: DataId): String {
            return dataHandler.getDataUnitById(chapterId).label
        }

        fun getAccountText(accountId: DataId): String {
            return dataHandler.getDataUnitById(accountId).label
        }

        fun getDataUnitText(dataUnit: DataUnit): String {
            return dataUnit.label
        }
    }
}