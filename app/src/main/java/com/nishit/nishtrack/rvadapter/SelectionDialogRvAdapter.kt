package com.nishit.nishtrack.rvadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.nishit.nishtrack.R
import com.nishit.nishtrack.dtos.DataUnit
import com.nishit.nishtrack.helper.DataTransferHelper
import com.nishit.nishtrack.model.enums.InputType
import kotlinx.android.synthetic.main.selection_dialog_item.view.*

class SelectionDialogRvAdapter(
    private val inputType: InputType,
    private val dataUnits: List<DataUnit>,
    private val dataTransferHelper: DataTransferHelper,
    private val parentDialogFragment: DialogFragment
) : RecyclerView.Adapter<SelectionDialogRvAdapter.SelectionDialogViewHolder>() {
    inner class SelectionDialogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectionDialogViewHolder {
        return SelectionDialogViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.selection_dialog_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SelectionDialogViewHolder, position: Int) {
        val item = dataUnits[position]

        holder.itemView.apply {
            selectoionItemNameTv.text = item.label
        }

        holder.itemView.setOnClickListener {
            dataTransferHelper.transferData(item.id, inputType)
            parentDialogFragment.dismiss()
        }
    }

    override fun getItemCount(): Int {
        return dataUnits.size
    }
}