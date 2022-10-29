package com.nishit.nishtrack

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nishit.nishtrack.dtos.dataunit.DataUnit
import com.nishit.nishtrack.helper.DataTransferHelper
import com.nishit.nishtrack.model.enums.InputType
import com.nishit.nishtrack.rvadapter.SelectionDialogRvAdapter
import kotlinx.android.synthetic.main.selection_dialog.*

class SelectionDialogFragment(
    private val inputType: InputType,
    private val dataUnits: List<DataUnit>,
    private val dataTransferHelper: DataTransferHelper
) : DialogFragment(R.layout.selection_dialog) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectionDialogHeadingTv.text = inputType.heading

        selectionDialogItemsRv.apply {
            adapter = SelectionDialogRvAdapter(inputType, dataUnits, dataTransferHelper, this@SelectionDialogFragment)
            layoutManager = LinearLayoutManager(requireActivity())
        }
    }
}