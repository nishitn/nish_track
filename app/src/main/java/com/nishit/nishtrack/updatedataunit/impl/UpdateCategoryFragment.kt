package com.nishit.nishtrack.updatedataunit.impl

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.nishit.nishtrack.R
import com.nishit.nishtrack.data.DataHandler
import com.nishit.nishtrack.data.impl.LocalDataHandler
import com.nishit.nishtrack.dtos.DataId
import com.nishit.nishtrack.dtos.dataunit.Category
import com.nishit.nishtrack.factory.DataListFactory
import com.nishit.nishtrack.helper.DataTransferHelper
import com.nishit.nishtrack.model.enums.DataType
import com.nishit.nishtrack.model.enums.InputType
import com.nishit.nishtrack.model.exceptions.GeneratedException
import com.nishit.nishtrack.rvadapter.CategoryRvAdapter
import com.nishit.nishtrack.updatedataunit.UpdateDataUnitFragment
import com.nishit.nishtrack.util.BundleUtil
import kotlinx.android.synthetic.main.update_category.*

class UpdateCategoryFragment : UpdateDataUnitFragment(R.layout.update_category) {
    private val dataHandler: DataHandler = LocalDataHandler
    private val inputDataMap: MutableMap<InputType, Any?> = mutableMapOf()
    private lateinit var selectedDataId: DataId

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectedDataId = BundleUtil.getDataId(arguments) ?: DataId(DataType.Category)

        populateTempDataStore(selectedDataId)
        updateInputFields()

        setSaveBtnBehaviour(selectedDataId)
    }

    private fun populateTempDataStore(selectedDataId: DataId) {
        val dataUnit = dataHandler.getDataUnitOrNullById(selectedDataId)
        if (dataUnit != null) {
            val category = dataUnit as Category
            inputDataMap[InputType.LABEL] = category.label
            inputDataMap[InputType.CATEGORY] = dataHandler.getDataUnitsByIds(category.hasCategories)
        }
    }

    private fun updateInputFields() {
        val label = inputDataMap[InputType.LABEL] as String?
        if (label != null) categoryLabelRowET.setText(label)

        val categoryList = getCategoryListFromInputMap()
        val categoryRvAdapter = CategoryRvAdapter(
            categoryList, dataTransferHelper, requireActivity().supportFragmentManager
        )
        categorySubCategoryRowRV.apply {
            adapter = categoryRvAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }
    }

    private fun setSaveBtnBehaviour(chapterId: DataId) {
        val btn = updateCategorySaveBtn

        btn.setOnClickListener {
            if (!isInputValid()) {
                Toast.makeText(btn.context, "Please input data saving", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val chapter = createCategory(chapterId)

            dataHandler.mergeDataUnit(chapter)
            requireActivity().finish()
        }
    }

    private fun createCategory(categoryId: DataId): Category {
        val label = categoryLabelRowET.text.toString()
        val categories = getCategoryListFromInputMap()
        val categoryIds = categories.map { dataUnit -> dataUnit.id }.toMutableSet()
        return Category(categoryId, label, categoryIds)
    }

    private fun isInputValid(): Boolean {
        return inputDataMap[InputType.CATEGORY] != null && categoryLabelRowET.text.isNotBlank()
    }

    private fun getCategoryListFromInputMap(): MutableList<Category> {
        val categories = inputDataMap[InputType.CATEGORY] as List<*>? ?: listOf<Category>()
        return DataListFactory.mutableListOf(categories)
    }

    private val dataTransferHelper = object : DataTransferHelper {
        override fun <T : Any> transferData(data: T, inputType: InputType) {
            when (inputType) {
                InputType.CATEGORY -> {
                    data as DataId
                    val categories = getCategoryListFromInputMap()
                    if (categories.any { category -> category.id == data }) {
                        categories.removeIf { category -> category.id == data }
                    } else {
                        val category = dataHandler.getDataUnitById(data) as Category
                        categories.add(category)
                    }
                    inputDataMap[InputType.CATEGORY] = categories
                }
                else -> throw GeneratedException("Found Unexpected Input Type: ${inputType.name}")
            }

            updateInputFields()
        }
    }
}