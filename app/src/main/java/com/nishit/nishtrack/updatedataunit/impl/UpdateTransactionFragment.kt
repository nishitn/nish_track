package com.nishit.nishtrack.updatedataunit.impl

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.nishit.nishtrack.R
import com.nishit.nishtrack.SelectionDialogFragment
import com.nishit.nishtrack.data.DataHandler
import com.nishit.nishtrack.data.impl.LocalDataHandler
import com.nishit.nishtrack.dtos.DataId
import com.nishit.nishtrack.dtos.DataUnit
import com.nishit.nishtrack.dtos.impl.*
import com.nishit.nishtrack.helper.DataTransferHelper
import com.nishit.nishtrack.model.enums.Currency
import com.nishit.nishtrack.model.enums.DataType
import com.nishit.nishtrack.model.enums.InputType
import com.nishit.nishtrack.model.exceptions.GeneratedException
import com.nishit.nishtrack.updatedataunit.UpdateDataUnitFragment
import com.nishit.nishtrack.util.BundleUtil
import com.nishit.nishtrack.util.DataUnitUtil
import com.nishit.nishtrack.util.DateTimeUtil
import kotlinx.android.synthetic.main.update_transaction.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class UpdateTransactionFragment : UpdateDataUnitFragment(R.layout.update_transaction) {
    private val dataHandler: DataHandler = LocalDataHandler
    private val inputDataMap: MutableMap<InputType, Any?> = mutableMapOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selectedDataId = BundleUtil.getDataIdFromBundle(arguments) ?: DataId(DataType.Transaction)

        populateTempDataStore(selectedDataId)
        updateInputFields()

        setDateInputBehaviour()
        setTimeInputBehaviour()

        setCategoryInputBehaviour()
        setChapterInputBehaviour()
        setAccountInputBehaviour()

        setSaveBtnBehaviour(selectedDataId)
    }

    private fun populateTempDataStore(selectedDataId: DataId) {
        val dataUnit = dataHandler.getDataUnitOrNullById(selectedDataId)
        if (dataUnit == null) {
            val now = LocalDateTime.now()
            inputDataMap[InputType.DATE] = now.toLocalDate()
            inputDataMap[InputType.TIME] = now.toLocalTime()
            inputDataMap[InputType.CURRENCY] = Currency.INR
        } else {
            val transaction = dataUnit as Transaction
            inputDataMap[InputType.DATE] = transaction.date.toLocalDate()
            inputDataMap[InputType.TIME] = transaction.date.toLocalTime()
            inputDataMap[InputType.CURRENCY] = transaction.currency
            inputDataMap[InputType.AMOUNT] = transaction.amount
            inputDataMap[InputType.NOTE] = transaction.note
            inputDataMap[InputType.DESCRIPTION] = transaction.description
            inputDataMap[InputType.CHAPTER] = dataHandler.getDataUnitById(transaction.chapter)
            inputDataMap[InputType.ACCOUNT] = dataHandler.getDataUnitById(transaction.account)
            inputDataMap[InputType.CATEGORY] = dataHandler.getDataUnitById(transaction.categories[0])
        }
    }

    private fun setDateInputBehaviour() {
        val btn = dateRowBtn
        val startDate = DateTimeUtil.readDate(btn.text.toString())

        btn.setOnClickListener {
            DatePickerDialog(
                btn.context, { _, year, monthOfYear, dayOfMonth ->
                    val date = LocalDate.of(year, monthOfYear + 1, dayOfMonth)
                    dataTransferHelper.transferData(date, InputType.DATE)
                }, startDate.year, startDate.monthValue - 1, startDate.dayOfMonth
            ).show()
        }
    }

    private fun setTimeInputBehaviour() {
        val btn = timeRowBtn
        val startTime = DateTimeUtil.readTime(btn.text.toString())

        btn.setOnClickListener {
            TimePickerDialog(
                btn.context, { _, hour, minute ->
                    val time = LocalTime.of(hour, minute)
                    dataTransferHelper.transferData(time, InputType.TIME)
                }, startTime.hour, startTime.minute, true
            ).show()
        }
    }

    private fun setCategoryInputBehaviour() {
        val btn = categoryRowBtn

        btn.setOnClickListener {
            Log.i(TAG, "Category Input Btn Clicked")
            if (inputDataMap[InputType.CHAPTER] == null) {
                Toast.makeText(btn.context, "Select Chapter before Category", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Log.i(TAG, "Loading Categories")
            val allCategories = dataHandler.getDataListByDataType(DataType.Category) as Categories
            val selectedChapter = inputDataMap[InputType.CHAPTER]!! as Chapter
            val availableCategoryIds = selectedChapter.hasCategories
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
            val chapters = dataHandler.getDataListByDataType(DataType.Chapter)
            val selectionDialog = SelectionDialogFragment(InputType.CHAPTER, chapters.dataUnits, dataTransferHelper)
            selectionDialog.show(requireActivity().supportFragmentManager, "SelectionDialog")
        }
    }

    private fun setAccountInputBehaviour() {
        val btn = accountRowBtn

        btn.setOnClickListener {
            val accounts = dataHandler.getDataListByDataType(DataType.Account)
            val selectionDialog = SelectionDialogFragment(InputType.ACCOUNT, accounts.dataUnits, dataTransferHelper)
            selectionDialog.show(requireActivity().supportFragmentManager, "SelectionDialog")
        }
    }

    private fun setSaveBtnBehaviour(transactionId: DataId) {
        val btn = updateTransactionSaveBtn

        btn.setOnClickListener {
            Log.i(TAG, "Save Btn Clicked")
            if (!isInputValid()) {
                Toast.makeText(btn.context, "Please input data saving", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val transaction = createTransaction(transactionId)

            dataHandler.mergeDataUnit(transaction)
            requireActivity().finish()
        }
    }

    private fun createTransaction(transactionId: DataId): Transaction {
        val dateTime =
            LocalDateTime.of(inputDataMap[InputType.DATE]!! as LocalDate, inputDataMap[InputType.TIME]!! as LocalTime)
        val chapter = inputDataMap[InputType.CHAPTER]!! as Chapter
        val account = inputDataMap[InputType.ACCOUNT]!! as Account
        val category = inputDataMap[InputType.CATEGORY]!! as Category
        val note = noteRowET.text.toString()
        val amount = amountRowET.text.toString().toDouble()
        val currency = inputDataMap[InputType.CURRENCY]!! as Currency
        val description = descriptionRowET.text.toString()

        return Transaction(
            transactionId, dateTime, chapter.id, account.id, listOf(category.id), note, currency, amount, description
        )
    }

    private fun updateInputFields() {
        dateRowBtn.text = DateTimeUtil.formatDate(inputDataMap[InputType.DATE] as LocalDate? ?: LocalDate.now())
        timeRowBtn.text = DateTimeUtil.formatTime(inputDataMap[InputType.TIME] as LocalTime? ?: LocalTime.now())

        updateBtnText(chapterRowBtn, InputType.CHAPTER, inputDataMap[InputType.CHAPTER] as DataUnit?)
        updateBtnText(categoryRowBtn, InputType.CATEGORY, inputDataMap[InputType.CATEGORY] as DataUnit?)
        updateBtnText(accountRowBtn, InputType.ACCOUNT, inputDataMap[InputType.ACCOUNT] as DataUnit?)

        updateInputFieldText(amountRowET, inputDataMap[InputType.AMOUNT] as Double?)
        updateInputFieldText(noteRowET, inputDataMap[InputType.NOTE] as String?)
        updateInputFieldText(descriptionRowET, inputDataMap[InputType.DESCRIPTION] as String?)
    }

    private fun updateBtnText(btn: Button, inputType: InputType, data: DataUnit?) {
        btn.text = if (data == null) inputType.defaultText else DataUnitUtil.getDataUnitText(data)
    }

    private fun updateInputFieldText(inputField: EditText, data: Any?) {
        if (data != null) inputField.setText(data.toString())
    }

    private fun isInputValid(): Boolean {
        return inputDataMap[InputType.CHAPTER] != null &&
            inputDataMap[InputType.ACCOUNT] != null &&
            inputDataMap[InputType.CATEGORY] != null &&
            inputDataMap[InputType.DATE] != null &&
            inputDataMap[InputType.TIME] != null
    }

    private val dataTransferHelper = object : DataTransferHelper {
        override fun <T : Any> transferData(data: T, inputType: InputType) {
            when (inputType) {
                InputType.DATE, InputType.TIME -> {
                    inputDataMap[inputType] = data
                }
                InputType.CATEGORY, InputType.ACCOUNT -> {
                    data as DataId
                    inputDataMap[inputType] = dataHandler.getDataUnitById(data)
                }
                InputType.CHAPTER -> {
                    data as DataId
                    inputDataMap[inputType] = dataHandler.getDataUnitById(data)
                    inputDataMap[InputType.CATEGORY] = null
                }
                else -> throw GeneratedException("Found Unexpected Input Type: ${inputType.name}")
            }

            updateInputFields()
        }
    }

    companion object {
        private const val TAG: String = "UpdateTransactionInputFragment"
    }
}