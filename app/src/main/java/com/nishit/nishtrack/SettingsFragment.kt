package com.nishit.nishtrack

import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.settings.*

class SettingsFragment : Fragment(R.layout.settings) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAccountsClickBehaviour()
    }

    private fun setAccountsClickBehaviour() {
        val label = getString(R.string.accounts)
        text_item_1.text = label
    }
    companion object {
        private const val TAG = "SettingsFragment"
    }
}