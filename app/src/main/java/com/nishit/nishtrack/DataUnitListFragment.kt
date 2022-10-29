package com.nishit.nishtrack

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nishit.nishtrack.model.enums.DataType
import com.nishit.nishtrack.model.exceptions.GeneratedException
import com.nishit.nishtrack.rvadapter.DataUnitRvAdapter
import com.nishit.nishtrack.util.BundleUtil
import kotlinx.android.synthetic.main.data_unit_list.*

class DataUnitListFragment : Fragment(R.layout.data_unit_list) {
    private lateinit var dataUnitListRvAdapter: DataUnitRvAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataType = BundleUtil.getDataType(arguments) ?: throw GeneratedException("DataType was not provided")

        dataUnitListRvAdapter = DataUnitRvAdapter(dataType)

        dataUnitListRV.apply {
            adapter = dataUnitListRvAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }

        addMoreLL.setOnClickListener {
            val intent = Intent(it.context, UpdateDataUnitActivity::class.java)
            intent.putExtras(UpdateDataUnitActivity.createBundle(dataType))
            it.context.startActivity(intent)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        dataUnitListRvAdapter.updateDataUnits()
        dataUnitListRvAdapter.notifyDataSetChanged()
        super.onResume()
    }

    companion object {
        fun createBundle(dataType: DataType): Bundle {
            return BundleUtil.getDataTypeBundle(dataType)
        }
    }
}