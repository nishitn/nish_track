package com.nishit.nishtrack

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.nishit.nishtrack.model.enums.DataType
import kotlinx.android.synthetic.main.settings.*

class SettingsFragment : Fragment(R.layout.settings) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAccountsClickBehaviour()
        setChapterClickBehaviour()
        setCategoryClickBehaviour()
    }

    private fun setAccountsClickBehaviour() {
        val label = getString(R.string.accounts)
        text_item_1.text = label

        rl_item_1.setOnClickListener {
            val intent = Intent(requireActivity(), DataUnitSettingsActivity::class.java)
            intent.putExtras(DataUnitSettingsActivity.createBundle(DataType.Account))
            startActivity(intent)
        }
    }

    private fun setChapterClickBehaviour() {
        val label = getString(R.string.chapters)
        text_item_2.text = label

        rl_item_2.setOnClickListener {
            val intent = Intent(requireActivity(), DataUnitSettingsActivity::class.java)
            intent.putExtras(DataUnitSettingsActivity.createBundle(DataType.Chapter))
            startActivity(intent)
        }
    }

    private fun setCategoryClickBehaviour() {
        val label = getString(R.string.categories)
        text_item_3.text = label

        rl_item_3.setOnClickListener {
            val intent = Intent(requireActivity(), DataUnitSettingsActivity::class.java)
            intent.putExtras(DataUnitSettingsActivity.createBundle(DataType.Category))
            startActivity(intent)
        }
    }

    companion object {
        private const val TAG = "SettingsFragment"
    }
}