package com.nishit.nishtrack.rvadapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nishit.nishtrack.R
import com.nishit.nishtrack.UpdateDataUnitActivity
import com.nishit.nishtrack.data.DataHandler
import com.nishit.nishtrack.data.impl.LocalDataHandler
import com.nishit.nishtrack.dtos.datalist.DataList
import com.nishit.nishtrack.model.enums.DataType
import kotlinx.android.synthetic.main.data_unit_list_item.view.*

class DataUnitRvAdapter(private val dataType: DataType) : RecyclerView.Adapter<DataUnitRvAdapter.DataUnitViewHolder>() {
    inner class DataUnitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val dataHandler: DataHandler = LocalDataHandler
    private var dataList: DataList

    init {
        dataList = getDataUnitList(dataType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataUnitViewHolder {
        return DataUnitViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.data_unit_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DataUnitViewHolder, position: Int) {
        val dataUnit = dataList.dataUnits[position]
        holder.itemView.apply {
            dataUnitItemText.text = dataUnit.label
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, UpdateDataUnitActivity::class.java)
            intent.putExtras(UpdateDataUnitActivity.createBundle(dataUnit.id))
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataList.dataUnits.size
    }

    fun updateDataUnits() {
        dataList = getDataUnitList(dataType)
    }

    private fun getDataUnitList(dataType: DataType): DataList {
        return dataHandler.getDataListByDataType(dataType)
    }
}