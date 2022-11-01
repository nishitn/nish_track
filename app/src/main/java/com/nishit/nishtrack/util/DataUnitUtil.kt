package com.nishit.nishtrack.util

import com.nishit.nishtrack.data.DataHandler
import com.nishit.nishtrack.data.impl.LocalDataHandler
import com.nishit.nishtrack.dtos.DataId
import com.nishit.nishtrack.dtos.datalist.Categories
import com.nishit.nishtrack.dtos.dataunit.Category
import com.nishit.nishtrack.dtos.dataunit.Chapter
import com.nishit.nishtrack.dtos.dataunit.DataUnit
import com.nishit.nishtrack.model.enums.DataType
import com.nishit.nishtrack.model.enums.Separator
import com.nishit.nishtrack.model.exceptions.GeneratedException
import org.apache.commons.lang3.StringUtils

class DataUnitUtil {
    companion object {
        private val dataHandler: DataHandler = LocalDataHandler

        private fun getCategoryText(categoryId: DataId): String {
            val allCategories = dataHandler.getDataListByDataType(DataType.Category) as Categories
            val categoryNames = mutableListOf<String>()
            var currentId: DataId? = categoryId
            while (currentId != null) {
                val currentUnit = allCategories.dataUnits.firstOrNull { it.id == currentId }
                    ?: throw GeneratedException("")
                categoryNames.add(0, currentUnit.label)
                currentId = currentUnit.parentCategory
            }
            return StringUtils.join(categoryNames, Separator.CategorySeparator.separator)
        }

        fun getDataUnitText(dataUnit: DataUnit): String {
            return when (dataUnit.id.dataType) {
                DataType.Category -> getCategoryText(dataUnit.id)
                DataType.Account, DataType.Chapter, DataType.Transaction, DataType.User -> dataUnit.label
            }
        }

        fun getDataUnitText(dataId: DataId): String {
            return when (dataId.dataType) {
                DataType.Category -> getCategoryText(dataId)
                DataType.Account, DataType.Chapter, DataType.Transaction, DataType.User -> dataHandler.getDataUnitById(
                    dataId
                ).label
            }
        }

        fun getCategoriesForChapter(chapter: Chapter): List<DataUnit> {
            val availableCategoryIds = chapter.hasCategories
            return dataHandler.getDataUnitsByIds(availableCategoryIds)
        }

        fun getRemainingBaseCategories(categoryList: List<Category>): List<DataUnit> {
            val allCategories = dataHandler.getDataListByDataType(DataType.Category) as Categories
            val currentCategoryIds = categoryList.map { category -> category.id }.toSet()
            val addableDataUnits = allCategories.dataUnits.filter { it.id !in currentCategoryIds }

            return addableDataUnits.filter { category -> isBaseCategory(category) }
        }

        fun getRemainingSubCategories(categoryList: List<Category>): List<DataUnit> {
            val allCategories = dataHandler.getDataListByDataType(DataType.Category) as Categories
            val currentCategoryIds = categoryList.map { category -> category.id }.toSet()
            val addableDataUnits = allCategories.dataUnits.filter { it.id !in currentCategoryIds }

            return addableDataUnits.filter { category -> !isBaseCategory(category) }
        }

        private fun isBaseCategory(category: Category) = category.parentCategory == null
    }
}