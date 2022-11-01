package com.nishit.nishtrack.updatedataunit.impl

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.nishit.nishtrack.R
import com.nishit.nishtrack.data.DataHandler
import com.nishit.nishtrack.data.impl.LocalDataHandler
import com.nishit.nishtrack.dtos.clearid.DataId
import com.nishit.nishtrack.dtos.dataunit.Account
import com.nishit.nishtrack.model.enums.DataType
import com.nishit.nishtrack.model.enums.InputType
import com.nishit.nishtrack.updatedataunit.UpdateDataUnitFragment
import com.nishit.nishtrack.util.BundleUtil
import kotlinx.android.synthetic.main.update_account.*

class UpdateAccountFragment : UpdateDataUnitFragment(R.layout.update_account) {
    private val dataHandler: DataHandler = LocalDataHandler
    private val inputDataMap: MutableMap<InputType, Any?> = mutableMapOf()
    private lateinit var selectedDataId: DataId

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectedDataId = BundleUtil.getDataId(arguments) ?: DataId(DataType.Account)

        populateTempDataStore(selectedDataId)
        updateInputFields()

        setSaveBtnBehaviour(selectedDataId)
    }

    private fun populateTempDataStore(selectedDataId: DataId) {
        val dataUnit = dataHandler.getDataUnitOrNullById(selectedDataId)
        if (dataUnit != null) {
            val account = dataUnit as Account
            inputDataMap[InputType.LABEL] = account.label
        }
    }

    private fun updateInputFields() {
        val label = inputDataMap[InputType.LABEL] as String?
        if (label != null) accountLabelRowET.setText(label)
    }

    private fun setSaveBtnBehaviour(accountId: DataId) {
        val btn = updateAccountSaveBtn

        btn.setOnClickListener {
            if (!isInputValid()) {
                Toast.makeText(btn.context, "Please input data saving", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val account = createAccount(accountId)

            dataHandler.mergeDataUnit(account)
            requireActivity().finish()
        }
    }

    private fun createAccount(accountId: DataId): Account {
        val label = accountLabelRowET.text.toString()
        return Account(accountId, label)
    }

    private fun isInputValid(): Boolean {
        return accountLabelRowET.text.isNotBlank()
    }
}