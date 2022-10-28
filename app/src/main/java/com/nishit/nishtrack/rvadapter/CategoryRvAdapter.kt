package com.nishit.nishtrack.rvadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nishit.nishtrack.R
import com.nishit.nishtrack.data.DataHandler
import com.nishit.nishtrack.data.impl.LocalDataHandler
import com.nishit.nishtrack.dtos.DataId
import com.nishit.nishtrack.dtos.impl.Category
import com.nishit.nishtrack.dtos.impl.Chapter
import com.nishit.nishtrack.model.exceptions.GeneratedException
import kotlinx.android.synthetic.main.update_field_item.view.*

class CategoryRvAdapter(
    private val chapterId: DataId
) : RecyclerView.Adapter<CategoryRvAdapter.CategoryViewHolder>() {
    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val dataHandler: DataHandler = LocalDataHandler
    private var categories: List<Category>

    init {
        categories = getCategoriesForDataId()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.update_field_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        if (position == itemCount - 1) {
            val label = "Add More"
            holder.itemView.apply {
                updateFieldItemText.text = label
                updateFieldItemUnderline.alpha = 0F
                updateFieldItemText.alpha = 0.2F
            }
        } else {
            val category = categories[position]
            holder.itemView.apply {
                updateFieldItemText.text = category.label
            }
        }
    }

    override fun getItemCount(): Int {
        return categories.size + 1
    }

    fun updateCategories() {
        categories = getCategoriesForDataId()
    }

    private fun getCategoriesForDataId(): List<Category> {
        val chapter = dataHandler.getDataUnitOrNullById(chapterId) ?: return listOf()
        val categoryList = dataHandler.getDataUnitsById((chapter as Chapter).hasCategories)
        if (categoryList.any { it !is Category }) {
            throw GeneratedException("Category list contains data other than category")
        }
        return categoryList.map { it as Category }
    }
}