package com.nishit.nishtrack

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
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
import kotlin.concurrent.thread

class AddOrUpdateInputFragment : Fragment(R.layout.add_or_update_transaction) {
    private val dataHandler: DataHandler = LocalDataHandler
    private val tempDataStore = TempDataStore()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selectedDataId = BundleUtil.getSelectedDataIdFromBundle(arguments) ?: DataId(DataType.Transaction)

        Log.i(TAG, "Populating TempDataStore")
        populateTempDataStore(selectedDataId)
        Log.i(TAG, "Updating Input Fields's texts")
        updateInputFields()

        thread {
            Log.i(TAG, "Date Input's behaviour creation started")
            setDateInputBehaviour()
            Log.i(TAG, "Date Input's behaviour creation complete")
        }
        thread {
            Log.i(TAG, "Time Input's behaviour creation started")
            setTimeInputBehaviour()
            Log.i(TAG, "Time Input's behaviour creation complete")
        }
        thread {
            Log.i(TAG, "Category Input's behaviour creation started")
            setCategoryInputBehaviour()
            Log.i(TAG, "Category Input's behaviour creation complete")
        }
        thread {
            Log.i(TAG, "Chapter Input's behaviour creation started")
            setChapterInputBehaviour()
            Log.i(TAG, "Chapter Input's behaviour creation complete")
        }
        thread {
            Log.i(TAG, "Account Input's behaviour creation started")
            setAccountInputBehaviour()
            Log.i(TAG, "Account Input's behaviour creation complete")
        }
        thread {
            Log.i(TAG, "Save Button's behaviour creation started")
            setSaveBtnBehaviour(selectedDataId)
            Log.i(TAG, "Save Button's behaviour creation complete")
        }
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
            Log.i(TAG, "Category Input Btn Clicked")
            if (tempDataStore.chapter == null) {
                Toast.makeText(btn.context, "Select Chapter before Category", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Log.i(TAG, "Loading Categories")
            val allCategories = dataHandler.getDataByDataType(DataType.Category) as Categories
            val availableCategoryIds = tempDataStore.chapter!!.hasCategories
            val availableCategories = allCategories.dataUnits
                .filter { category -> availableCategoryIds.contains(category.id) }

            Log.i(TAG, "Selection Dialog Fragment creation started")
            val selectionDialog = SelectionDialogFragment(InputType.CATEGORY, availableCategories, dataTransferHelper)
            selectionDialog.show(requireActivity().supportFragmentManager, "SelectionDialog")

            Log.i(TAG, "Selection Dialog Fragment creation complete")
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

    private fun setSaveBtnBehaviour(transactionId: DataId) {
        val btn = addOrUpdateTransactionSaveBtn

        btn.setOnClickListener {
            Log.i(TAG, "Save Btn Clicked")
            val note = noteRowET.text.toString()
            val amount = amountRowET.text.toString().toDouble()
            val description = descriptionRowET.text.toString()

            if (!tempDataStore.isValid()) {
                Toast.makeText(btn.context, "Please input data saving", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val transaction = Transaction(
                transactionId,
                LocalDateTime.of(tempDataStore.date, tempDataStore.time),
                tempDataStore.chapter!!.id,
                tempDataStore.account!!.id,
                listOf(tempDataStore.category!!.id),
                note,
                tempDataStore.currency,
                amount,
                description
            )

            dataHandler.addOrUpdateDataList(transaction)
            requireActivity().finish()
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
        private const val TAG: String = "AddOrUpdateInputFragment"

        fun createBundle(selectedDataId: DataId): Bundle {
            return BundleUtil.createSelectedDataIdBundle(selectedDataId)
        }
    }
}