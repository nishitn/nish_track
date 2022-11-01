package com.nishit.nishtrack.rvadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.nishit.nishtrack.R
import com.nishit.nishtrack.SelectionDialogFragment
import com.nishit.nishtrack.dtos.dataunit.Category
import com.nishit.nishtrack.helper.DataTransferHelper
import com.nishit.nishtrack.model.enums.InputType
import com.nishit.nishtrack.util.DataUnitUtil
import kotlinx.android.synthetic.main.update_field_item.view.*

class CategoryRvAdapter(
    private val categoryList: List<Category>,
    private val dataTransferHelper: DataTransferHelper,
    private val supportFragmentManager: FragmentManager
) : RecyclerView.Adapter<CategoryRvAdapter.CategoryViewHolder>() {
    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.update_field_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        if (position == itemCount - 1) {
            holder.itemView.apply {
                updateFieldItemText.text = resources.getText(R.string.add_more)
                updateFieldItemUnderline.alpha = 0F
                updateFieldItemText.alpha = 0.2F
                updateFieldItemDeleteIcon.apply {
                    visibility = View.GONE
                    layoutParams = RecyclerView.LayoutParams(0, 0)
                }
            }

            holder.itemView.setOnClickListener {
                val addableDataUnits = DataUnitUtil.getRemainingBaseCategories(categoryList)
                val selectionDialog = SelectionDialogFragment(InputType.CATEGORY, addableDataUnits, dataTransferHelper)
                selectionDialog.show(supportFragmentManager, "SelectionDialog")
            }
        } else {
            val dataUnit = categoryList[position]
            holder.itemView.apply {
                updateFieldItemText.text = dataUnit.label
            }

            holder.itemView.updateFieldItemDeleteIcon.setOnClickListener {
                dataTransferHelper.transferData(dataUnit.id, InputType.CATEGORY)
            }
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size + 1
    }
}