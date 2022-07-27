package com.nishit.nishtrack

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.nishit.nishtrack.data.DataHandler
import com.nishit.nishtrack.data.impl.LocalDataHandler
import com.nishit.nishtrack.dtos.DataId
import com.nishit.nishtrack.dtos.impl.Transaction
import com.nishit.nishtrack.model.enums.DataType
import com.nishit.nishtrack.util.BundleUtil
import com.nishit.nishtrack.util.DataUnitUtil
import com.nishit.nishtrack.util.DateTimeUtil
import kotlinx.android.synthetic.main.add_or_update_transaction.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class AddOrUpdateInputFragment : Fragment(R.layout.add_or_update_transaction) {
    private val dataHandler: DataHandler = LocalDataHandler

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selectedDataId = BundleUtil.getSelectedDataIdFromBundle(arguments) ?: DataId(DataType.Transaction)

        populateForTransactionId(selectedDataId)
        setDateInputBehaviour()
        setTimeInputBehaviour()
    }

    private fun populateForTransactionId(selectedDataId: DataId) {
        val dataUnit = dataHandler.getDataUnitOrNullById(selectedDataId, DataType.Transaction)
        if (dataUnit == null) {
            val now = LocalDateTime.now()
            dateRowBtn.text = DateTimeUtil.formatDate(now.toLocalDate())
            timeRowBtn.text = DateTimeUtil.formatTime(now.toLocalTime())
        } else {
            val transaction = dataUnit as Transaction
            dateRowBtn.text = DateTimeUtil.formatDate(transaction.date.toLocalDate())
            timeRowBtn.text = DateTimeUtil.formatTime(transaction.date.toLocalTime())
            amountRowET.setText(transaction.amount.toString())
            noteRowET.setText(transaction.note)
            chapterRowET.setText(DataUnitUtil.getChapterText(transaction.chapter))
            accountRowET.setText(DataUnitUtil.getAccountText(transaction.account))
            categoryRowET.setText(DataUnitUtil.getCategoryText(transaction.categories))
            descriptionRowET.setText(transaction.description)
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
                    btn.text = DateTimeUtil.formatDate(date)
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
                    btn.text = DateTimeUtil.formatTime(time)
                },
                startTime.hour, startTime.minute, true
            ).show()
        }
    }

    companion object {
        fun createBundle(selectedDataId: DataId): Bundle {
            return BundleUtil.createSelectedDataIdBundle(selectedDataId)
        }
    }
}