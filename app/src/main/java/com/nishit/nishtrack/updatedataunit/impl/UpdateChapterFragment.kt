package com.nishit.nishtrack.updatedataunit.impl

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.nishit.nishtrack.R
import com.nishit.nishtrack.data.DataHandler
import com.nishit.nishtrack.data.impl.LocalDataHandler
import com.nishit.nishtrack.dtos.DataId
import com.nishit.nishtrack.dtos.impl.Category
import com.nishit.nishtrack.dtos.impl.Chapter
import com.nishit.nishtrack.model.enums.DataType
import com.nishit.nishtrack.model.enums.InputType
import com.nishit.nishtrack.rvadapter.CategoryRvAdapter
import com.nishit.nishtrack.updatedataunit.UpdateDataUnitFragment
import com.nishit.nishtrack.util.BundleUtil
import kotlinx.android.synthetic.main.update_chapter.*

class UpdateChapterFragment : UpdateDataUnitFragment(R.layout.update_chapter) {
    private val dataHandler: DataHandler = LocalDataHandler
    private val inputDataMap: MutableMap<InputType, Any?> = mutableMapOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selectedDataId = BundleUtil.getDataIdFromBundle(arguments) ?: DataId(DataType.Chapter)

        populateTempDataStore(selectedDataId)
        updateInputFields(selectedDataId)

        setCategoryInputBehaviour()

        setSaveBtnBehaviour(selectedDataId)
    }

    private fun populateTempDataStore(selectedDataId: DataId) {
        val dataUnit = dataHandler.getDataUnitOrNullById(selectedDataId)
        if (dataUnit != null) {
            val chapter = dataUnit as Chapter
            inputDataMap[InputType.LABEL] = chapter.label
            inputDataMap[InputType.CATEGORY] = dataHandler.getDataUnitsById(chapter.hasCategories)
        }
    }

    private fun setCategoryInputBehaviour() {
        return
    }

    private fun updateInputFields(selectedDataId: DataId) {
        val label = inputDataMap[InputType.LABEL] as String?
        if (label != null) chapterLabelRowET.setText(label)

        val categoryRvAdapter = CategoryRvAdapter(selectedDataId)
        chapterCategoryRowRV.apply {
            adapter = categoryRvAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }
    }

    private fun setSaveBtnBehaviour(transactionId: DataId) {
        val btn = updateChapterSaveBtn


        btn.setOnClickListener {
            if (!isInputValid()) {
                Toast.makeText(btn.context, "Please input data saving", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val transaction = createChapter(transactionId)

            dataHandler.mergeDataUnit(transaction)
            requireActivity().finish()
        }
    }

    private fun createChapter(chapterId: DataId): Chapter {
        val label = chapterLabelRowET.text.toString()
        val category = inputDataMap[InputType.CATEGORY]!! as Category

        return Chapter(chapterId, label, mutableListOf(category.id))
    }

    private fun isInputValid(): Boolean {
        return inputDataMap[InputType.LABEL] != null && inputDataMap[InputType.CATEGORY] != null
    }
}