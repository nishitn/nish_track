package com.nishit.nishtrack

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.nishit.nishtrack.model.enums.DataType
import kotlinx.android.synthetic.main.settings.*

class SettingsFragment : Fragment(R.layout.settings) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickBehaviour(text_item_1, rl_item_1, R.string.accounts, DataType.Account)
        setClickBehaviour(text_item_2, rl_item_2, R.string.chapters, DataType.Chapter)
        setClickBehaviour(text_item_3, rl_item_3, R.string.categories, DataType.Category)
    }

    private fun setClickBehaviour(
        textView: TextView, relativeLayout: RelativeLayout, labelId: Int, dataType: DataType
    ) {
        textView.text = getString(labelId)

        relativeLayout.setOnClickListener {
            val intent = Intent(requireActivity(), DataUnitSettingsActivity::class.java)
            intent.putExtras(DataUnitSettingsActivity.createBundle(dataType))
            startActivity(intent)
        }
    }

    companion object {
        private const val TAG = "SettingsFragment"
    }
}