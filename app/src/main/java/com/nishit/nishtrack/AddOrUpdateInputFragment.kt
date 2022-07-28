package com.nishit.nishtrack

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.nishit.nishtrack.data.DataHandler
import com.nishit.nishtrack.data.impl.LocalDataHandler
import com.nishit.nishtrack.dtos.DataId
import com.nishit.nishtrack.dtos.DataUnit
import com.nishit.nishtrack.dtos.impl.*
import com.nishit.nishtrack.helper.DataTransferHelper
import com.nishit.nishtrack.model.TempDataStore
import com.nishit.nishtrack.model.enums.DataType
import com.nishit.nishtrack.model.enums.InputType
import com.nishit.nishtrack.model.exceptions.GeneratedException
import com.nishit.nishtrack.util.BundleUtil
import com.nishit.nishtrack.util.DataUnitUtil
import com.nishit.nishtrack.util.DateTimeUtil
import kotlinx.android.synthetic.main.add_or_update_transaction.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class AddOrUpdateInputFragment : Fragment(R.layout.add_or_update_transaction) {
    private val dataHandler: DataHandler = LocalDataHandler
    private val tempDataStore: TempDataStore = TempDataStore()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selectedDataId = BundleUtil.getSelectedDataIdFromBundle(arguments) ?: DataId(DataType.Transaction)

        populateTempDataStore(selectedDataId)
        updateInputFields()

        setDateInputBehaviour()
        setTimeInputBehaviour()
        setCategoryInputBehaviour()
        setChapterInputBehaviour()
        setAccountInputBehaviour()
    }

    private fun populateTempDataStore(selectedDataId: DataId) {
        val dataUnit = dataHandler.getDataUnitOrNullById(selectedDataId)
        if (dataUnit == null) {
            val now = LocalDateTime.now()
            tempDataStore.date = now.toLocalDate()
            tempDataStore.time = now.toLocalTime()
        } else {
            val transaction = dataUnit as Transaction
            tempDataStore.date = transaction.date.toLocalDate()
            tempDataStore.time = transaction.date.toLocalTime()
            tempDataStore.amount = transaction.amount
            tempDataStore.note = transaction.note
            tempDataStore.description = transaction.description
            tempDataStore.chapter = dataHandler.getDataUnitById(transaction.chapter) as Chapter
            tempDataStore.account = dataHandler.getDataUnitById(transaction.account) as Account
            tempDataStore.category = dataHandler.getDataUnitById(transaction.categories[0]) as Category
        }
    }

    private fun setDateInputBehaviour() {
        val btn = dateRowBtn
        val startDate = DateTimeUtil.readDate(btn.text.toString())

        btn.setOnClickListener {
            DatePickerDialog(
                btn.context,
                { _, year, monthOfYear, dayOfMonth ->
                    val date = LocalDate.of(year, monthOfYear + 1, dayOfMonth)
                    dataTransferHelper.transferData(date, InputType.DATE)
                },
                startDate.year, startDate.monthValue - 1, startDate.dayOfMonth
            ).show()
        }
    }

    private fun setTimeInputBehaviour() {
        val btn = timeRowBtn
        val startTime = DateTimeUtil.readTime(btn.text.toString())

        btn.setOnClickListener {
            TimePickerDialog(
                btn.context,
                { _, hour, minute ->
                    val time = LocalTime.of(hour, minute)
                    dataTransferHelper.transferData(time, InputType.TIME)
                },
                startTime.hour, startTime.minute, true
            ).show()
        }
    }

    private fun setCategoryInputBehaviour() {
        val btn = categoryRowBtn

        btn.setOnClickListener {
            if (tempDataStore.chapter == null) {
                Toast.makeText(btn.context, "Select Chapter before Category", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val allCategories = dataHandler.getDataByDataType(DataType.Category) as Categories
            val availableCategoryIds = tempDataStore.chapter!!.hasCategories

            val availableCategories = allCategories.dataUnits
                .filter { category -> availableCategoryIds.contains(category.id) }

            val selectionDialog = SelectionDialogFragment(InputType.CATEGORY, availableCategories, dataTransferHelper)
            selectionDialog.show(requireActivity().supportFragmentManager, "SelectionDialog")
        }
    }

    private fun setChapterInputBehaviour() {
        val btn = chapterRowBtn

        btn.setOnClickListener {
            val chapters = dataHandler.getDataByDataType(DataType.Chapter)
            val selectionDialog = SelectionDialogFragment(InputType.CHAPTER, chapters.dataUnits, dataTransferHelper)
            selectionDialog.show(requireActivity().supportFragmentManager, "SelectionDialog")
        }
    }

    private fun setAccountInputBehaviour() {
        val btn = accountRowBtn

        btn.setOnClickListener {
            val accounts = dataHandler.getDataByDataType(DataType.Account)
            val selectionDialog = SelectionDialogFragment(InputType.ACCOUNT, accounts.dataUnits, dataTransferHelper)
            selectionDialog.show(requireActivity().supportFragmentManager, "SelectionDialog")
        }
    }

    private fun updateInputFields() {
        dateRowBtn.text = DateTimeUtil.formatDate(tempDataStore.date ?: LocalDate.now())
        timeRowBtn.text = DateTimeUtil.formatTime(tempDataStore.time ?: LocalTime.now())

        updateBtnText(chapterRowBtn, InputType.CHAPTER, tempDataStore.chapter)
        updateBtnText(categoryRowBtn, InputType.CATEGORY, tempDataStore.category)
        updateBtnText(accountRowBtn, InputType.ACCOUNT, tempDataStore.account)
    }

    private fun updateBtnText(btn: Button, inputType: InputType, data: DataUnit?) {
        btn.text = when (data) {
            null -> inputType.defaultText
            else -> DataUnitUtil.getDataUnitText(data)
        }
    }

    private val dataTransferHelper = object : DataTransferHelper {
        override fun <T : Any> transferData(data: T, inputType: InputType) {
            when (inputType) {
                InputType.DATE -> {
                    data as LocalDate
                    tempDataStore.date = data
                }
                InputType.TIME -> {
                    data as LocalTime
                    tempDataStore.time = data
                }
                InputType.CATEGORY -> {
                    data as DataId
                    tempDataStore.category = dataHandler.getDataUnitById(data) as Category
                }
                InputType.CHAPTER -> {
                    data as DataId
                    tempDataStore.chapter = dataHandler.getDataUnitById(data) as Chapter
                    tempDataStore.category = null
                }
                InputType.ACCOUNT -> {
                    data as DataId
                    tempDataStore.account = dataHandler.getDataUnitById(data) as Account
                }
                else -> throw GeneratedException("Found Unexpected Input Type: ${inputType.name}")
            }

            updateInputFields()
        }
    }

    companion object {
        fun createBundle(selectedDataId: DataId): Bundle {
            return BundleUtil.createSelectedDataIdBundle(selectedDataId)
        }
    }
}