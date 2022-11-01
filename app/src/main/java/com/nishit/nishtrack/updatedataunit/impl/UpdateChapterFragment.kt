package com.nishit.nishtrack.updatedataunit.impl

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.nishit.nishtrack.R
import com.nishit.nishtrack.data.DataHandler
import com.nishit.nishtrack.data.impl.LocalDataHandler
import com.nishit.nishtrack.dtos.clearid.DataId
import com.nishit.nishtrack.dtos.dataunit.Category
import com.nishit.nishtrack.dtos.dataunit.Chapter
import com.nishit.nishtrack.helper.DataTransferHelper
import com.nishit.nishtrack.model.enums.DataType
import com.nishit.nishtrack.model.enums.InputType
import com.nishit.nishtrack.model.exceptions.GeneratedException
import com.nishit.nishtrack.rvadapter.CategoryRvAdapter
import com.nishit.nishtrack.updatedataunit.UpdateDataUnitFragment
import com.nishit.nishtrack.util.BundleUtil
import com.nishit.nishtrack.util.DataUnitUtil
import kotlinx.android.synthetic.main.update_chapter.*

class UpdateChapterFragment : UpdateDataUnitFragment(R.layout.update_chapter) {
    private val dataHandler: DataHandler = LocalDataHandler
    private val inputDataMap: MutableMap<InputType, Any?> = mutableMapOf()
    private lateinit var selectedDataId: DataId

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectedDataId = BundleUtil.getDataId(arguments) ?: DataId(DataType.Chapter)

        populateTempDataStore(selectedDataId)
        updateInputFields()

        setSaveBtnBehaviour(selectedDataId)
    }

    private fun populateTempDataStore(selectedDataId: DataId) {
        val dataUnit = dataHandler.getDataUnitOrNullById(selectedDataId)
        if (dataUnit != null) {
            val chapter = dataUnit as Chapter
            inputDataMap[InputType.LABEL] = chapter.label
            inputDataMap[InputType.CATEGORY] = dataHandler.getDataUnitsByIds(chapter.hasCategories)
        }
    }

    private fun updateInputFields() {
        val label = inputDataMap[InputType.LABEL] as String?
        if (label != null) chapterLabelRowET.setText(label)

        val categoryList = getCategoryListFromInputMap()
        val categoryRvAdapter = CategoryRvAdapter(
            categoryList, dataTransferHelper, requireActivity().supportFragmentManager
        )
        chapterCategoryRowRV.apply {
            adapter = categoryRvAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }
    }

    private fun setSaveBtnBehaviour(chapterId: DataId) {
        val btn = updateChapterSaveBtn

        btn.setOnClickListener {
            if (!isInputValid()) {
                Toast.makeText(btn.context, "Please input data saving", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val chapter = createChapter(chapterId)

            dataHandler.mergeDataUnit(chapter)
            requireActivity().finish()
        }
    }

    private fun createChapter(chapterId: DataId): Chapter {
        val label = chapterLabelRowET.text.toString()
        val categories = getCategoryListFromInputMap()
        val categoryIds = categories.map { dataUnit -> dataUnit.id }.toMutableSet()
        return Chapter(chapterId, label, categoryIds)
    }

    private fun isInputValid(): Boolean {
        return inputDataMap[InputType.CATEGORY] != null && chapterLabelRowET.text.isNotBlank()
    }

    private fun getCategoryListFromInputMap(): MutableList<Category> {
        val categories = inputDataMap[InputType.CATEGORY] as List<*>? ?: listOf<Category>()
        return DataUnitUtil.convertToMutableList(categories)
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